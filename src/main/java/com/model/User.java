package com.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class User implements Serializable {
    private int userID;
    private String email;
    private String lastName;
    private String midName;
    private String firstName;
    private LocalDateTime dateCreated;
    private String password;
    private UserRole role;


    public enum UserRole {
        USER, ADMIN
    }

    public User(int userID, String email, String lastName, String midName, String firstName,
                LocalDateTime dateCreated, String password, UserRole role) {
        this.userID = userID;
        this.email = email;
        this.lastName = lastName;
        this.midName = midName;
        this.firstName = firstName;
        this.dateCreated = dateCreated;
        this.password = password;
        this.role = role;
    }

    public boolean isAdmin() {
        return role == UserRole.ADMIN;
    }

    public UserRole getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    // Helper method to get full name for display
    public String getFullName() {
        StringBuilder name = new StringBuilder();
        if (firstName != null && !firstName.isEmpty()) {
            name.append(firstName);
        }
        if (midName != null && !midName.isEmpty()) {
            if (name.length() > 0) name.append(" ");
            name.append(midName);
        }
        if (lastName != null && !lastName.isEmpty()) {
            if (name.length() > 0) name.append(" ");
            name.append(lastName);
        }
        return name.length() > 0 ? name.toString() : email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUserID() {
        return userID;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMidName() {
        return midName;
    }

    public void setMidName(String midName) {
        this.midName = midName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
