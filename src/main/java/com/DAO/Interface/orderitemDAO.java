package com.DAO.Interface;

import com.model.OrderItem;

import java.util.List;

public interface orderitemDAO {
    OrderItem findById(String oiID);
    List<OrderItem> findByOrderId(String orderID);
    List<OrderItem> findMostSaleProduct();
    boolean insert(OrderItem item);
    boolean update(OrderItem item);
    boolean delete(String oiID);
    boolean deleteByOrderId(String orderID);
}
