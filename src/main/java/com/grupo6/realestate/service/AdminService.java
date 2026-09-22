package com.grupo6.realestate.service;

import com.grupo6.realestate.dao.AdminDao;
import com.grupo6.realestate.entity.Admin;
import com.grupo6.realestate.exceptions.ServiceException;
import java.util.List;
import java.util.Scanner;

public class AdminService {
    private final AdminDao adminDao;
    
    public AdminService(
        AdminDao adminDao
    ) {
        this.adminDao = adminDao;
    }
    
    public Admin verifyAdmin() {
        Scanner scn = new Scanner(System.in);
        System.out.println("Enter your admin id");
        try {
            Long id = Long.valueOf(scn.nextLine());
            return adminDao.findById(id)
                    .orElseThrow(() -> new ServiceException("Admin not found"));
            
        } catch (NumberFormatException e) {
            System.out.println("Failed to request the the id: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println("Something went wrong with the program: " + e.getMessage());
            return null;
        }
    }
    
    public void addNewAdmin() {
        Scanner scn = new Scanner(System.in);
        while (true) {
            System.out.println("Enter the new admin's name");
            String adminName = scn.nextLine();
            if (adminName.isBlank()) {
                System.out.println("Invalid admin name");
                continue;
            }
            adminDao.saveAdmin(new Admin(adminName));
            System.out.println(adminName + " was saved as an admin");
            break;
        }
    }
    
    public void viewAllAdmins() {
        System.out.println("Admin List");
        List<Admin> adminsList = adminDao.findAll();
        for (int i = 0; i < adminsList.size(); i++) System.out.println(adminsList.get(i).toString());
    }
    
    
}
