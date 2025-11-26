package com.UI.admin;

import com.DAO.Interface.orderDAO;
import com.DAO.Interface.orderitemDAO;
import com.DAO.Interface.productDAO;
import com.DAO.Interface.userDAO;
import com.DAO.orderDAOimpl;
import com.DAO.orderitemDAOimpl;
import com.DAO.productDAOimpl;
import com.DAO.userDAOimpl;
import com.model.OrderItem;
import com.model.Product;

import java.util.List;

public class AdminDashboardController {

    private final productDAO productDAO;
    private final userDAO userDAO;
    private final orderDAO orderDAO;
    private final orderitemDAO orderitemDAO;

    public AdminDashboardController() {
        this.productDAO = new productDAOimpl();
        this.userDAO=new userDAOimpl();
        this.orderDAO=new orderDAOimpl();
        this.orderitemDAO=new orderitemDAOimpl();
    }

    public int getTotalUsers() {
        List<com.model.User> users = userDAO.findAll();
        return users.size();
    }

    public int totalRevenue() {
        List<com.model.Order> orders = orderDAO.findAll();
        int total = 0;
        for (com.model.Order order : orders) {
            total += order.getTotalAmount().intValue();
        }
        return total;
    }

    public List<OrderItem> getBestSellingItems() {
        return orderitemDAO.findMostSaleProduct();
    }


    public int getTotalOrders() {
        List<com.model.Order> orders = orderDAO.findAll();
        return orders.size();
    }

    public int getLowStockProductsCount(int threshold) {
        List<Product> products = productDAO.findAll();
        return (int) products.stream().filter(p -> p.getStockQuantity() <= threshold).count();
    }

    public int getHighStockProductsCount(int threshold) {
        List<Product> products = productDAO.findAll();
        return (int) products.stream().filter(p -> p.getStockQuantity() > threshold).count();
    }

    public List<Product> getUnderStockProducts(int threshold) {
        List<Product> products = productDAO.findAll();
        return products.stream().filter(p -> p.getStockQuantity() <= threshold).toList();
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

    public Product getProductById(String pid) {
        return productDAO.findById(pid);
    }

}
