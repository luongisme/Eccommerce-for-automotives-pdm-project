package com.DAO;

import com.DAO.Interface.addressDAO;
import com.model.Address;

import java.util.List;

public class addressDAOimpl implements addressDAO {
    @Override
    public Address findById(int aid) {
        return null;
    }

    @Override
    public List<Address> findByUserId(int userID) {
        return List.of();
    }

    @Override
    public List<Address> findAll() {
        return List.of();
    }

    @Override
    public boolean insert(Address address) {
        return false;
    }

    @Override
    public boolean update(Address address) {
        return false;
    }

    @Override
    public boolean delete(int aid) {
        return false;
    }
}
