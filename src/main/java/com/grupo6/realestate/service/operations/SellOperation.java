package com.grupo6.realestate.service.operations;

import com.grupo6.realestate.dao.AdminDao;
import com.grupo6.realestate.dao.PropertieDao;
import com.grupo6.realestate.dao.TransactionDao;
import com.grupo6.realestate.dao.UserDao;
import com.grupo6.realestate.entity.Admin;
import com.grupo6.realestate.entity.Propertie;
import com.grupo6.realestate.entity.Transaction;
import com.grupo6.realestate.entity.User;
import com.grupo6.realestate.entity.enums.Department;
import com.grupo6.realestate.entity.enums.ListingStatus;
import com.grupo6.realestate.entity.enums.MarketTransaction;
import com.grupo6.realestate.entity.enums.PropertyCondition;
import com.grupo6.realestate.entity.enums.RealStateCategory;
import com.grupo6.realestate.exceptions.InvalidDataRequest;
import com.grupo6.realestate.exceptions.ServiceException;
import com.grupo6.realestate.exceptions.TransactionException;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Scanner;
import java.util.stream.Stream;

public interface SellOperation extends Evaluate {
    default void buy(Scanner scn) {
        UserDao userDao = new UserDao();
        AdminDao adminDao = new AdminDao();
        PropertieDao propertieDao = new PropertieDao();
        TransactionDao transactionDao = new TransactionDao();

        // Property variables
        Admin newManager = null;
        BigDecimal askingPrice = null;
        double area = 0;
        RealStateCategory category = null;
        Department department = null;
        PropertyCondition propertyCondition = null;

        // Transaction variables
        User custodian = null;
        BigDecimal transactionAmount = null;

        String[] process = {
            "Manager information",
            "Purchase price",
            "Location information",
            "Property information",
            "Area information",
            "Status information",
            "New price information",
            "Custodian information"
        };
        for (String step : process) {
            while (true) {
                try {
                    System.out.println(step);
                    switch (step) {
                        case "Manager information":
                            System.out.println("Enter your admin id");
                            String adminId = scn.nextLine();
                            if (adminId.isBlank()) {
                                System.out.println("Invalid id");
                                continue;
                            }
                            evaluateData(adminId);
                            newManager = adminDao.findById(Long.valueOf(adminId))
                                    .orElseThrow(() -> new InvalidDataRequest("User not found"));
                            break;
                        case "Purchase price":
                            System.out.println("Enter the purchase price");
                            String stringPurchasePrice = scn.nextLine();
                            if (stringPurchasePrice.isBlank()) {
                                System.out.println("Invalid purchase price");
                                continue;
                            }
                            evaluateData(stringPurchasePrice);
                            transactionAmount = new BigDecimal(stringPurchasePrice);
                            if (transactionAmount.compareTo(BigDecimal.ZERO) <= 0) {
                                System.out.println("Invalid purchase price");
                                continue;
                            }
                            break;
                        case "Location information":
                            System.out.println("Enter the location");
                            Stream.of(Department.values()).forEach(System.out::println);
                            String stringDepartment = scn.nextLine().toUpperCase();
                            if (stringDepartment.isBlank()) {
                                System.out.println("Enter a location");
                                continue;
                            }
                            evaluateData(stringDepartment);
                            department = Department.valueOf(stringDepartment);
                            break;
                        case "Property information":
                            System.out.println("Enter the property type");
                            Stream.of(RealStateCategory.values()).forEach(System.out::println);
                            String stringPropertieType = scn.nextLine().toUpperCase();
                            if (stringPropertieType.isBlank()) {
                                System.out.println("Enter a property type");
                                continue;
                            }
                            evaluateData(stringPropertieType);
                            category = RealStateCategory.valueOf(stringPropertieType);
                            break;
                        case "Area information":
                            System.out.println("Enter the property area");
                            String stringArea = scn.nextLine();
                            if (stringArea.isBlank()) {
                                System.out.println("Enter an area");
                                continue;
                            }
                            evaluateData(stringArea);
                            area = Double.parseDouble(stringArea);
                            if (area <= 0) {
                                System.out.println("Invalid area");
                                continue;
                            }
                            break;
                        case "Status information":
                            System.out.println("Enter the property status");
                            System.out.println("The condition can be:");
                            Stream.of(PropertyCondition.values()).forEach(System.out::println);
                            String stringStatus = scn.nextLine().toUpperCase();
                            if (stringStatus.isBlank()) {
                                System.out.println("Enter a status");
                                continue;
                            }
                            evaluateData(stringStatus);
                            propertyCondition = PropertyCondition.valueOf(stringStatus);
                            break;
                        case "New price information":
                            System.out.println("Enter the new property price");
                            String newPrice = scn.nextLine();
                            if (newPrice.isBlank()) {
                                System.out.println("Enter a price");
                                continue;
                            }
                            evaluateData(newPrice);
                            askingPrice = new BigDecimal(newPrice);
                            if (askingPrice.compareTo(BigDecimal.ZERO) <= 0) {
                                System.out.println("Invalid price");
                                continue;
                            }
                            break;
                        case "Custodian information":
                            System.out.println("Enter the user email");
                            String userEmail = scn.nextLine();
                            if (userEmail.isBlank()) {
                                System.out.println("Enter an email");
                                continue;
                            }
                            evaluateData(userEmail);
                            if (!userEmail.endsWith("@email.com")) {
                                System.out.println("Invalid email");
                                continue;
                            }
                            custodian = userDao.findByEmail(userEmail)
                                .orElseThrow(() -> new InvalidDataRequest("User not found"));
                            break;
                        default:
                            throw new ServiceException("Something went wrong while buying");
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.err.println("Failed to read the value");
                } catch (IllegalArgumentException e) {
                    System.err.println("Invalid request data: " + e.getMessage());
                }
            }
        }
        Propertie propertie = new Propertie(
            newManager,
            transactionAmount,
            askingPrice,
            area,
            category,
            department,
            ListingStatus.AVAILABLE,
            propertyCondition
        );
        propertieDao.savePropertie(propertie);
        Transaction transaction = new Transaction(
            propertie,
            custodian,
            transactionAmount,
            MarketTransaction.SALE
        );
        transactionDao.saveTransaction(transaction);
        System.out.println("Propertie bought");
    }

    default void sell(Scanner scn) {
        UserDao userDao = new UserDao();
        PropertieDao propertieDao = new PropertieDao();
        TransactionDao transactionDao = new TransactionDao();
        Propertie property = null;
        User user = null;
        String[] process = {
            "Propertie Information",
            "Buyer's Information",
            "Transaction amount"
        };
        for (String step : process) {
            System.out.println(step);
            while (true) {
                try {
                    switch (step) {
                        case "Propertie Information":
                            System.out.println("Enter the property id");
                            String dataId = scn.nextLine();
                            if (dataId.isBlank()) {
                                System.out.println("Enter a property id");
                                continue;
                            }
                            evaluateData(dataId);
                            property = propertieDao.findById(Long.valueOf(dataId))
                                .orElseThrow(() -> new InvalidDataRequest("Property not found"));
                            break;
                        case "Buyer's Information":
                            System.out.println("Enter the buyer email");
                            String buyer = scn.nextLine();
                            if (buyer.isBlank()) {
                                System.out.println("Invalid user email");
                                continue;
                            }
                            evaluateData(buyer);
                            if (!buyer.endsWith("@email.com")) {
                                System.out.println("Invalid email");
                                continue;
                            }
                            user = userDao.findByEmail(buyer)
                                .orElseThrow(() -> new InvalidDataRequest("User not found"));
                            break;
                        case "Transaction amount":
                            System.out.println("Do you want to change the asking price? yes/no");
                            String res = scn.nextLine().toLowerCase();
                            if (res.isBlank()) {
                                System.out.println("Enter an answer");
                                continue;
                            }
                            evaluateData(res);
                            if (res.equals("yes")) {
                                System.out.println("Enter the new price");
                                String stringPrice = scn.nextLine();
                                if (stringPrice.isBlank()) {
                                    System.out.println("Failed to change the price");
                                    continue;
                                }
                                evaluateData(stringPrice);
                                BigDecimal newPrice = new BigDecimal(stringPrice);
                                if (newPrice.compareTo(BigDecimal.ZERO) <= 0) {
                                    System.out.println("Invalid price");
                                    continue;
                                }
                                property.setAskingPrice(newPrice);
                            }
                            break;
                        default:
                            throw new TransactionException("Something went wrong");
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.err.println("Failed to read the value");
                } catch (IllegalArgumentException e) {
                    System.err.println("Invalid request data: " + e.getMessage());
                }
            }
        }
        property.setListingStatus(ListingStatus.SOLD);
        propertieDao.report(property);
        Transaction transaction = new Transaction(
            property,
            user,
            property.getAskingPrice(),
            MarketTransaction.SALE
        );
        transactionDao.saveTransaction(transaction);
        System.out.println("Sell succesfuly");
    }
    
    @Transactional
    default void sale() {
        Scanner scn = new Scanner(System.in);
        System.out.println("Sale process...");
        System.out.println("If you want to cancel, write cancel");
        while (true) {
            System.out.println("Is it buy or sell?");
            String option = scn.nextLine().toLowerCase();
            if (option.isBlank()) {
                System.out.println("Enter an option");
                continue;
            }
            evaluateData(option);
            if (option.equals("buy")) {
                buy(scn);
                return;
            }
            if (option.equals("sell")) {
                sell(scn);
                return;
            }
            System.out.println("Invalid option");
        }
    }
}