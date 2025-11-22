package com.DAO;

import com.DAO.Interface.userDAO;
import com.model.User;

import java.util.List;

public class userDAOimpl implements userDAO {

    @Override
    public User findById(int userID) {
        return null;
    }

    @Override
    public User findByEmail(String email) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return List.of();
    }

    @Override
    public boolean insert(User user) {
        return false;
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public boolean delete(int userID) {
        return false;
    }
}
