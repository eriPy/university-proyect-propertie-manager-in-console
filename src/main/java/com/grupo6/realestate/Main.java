package com.grupo6.realestate;

import com.grupo6.realestate.dao.AdminDao;
import com.grupo6.realestate.dao.UserDao;
import com.grupo6.realestate.questionaries.AdminFunctions;
import com.grupo6.realestate.questionaries.TransactionFuncions;
import com.grupo6.realestate.service.AdminService;
import com.grupo6.realestate.service.PropertieService;
import com.grupo6.realestate.service.UserService;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Programn is running");
        AdminDao adminDao = new AdminDao();
        UserDao userDao = new UserDao();
        PropertieService propertieService = new PropertieService();
        adminDao.ping();
        System.out.println("Connected to the database");
        AdminFunctions adminFunctions = new AdminFunctions(
            new AdminService(adminDao),
            new UserService(userDao),
            new TransactionFuncions(),
            propertieService
        );
        Scanner scn = new Scanner(System.in);
        while (true) {
            System.out.println("Choose a option");
            System.out.println("""
                1. Admin functions
                2. Search properties
                3. Shut down programn\n
            """);
            try {
                int option = Integer.parseInt(scn.nextLine());
                switch (option) {
                    case 1:
                        adminFunctions.adminQuestionary();
                        break;
                    case 2:
                        propertieService.searchPropertie();
                        break;
                    case 3:
                        System.out.println("Closing programn");
                        return;

                    default:
                        System.out.println("Invalid option");
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid option choose in the program: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Somethin went wrong in a proccess: " + e.getMessage());
            }
        }
    }
}
