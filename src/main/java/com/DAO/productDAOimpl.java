package com.DAO;

import com.DAO.Interface.productDAO;
import com.model.Product;

import java.util.List;

public class productDAOimpl implements productDAO {
    @Override
    public Product findById(int pid) {
        return null;
    }

    @Override
    public List<Product> findAll() {
        return List.of();
    }

    @Override
    public List<Product> findByCategory(String category) {
        return List.of();
    }

    @Override
    public List<Product> findByCompatibility(int coID) {
        return List.of();
    }

    @Override
    public boolean insert(Product product) {
        return false;
    }

    @Override
    public boolean update(Product product) {
        return false;
    }

    @Override
    public boolean delete(int pid) {
        return false;
    }
}
