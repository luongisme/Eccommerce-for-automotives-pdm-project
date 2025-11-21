package com.DAO.Interface;

import com.model.ProductCompatibility;

import java.util.List;

public interface productcompatibilityDAO {
    ProductCompatibility findById(int coID);
    List<ProductCompatibility> findAll();
    boolean insert(ProductCompatibility pc);
    boolean update(ProductCompatibility pc);
    boolean delete(int coID);
}
