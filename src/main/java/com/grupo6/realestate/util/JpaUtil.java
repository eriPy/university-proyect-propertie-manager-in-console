package com.grupo6.realestate.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil {
    public static final EntityManagerFactory emf = Persistence
        .createEntityManagerFactory("realStatePU");
    
    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
