package com.grupo6.realestate.dao;

import com.grupo6.realestate.entity.Admin;
import java.util.List;
import com.grupo6.realestate.entity.User;
import com.grupo6.realestate.util.JpaUtil;
import jakarta.persistence.EntityManager;
import java.util.Optional;

public class UserDao {
    public List<User> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery(
                "SELECT u FROM User u", User.class
            ).getResultList();
        } finally {
            em.close();
        }
    }
    
    public void saveUser(User user) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            
        } catch (Exception e) {
            System.out.println("Failed to add a user");
        } finally {
            em.close();
        }
    }
    
    public boolean existsByEmail(String email) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Long total = em.createQuery(
                "SELECT COUNT(u) FROM User u WHERE u.email = :email", Long.class
            ).setParameter("email", email)
                .getSingleResult();
            return total > 0;
        } finally {
            em.close();
        }
    }
    
    public Optional<User> findByEmail(String email) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return Optional.ofNullable(em.find(User.class, email));
        } finally {
            em.close();
        }
    }
    
    public Optional<User> findById(Long id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return Optional.ofNullable(em.find(User.class, id));
        } finally {
            em.close();
        }
    }
}
