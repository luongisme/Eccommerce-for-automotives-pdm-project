package com.model;

import java.time.LocalDateTime;

public class Review {
    private String id;
    private String productId;
    private String userId;
    private String username;
    private int rating; // 1-5 stars
    private String title;
    private String comment;
    private LocalDateTime createdAt;

    public Review(String id, String productId, String userId, int rating, 
                  String comment, LocalDateTime createdAt) {
        this.id = id;
        this.productId = productId;
        this.userId = userId;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    // Constructor with all fields
    public Review(String id, String productId, String userId, String username, int rating, 
                  String title, String comment, LocalDateTime createdAt) {
        this.id = id;
        this.productId = productId;
        this.userId = userId;
        this.username = username;
        this.rating = rating;
        this.title = title;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    // Getters
    public String getId() { return id; }
    public String getProductId() { return productId; }
    public String getUserId() { return userId; }
    public String getUsername() { return username; }
    public int getRating() { return rating; }
    public String getTitle() { return title; }
    public String getComment() { return comment; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setProductId(String productId) { this.productId = productId; }
    public void setUserId(String userId) { this.userId = userId; }
    public void setUsername(String username) { this.username = username; }
    public void setRating(int rating) { this.rating = rating; }
    public void setTitle(String title) { this.title = title; }
    public void setComment(String comment) { this.comment = comment; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
