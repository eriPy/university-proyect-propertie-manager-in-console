package com.grupo6.realestate.service;

import com.grupo6.realestate.dao.UserDao;
import com.grupo6.realestate.entity.User;
import com.grupo6.realestate.entity.enums.UserType;
import com.grupo6.realestate.exceptions.ServiceException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Stream;

public class UserService {
    private final UserDao userDao;
    
    public UserService(
        UserDao userDao
    ) {
        this.userDao = userDao;
    }
    
    public void addUser() {
        Scanner scn = new Scanner(System.in);
        String userName = "";
        String lastName = "";
        String email = "";
        UserType userType = null;
        String[] data = {"name", "last name", "email", "user type"};
        for (String datum: data) {
            while (true) {
                System.out.println(datum);
                switch (datum) {
                    case "name":
                        System.out.println("Enter a user name");
                        userName = scn.nextLine();
                        if (userName.isBlank()) {
                            System.out.println("Invalid user name");
                            continue;
                        }
                        break;
                    case "last name":
                        System.out.println("Enter a last name");
                        lastName = scn.nextLine();
                        if (lastName.isBlank()) {
                            System.out.println("Invalid last name");
                            continue;
                        }
                        break;
                    case "email":
                        System.out.println("Enter a user email");
                        email = scn.nextLine();
                        if (email.isBlank()) {
                            System.out.println("Invalid user email");
                            continue;
                        }
                        try {
                            String emailDomain = email.substring(email.length() - 10);
                            if (!emailDomain.equals("@email.com")) {
                                System.out.println("Invalid email, please try again");
                            }
                        } catch (StringIndexOutOfBoundsException e) {
                            System.out.println("Invalid email, please try again");
                        }
                        if (userDao.existsByEmail(email)) {
                            System.out.println("This email already exists");
                            continue;
                        }
                        break;
                    case "user type":
                        System.out.println("Enter one of the following user type:");
                        Stream.of(UserType.values()).forEach(System.out::println);
                        String input = scn.nextLine().toUpperCase();
                        try {
                            userType = userType.valueOf(input);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid user type");
                        }
                        break;
                    default:
                        throw new ServiceException("Something went wrong in add user function");
                }
                break;
            }
        }
        userDao.saveUser(new User(userName, lastName, email, userType));
        System.out.println("New user: " + userName + " was saved");
    }
    
    public void viewAllUser() {
        System.out.println("Users list");
        List<User> usersList = userDao.findAll();
        for (int i = 0; i < usersList.size(); i++) usersList.get(i).toString();
    }
    
    public void viewUser() {
        try {
            while (true) {
                Scanner scn = new Scanner(System.in);
                User user;
                System.out.println("Search user by email or id?");
                System.out.println("Enter email or id");
                String option = scn.nextLine().toLowerCase();
                switch (option) {
                    case "email":
                        System.out.println("Enter a email: ");
                        String email = scn.nextLine();
                        if (email.isBlank() || !email.substring(email.length() - 11).equals("@email.com")) {
                            System.out.println("Invalid email");
                            continue;
                        }   user = userDao.findByEmail(email)
                                .orElseThrow(() -> new ServiceException("User not found"));
                        break;
                    case "id":
                        try {
                            System.out.println("Enter a id");
                            Long id = Long.parseLong(scn.nextLine());
                            user = userDao.findById(id)
                                    .orElseThrow(() -> new ServiceException("User not found"));
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid id");
                            continue;
                        }   
                        break;
                    default:
                        continue;
                }
            }
        } catch (ServiceException e) {
            System.out.println("User not found");
        }
    }
}
