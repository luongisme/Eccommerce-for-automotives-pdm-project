package com.DAO;

import com.DAO.Interface.orderDAO;
import com.model.Order;

import java.util.List;

public class orderDAOimpl implements orderDAO {
    @Override
    public Order findById(int orderID) {
        return null;
    }

    @Override
    public List<Order> findByUserId(int userID) {
        return List.of();
    }

    @Override
    public List<Order> findAll() {
        return List.of();
    }

    @Override
    public boolean insert(Order order) {
        return false;
    }

    @Override
    public boolean update(Order order) {
        return false;
    }

    @Override
    public boolean delete(int orderID) {
        return false;
    }
}
