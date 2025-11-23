package com.DAO.Interface;

import com.model.CartItem;

import java.util.List;

public interface cartitemDAO {
    CartItem findById(String ciID);
    List<CartItem> findByCartId(String cartID);
    boolean insert(CartItem item);
    boolean update(CartItem item);
    boolean delete(String ciID);
    boolean deleteByCartId(String cartID);
}
