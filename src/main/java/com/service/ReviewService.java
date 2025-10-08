package com.service;

import com.model.Review;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class to handle review-related business logic
 * Currently uses mock data - will be replaced with database operations
 */
public class ReviewService {
    private static ReviewService instance;
    private List<Review> reviews;

    private ReviewService() {
        this.reviews = new ArrayList<>();
        initializeMockReviews();
    }

    public static ReviewService getInstance() {
        if (instance == null) {
            instance = new ReviewService();
        }
        return instance;
    }

    private void initializeMockReviews() {
        // Mock reviews for testing
        reviews.add(new Review(1, 1, 1, "John Doe", 5, 
            "Excellent product!", 
            "These spark plugs exceeded my expectations. Great quality and performance.",
            LocalDateTime.now().minusDays(5)));
        
        reviews.add(new Review(2, 1, 2, "Jane Smith", 4, 
            "Good value", 
            "Good quality for the price. Installation was straightforward.",
            LocalDateTime.now().minusDays(10)));
        
        reviews.add(new Review(3, 1, 3, "Mike Johnson", 5, 
            "Perfect fit!", 
            "Fits perfectly in my vehicle. Notice better fuel efficiency already.",
            LocalDateTime.now().minusDays(15)));
        
        reviews.add(new Review(4, 2, 4, "Sarah Williams", 4, 
            "Great product", 
            "Very satisfied with this purchase. Would recommend to others.",
            LocalDateTime.now().minusDays(3)));
    }

    /**
     * Get all reviews for a specific product
     */
    public List<Review> getReviewsByProductId(int productId) {
        return reviews.stream()
            .filter(r -> r.getProductId() == productId)
            .collect(Collectors.toList());
    }

    /**
     * Add a new review
     */
    public boolean addReview(Review review) {
        try {
            // In real implementation, this would save to database
            review.setId(reviews.size() + 1);
            review.setCreatedAt(LocalDateTime.now());
            reviews.add(review);
            return true;
        } catch (Exception e) {
            System.err.println("Error adding review: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get average rating for a product
     */
    public double getAverageRating(int productId) {
        List<Review> productReviews = getReviewsByProductId(productId);
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
    public int getReviewCount(int productId) {
        return (int) reviews.stream()
            .filter(r -> r.getProductId() == productId)
            .count();
    }

    /**
     * Check if a user has already reviewed a product
     */
    public boolean hasUserReviewed(int userId, int productId) {
        return reviews.stream()
            .anyMatch(r -> r.getUserId() == userId && r.getProductId() == productId);
    }

    /**
     * Delete a review (admin functionality)
     */
    public boolean deleteReview(int reviewId) {
        return reviews.removeIf(r -> r.getId() == reviewId);
    }
}
