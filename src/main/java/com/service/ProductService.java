package com.service;

import com.model.Product;
import java.util.*;
import java.util.stream.Collectors;

public class ProductService {
    private static ProductService instance;
    private List<Product> allProducts;

    private ProductService() {
        initMockData();
    }

    public static ProductService getInstance() {
        if (instance == null) {
            instance = new ProductService();
        }
        return instance;
    }

    private void initMockData() {
        allProducts = new ArrayList<>();
        
        // Mock data with various categories and brands
        String[] categories = {"Engine", "Brakes", "Wheels & Tires", "Suspension", "Electrical", 
                               "Transmission", "Exhaust", "Body parts", "Interior", "Fluids & Chemicals"};
        String[] brands = {"Bosch", "Brembo", "Michelin", "Monroe", "ACDelco", "NGK", "Denso", "Gates", "Continental", "Moog"};
        
        int id = 1;
        for (int i = 0; i < 50; i++) {
            String category = categories[i % categories.length];
            String brand = brands[i % brands.length];
            double price = 50 + (Math.random() * 950); // Random price between $50-$1000
            double rating = 3.0 + (Math.random() * 2.0); // Random rating 3.0-5.0
            boolean isNew = i < 10; // First 10 products are "new"
            
            allProducts.add(new Product(
                id++,
                category + " Part #" + (i + 1),
                category,
                brand,
                Math.round(price * 100.0) / 100.0,
                Math.round(rating * 10.0) / 10.0,
                null, // No image URL for mock data
                "High-quality " + category.toLowerCase() + " part from " + brand,
                isNew
            ));
        }
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(allProducts);
    }

    public List<Product> filterProducts(Set<String> categories, Set<String> brands, 
                                       double minPrice, double maxPrice) {
        return allProducts.stream()
            .filter(p -> categories.isEmpty() || categories.contains(p.getCategory()))
            .filter(p -> brands.isEmpty() || brands.contains(p.getBrand()))
            .filter(p -> p.getPrice() >= minPrice && p.getPrice() <= maxPrice)
            .collect(Collectors.toList());
    }

    public List<Product> sortProducts(List<Product> products, String sortBy) {
        List<Product> sorted = new ArrayList<>(products);
        
        switch (sortBy) {
            case "new":
                sorted.sort((p1, p2) -> Boolean.compare(p2.isNew(), p1.isNew()));
                break;
            case "price_asc":
                sorted.sort(Comparator.comparingDouble(Product::getPrice));
                break;
            case "price_desc":
                sorted.sort((p1, p2) -> Double.compare(p2.getPrice(), p1.getPrice()));
                break;
            case "rating":
                sorted.sort((p1, p2) -> Double.compare(p2.getRating(), p1.getRating()));
                break;
        }
        
        return sorted;
    }

    public List<String> getAllCategories() {
        return allProducts.stream()
            .map(Product::getCategory)
            .distinct()
            .sorted()
            .collect(Collectors.toList());
    }

    public List<String> getAllBrands() {
        return allProducts.stream()
            .map(Product::getBrand)
            .distinct()
            .sorted()
            .collect(Collectors.toList());
    }
}
