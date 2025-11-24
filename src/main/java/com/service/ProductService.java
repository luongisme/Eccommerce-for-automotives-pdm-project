package com.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.DAO.productDAOimpl;
import com.model.Product;

public class ProductService {
    private static ProductService instance;
    private final productDAOimpl productDAO;

    private ProductService() {
        this.productDAO = new productDAOimpl();
    }

    public static ProductService getInstance() {
        if (instance == null) {
            instance = new ProductService();
        }
        return instance;
    }

    public List<Product> getAllProducts() {
        return productDAO.findAll();
    }

    public Product getProductById(String pid) {
        return productDAO.findById(pid);
    }

    public List<Product> getProductsByCategory(String category) {
        return productDAO.findByCategory(category);
    }

    public List<Product> getProductsByCompatibility(String coID) {
        return productDAO.findByCompatibility(coID);
    }

    public boolean addProduct(Product product) {
        return productDAO.insert(product);
    }

    public boolean updateProduct(Product product) {
        return productDAO.update(product);
    }

    public boolean deleteProduct(String pid) {
        return productDAO.delete(pid);
    }

    public List<Product> filterProducts(Set<String> categories, Set<String> brands, 
                                       double minPrice, double maxPrice) {
        List<Product> allProducts = getAllProducts();
        return allProducts.stream()
            .filter(p -> categories.isEmpty() || categories.contains(p.getCategory()))
            .filter(p -> brands.isEmpty() || brands.contains(p.getBrand()))
            .filter(p -> p.getPrice() >= minPrice && p.getPrice() <= maxPrice)
            .collect(Collectors.toList());
    }

    public List<Product> sortProducts(List<Product> products, String sortBy) {
        List<Product> sorted = new ArrayList<>(products);
        
        switch (sortBy) {
            case "available":
                sorted.sort((p1, p2) -> Boolean.compare(p2.isAvailable(), p1.isAvailable()));
                break;
            case "price_asc":
                sorted.sort(Comparator.comparingDouble(Product::getPrice));
                break;
            case "price_desc":
                sorted.sort((p1, p2) -> Double.compare(p2.getPrice(), p1.getPrice()));
                break;
            case "rating":
                sorted.sort((p1, p2) -> Double.compare(p2.getPrice(), p1.getPrice())); // Assuming price as a proxy for rating
                break;
            default:
                // No sorting
                break;
        }
        
        return sorted;
    }

    public List<String> getAllCategories() {
        List<Product> allProducts = getAllProducts();
        return allProducts.stream()
            .map(Product::getCategory)
            .distinct()
            .sorted()
            .collect(Collectors.toList());
    }

    public List<String> getAllBrands() {
        List<Product> allProducts = getAllProducts();
        return allProducts.stream()
            .map(Product::getBrand)
            .distinct()
            .sorted()
            .collect(Collectors.toList());
    }
}
