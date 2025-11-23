package com.DAO.Interface;

import com.model.ShoppingCart;

public interface shoppingcartDAO {
    ShoppingCart findById(String cartID);
    ShoppingCart findByUserId(String userID);
    boolean insert(ShoppingCart cart);
    boolean update(ShoppingCart cart);
    boolean delete(String cartID);
}