package com.model;


public class ShoppingCart {
    private int cartID;
    private int userID;

    public ShoppingCart() {}

    public ShoppingCart(int cartID, int userID) {
        this.cartID = cartID;
        this.userID = userID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getCartID() {
        return cartID;
    }

    public void setCartID(int cartID) {
        this.cartID = cartID;
    }
}

