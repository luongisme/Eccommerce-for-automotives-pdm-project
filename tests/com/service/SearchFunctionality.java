package com.service;

import com.Util.JdbcConnector;
import com.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SearchFunctionality {

    private JdbcConnector connector;

    public SearchFunctionality() {
        connector = new JdbcConnector();
    }

    // Search products by Product ID
    public List<Product> searchByID(String productID) {
        List<Product> results = new ArrayList<>();
        String sql = "SELECT * FROM Products WHERE PID = ?";

        try (Connection conn = connector.connect();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(productID));

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("PID"));
                product.setName(rs.getString("PName"));
                product.setBrand(rs.getString("Brand"));
                product.setCategory(rs.getString("Category"));
                product.setPrice(rs.getDouble("Price"));
                product.setDescription(rs.getString("Description"));
                product.setStockQuantity(rs.getInt("StockQuantity"));
                product.setInStock(rs.getBoolean("IsAvailable"));
                product.setSku(rs.getString("SKU"));
                product.setImageUrl(rs.getString("Image"));
                // Parse Specifications JSON if stored as string in DB
                // product.setSpecifications(parseSpecifications(rs.getString("Specifications")));

                results.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return results;
    }

    // Search products by keyword (Product Name)
    public List<Product> searchByKeyword(String keyword) {
        List<Product> results = new ArrayList<>();
        String sql = "SELECT * "
                +
                "FROM Products " +
                "WHERE LOWER(PName) LIKE LOWER(?)";

        try (Connection conn = connector.connect();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            String searchPattern = "%" + keyword + "%"; // wildcard search
            stmt.setString(1, searchPattern);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("PID"));
                product.setName(rs.getString("PName"));
                product.setBrand(rs.getString("Brand"));
                product.setCategory(rs.getString("Category"));
                product.setPrice(rs.getDouble("Price"));
                product.setDescription(rs.getString("Description"));
                product.setStockQuantity(rs.getInt("StockQuantity"));
                product.setInStock(rs.getBoolean("IsAvailable"));
                product.setSku(rs.getString("SKU"));
                product.setImageUrl(rs.getString("Image"));
                // Parse Specifications JSON if stored as string in DB
                // product.setSpecifications(parseSpecifications(rs.getString("Specifications")));

                results.add(product);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return results;
    }

    // Search by both keywords + filters (From ProductService.java)
    public List<Product> mixedSearch(String keyword,
            Set<String> categories,
            Set<String> brands,
            Double minPrice,
            Double maxPrice) {

        List<Product> results = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT * FROM Products WHERE 1=1 ");

        List<Object> params = new ArrayList<>();

        // -----------------------------------
        // Keyword (matches name, category, brand, description, SKU)
        // -----------------------------------
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (PName LIKE ?)");
            String like = "%" + keyword + "%";
            params.add(like);
        }

        // -----------------------------------
        // Category filters
        // -----------------------------------
        if (categories != null && !categories.isEmpty()) {
            sql.append(" AND Category IN (");
            sql.append("?, ".repeat(categories.size()));
            sql.setLength(sql.length() - 2); // remove last comma
            sql.append(")");

            params.addAll(categories);
        }

        // -----------------------------------
        // Brand filters
        // -----------------------------------
        if (brands != null && !brands.isEmpty()) {
            sql.append(" AND Brand IN (");
            sql.append("?, ".repeat(brands.size()));
            sql.setLength(sql.length() - 2);
            sql.append(")");

            params.addAll(brands);
        }

        // -----------------------------------
        // Price range
        // -----------------------------------
        if (minPrice != null) {
            sql.append(" AND Price >= ?");
            params.add(minPrice);
        }

        if (maxPrice != null) {
            sql.append(" AND Price <= ?");
            params.add(maxPrice);
        }

        try (Connection conn = connector.connect();
                PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            // Insert all dynamic params into PreparedStatement
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Product product = new Product();

                product.setId(rs.getInt("PID"));
                product.setName(rs.getString("PName"));
                product.setBrand(rs.getString("Brand"));
                product.setCategory(rs.getString("Category"));
                product.setPrice(rs.getDouble("Price"));
                product.setDescription(rs.getString("Description"));
                product.setStockQuantity(rs.getInt("StockQuantity"));
                product.setInStock(rs.getBoolean("IsAvailable"));
                product.setSku(rs.getString("SKU"));
                product.setImageUrl(rs.getString("Image"));

                results.add(product);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return results;
    }

}
