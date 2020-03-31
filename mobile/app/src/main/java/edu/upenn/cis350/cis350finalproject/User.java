package edu.upenn.cis350.cis350finalproject;

import java.io.Serializable;

public class User implements Serializable {
    private String firstName;
    private String lastName;
    private String location;
    private String userType; //obtainer, donor, deliverer
    private String username;
    private String password;
    private String phoneNumber; //this is easiest as a String, Please change the DB
    private String email;
    private String organization;
    //I would think we also need a username

    //Constructor
    public User (String firstName, String lastName, String location, String userType,
                 String username, String password,
                 String phoneNumber, String email, String organization) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.location = location;
        this.userType = userType;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.organization = organization;
    }
    //Getters

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLocation() {
        return location;
    }

    public String getUserType() {
        return userType;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getOrganization() {
        return organization;
    }
}

