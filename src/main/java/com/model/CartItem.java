package com.model;

public class CartItem {
    private int ciID;
    private int cartID;
    private int pid;
    private int quantity;

    public CartItem() {}

    public CartItem(int ciID, int cartID, int pid, int quantity) {
        this.ciID = ciID;
        this.cartID = cartID;
        this.pid = pid;
        this.quantity = quantity;
    }

    public int getCartID() {
        return cartID;
    }

    public void setCartID(int cartID) {
        this.cartID = cartID;
    }

    public int getCiID() {
        return ciID;
    }

    public void setCiID(int ciID) {
        this.ciID = ciID;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
