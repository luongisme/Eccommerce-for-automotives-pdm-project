package com.DAO.Interface;

import com.model.User;

import java.util.List;

public interface userDAO {
    User findById(String userID);
    User findByEmail(String email);
    List<User> findAll();
    boolean insert(User user);
    boolean update(User user);
    boolean delete(String userID);
}
