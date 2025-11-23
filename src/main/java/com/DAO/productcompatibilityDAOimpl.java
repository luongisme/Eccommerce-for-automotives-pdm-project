package com.DAO;

import com.DAO.Interface.productcompatibilityDAO;
import com.model.ProductCompatibility;
import com.model.Review;

import java.util.List;

public class productcompatibilityDAOimpl implements productcompatibilityDAO {

    @Override
    public ProductCompatibility findById(String coID) {
        return null;
    }

    @Override
    public List<ProductCompatibility> findAll() {
        return List.of();
    }

    @Override
    public boolean insert(ProductCompatibility pc) {
        return false;
    }

    @Override
    public boolean update(ProductCompatibility pc) {
        return false;
    }

    @Override
    public boolean delete(String coID) {
        return false;
    }
}
