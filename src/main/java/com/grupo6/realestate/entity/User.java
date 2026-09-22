package com.grupo6.realestate.entity;

import com.grupo6.realestate.entity.enums.UserType;
import com.grupo6.realestate.exceptions.InvalidDataRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table(name = "users", schema = "real_state_db")
public class User {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 

    @Column(name = "user_name", nullable = false)
    private String userName;
    
    @Column(name = "last_name", nullable = false)
    private String lastName;
    
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    
    @Column(name = "user_type", nullable = false)
    private UserType userType;

    public String getLastName() {return lastName;}

    public String getEmail() {return email;}

    public UserType getUserType() {return userType;}
    
    public Long getId() {return id;}

    public String getUserName() {return userName;}
    
    public void setUserName(String userName) {
        if (userName == null || userName.isBlank()) throw new InvalidDataRequest("The user name is invalid");
        this.userName = userName.trim();
    }
    
    public void setLastName(String lastName) {
        if (lastName == null || lastName.isBlank()) throw new InvalidDataRequest("The user name is invalid");
        this.lastName = lastName.trim();
        
    }
    
    public void setEmail(String email) {
        if (email == null || email.isBlank()) throw new InvalidDataRequest("Invalid data request");
        this.email = email;
    }
    
    public void setUserType(UserType userType) {
        if (userType == null) throw new InvalidDataRequest("Invalid user type");
        this.userType = userType;
    }
    
    @Override
    public String toString() {
        return "User name: " + userName + " LastName: " + lastName + "\nid: " + id
            + "\nuser email: " + email + "\nUser type: " + userType;
    }
    
    public User() {}
    
    public User(
        String userName, 
        String lastName, 
        String email, 
        UserType userType
    ) {
        this.userName = userName;
        this.lastName = lastName;
        this.email = email;
        this.userType = userType;
    }
} 
