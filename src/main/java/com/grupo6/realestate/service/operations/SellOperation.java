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
        // propertie variables
        Admin newManager = new Admin();
        BigDecimal askingPrice = null;
        double area = 0;
        RealStateCategory category = null;
        Department department = null;
        PropertyCondition propertyCondition = null;

        // transaction variables
        User custodian = null;
        BigDecimal transactionAmount = null;

        String[] process = {
            "Manager informacion",
            "Purchase price",
            "Location information",
            "Property informacion",
            "Area information",
            "Status Information",
            "New Price Information",
            "Custodian information"
        };
        for (String step : process) {
            while (true) {
                try {
                    System.out.println(step);
                    switch (step) {
                        case "Manager informacion":
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
                            evaluateData(stringPurchasePrice);
                            if (stringPurchasePrice.isBlank() || Integer.parseInt(stringPurchasePrice) <= 0) {
                                System.out.println("Invalid purchase price");
                                continue;
                            }
                            transactionAmount = new BigDecimal(stringPurchasePrice);
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
                        case "Property informacion":
                            System.out.println("Enter the propertie type");
                            Stream.of(RealStateCategory.values()).forEach(System.out::println);
                            String stringPropertieType = scn.nextLine().toUpperCase();
                            if (stringPropertieType.isBlank()) {
                                System.out.println("Enter a propertie type");
                                continue;
                            }
                            evaluateData(stringPropertieType);
                            category = RealStateCategory.valueOf(stringPropertieType);
                            break;
                        case "Area information":
                            System.out.println("Enter the propertie area");
                            String stringArea = scn.nextLine();
                            if (stringArea.isBlank()) {
                                System.out.println("Enter a area");
                                continue;
                            }
                            evaluateData(stringArea);
                            if (Integer.parseInt(stringArea) <= 0) {
                                System.out.println("Invalid area");
                                continue;
                            }
                            area = Double.parseDouble(stringArea);
                            break;
                        case "Status Information":
                            System.out.println("Enter the propertie status");
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
                        case "New Price Information":
                            System.out.println("Enter the new propertie price");
                            String newPrice = scn.nextLine();
                            if (newPrice.isBlank()) {
                                System.out.println("Enter a price");
                                continue;
                            }
                            evaluateData(newPrice);
                            askingPrice = new BigDecimal(newPrice);
                            break;
                        case "Custodian information":
                            System.out.println("Enter the user email");
                            String userEmail = scn.nextLine();
                            if (userEmail.isBlank()) {
                                System.out.println("Enter a email");
                                continue;
                            }
                            evaluateData(userEmail);
                            if (!userEmail.substring(userEmail.length() - 10).equals("@email.com")) {
                                System.out.println("Invalid email");
                                continue;
                            }
                            custodian = userDao.findByEmail(userEmail)
                                    .orElseThrow(() -> new InvalidDataRequest("User not found"));
                            break;
                        default:
                            throw new ServiceException("Something went wrong to buy");
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.err.println("Failed to read the value");
                } catch (TransactionException e) {
                    System.err.println("Failed to request data: " + e.getMessage());
                } catch (StringIndexOutOfBoundsException e) {
                    System.err.println("Invalid request data: " + e.getMessage());
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
        String[] process = {"Propertie Information", "Buyer's Information", "Transaction amount"};
        for (String step : process) {
            System.out.println(step);
            while (true) {
                try {
                    switch (step) {
                        case "Propertie Information":
                            System.out.println("Enter the property id");
                            String dataId = scn.nextLine();
                            if (dataId.isBlank()) {
                                System.out.println("Enter a property d");
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
                            if (!buyer.substring(buyer.length() - 10).equals("@email.com")) {
                                System.out.println("Invalid email");
                                continue;
                            }
                            user = userDao.findByEmail(buyer)
                                .orElseThrow(() -> new InvalidDataRequest("User not found"));
                            break;
                        case "Transaction amount":
                            System.out.println("You want to change the asking price? yes?");
                            String res = scn.nextLine();
                            if (res.isBlank()) {
                            System.out.println("Enter a answer");
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
                                if (Integer.parseInt(stringPrice) <= 0) {
                                    System.out.println("Invalid price");
                                    continue;
                                }
                                property.setAskingPrice(new BigDecimal(stringPrice));
                            }
                            break;
                        default:
                            throw new TransactionException("Something went wrong");
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.err.println("Failed to read the value");
                } catch (TransactionException e) {
                    System.err.println("Failed to request data: " + e.getMessage());
                } catch (StringIndexOutOfBoundsException e) {
                    System.err.println("Invalid to request data: " + e.getMessage());
                } catch (IllegalArgumentException e) {
                    System.err.println("Invalid request data: " + e.getMessage());
                } 
            }
        }
        property.setListingStatus(ListingStatus.SOLD);
        propertieDao.savePropertie(property);
        Transaction transaction = new Transaction(
            property,
            user,
            property.getAskingPrice(),
            MarketTransaction.SALE
        );
        transactionDao.saveTransaction(transaction);
    }

    @Transactional
    default void sale() {
        Scanner scn = new Scanner(System.in);
        System.out.println("Sale process..,");
        System.out.println("if want to cancel write cancel");
        while (true) {
            System.out.println("Is buy or sell?");
            String option = scn.nextLine().toLowerCase();
            if (option.isBlank()) {
                System.out.println("Enter a option");
                continue;
            }
            evaluateData(option);
            if (option.equals("buy")) {
                buy(scn);
                return;
            } else if (option.equals("sell")) {
                sell(scn);
                return;
            }
        }
    }
}
