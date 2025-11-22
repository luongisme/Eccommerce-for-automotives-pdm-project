package com.DAO.Interface;

import com.model.CartItem;

import java.util.List;

public interface cartitemDAO {
    CartItem findById(int ciID);
    List<CartItem> findByCartId(int cartID);
    boolean insert(CartItem item);
    boolean update(CartItem item);
    boolean delete(int ciID);
    boolean deleteByCartId(int cartID);
}
