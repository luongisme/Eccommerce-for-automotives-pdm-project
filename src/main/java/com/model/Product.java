package com.model;

import java.util.HashMap;
import java.util.Map;

public class Product {
    private String id;
    private String name;
    private String category;
    private String brand;
    private double price;
    private double rating;
    private String imageUrl;
    private String description;
    private boolean isNew;
    private String carBrand; // e.g., "Sedan", "SUV", "Truck", etc.
    
    // Additional fields for product details
    private String sku; // Stock Keeping Unit
    private int stockQuantity;
    private boolean inStock;
    private Map<String, String> specifications; // e.g., "Material" -> "Iridium", "Gap" -> "0.028-0.031 inches"
    
    public Product(String id, String name, String category, String brand, double price,
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
        this.carBrand = "All Vehicles"; // Default value
        this.specifications = new HashMap<>();
        this.stockQuantity = 0;
        this.inStock = false;
    }
    
    // Constructor with all fields
    public Product(String id, String name, String category, String brand, double price,
                   double rating, String imageUrl, String description, boolean isNew,
                   String sku, int stockQuantity, Map<String, String> specifications) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.brand = brand;
        this.price = price;
        this.rating = rating;
        this.imageUrl = imageUrl;
        this.description = description;
        this.isNew = isNew;
        this.carBrand = "All Vehicles"; // Default value
        this.sku = sku;
        this.stockQuantity = stockQuantity;
        this.inStock = stockQuantity > 0;
        this.specifications = specifications != null ? specifications : new HashMap<>();
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public String getBrand() { return brand; }
    public double getPrice() { return price; }
    public double getRating() { return rating; }
    public String getImageUrl() { return imageUrl; }
    public String getDescription() { return description; }
    public boolean isNew() { return isNew; }
    public String getSku() { return sku; }
    public int getStockQuantity() { return stockQuantity; }
    public boolean isInStock() { return inStock; }
    public Map<String, String> getSpecifications() { return specifications; }
    public String getCarBrand() { return carBrand; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setCategory(String category) { this.category = category; }
    public void setBrand(String brand) { this.brand = brand; }
    public void setPrice(double price) { this.price = price; }
    public void setRating(double rating) { this.rating = rating; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setDescription(String description) { this.description = description; }
    public void setNew(boolean isNew) { this.isNew = isNew; }
    public void setSku(String sku) { this.sku = sku; }
    public void setStockQuantity(int stockQuantity) { 
        this.stockQuantity = stockQuantity; 
        this.inStock = stockQuantity > 0;
    }
    public void setInStock(boolean inStock) { this.inStock = inStock; }
    public void setSpecifications(Map<String, String> specifications) { this.specifications = specifications; }
    public void setCarBrand(String carBrand) { this.carBrand = carBrand; }
    
    // Utility methods
    public void addSpecification(String key, String value) {
        this.specifications.put(key, value);
    }
    
    public String getSpecification(String key) {
        return this.specifications.get(key);
    }
}
