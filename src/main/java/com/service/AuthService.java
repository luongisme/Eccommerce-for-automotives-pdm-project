package com.service;

import com.DAO.Interface.userDAO;
import com.DAO.userDAOimpl;
import com.model.User;
import com.model.User.UserRole;

import java.util.UUID;
import java.util.regex.Pattern;

public class AuthService {
    private static AuthService instance;


    private final userDAO userDAO;

    private static final String EMAIL_REGEX =
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    private AuthService() {
        this.userDAO = new userDAOimpl();
    }

    public static AuthService getInstance() {
        if (instance == null) {
            instance = new AuthService();
        }
        return instance;
    }

    // lấy user theo email từ DB, rồi check password
    public User authenticate(String email, String password) {
        User user = userDAO.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    // Register user mới vào DB bằng DAO
    public boolean register(String username, String email, String password) {
        // 1. Kiểm tra email đã tồn tại trong DB chưa
        User existing = userDAO.findByEmail(email);
        if (existing != null) {
            return false; // user đã tồn tại
        }

        // 2. Tạo UserID (tạm thời random, sau này có thể để DB sinh)
        String userID = "U" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        // 3. Tạo đối tượng User
        User newUser = new User(
                userID,
                username,
                email,
                /* lastName */  "",
                /* midName  */  "",
                /* firstName*/  username,
                java.time.LocalDateTime.now(),
                password,
                UserRole.CUSTOMER
        );

        // 4. Ghi vào DB qua DAO
        return userDAO.insert(newUser);
    }


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
        // Hỏi thẳng DB luôn
        return userDAO.findByEmail(email) != null;
    }
}
