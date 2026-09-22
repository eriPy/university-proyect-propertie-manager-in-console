package com.grupo6.realestate.service.operations;

import com.grupo6.realestate.entity.Propertie;
import com.grupo6.realestate.entity.Transaction;

public interface TransferOperation {
    default void transfer() {
        Propertie propertie = new Propertie();
        Transaction trasaction = new Transaction();
        System.out.println("Transfer process..,");
        System.out.println("if want to cancel write cancel");
        while (true) {
            
        }
    }
}
