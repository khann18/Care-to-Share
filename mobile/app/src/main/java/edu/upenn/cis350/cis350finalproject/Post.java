package edu.upenn.cis350.cis350finalproject;

import java.io.Serializable;
import java.util.Date;

public class Post implements Serializable {
    private String description;
    private String location;
    private Date pickupTime;
    private User postedBy; //username
    private String contactInfo; //email or phone number

    public Post (String description, String location, Date pickupTime, User postedBy, String contactInfo) {
        this.description = description;
        this.location = location;
        this.pickupTime = pickupTime;
        this.postedBy = postedBy;
        this.contactInfo = contactInfo;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public Date getPickupTime() {
        return pickupTime;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public User getPostedBy() {
        return postedBy;
    }

}