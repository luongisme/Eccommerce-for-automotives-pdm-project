package com.DAO.Interface;

import com.model.Review;

import java.util.List;

public interface reviewDAO {
    Review findById(int rid);
    List<Review> findByProductId(int pid);
    List<Review> findByUserId(int userID);
    boolean insert(Review review);
    boolean update(Review review);
    boolean delete(int rid);
}
