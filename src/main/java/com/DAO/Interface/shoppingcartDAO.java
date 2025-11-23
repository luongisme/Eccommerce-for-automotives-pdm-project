package com.DAO.Interface;

import com.model.ShoppingCart;

import java.util.List;

public interface shoppingcartDAO {
    ShoppingCart findById(String cartID);
    List<ShoppingCart> findByUserId(String userID);
    boolean insert(ShoppingCart cart);
    boolean update(ShoppingCart cart);
    boolean delete(String cartID);
}