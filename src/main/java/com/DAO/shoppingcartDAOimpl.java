package com.DAO;

import com.DAO.Interface.shoppingcartDAO;
import com.model.Product;
import com.model.ShoppingCart;

import java.util.List;

public class shoppingcartDAOimpl implements shoppingcartDAO {

    @Override
    public ShoppingCart findById(String cartID) {
        return null;
    }

    @Override
    public ShoppingCart findByUserId(String userID) {
        return null;
    }

    @Override
    public boolean insert(ShoppingCart cart) {
        return false;
    }

    @Override
    public boolean update(ShoppingCart cart) {
        return false;
    }

    @Override
    public boolean delete(String cartID) {
        return false;
    }
}
