package com.newsletter.model;

public class Subscriber {
    private String name;
    private String email;
    private String interested_with; 
    private String country; 
    public Subscriber() {}

    public Subscriber(String name, String email, String interested_with, String country) {

        this.name = name;
        this.email = email;
        this.interested_with = interested_with;
        this.country = country;
    }

    // Getters  
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getInterestedWith() { return interested_with; }
    public String getCountry() { return country; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setInterestedWith(String interested_with) { this.interested_with = interested_with; }
    public void setCountry(String country) { this.country = country; }

}
