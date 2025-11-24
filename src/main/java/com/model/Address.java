package com.model;

public class Address {
    private String aid;
    private String street;
    private String city;
    private String postalCode;
    private String country;
    private boolean isDefaultShipping;
    private String userID;

    public Address() {}

    public Address(String aid, String street, String city,
                   String postalCode,
                   String country, boolean isDefaultShipping,  String userID) {
        this.aid = aid;
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
        this.isDefaultShipping = isDefaultShipping;
        this.userID = userID;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isDefaultShipping() {
        return isDefaultShipping;
    }

    public void setDefaultShipping(boolean defaultShipping) {
        isDefaultShipping = defaultShipping;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
