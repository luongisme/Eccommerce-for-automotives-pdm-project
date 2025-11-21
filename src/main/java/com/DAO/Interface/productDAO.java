package com.DAO.Interface;

import com.model.Product;

import java.util.List;

public interface productDAO {
    Product findById(int pid);
    List<Product> findAll();
    List<Product> findByCategory(String category);
    List<Product> findByCompatibility(int coID);
    boolean insert(Product product);
    boolean update(Product product);
    boolean delete(int pid);
}
