package com.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Order {
    private int orderID;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private String orderStatus;
    private String userID;
    private int paymentID;

    public Order() {}

    public Order(int orderID, LocalDateTime orderDate, BigDecimal totalAmount,
                 String orderStatus, String userID, int paymentID) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
        this.userID = userID;
        this.paymentID = paymentID;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
