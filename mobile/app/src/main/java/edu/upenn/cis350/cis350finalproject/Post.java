package edu.upenn.cis350.cis350finalproject;

import java.io.Serializable;
import java.util.Date;

public class Post implements Serializable {
    private String description;
    private String location;
    private Date pickupTime;
    private User postedBy; //username
    private String contactInfo; //email or phone number
    private boolean isClaimed;
    private String claimMessage;

    public Post (String description, String location, Date pickupTime, User postedBy,
                 String contactInfo, boolean isClaimed, String claimMessage) {
        this.description = description;
        this.location = location;
        this.pickupTime = pickupTime;
        this.postedBy = postedBy;
        this.contactInfo = contactInfo;
        this.isClaimed = isClaimed;
        this.claimMessage = claimMessage;
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

    public boolean getIsClaimed() {
        return isClaimed;
    }

    public void setIsClaimed() {
        isClaimed = true;
        //Maybe take out of db at this time?
    }

    public String getClaimMessage() {
        return claimMessage;
    }

    public void setClaimMessage(String msg) {
        claimMessage = msg;

    }
}