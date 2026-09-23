package com.grupo6.realestate.questionaries;
import com.grupo6.realestate.dao.UserDao;
import com.grupo6.realestate.service.AdminService;
import com.grupo6.realestate.entity.Admin;
import com.grupo6.realestate.service.PropertieService;
import com.grupo6.realestate.service.UserService;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AdminFunctions {
    private final AdminService adminService;
    private final Map<Integer, Runnable> adminActions = new HashMap<>();
    private Admin currentAdmin;
    
    public AdminFunctions(
        AdminService adminService,
        UserService userService,
        TransactionFuncions transactionFuncions,
        PropertieService propertieService
    ) {
        this.adminService = adminService;
        adminActions.put(1, () -> {
            System.out.println("Adding a new Admin");
            adminService.addNewAdmin();
        });
        adminActions.put(2, () -> {
            System.out.println("Adding a new User");
            userService.addUser();
        });
        adminActions.put(3, () -> {
            System.out.println("Your profile");
            System.out.println(currentAdmin.toString());
        });
        adminActions.put(4, () -> {
            System.out.println("All admins");
            adminService.viewAllAdmins();
        });
        adminActions.put(5, () -> {
            System.out.println("User details");
            userService.viewUser();
        });
        adminActions.put(6, () -> {
            System.out.println("All users");
            userService.viewAllUser();
        });
        adminActions.put(7, () -> {
            System.out.println("Processing a transaction...");
            transactionFuncions.questionary();
        });
        adminActions.put(8, () -> {
            System.out.println("Reporting a property");
            propertieService.report();
        });
    }
    
    public void adminQuestionary() {
        Scanner scn = new Scanner(System.in);
        System.out.println("Interface admin running");
        System.out.println("Admin verification");
        currentAdmin = adminService.verifyAdmin();
        if (currentAdmin == null) {
            System.out.println("Invalid admin");
            return;
        } else {
            System.out.println("Verification successful");
        }
        while (true) {
            try {
                System.out.println("This is the admin questionnaire.");
                System.out.println("As a admin, you can choose from the following functions");
                System.out.println("""
                    1. Add an admin
                    2. Add a user
                    3. View profile
                    4. View all admins
                    5. View a user
                    6. View all users
                    7. Process a transaction
                    8. Report property status
                    9. Shut down admin questionnaire
                """);
                int option = Integer.parseInt(scn.nextLine());
                if (option < 9 && option > 0) {
                    Runnable action = adminActions.get(option);
                    action.run();
                } else {
                    if (option == 9) {
                        System.out.println("Closing questionnaire");
                        return;
                    } else {
                        System.out.println("Invalid option");
                    }
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid option");
            } catch (Exception e) {
                System.err.println("Something went wrong in the process: " + e.getMessage());
            }
        }
    }
}
