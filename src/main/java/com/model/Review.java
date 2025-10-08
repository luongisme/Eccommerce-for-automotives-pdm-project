package com.model;

import java.time.LocalDateTime;

public class Review {
    private int id;
    private int productId;
    private int userId;
    private String username;
    private int rating; // 1-5 stars
    private String title;
    private String body;
    private LocalDateTime createdAt;

    public Review(int id, int productId, int userId, String username, int rating, 
                  String title, String body, LocalDateTime createdAt) {
        this.id = id;
        this.productId = productId;
        this.userId = userId;
        this.username = username;
        this.rating = rating;
        this.title = title;
        this.body = body;
        this.createdAt = createdAt;
    }

    // Getters
    public int getId() { return id; }
    public int getProductId() { return productId; }
    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public int getRating() { return rating; }
    public String getTitle() { return title; }
    public String getBody() { return body; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setProductId(int productId) { this.productId = productId; }
    public void setUserId(int userId) { this.userId = userId; }
    public void setUsername(String username) { this.username = username; }
    public void setRating(int rating) { this.rating = rating; }
    public void setTitle(String title) { this.title = title; }
    public void setBody(String body) { this.body = body; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
