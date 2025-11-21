package com.DAO.Interface;

import com.model.ShoppingCart;

public interface shoppingcartDAO {
    ShoppingCart findById(int cartID);
    ShoppingCart findByUserId(int userID);
    boolean insert(ShoppingCart cart);
    boolean update(ShoppingCart cart);
    boolean delete(int cartID);
}