package com.grupo6.realestate.service.operations;

import com.grupo6.realestate.exceptions.TransactionException;

public interface Evaluate {
    default void evaluateData(String data) {
        System.out.println("Leave the trasaction process");
        throw new TransactionException("The transaction was cancel");
    }
}
