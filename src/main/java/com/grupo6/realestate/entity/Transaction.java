package com.grupo6.realestate.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import com.grupo6.realestate.entity.enums.MarketTransaction;
import com.grupo6.realestate.exceptions.InvalidDataRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.Check;

@Entity 
@Table(
    name = "transactions", 
    schema = "real_state_db"
)
@Check(constraints = "market_transaction NOT IN ('LEASE', 'RENOVATION', 'REPAIRS') OR transaction_end_date IS NOT NULL")
@Check(constraints = "market_transaction NOT IN ('LEASE', 'RENOVATION', 'REPAIRS', 'SALE') OR transaction_amount IS NOT NULL")
public class Transaction {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   

    @ManyToOne 
    @JoinColumn(name = "property_id", nullable = false)
    private Propertie propertie;

    @ManyToOne 
    @JoinColumn(name = "propertie_custodian", nullable = false)
    private User propertieCustodian;

    @Column(name = "transaction_amount")
    private BigDecimal transactionAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "market_transaction", nullable = false)
    private MarketTransaction marketTransaction;
    
    @CreationTimestamp 
    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;
    
    @Column(name = "transaction_end_date")
    private LocalDateTime transactionEndDate;
    
    public Transaction() {}
    
    public Transaction(
        Propertie propertie,
        User propertieCustodian,
        MarketTransaction marketTransaction
    ) {
        this.propertie = propertie;
        this.propertieCustodian = propertieCustodian;
        this.marketTransaction = marketTransaction;
    }
    
    public Transaction(
        Propertie propertie,
        User propertieCustodian,
        BigDecimal transactionAmount,
        MarketTransaction marketTransaction
    ) {
        this.propertie = propertie;
        this.propertieCustodian = propertieCustodian;
        this.transactionAmount = transactionAmount;
        this.marketTransaction = marketTransaction;
    }
    
    public Transaction(
        Propertie propertie,
        User propertieCustodian,
        BigDecimal transactionAmount,
        MarketTransaction marketTransaction,
        LocalDateTime transactionEndDate
    ) {
        this(propertie, propertieCustodian, transactionAmount, marketTransaction);
        this.transactionEndDate = transactionEndDate;
    }
    
    public Long getId() {return id;}

    public Propertie getPropertie() {return propertie;}

    public User getPropertieCustodian() {return propertieCustodian;}

    public BigDecimal getTransactionAmount() {return transactionAmount;}

    public MarketTransaction getMarketTransaction() {return marketTransaction;}

    public LocalDateTime getTransactionDate() {return transactionDate;}
    
    public LocalDateTime getTransactionEndDate() {return transactionEndDate;}
    
    public void setPropertie(Propertie propertie) {
        if (propertie == null) throw new InvalidDataRequest("The propertie is invalid");
        this.propertie = propertie;
    }
    
    public void setPropertieCustodian(User propertieCustodian) {
        if (propertieCustodian == null) throw new InvalidDataRequest("The user is invalid");
        this.propertieCustodian = propertieCustodian;
    }
    
    public void setTransactionAmout(BigDecimal transactionAmount) {
        if (transactionAmount == null || transactionAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidDataRequest("The transaction amount is invalid");
        }
        this.transactionAmount = transactionAmount;
    }
    
    public void setMarketTransaction(MarketTransaction marketTransaction) {
        if (marketTransaction == null) throw new InvalidDataRequest("The market transaction is invalid");
        this.marketTransaction = marketTransaction;
    }
    
    public void setTransactionEndDateTime(LocalDateTime transactionEndDate) {
        if (transactionEndDate == null) throw new InvalidDataRequest("The transaction end date is invalid");
        this.transactionEndDate = transactionEndDate;
    }
    
    @Override
    public String toString() {
        return "Transaction:\n" +
            "Transaction id: " + id +
            propertie.toString() +
            "\nCustodian: " + propertieCustodian.getEmail() +
            "\nAmount: " + transactionAmount + 
            "\nTransaction date: " + transactionDate.toString();
    }
}
