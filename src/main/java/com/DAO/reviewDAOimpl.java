package com.DAO;

import com.DAO.Interface.reviewDAO;
import com.model.Review;

import java.util.List;

public class reviewDAOimpl implements reviewDAO {

    @Override
    public Review findById(String rid) {
        return null;
    }

    @Override
    public List<Review> findByProductId(String pid) {
        return List.of();
    }

    @Override
    public List<Review> findByUserId(String userID) {
        return List.of();
    }

    @Override
    public boolean insert(Review review) {
        return false;
    }

    @Override
    public boolean update(Review review) {
        return false;
    }

    @Override
    public boolean delete(String rid) {
        return false;
    }
}
