package com.grupo6.realestate.service;

import com.grupo6.realestate.entity.enums.MarketTransaction;
import com.grupo6.realestate.exceptions.TransactionException;
import com.grupo6.realestate.service.operations.LeaseOperation;
import com.grupo6.realestate.service.operations.RenovationOperation;
import com.grupo6.realestate.service.operations.RepairOperatoon;
import com.grupo6.realestate.service.operations.SellOperation;
import com.grupo6.realestate.service.operations.TransferOperation;
import java.util.EnumMap;
import java.util.Map;

public class TransactionService
    implements SellOperation,
        LeaseOperation,
        TransferOperation,
        RenovationOperation,
        RepairOperatoon
{
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
    }
}
