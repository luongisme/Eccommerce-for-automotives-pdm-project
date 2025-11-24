package com.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.DAO.reviewDAOimpl;
import com.model.Review;

public class ReviewService {
    private static ReviewService instance;
    private final reviewDAOimpl reviewDAO;

    private ReviewService() {
        this.reviewDAO = new reviewDAOimpl();
    }

    public static ReviewService getInstance() {
        if (instance == null) {
            instance = new ReviewService();
        }
        return instance;
    }

    public Review getReviewById(String rId){
        return reviewDAO.findById(rId);
    }

    public List<Review> getReviewByProductId(String pId){
        return reviewDAO.findByProductId(pId);
    }

    public List<Review> getReviewByUserId(String uId){
        return reviewDAO.findByUserId(uId);
    }

    public boolean addReview(Review review){
        return reviewDAO.insert(review);
    }

    public boolean updateReview(Review review){
        return reviewDAO.update(review);
    }

    public boolean deleteReview(String rId){
        return reviewDAO.delete(rId);
    }


    /**
     * Calculate average rating for a product
     */
    public double getAverageRating(String productId) {
        List<Review> productReviews = getReviewByProductId(productId);
        if (productReviews.isEmpty()) {
            return 0.0;
        }
        
        double sum = productReviews.stream()
            .mapToInt(Review::getRating)
            .sum();
        return sum / productReviews.size();
    }

    /**
     * Get total review count for a product
     */
    public int getReviewCount(String productId) {
        List<Review> productReviews = getReviewByProductId(productId);
        return productReviews.size();
    }


    public boolean hasUserReviewed(String userId, String productId) {
        List<Review> productReviews = getReviewByProductId(productId);
        return productReviews.stream()
            .anyMatch(r -> r.getUserId().equals(userId));
    }

}
