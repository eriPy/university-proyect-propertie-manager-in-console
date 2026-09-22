package com.grupo6.realestate.questionaries;

import com.grupo6.realestate.dao.AdminDao;
import com.grupo6.realestate.dao.UserDao;
import com.grupo6.realestate.service.AdminService;
import com.grupo6.realestate.entity.Admin;
import com.grupo6.realestate.service.UserService;
import java.util.Scanner;

public class AdminFunctions {
    private final AdminService adminService;
    
    public AdminFunctions(
        AdminService adminService
    ) {
        this.adminService = adminService;
    }
    
    public void adminQuestionary() {
        UserService userService = new UserService(new UserDao());
        TransactionFuncions transactionFuncions = new TransactionFuncions();
        Scanner scn = new Scanner(System.in);
        System.out.println("Interface admin running");
        System.out.println("Admin verification");
        Admin admin = adminService.verifyAdmin();
        if (admin == null) {
            System.out.println("Invalid admin");
            return;
        } else {
            System.out.println("Verification successful");
        }
        while (true) {
            try {
                System.out.println("This is the admin questionnaire.");
                System.out.println("As a admin, you can choose from the following functions");
                System.out.println(
                    "1. Add an admin\n" +
                    "2. Add a user\n" +
                    "3. View profile\n" +
                    "4. View all admins\n" +
                    "5. View a user\n" +
                    "6. View all users\n" +
                    "7. Process a transaction\n" +
                    "8. Shut down admin questionnaire"
                );
                int option = Integer.parseInt(scn.nextLine());
                switch (option) {
                    case 1:
                        System.out.println("Adding a new Admin");
                        adminService.addNewAdmin(admin);
                        break;
                    case 2:
                        System.out.println("Adding a new User");
                        userService.addUser();
                        break;
                    case 3:
                        System.out.println("Your profile");
                        System.out.println(admin.toString());
                        break;
                    case 4:
                        System.out.println("All admins");
                        adminService.viewAllAdmins();
                        break;
                    case 5:
                        System.out.println("User details");
                        userService.viewUser();
                        break;
                    case 6:
                        System.out.println("All users");
                        userService.viewAllUser();
                        break;
                    case 7:
                        System.out.println("Processing a transaction...");
                        transactionFuncions.questionary();
                        break;
                    case 8:
                        System.out.println("Closing questionnaire");
                        return;
                    default:
                        System.out.println("Invalid option");
                }
            
            } catch (NumberFormatException e) {
                System.err.println("Invalid option");
            } catch (Exception e) {
                System.err.println("Something went wrong in the process: " + e.getMessage());
            }
        }
    }
}
