package com.DAO.Interface;

import java.util.List;

import  com.model.Address;

public interface addressDAO {
    Address findById(String aid);
    List<Address> findByUserId(String userID);
    List<Address> findAll();
    boolean insert(Address address);
    boolean update(Address address);
    boolean delete(String aid);

}