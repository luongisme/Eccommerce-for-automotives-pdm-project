package com.UI.register;

import com.Main.AppFrame;
import com.Main.Screen;
import com.service.AuthService;
import com.service.UserSession;
import com.UI.login.LoginScreen;
import com.UI.store.StoreScreen;

import javax.swing.*;
import java.awt.*;

public class RegisterScreen extends Screen {
    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passField;
    private JPasswordField confirmPassField;
    private JButton registerBtn;
    private JButton loginLinkBtn;
    private JLabel errorLabel;

    public RegisterScreen(AppFrame appFrame) {
        super(appFrame);
        panel.setLayout(null);
        panel.setBackground(new Color(245, 245, 245));
        initUI();
    }

    @Override
    protected void initUI() {
        int frameW = 700;
        int brandH = 56;

        // Brand bar
        JPanel brandBar = new JPanel(null);
        brandBar.setOpaque(true);
        brandBar.setBackground(new Color(245, 245, 245));
        brandBar.setBounds(0, 0, frameW, brandH);

        JLabel brand = new JLabel("AutoParts Pro");
        brand.setForeground(Color.BLACK);
        brand.setFont(new Font("Arial", Font.BOLD, 30));
        brand.setBounds(18, 14, 300, 28);
        brandBar.add(brand);

        panel.add(brandBar);

        // Title
        JLabel title = new JLabel("Create Account");
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setForeground(Color.BLACK);
        title.setBounds(220, 80, 350, 50);
        panel.add(title);

        // Form panel
        JPanel formPanel = new JPanel(null);
        formPanel.setOpaque(false);
        formPanel.setBounds(160, 150, 380, 450);

        int yPos = 0;

        // Username field
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setForeground(Color.BLACK);
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        usernameLabel.setBounds(0, yPos, 200, 25);
        formPanel.add(usernameLabel);
        yPos += 30;

        usernameField = new JTextField();
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setBounds(0, yPos, 380, 36);
        usernameField.setBackground(Color.WHITE);
        usernameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        formPanel.add(usernameField);
        yPos += 50;

        // Email field
        JLabel emailLabel = new JLabel("Email address");
        emailLabel.setForeground(Color.BLACK);
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        emailLabel.setBounds(0, yPos, 200, 25);
        formPanel.add(emailLabel);
        yPos += 30;

        emailField = new JTextField();
        emailField.setFont(new Font("Arial", Font.PLAIN, 14));
        emailField.setBounds(0, yPos, 380, 36);
        emailField.setBackground(Color.WHITE);
        emailField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        formPanel.add(emailField);
        yPos += 50;

        // Password field
        JLabel passLabel = new JLabel("Password");
        passLabel.setForeground(Color.BLACK);
        passLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        passLabel.setBounds(0, yPos, 200, 25);
        formPanel.add(passLabel);
        yPos += 30;

        passField = new JPasswordField();
        passField.setFont(new Font("Arial", Font.PLAIN, 14));
        passField.setBounds(0, yPos, 380, 36);
        passField.setBackground(Color.WHITE);
        passField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        formPanel.add(passField);
        yPos += 50;

        // Confirm password field
        JLabel confirmPassLabel = new JLabel("Confirm Password");
        confirmPassLabel.setForeground(Color.BLACK);
        confirmPassLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        confirmPassLabel.setBounds(0, yPos, 200, 25);
        formPanel.add(confirmPassLabel);
        yPos += 30;

        confirmPassField = new JPasswordField();
        confirmPassField.setFont(new Font("Arial", Font.PLAIN, 14));
        confirmPassField.setBounds(0, yPos, 380, 36);
        confirmPassField.setBackground(Color.WHITE);
        confirmPassField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        formPanel.add(confirmPassField);
        yPos += 50;

        // Error label (positioned above register button)
        errorLabel = new JLabel("", SwingConstants.CENTER);
        errorLabel.setForeground(Color.RED);
        errorLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        errorLabel.setBounds(0, yPos, 380, 30);
        formPanel.add(errorLabel);
        yPos += 35;

        // Register button
        registerBtn = new JButton("Register");
        registerBtn.setBounds(0, yPos, 380, 40);
        registerBtn.setBackground(Color.BLACK);
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFont(new Font("Arial", Font.BOLD, 17));
        registerBtn.setFocusPainted(false);
        registerBtn.setBorder(BorderFactory.createEmptyBorder());
        registerBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerBtn.addActionListener(e -> handleRegister());
        formPanel.add(registerBtn);

        panel.add(formPanel);

        // Login link
        JPanel loginPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        loginPanel.setOpaque(false);
        loginPanel.setBounds(200, 610, 300, 30);

        JLabel loginText = new JLabel("Already have an account? ");
        loginText.setFont(new Font("Arial", Font.PLAIN, 13));
        loginText.setForeground(Color.BLACK);

        loginLinkBtn = new JButton("Login");
        loginLinkBtn.setFont(new Font("Arial", Font.BOLD, 13));
        loginLinkBtn.setForeground(new Color(37, 99, 235));
        loginLinkBtn.setBackground(new Color(245, 245, 245));
        loginLinkBtn.setBorder(BorderFactory.createEmptyBorder());
        loginLinkBtn.setFocusPainted(false);
        loginLinkBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginLinkBtn.addActionListener(e -> appFrame.setScreen(new LoginScreen(appFrame)));

        loginPanel.add(loginText);
        loginPanel.add(loginLinkBtn);
        panel.add(loginPanel);
    }

    private void handleRegister() {
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passField.getPassword());
        String confirmPassword = new String(confirmPassField.getPassword());
        AuthService authService = AuthService.getInstance();

        // Clear previous error
        errorLabel.setText("");

        // Validation
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            errorLabel.setText("All fields are required");
            return;
        }

        // Username validation
        if (!authService.isValidUsername(username)) {
            errorLabel.setText("Username must be 3-20 characters (letters, numbers, underscore)");
            return;
        }

        // Email validation
        if (!authService.isValidEmail(email)) {
            errorLabel.setText("Invalid email format");
            return;
        }

        // Check if email already exists
        if (authService.emailExists(email)) {
            errorLabel.setText("Email already registered");
            return;
        }

        // Password match validation
        if (!password.equals(confirmPassword)) {
            errorLabel.setText("Passwords do not match");
            return;
        }

        // Password strength validation
        if (!authService.isValidPassword(password)) {
            errorLabel.setText("Password must be 8+ chars with uppercase, lowercase, and digit");
            return;
        }

        // Register user
        boolean success = authService.register(username, email, password);

        if (success) {
            // Show success message
            JOptionPane.showMessageDialog(this.panel, 
                "Registration successful! Welcome " + username + "!", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            
            // Auto-login after registration
            com.model.User user = authService.authenticate(email, password);
            if (user != null) {
                UserSession.getInstance().login(user);
                appFrame.setScreen(new StoreScreen(appFrame));
            }
        } else {
            errorLabel.setText("Registration failed. Please try again.");
        }
    }
}
