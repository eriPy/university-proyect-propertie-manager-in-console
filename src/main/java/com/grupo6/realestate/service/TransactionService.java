package com.grupo6.realestate.service;

import com.grupo6.realestate.dao.TransactionDao;
import com.grupo6.realestate.entity.enums.MarketTransaction;
import com.grupo6.realestate.exceptions.TransactionException;
import com.grupo6.realestate.service.operations.Evaluate;
import com.grupo6.realestate.service.operations.LeaseOperation;
import com.grupo6.realestate.service.operations.RenovationOperation;
import com.grupo6.realestate.service.operations.RepairOperatoon;
import com.grupo6.realestate.service.operations.SellOperation;
import com.grupo6.realestate.service.operations.TransferOperation;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import com.grupo6.realestate.entity.Transaction;
import java.util.stream.Stream;

public class TransactionService
        implements SellOperation,
        LeaseOperation,
        TransferOperation,
        RenovationOperation,
        RepairOperatoon,
        Evaluate {

    private final Map<MarketTransaction, Runnable> actions = new EnumMap<>(MarketTransaction.class);

    public TransactionService() {
        actions.put(MarketTransaction.SALE, this::sale);
        actions.put(MarketTransaction.LEASE, this::lease);
        actions.put(MarketTransaction.TRANSFER, this::transfer);
        actions.put(MarketTransaction.RENOVATION, this::renovation);
        actions.put(MarketTransaction.REPAIRS, this::repair);
    }

    public void processTransaction(MarketTransaction choose) {
        Runnable action = actions.get(choose);
        if (action == null) {
            throw new TransactionException("Invalid option");
        }
        action.run();
    }

    public void searchTransaction() {
        Scanner scn = new Scanner(System.in);
        TransactionDao transactionDao = new TransactionDao();
        System.out.println("Searching transaction...");
        System.out.println("Write exit if you want to exit");
        while (true) {
            try {
                System.out.println("Enter the transaction type");
                Stream.of(MarketTransaction.values()).forEach(System.out::println);
                String option = scn.nextLine().toUpperCase();
                if (option.isBlank()) {
                    System.out.println("Enter a transaction type");
                }
                evaluateData(option);
                MarketTransaction getEnum = MarketTransaction.valueOf(option);
                List<Transaction> transactions = transactionDao.findByTransactionType(getEnum);
                if (transactions.size() == 0) {
                    System.out.println("This type transactions not found");
                } else {
                    for (Transaction transaction: transactions) {
                        System.out.println("\n" + transaction.toString() + "\n");
                    }
                }
            } catch (IllegalArgumentException e) {
                System.err.println("Invalid data: " + e.getMessage());
            }
        }
    }
}
