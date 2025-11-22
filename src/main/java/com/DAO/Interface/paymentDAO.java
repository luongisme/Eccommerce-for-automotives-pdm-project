package com.DAO.Interface;

import com.model.Payment;

import java.util.List;

public interface paymentDAO {
    Payment findById(int paymentID);
    List<Payment> findAll();
    boolean insert(Payment payment);
    boolean update(Payment payment);
    boolean delete(int paymentID);
}
