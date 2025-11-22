package com.model;


public class ShoppingCart {
    private String cartID;
    private String userID;

    public ShoppingCart() {}

    public ShoppingCart(String cartID, String userID) {
        this.cartID = cartID;
        this.userID = userID;
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
}

