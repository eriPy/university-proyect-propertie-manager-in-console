package com.grupo6.realestate.service;

import com.grupo6.realestate.dao.PropertieDao;
import com.grupo6.realestate.service.operations.Evaluate;
import java.util.Scanner;
import com.grupo6.realestate.entity.Propertie;
import com.grupo6.realestate.entity.enums.Department;
import com.grupo6.realestate.entity.enums.ListingStatus;
import com.grupo6.realestate.exceptions.InvalidDataRequest;
import java.util.stream.Stream;
import com.grupo6.realestate.entity.enums.PropertyCondition;
import com.grupo6.realestate.entity.enums.RealStateCategory;
import java.math.BigDecimal;
import java.util.List;

public class PropertieService implements Evaluate {
    public void searchPropertie() {
        Scanner scn = new Scanner(System.in);
        PropertieDao propertieDao = new PropertieDao();
        Propertie propertie = null;
        while (true) {
            System.out.println("Searching a property\nSeach by id or search by preferences (preferences)");
            String option = scn.nextLine();
            if (option.isBlank()) {
                System.out.println("Invalid option");
                continue;
            }
            if (option.equalsIgnoreCase("exit") || option.equalsIgnoreCase("cancel")) {
                return;
            }
            if (option.equalsIgnoreCase("preferences")) {
                break;
            }
            try {
                propertie = propertieDao.findById(Long.valueOf(option))
                        .orElseThrow(() -> new InvalidDataRequest("Property not found"));
                System.out.println(propertie.toString());
                return;
            } catch (Exception e) {
                System.out.println("Invaid data request: " + e.getMessage());
            }
        }
        BigDecimal cost = null;
        Boolean minCost = null;
        BigDecimal price = null;
        Boolean minPrice = null;
        Double area = null;
        Boolean minArea = null;
        RealStateCategory category = null;
        Department department = null;
        ListingStatus status = null;
        PropertyCondition condition = null;
        String[] preferences = {"cost", "price", "area", "category", "department", "status", "condition"};
        System.out.println("If you don't want to check something, write skip");
        for (String preference : preferences) {
            skipPreferences:
            while (true) {
                try {
                    System.out.println("Search by " + preference);
                    switch (preference) {
                        case "cost", "price", "area":
                            System.out.println("Are you looking for a higher or lower price? max/min");
                            String option = scn.nextLine();
                            if (option.isBlank()) {
                                System.out.println("Enter a valid value");
                                continue;
                            }
                            if (option.equals("skip")) break skipPreferences;
                            evaluateData(option);
                            Boolean min = option.equalsIgnoreCase("min");
                            if (!option.equalsIgnoreCase("max") || !option.equalsIgnoreCase("min")) {
                                System.out.println("");
                                continue;
                            }
                            System.out.println("What price are you looking for?");
                            String stringValue = scn.nextLine();
                            if (stringValue.isBlank()) {
                                System.out.println("Enter a valid " + preference);
                                continue;
                            }
                            switch (preference) {
                                case "cost":
                                    cost = new BigDecimal(stringValue);
                                    minCost = min;
                                    break;
                                case "price":
                                    price = new BigDecimal(stringValue);
                                    minPrice = min;
                                    break;
                                case "area":
                                    area = Double.parseDouble(stringValue);
                                    minArea = min;
                                    break;
                            }
                            break;
                        case "category", "department", "status", "condition":
                            System.out.println("What are you looking for?");
                            Stream.of(
                                preference.equals("category") ? RealStateCategory.values() :
                                preference.equals("department") ? Department.values() :
                                preference.equals("status") ? ListingStatus.values() : PropertyCondition.values()
                            ).forEach(System.out::println);
                            String select = scn.nextLine();
                            if (select.isBlank()) {
                                System.out.println("Enter a valid value");
                                continue;
                            }
                            if (select.equals("skip")) break skipPreferences;
                            switch (preference) {
                                case "category" -> category = RealStateCategory.valueOf(select);
                                case "department" -> department = Department.valueOf(select);
                                case "status" -> status = ListingStatus.valueOf(select);
                                case "condition" -> condition = PropertyCondition.valueOf(select);
                            }
                            break;
                    }
                } catch (Exception e) {
                    System.out.println("Invalid data request: " + e.getMessage());
                }
            }
        }
        List<Propertie> properties = propertieDao
                .searchByPreferences(
                        cost,
                        minCost,
                        price,
                        minPrice,
                        area,
                        minArea,
                        category,
                        department,
                        status,
                        condition
                );
        System.out.println("Properties found:");
        if (properties.size() == 0) {
            System.out.println("Properties not found");
        }
        for (Propertie property : properties) {
            System.out.println(propertie.toString());
        }
    }

    public void report() {
        Scanner scn = new Scanner(System.in);
        PropertieDao propertieDao = new PropertieDao();
        System.out.println("Write exit or cancel to exit");
        String[] process = {"Property information", "Report"};
        Propertie propertie = null;
        for (String step : process) {
            try {
                while (true) {
                    switch (step) {
                        case "Property information":
                            System.out.println(step);
                            System.out.println("Id property to report");
                            String stringId = scn.nextLine();
                            if (stringId.isBlank()) {
                                System.out.println("Enter a id");
                                continue;
                            }
                            evaluateData(stringId);
                            propertie = propertieDao.findById(Long.valueOf(stringId))
                                    .orElseThrow(() -> new InvalidDataRequest("Propertie not found"));
                            break;
                        case "Report":
                            System.out.println("Property:");
                            System.out.println(propertie.toString() + "\nCondition: " + propertie.getPropertyCondition());
                            System.out.println("Enter the property condition report");
                            Stream.of(PropertyCondition.values()).forEach(System.out::println);
                            String condition = scn.nextLine();
                            if (condition.isBlank()) {
                                System.out.println("Invalid condition");
                            }
                            evaluateData(condition);
                            propertie.setPropertyCondition(PropertyCondition.valueOf(condition));
                            propertieDao.savePropertie(propertie);
                            System.out.println("The report was made");
                            return;
                        default:
                            throw new AssertionError();
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid data: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid condition: " + e.getMessage());
            }
        }
    }
}
