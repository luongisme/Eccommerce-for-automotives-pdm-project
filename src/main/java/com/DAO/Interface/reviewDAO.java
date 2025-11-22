package com.DAO.Interface;

import com.model.Review;

import java.util.List;

public interface reviewDAO {
    Review findById(String rid);
    List<Review> findByProductId(String pid);
    List<Review> findByUserId(String userID);
    boolean insert(Review review);
    boolean update(Review review);
    boolean delete(String rid);
}
