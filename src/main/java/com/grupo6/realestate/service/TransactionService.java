package com.grupo6.realestate.service;

import com.grupo6.realestate.entity.Propertie;
import com.grupo6.realestate.entity.Transaction;
import com.grupo6.realestate.entity.enums.MarketTransaction;
import com.grupo6.realestate.exceptions.TransactionException;
import java.util.EnumMap;
import java.util.Map;
import java.util.Scanner;

public class TransactionService {
    private final Scanner scn = new Scanner(System.in);
    private final Map<MarketTransaction, Runnable> actions = new EnumMap<>(MarketTransaction.class);
    
    public TransactionService() {
        actions.put(MarketTransaction.SALE, this::sale);
        actions.put(MarketTransaction.LEASE, this::lease);
        actions.put(MarketTransaction.TRANSFER, this::transfer);
        actions.put(MarketTransaction.RENOVATION, this::renovation);
        actions.put(MarketTransaction.REPAIRS, this::repair);
    }
    
    public void evaluateData(String data) {
        System.out.println("Leave the trasaction process");
        throw new TransactionException("The transaction was cancel");
    }
    
    public void processTransaction(MarketTransaction choose) {
        Runnable action = actions.get(choose);
        if (action == null) {
            throw new TransactionException("Invalid option");
        }
        action.run();
    }    
   
    public void searchTransaction() {
    }
    
    public void sale() {
        Propertie propertie = new Propertie();
        Transaction trasaction = new Transaction();
        System.out.println("Sale process..,");
        System.out.println("if want to cancel write cancel");
        while (true) {
            System.out.println("");
        }
    }
    
    public void lease() {
        Propertie propertie = new Propertie();
        Transaction trasaction = new Transaction();
        System.out.println("Lease process..,");
        System.out.println("if want to cancel write cancel");
        while (true) {
            
        }
    }
    
    public void transfer() {
        Propertie propertie = new Propertie();
        Transaction trasaction = new Transaction();
        System.out.println("Transfer process..,");
        System.out.println("if want to cancel write cancel");
        while (true) {
            
        }
    }
    
    public void renovation() {
        Propertie propertie = new Propertie();
        Transaction trasaction = new Transaction();
        System.out.println("Renovation process..,");
        System.out.println("if want to cancel write cancel");
        while (true) {
            
        }
    }
    
    public void repair() {
        Propertie propertie = new Propertie();
        Transaction trasaction = new Transaction();
        System.out.println("Repair process..,");
        System.out.println("if want to cancel write cancel");
        while (true) {
            
        }
    }
}
