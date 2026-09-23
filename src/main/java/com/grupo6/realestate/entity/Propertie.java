package com.grupo6.realestate.entity;

import java.math.BigDecimal;
import com.grupo6.realestate.entity.enums.Department;
import com.grupo6.realestate.entity.enums.ListingStatus;
import com.grupo6.realestate.entity.enums.PropertyCondition;
import com.grupo6.realestate.entity.enums.RealStateCategory;
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

@Entity 
@Table(name = "properties", schema = "real_state_db")
public class Propertie {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne 
    @JoinColumn(name = "propertie_manager", nullable = false)
    private Admin admin;

    @Column(name = "adquisition_cost", nullable = false)
    private BigDecimal acquisitionCost;

    @Column(name = "asking_price", nullable = false)
    private BigDecimal askingPrice;

    @Column(name = "area", nullable = false)
    private double area;

    @Enumerated(EnumType.STRING)
    @Column(name = "category",  nullable = false)
    private RealStateCategory category;

    @Enumerated(EnumType.STRING)
    @Column(name = "department", nullable = false)
    private Department department;

    @Enumerated(EnumType.STRING)
    @Column(name = "listing_status", nullable = false)
    private ListingStatus listingStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "property_condition", nullable = false)
    private PropertyCondition propertyCondition;
    
    public Propertie() {}
    
    public Propertie(
        Admin admin,
        BigDecimal acquisitionCost,
        BigDecimal askingPrice,
        double area,
        RealStateCategory category,
        Department department,
        ListingStatus listingStatus,
        PropertyCondition propertyCondition
    ) {
        this.admin = admin;
        this.acquisitionCost = acquisitionCost;
        this.askingPrice = askingPrice;
        this.area = area;
        this.category = category;
        this.department = department;
        this.listingStatus = listingStatus;
        this.propertyCondition = propertyCondition;
    }
    
    public Long getId() {return id;}

    public Admin getAdmin() {return admin;}

    public BigDecimal getAcquisitionCost() {return acquisitionCost;}

    public BigDecimal getAskingPrice() {return askingPrice;}

    public double getArea() {return area;}

    public RealStateCategory getCategory() {return category;}

    public Department getDepartment() {return department;}

    public ListingStatus getListingStatus() {return listingStatus;}

    public PropertyCondition getPropertyCondition() {return propertyCondition;}
    
    public void setAdmin(Admin admin) {
        if (admin == null) throw new InvalidDataRequest("Admin is invalid");
        this.admin = admin;
    }
   
    public void setAcquisitionCost(BigDecimal acquisitionCost) {
        if (acquisitionCost == null || acquisitionCost.compareTo(BigDecimal.ZERO) <= 0) throw new InvalidDataRequest("Invalid acquisition cost");
        this.acquisitionCost = acquisitionCost;
    }
    
    public void setAskingPrice(BigDecimal askingPrice) {
        if (askingPrice == null || askingPrice.compareTo(BigDecimal.ZERO) <= 0) throw new InvalidDataRequest("Invalid asking price");
        this.askingPrice = askingPrice;
    }

    public void setArea(double area) {
        if (area <= 0) throw new InvalidDataRequest("Invalid area");
        this.area = area;
    }
    
    public void setCategory(RealStateCategory category) {
        if (category == null) throw new InvalidDataRequest("The category is invalid");
        this.category = category;
    }
    
    public void setDepartment(Department department) {
        if (department == null) throw new InvalidDataRequest("The department is invalid");
        this.department = department;
    }
    
    public void setListingStatus(ListingStatus listingStatus) {
        if (listingStatus == null) throw new InvalidDataRequest("The listing status is invalid");
        this.listingStatus = listingStatus;
    }
    
    public void setPropertyCondition(PropertyCondition propertyCondition) {
        if (propertyCondition == null) throw new InvalidDataRequest("The listing status is invalid");
        this.propertyCondition = propertyCondition;
    }
    
    @Override
    public String toString() {
        return "\nProperty id: " + id +
            "\nProperty manager: " + admin.getAdminName() +
            "\nProperty type: " + category +
            "\nProperty location: " + department;
    }
}
