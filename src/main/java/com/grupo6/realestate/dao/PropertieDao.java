package com.grupo6.realestate.dao;

import com.grupo6.realestate.entity.Propertie;
import com.grupo6.realestate.util.JpaUtil;
import jakarta.persistence.EntityManager;

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
}
