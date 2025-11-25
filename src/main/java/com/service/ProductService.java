package com.service;

import java.util.*;
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

    private final Map<String, Boolean> imageExistCache = new HashMap<>();

    private int imagePriority(Product p) {
        String img = p.getImageUrl();
        if (img == null) return 1;

        String fileName = img.trim();
        if (fileName.isEmpty()) return 1;

        Boolean cached = imageExistCache.get(fileName);
        if (cached != null) {
            return cached ? 0 : 1;
        }

        String imagePath = "/images/Product/" + fileName;
        java.net.URL url = ProductService.class.getResource(imagePath);

        boolean hasRealImage = (url != null);
        imageExistCache.put(fileName, hasRealImage);

        return hasRealImage ? 0 : 1;
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
        System.out.println(">>> sortBy = " + sortBy + ", size = " + products.size());

        switch (sortBy) {
            case "available":
                System.out.println(">>> Using sort: AVAILABLE");
                sorted.sort(
                        Comparator
                                .comparing(Product::isAvailable).reversed()
                                .thenComparing(Product::getName, String.CASE_INSENSITIVE_ORDER)
                );
                break;

            case "price_asc":
                System.out.println(">>> Using sort: PRICE_ASC");
                sorted.sort(
                        Comparator
                                .comparingDouble(Product::getPrice)
                                .thenComparing(Product::getName, String.CASE_INSENSITIVE_ORDER)
                );
                break;

            case "price_desc":
                System.out.println(">>> Using sort: PRICE_DESC");
                sorted.sort(
                        Comparator
                                .comparingDouble(Product::getPrice).reversed()
                                .thenComparing(Product::getName, String.CASE_INSENSITIVE_ORDER)
                );
                break;

            case "rating":
                System.out.println(">>> Using sort: RATING");
                // tạm dùng price làm rating
                sorted.sort(
                        Comparator
                                .comparingDouble(Product::getPrice).reversed()
                                .thenComparing(Product::getName, String.CASE_INSENSITIVE_ORDER)
                );
                break;
            case "new":
                System.out.println(">>> Using sort: DEFAULT (imagePriority)");
                sorted.sort(
                        Comparator
                                .comparingInt(this::imagePriority)
                                .thenComparing(Product::getName, String.CASE_INSENSITIVE_ORDER)
                );
                break;

        }
        for (int i = 0; i < Math.min(5, sorted.size()); i++) {
            Product p = sorted.get(i);
            System.out.println(
                    "   #" + i +
                            " | name=" + p.getName() +
                            " | price=" + p.getPrice() +
                            " | hasImage=" + (p.getImageUrl() != null && !p.getImageUrl().trim().isEmpty())
            );
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
