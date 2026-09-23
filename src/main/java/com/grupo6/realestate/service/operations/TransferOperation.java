package com.grupo6.realestate.service.operations;

import com.grupo6.realestate.dao.PropertieDao;
import com.grupo6.realestate.dao.TransactionDao;
import com.grupo6.realestate.dao.UserDao;
import com.grupo6.realestate.entity.Propertie;
import com.grupo6.realestate.entity.Transaction;
import com.grupo6.realestate.entity.User;
import com.grupo6.realestate.entity.enums.MarketTransaction;
import com.grupo6.realestate.exceptions.InvalidDataRequest;
import com.grupo6.realestate.exceptions.TransactionException;
import java.util.Scanner;

public interface TransferOperation extends Evaluate {

    default void transfer() {

        Scanner scn = new Scanner(System.in);

        UserDao userDao = new UserDao();
        PropertieDao propertieDao = new PropertieDao();
        TransactionDao transactionDao = new TransactionDao();

        Propertie property = null;
        User user = null;

        String[] process = {
            "Propertie Information",
            "Buyer's Information"
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

                            property = propertieDao.findById(
                                    Long.valueOf(dataId)
                            ).orElseThrow(
                                    () -> new InvalidDataRequest(
                                            "Property not found"
                                    )
                            );

                            break;

                        case "Buyer's Information":

                            System.out.println("Enter the buyer email");

                            String buyer = scn.nextLine().trim();

                            if (buyer.isBlank()) {
                                System.out.println(
                                        "Invalid user email"
                                );
                                continue;
                            }

                            evaluateData(buyer);

                            if (!buyer.endsWith("@email.com")) {
                                System.out.println("Invalid email");
                                continue;
                            }

                            user = userDao.findByEmail(buyer)
                                    .orElseThrow(
                                            () -> new InvalidDataRequest(
                                                    "User not found"
                                            )
                                    );

                            break;

                        default:

                            throw new TransactionException(
                                    "Something went wrong"
                            );
                    }

                    break;

                } catch (NumberFormatException e) {

                    System.err.println(
                            "Failed to read the value"
                    );

                } catch (IllegalArgumentException e) {

                    System.err.println(
                            "Invalid request data: " + e.getMessage()
                    );
                }
            }
        }

        Transaction transaction = new Transaction(
                property,
                user,
                MarketTransaction.TRANSFER
        );

        transactionDao.saveTransaction(transaction);
    }
}