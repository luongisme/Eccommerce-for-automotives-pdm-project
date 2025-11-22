package com.model;



import java.math.BigDecimal;

public class OrderItem {
    private String oiID;
    private String orderID;
    private String pid;
    private int quantity;
    private BigDecimal priceAtPurchase;

    public OrderItem() {}

    public OrderItem(String oiID, String orderID, String pid, int quantity, BigDecimal priceAtPurchase) {
        this.oiID = oiID;
        this.orderID = orderID;
        this.pid = pid;
        this.quantity = quantity;
        this.priceAtPurchase = priceAtPurchase;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPriceAtPurchase() {
        return priceAtPurchase;
    }

    public void setPriceAtPurchase(BigDecimal priceAtPurchase) {
        this.priceAtPurchase = priceAtPurchase;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getOiID() {
        return oiID;
    }

    public void setOiID(String oiID) {
        this.oiID = oiID;
    }
}

