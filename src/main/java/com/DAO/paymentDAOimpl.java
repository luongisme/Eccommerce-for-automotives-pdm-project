package com.DAO;

import com.DAO.Interface.paymentDAO;
import com.model.Payment;
import com.model.Review;

import java.util.List;

public class paymentDAOimpl implements paymentDAO {

    @Override
    public Payment findById(int paymentID) {
        return null;
    }

    @Override
    public List<Payment> findAll() {
        return List.of();
    }

    @Override
    public boolean insert(Payment payment) {
        return false;
    }

    @Override
    public boolean update(Payment payment) {
        return false;
    }

    @Override
    public boolean delete(int paymentID) {
        return false;
    }
}
