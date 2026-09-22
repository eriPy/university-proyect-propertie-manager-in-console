package com.grupo6.realestate.service.operations;

import com.grupo6.realestate.entity.Propertie;
import com.grupo6.realestate.entity.Transaction;

public interface LeaseOperation {
    default void lease() {
        Propertie propertie = new Propertie();
        Transaction trasaction = new Transaction();
        System.out.println("Lease process..,");
        System.out.println("if want to cancel write cancel");
        while (true) {
            
        }
    }
}
