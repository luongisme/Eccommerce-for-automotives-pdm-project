package com.DAO.Interface;

import com.model.OrderItem;

import java.util.List;

public interface orderitemDAO {
    OrderItem findById(int oiID);
    List<OrderItem> findByOrderId(int orderID);
    boolean insert(OrderItem item);
    boolean update(OrderItem item);
    boolean delete(int oiID);
    boolean deleteByOrderId(int orderID);
}
