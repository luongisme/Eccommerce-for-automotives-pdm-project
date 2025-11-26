package com.DAO.Interface;

import com.model.Product;

import java.util.List;

public interface productDAO {
    Product findById(String pid);
    List<Product> findAll();
    List<Product> findByCategory(String category);
    List<Product> findByCompatibility(String coID);
    List<Product> findByVehicleMake(String make);


    boolean insert(Product product);
    boolean update(Product product);
    boolean delete(String pid);
}
