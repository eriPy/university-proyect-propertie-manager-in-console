package com.grupo6.realestate.dao;

import com.grupo6.realestate.entity.Transaction;
import com.grupo6.realestate.util.JpaUtil;
import jakarta.persistence.EntityManager;

public class TransactionDao {
    public void saveTransaction(Transaction transaction) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(transaction);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
