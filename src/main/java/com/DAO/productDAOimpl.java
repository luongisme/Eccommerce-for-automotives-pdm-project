package com.DAO;

import com.DAO.Interface.productDAO;
import com.model.Product;
import com.Util.JdbcConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class productDAOimpl implements productDAO {

    private Product mapRow(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId(rs.getString("PID"));
        product.setBrand(rs.getString("Brand"));
        product.setAvailable(rs.getBoolean("isAvailable"));
        product.setSku(rs.getString("SKU"));
        product.setName(rs.getString("PName"));
        product.setDescription(rs.getString("Description"));
        product.setPrice(rs.getDouble("Price"));
        product.setPartNumber(rs.getString("PartNumber"));


        String specsString = rs.getString("Specifications");
        if (specsString != null && !specsString.trim().isEmpty()) {
            // Assuming specifications stored as comma-separated key:value pairs
            // e.g., "Material:Iridium,Gap:0.028-0.031"
            String[] pairs = specsString.split(",");
            for (String pair : pairs) {
                String[] kv = pair.split(":", 2);
                if (kv.length == 2) {
                    product.addSpecification(kv[0].trim(), kv[1].trim());
                }
            }
        }

        product.setStockQuantity(rs.getInt("StockQuantity"));
        product.setImageUrl(rs.getString("Image"));
        product.setCategory(rs.getString("Category"));

        return product;
    }

    @Override
    public Product findById(String pid) {
        String sql = "SELECT * FROM Product WHERE PID = ?";

        try (Connection conn = JdbcConnector.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pid);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM Product ORDER BY PName";

        try (Connection conn = JdbcConnector.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                products.add(mapRow(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return products;
    }

    @Override
    public List<Product> findByCategory(String category) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM Product WHERE Category = ? ORDER BY PName";

        try (Connection conn = JdbcConnector.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, category);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    products.add(mapRow(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return products;
    }

    @Override
    public List<Product> findByCompatibility(String coID) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM Product WHERE CoID = ? ORDER BY PName";

        try (Connection conn = JdbcConnector.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, coID);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    products.add(mapRow(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return products;
    }

    @Override
    public List<Product> findByVehicleMake(String make) {
        return List.of();
    }

    @Override
    public boolean insert(Product product) {
        String sql = "INSERT INTO Product (PID, Brand, isAvailable, SKU, PName, Description, " +
                     "Price, PartNumber, Specifications, StockQuantity, Image, Category) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = JdbcConnector.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, product.getPid());
            ps.setString(2, product.getBrand());
            ps.setBoolean(3, product.isAvailable());
            ps.setString(4, product.getSku());
            ps.setString(5, product.getName());
            ps.setString(6, product.getDescription());
            ps.setDouble(7, product.getPrice());
            ps.setString(8, product.getPartNumber());

            // Convert specifications map to string format: "key1:value1,key2:value2"
            StringBuilder specsBuilder = new StringBuilder();
            if (product.getSpecifications() != null && !product.getSpecifications().isEmpty()) {
                for (var entry : product.getSpecifications().entrySet()) {
                    if (specsBuilder.length() > 0) {
                        specsBuilder.append(",");
                    }
                    specsBuilder.append(entry.getKey()).append(":").append(entry.getValue());
                }
            }
            ps.setString(9, specsBuilder.toString());

            ps.setInt(10, product.getStockQuantity());
            ps.setString(11, product.getImageUrl());
            ps.setString(12, product.getCategory());


            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Product product) {
        String sql = "UPDATE Product SET Brand = ?, isAvailable = ?, SKU = ?, PName = ?, " +
                     "Description = ?, Price = ?, PartNumber = ?, Specifications = ?, " +
                     "StockQuantity = ?, Image = ?, Category = ? WHERE PID = ?";

        try (Connection conn = JdbcConnector.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, product.getBrand());
            ps.setBoolean(2, product.isAvailable());
            ps.setString(3, product.getSku());
            ps.setString(4, product.getName());
            ps.setString(5, product.getDescription());
            ps.setDouble(6, product.getPrice());
            ps.setString(7, product.getPartNumber());

            // Convert specifications map to string
            StringBuilder specsBuilder = new StringBuilder();
            if (product.getSpecifications() != null && !product.getSpecifications().isEmpty()) {
                for (var entry : product.getSpecifications().entrySet()) {
                    if (specsBuilder.length() > 0) {
                        specsBuilder.append(",");
                    }
                    specsBuilder.append(entry.getKey()).append(":").append(entry.getValue());
                }
            }
            ps.setString(8, specsBuilder.toString());
            ps.setInt(9, product.getStockQuantity());
            ps.setString(10, product.getImageUrl());
            ps.setString(11, product.getCategory());

            ps.setString(12, product.getPid());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(String pid) {
        String sql = "DELETE FROM Product WHERE PID = ?";

        try (Connection conn = JdbcConnector.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pid);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
