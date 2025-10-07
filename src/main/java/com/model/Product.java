package com.model;

public class Product {
    private int id;
    private String name;
    private String category;
    private String brand;
    private double price;
    private double rating;
    private String imageUrl;
    private String description;
    private boolean isNew;

    public Product(int id, String name, String category, String brand, double price, 
                   double rating, String imageUrl, String description, boolean isNew) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.brand = brand;
        this.price = price;
        this.rating = rating;
        this.imageUrl = imageUrl;
        this.description = description;
        this.isNew = isNew;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public String getBrand() { return brand; }
    public double getPrice() { return price; }
    public double getRating() { return rating; }
    public String getImageUrl() { return imageUrl; }
    public String getDescription() { return description; }
    public boolean isNew() { return isNew; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setCategory(String category) { this.category = category; }
    public void setBrand(String brand) { this.brand = brand; }
    public void setPrice(double price) { this.price = price; }
    public void setRating(double rating) { this.rating = rating; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setDescription(String description) { this.description = description; }
    public void setNew(boolean isNew) { this.isNew = isNew; }
}
