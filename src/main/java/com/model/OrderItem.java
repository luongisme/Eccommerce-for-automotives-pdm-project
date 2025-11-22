package com.model;



import java.math.BigDecimal;

public class OrderItem {
    private int oiID;
    private int orderID;
    private int pid;
    private int quantity;
    private BigDecimal priceAtPurchase;

    public OrderItem() {}

    public OrderItem(int oiID, int orderID, int pid, int quantity, BigDecimal priceAtPurchase) {
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

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getOiID() {
        return oiID;
    }

    public void setOiID(int oiID) {
        this.oiID = oiID;
    }
}

