package com.DAO.Interface;

import com.model.Order;

import java.util.List;

public interface orderDAO {
    Order findById(String orderID);
    List<Order> findByUserId(String userID);
    List<Order> findAll();
    boolean insert(Order order);
    boolean update(Order order);
    boolean delete(String orderID);
}
