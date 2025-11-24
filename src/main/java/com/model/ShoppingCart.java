package com.model;


import java.time.LocalDateTime;

public class ShoppingCart {
    private String cartID;
    private String userID;
    private LocalDateTime createdAt;

    public ShoppingCart() {}

    public ShoppingCart(String cartID, String userID, LocalDateTime createdAt) {
        this.cartID = cartID;
        this.userID = userID;
        this.createdAt = createdAt;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getCartID() {
        return cartID;
    }

    public void setCartID(String cartID) {
        this.cartID = cartID;
    }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

