package com.DAO;

import com.DAO.Interface.cartitemDAO;
import com.model.CartItem;
import com.model.Review;

import java.util.List;

public class cartItemDAOimpl implements cartitemDAO {

    @Override
    public CartItem findById(String ciID) {
        return null;
    }

    @Override
    public List<CartItem> findByCartId(String cartID) {
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
    public boolean delete(String ciID) {
        return false;
    }

    @Override
    public boolean deleteByCartId(String cartID) {
        return false;
    }
}
