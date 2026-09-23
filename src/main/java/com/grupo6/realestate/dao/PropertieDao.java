package com.grupo6.realestate.dao;

import com.grupo6.realestate.entity.Propertie;
import com.grupo6.realestate.entity.enums.Department;
import com.grupo6.realestate.entity.enums.ListingStatus;
import com.grupo6.realestate.entity.enums.PropertyCondition;
import com.grupo6.realestate.entity.enums.RealStateCategory;
import com.grupo6.realestate.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class PropertieDao {
    public void savePropertie(Propertie propertie) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(propertie);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
    
    public Optional<Propertie> findById(Long id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return Optional.ofNullable(em.find(Propertie.class, id));
        } finally {
            em.close();
        }
    }
    
    public List<Propertie> searchByPreferences(
        BigDecimal acquisitionCost,
        Boolean minCost,
        BigDecimal askingPrice,
        Boolean minPrice,
        Double area,
        Boolean minArea,
        RealStateCategory category,
        Department department,
        ListingStatus listingStatus,
        PropertyCondition propertyCondition
    ) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            StringBuilder incompleteQuery = new StringBuilder(
                "SELECT p FROM Propertie p WHERE 1=1"
            );
            if (acquisitionCost != null) {
                incompleteQuery.append(" AND p.acquisitionCost ");
                incompleteQuery.append(minCost ? "<" : ">");
                incompleteQuery.append("= :cost");
            }
            if (askingPrice != null) {
                incompleteQuery.append(" AND p.askingPrice ");
                incompleteQuery.append(minPrice ? "<" : ">");
                incompleteQuery.append("= :price");
            }
            if (area != null) {
                incompleteQuery.append(" AND p.area ");
                incompleteQuery.append(minArea ? "<" : ">");
                incompleteQuery.append("= :area");
            }
            if (category != null) incompleteQuery.append(" AND p.category = :category");
            if (department != null) incompleteQuery.append(" AND p.department = :department");
            if (listingStatus != null) incompleteQuery.append(" AND p.listingStatus = :status");
            if (propertyCondition != null) incompleteQuery.append(" AND p.propertyCondition = :condition");
            TypedQuery<Propertie> query = em.createQuery(incompleteQuery.toString(), Propertie.class);
            if (acquisitionCost != null) query.setParameter("cost", acquisitionCost);
            if (askingPrice != null) query.setParameter("price", askingPrice);
            if (area != null) query.setParameter("area", area);
            if (category != null) query.setParameter("category", category);
            if (department != null) query.setParameter("department", department);
            if (listingStatus != null) query.setParameter("status", listingStatus);
            if (propertyCondition != null) query.setParameter("condition", propertyCondition);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
}
