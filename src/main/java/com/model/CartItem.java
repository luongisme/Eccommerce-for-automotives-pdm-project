package com.model;

public class CartItem {
    private String ciID;
    private String cartID;
    private String pid;
    private int quantity;

    public CartItem() {}

    public CartItem(String ciID, String cartID, String pid, int quantity) {
        this.ciID = ciID;
        this.cartID = cartID;
        this.pid = pid;
        this.quantity = quantity;
    }

    public String getCartID() {
        return cartID;
    }

    public void setCartID(String cartID) {
        this.cartID = cartID;
    }

    public String getCiID() {
        return ciID;
    }

    public void setCiID(String ciID) {
        this.ciID = ciID;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
