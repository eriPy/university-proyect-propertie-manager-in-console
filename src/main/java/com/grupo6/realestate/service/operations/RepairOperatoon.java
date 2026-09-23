package com.grupo6.realestate.service.operations;

import com.grupo6.realestate.dao.PropertieDao;
import com.grupo6.realestate.dao.TransactionDao;
import com.grupo6.realestate.dao.UserDao;
import com.grupo6.realestate.entity.Propertie;
import com.grupo6.realestate.entity.Transaction;
import com.grupo6.realestate.entity.User;
import com.grupo6.realestate.entity.enums.ListingStatus;
import com.grupo6.realestate.entity.enums.MarketTransaction;
import com.grupo6.realestate.exceptions.InvalidDataRequest;
import com.grupo6.realestate.exceptions.TransactionException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Scanner;

public interface RepairOperatoon extends Evaluate {
    default void repair() {
        Scanner scn = new Scanner(System.in);
        System.out.println("Repair process...");
        System.out.println("If you want to cancel, write cancel");
        UserDao userDao = new UserDao();
        PropertieDao propertieDao = new PropertieDao();
        TransactionDao transactionDao = new TransactionDao();
        Propertie property = null;
        User user = null;
        BigDecimal cost = null;
        LocalDateTime endDate = null;
        String[] process = {
            "Propertie Information",
            "Custodian",
            "Transaction amount",
            "Final repairs date"
        };
        for (String step : process) {
            System.out.println(step);
            while (true) {
                try {
                    switch (step) {
                        case "Propertie Information":
                            System.out.println("Enter the property id");
                            String dataId = scn.nextLine().trim();
                            if (dataId.isBlank()) {
                                System.out.println("Enter a property id");
                                continue;
                            }
                            evaluateData(dataId);
                            property = propertieDao.findById(Long.valueOf(dataId))
                                .orElseThrow(() -> new InvalidDataRequest("Property not found"));
                            break;
                        case "Custodian":
                            System.out.println("Enter the custodian email");
                            String buyer = scn.nextLine().trim();
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
                            System.out.println("Enter the renewal fee");
                            String res = scn.nextLine().trim();
                            if (res.isBlank()) {
                                System.out.println("Enter an answer");
                                continue;
                            }
                            evaluateData(res);
                            cost = new BigDecimal(res);
                            if (cost.compareTo(BigDecimal.ZERO) <= 0) {
                                System.out.println("Invalid cost");
                                continue;
                            }
                            break;
                        case "Final repairs date":
                            System.out.println("When will it be available? use the format dd/MM/yyyy");
                            String stringDate = scn.nextLine().trim();
                            if (stringDate.isBlank()) {
                                System.out.println("Invalid date");
                                continue;
                            }
                            evaluateData(stringDate);
                            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/uuuu")
                                .withResolverStyle(ResolverStyle.STRICT);
                            LocalDate date = LocalDate.parse(stringDate, format);
                            endDate = date.atStartOfDay();
                            break;
                        default:
                            throw new TransactionException("Something went wrong");
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.err.println("Failed to read the value");
                } catch (StringIndexOutOfBoundsException e) {
                    System.err.println("Invalid request data: " + e.getMessage());
                }
            }
        }
        property.setListingStatus(ListingStatus.UNDER_REPAIR);
        propertieDao.report(property);
        Transaction transaction = new Transaction(
            property,
            user,
            cost,
            MarketTransaction.REPAIRS,
            endDate
        );
        transactionDao.saveTransaction(transaction);
        System.out.println("The transaction was made");
    }
}