package com.grupo6.realestate.service;

public class TransactionService {
    private final AdminService adminService;
    
    public TransactionService(
        AdminService adminService
    ) {
        this.adminService = adminService;
    }
    
    public void searchTransaction() {
        
    }
    
    public void processTransaction() {
        adminService.verifyAdmin();
    }
}
