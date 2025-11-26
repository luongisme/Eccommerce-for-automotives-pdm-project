package com.UI.admin;

import com.DAO.Interface.productDAO;
import com.DAO.productDAOimpl;
import com.model.Product;

import java.util.List;

public class AdminDashboardController {

    private final productDAO productDAO;

    public AdminDashboardController() {
        this.productDAO = new productDAOimpl();
    }


    public List<Product> getAllProducts() {
        return productDAO.findAll();
    }


    public List<Product> getProductsByCategory(String category) {
        if (category == null || category.equalsIgnoreCase("All categories")) {
            return productDAO.findAll();
        }
        return productDAO.findByCategory(category);
    }


    public List<Product> searchProducts(String keyword, String category) {
        // tạm thời làm đơn giản: lấy all rồi filter ở client
        List<Product> base = getProductsByCategory(category);
        if (keyword == null || keyword.isBlank()) return base;

        String lower = keyword.toLowerCase();
        return base.stream()
                .filter(p -> p.getName() != null && p.getName().toLowerCase().contains(lower))
                .toList();
    }

    public boolean insertProduct(Product product) {
        return productDAO.insert(product);
    }

    public boolean updateProduct(Product product) {
        return productDAO.update(product);
    }

    public boolean deleteProduct(String pid) {
        return productDAO.delete(pid);
    }

}
