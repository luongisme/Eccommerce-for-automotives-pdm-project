package com.DAO.Interface;

import com.model.OrderItem;
import com.model.Payment;

import java.util.List;

public interface paymentDAO {
    Payment findById(String paymentID);
    List<Payment> findAll();
    boolean insert(Payment payment);
    boolean update(Payment payment);
    boolean delete(String paymentID);
}
