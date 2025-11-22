package com.model;


public class ShoppingCart {
    private int cartID;
    private String userID;

    public ShoppingCart() {}

    public ShoppingCart(int cartID, String userID) {
        this.cartID = cartID;
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getCartID() {
        return cartID;
    }

    public void setCartID(int cartID) {
        this.cartID = cartID;
    }
}

