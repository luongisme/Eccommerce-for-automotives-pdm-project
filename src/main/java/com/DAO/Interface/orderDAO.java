package com.DAO.Interface;

import com.model.Order;

import java.util.List;

public interface orderDAO {
    Order findById(int orderID);
    List<Order> findByUserId(int userID);
    List<Order> findAll();
    boolean insert(Order order);
    boolean update(Order order);
    boolean delete(int orderID);
}
