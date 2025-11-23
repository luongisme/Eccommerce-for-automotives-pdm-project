package com.DAO;

import com.DAO.Interface.cartitemDAO;
import com.model.CartItem;
import com.model.Review;

import java.util.List;

public class cartItemDAOimpl implements cartitemDAO {

    @Override
    public CartItem findById(int ciID) {
        return null;
    }

    @Override
    public List<CartItem> findByCartId(int cartID) {
        return List.of();
    }

    @Override
    public boolean insert(CartItem item) {
        return false;
    }

    @Override
    public boolean update(CartItem item) {
        return false;
    }

    @Override
    public boolean delete(int ciID) {
        return false;
    }

    @Override
    public boolean deleteByCartId(int cartID) {
        return false;
    }
}
