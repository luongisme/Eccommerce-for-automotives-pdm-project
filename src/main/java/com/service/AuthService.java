package com.service;

import com.model.User;
import com.model.User.UserRole;
import java.util.HashMap;
import java.util.Map;

public class AuthService {
    private static AuthService instance;
    private Map<String, User> users;

    private AuthService() {
        initMockUsers();
    }

    public static AuthService getInstance() {
        if (instance == null) {
            instance = new AuthService();
        }
        return instance;
    }

    private void initMockUsers() {
        users = new HashMap<>();
        
        // Mock users
        User user1 = new User(1, "User", "user123@gmail.com", "Password123", UserRole.USER);
        User admin = new User(2, "Admin", "admin123@gmail.com", "Password123", UserRole.ADMIN);
        
        users.put(user1.getEmail(), user1);
        users.put(admin.getEmail(), admin);
    }

    public User authenticate(String email, String password) {
        User user = users.get(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public boolean register(String username, String email, String password) {
        if (users.containsKey(email)) {
            return false; // User already exists
        }
        
        int newId = users.size() + 1;
        User newUser = new User(newId, username, email, password, UserRole.USER);
        users.put(email, newUser);
        return true;
    }
}
