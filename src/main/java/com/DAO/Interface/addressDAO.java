package com.DAO.Interface;

import java.util.List;

import  com.model.Address;

public interface addressDAO {
    Address findById(int aid);
    List<Address> findByUserId(int userID);
    List<Address> findAll();
    boolean insert(Address address);
    boolean update(Address address);
    boolean delete(int aid);

}