package com.grupo6.realestate.dao;

import com.grupo6.realestate.entity.Admin;
import com.grupo6.realestate.util.JpaUtil;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class AdminDao {
    public void ping() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.createNativeQuery("SELECT 1")
                .getSingleResult();
        } finally {
            em.close();
        }
    }
    
    public void saveAdmin(Admin admin) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(admin);
            em.getTransaction().commit();
            
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
    
    public Optional<Admin> findById(Long id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return Optional.ofNullable(em.find(Admin.class, id));
        } finally {
            em.close();
        }
    }
    
    public List<Admin> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery(
                "SELECT a FROM Admin a", Admin.class
            ).getResultList();
        } finally {
            em.close();
        }
    }
}
