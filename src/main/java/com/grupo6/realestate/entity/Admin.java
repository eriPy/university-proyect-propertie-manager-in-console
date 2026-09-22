package com.grupo6.realestate.entity;

import com.grupo6.realestate.exceptions.InvalidDataRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table(name = "admins", schema = "real_state_db")
public class Admin {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "admin_name", nullable = false)
    private String adminName;

    public Long getId() {return id;}

    public String getAdminName() {return adminName;}
    
    public void setAdminName(String adminName) {
        if (adminName == null || adminName.isBlank()) throw new InvalidDataRequest("The admin name is invalid");
        this.adminName = adminName.trim();
    }
    
    public Admin() {}
    
    public Admin(String adminName) {
        this.adminName = adminName;
    }

    @Override
    public String toString() {
        return "Admin name:" + adminName + "\nid: " + id;
    }
}
