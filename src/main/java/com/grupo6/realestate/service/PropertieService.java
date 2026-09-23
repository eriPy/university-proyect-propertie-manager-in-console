package com.grupo6.realestate.service;

import com.grupo6.realestate.dao.PropertieDao;
import com.grupo6.realestate.service.operations.Evaluate;
import java.util.Scanner;
import com.grupo6.realestate.entity.Propertie;
import com.grupo6.realestate.exceptions.InvalidDataRequest;
import java.util.stream.Stream;
import com.grupo6.realestate.entity.enums.PropertyCondition;

public class PropertieService implements Evaluate {
    public void searchPropertie() {

    }

    public void report() {
        Scanner scn = new Scanner(System.in);
        PropertieDao propertieDao = new PropertieDao();
        System.out.println("Write exit or cancel to exit");
        String[] process = {"Property information", "Report"};
        Propertie propertie = null;
        for (String step: process) {
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
