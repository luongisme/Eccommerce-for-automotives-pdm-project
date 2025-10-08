package com.service;

import com.model.User;
import com.model.User.UserRole;
import java.util.HashMap;
import java.util.Map;
import java.io.*;
import java.util.regex.Pattern;

public class AuthService {
    private static AuthService instance;
    private Map<String, User> users;
    private static final String USERS_FILE = "users.dat";
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private int nextUserId;

    private AuthService() {
        loadUsers();
    }

    public static AuthService getInstance() {
        if (instance == null) {
            instance = new AuthService();
        }
        return instance;
    }

    private void loadUsers() {
        users = new HashMap<>();
        File file = new File(USERS_FILE);
        
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                @SuppressWarnings("unchecked")
                Map<String, User> loadedUsers = (Map<String, User>) ois.readObject();
                users = loadedUsers;
                // Calculate next user ID
                nextUserId = users.values().stream()
                    .mapToInt(User::getId)
                    .max()
                    .orElse(0) + 1;
                System.out.println("Loaded " + users.size() + " users from file");
            } catch (Exception e) {
                System.err.println("Error loading users: " + e.getMessage());
                initDefaultUsers();
            }
        } else {
            // First time - create default users
            initDefaultUsers();
            saveUsers();
        }
    }
    
    private void initDefaultUsers() {
        users.clear();
        nextUserId = 1;
        
        // Default mock users
        User user1 = new User(nextUserId++, "User", "user123@gmail.com", "Password123", UserRole.USER);
        User admin = new User(nextUserId++, "Admin", "admin123@gmail.com", "Password123", UserRole.ADMIN);
        
        users.put(user1.getEmail(), user1);
        users.put(admin.getEmail(), admin);
    }
    
    private void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USERS_FILE))) {
            oos.writeObject(users);
            System.out.println("Saved " + users.size() + " users to file");
        } catch (Exception e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
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
        
        User newUser = new User(nextUserId++, username, email, password, UserRole.USER);
        users.put(email, newUser);
        saveUsers(); // Persist to file
        return true;
    }
    
    // Validation methods
    public boolean isValidEmail(String email) {
        return Pattern.matches(EMAIL_REGEX, email);
    }
    
    public boolean isValidPassword(String password) {
        // At least 8 characters, 1 uppercase, 1 lowercase, 1 digit
        if (password.length() < 8) return false;
        
        boolean hasUpper = false, hasLower = false, hasDigit = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            if (Character.isLowerCase(c)) hasLower = true;
            if (Character.isDigit(c)) hasDigit = true;
        }
        
        return hasUpper && hasLower && hasDigit;
    }
    
    public boolean isValidUsername(String username) {
        // 3-20 characters, alphanumeric and underscore only
        return username.matches("^[a-zA-Z0-9_]{3,20}$");
    }
    
    public boolean emailExists(String email) {
        return users.containsKey(email);
    }
}
