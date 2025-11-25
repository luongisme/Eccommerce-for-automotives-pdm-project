package com.DAO.Interface;

import com.model.Review;

import java.util.List;
import java.util.Map;

public interface reviewDAO {
    Review findById(String rid);
    List<Review> findByProductId(String pid);
    List<Review> findByUserId(String userID);
    boolean insert(Review review);
    boolean update(Review review);
    boolean delete(String rid);
    Map<String, Double> getAverageRatingsForProducts(List<String> pids);
}
