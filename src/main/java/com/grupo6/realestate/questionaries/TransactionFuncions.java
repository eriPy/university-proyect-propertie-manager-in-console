package com.grupo6.realestate.questionaries;

import com.grupo6.realestate.entity.enums.MarketTransaction;
import com.grupo6.realestate.exceptions.ServiceException;
import com.grupo6.realestate.service.TransactionService;
import java.util.Scanner;
import java.util.stream.Stream;

public class TransactionFuncions {

    public void questionary() {
        Scanner scn = new Scanner(System.in);
        TransactionService transactionService = new TransactionService();
        System.out.println("Transaction functions interface");
        while (true) {
            try {
                System.out.println("Transaction questionnaire");
                System.out.println(
                    "1. Search transactions by type\n" + 
                    "2. Process a transaction\n" + 
                    "3. hut down Transaction questionnaire"
                );
                int option = Integer.parseInt(scn.nextLine());
                switch (option) {
                    case 1:
                        break;
                    case 2:
                        while (true) {
                            try {
                                System.err.println("Which type of transaction would you like to perfom?");
                                Stream.of(MarketTransaction.values()).forEach(System.out::println);
                                String input = scn.nextLine().toUpperCase();
                                MarketTransaction transactionType = MarketTransaction.valueOf(input);
                                transactionService.processTransaction(transactionType);
                                break;
                            } catch (IllegalArgumentException e) {
                                System.out.println("Invalid type of transaction");
                                continue;
                            } catch (Exception e) {
                                System.out.println("Something went wrong: " + e.getMessage());
                            }
                        }
                        break;
                    case 3:
                        return;
                    default:
                        System.out.println("Invalid option");
                        continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid option");
            }
        }
    }
}
