package com.grupo6.realestate.service.operations;

import com.grupo6.realestate.entity.Propertie;
import com.grupo6.realestate.entity.Transaction;

public interface SellOperation extends Evaluate {
    default void sale() {
        Propertie propertie = new Propertie();
        Transaction trasaction = new Transaction();
        System.out.println("Sale process..,");
        System.out.println("if want to cancel write cancel");
        while (true) {
            System.out.println("");
        }
    }
}
