package com.UI.store;

import com.Main.AppFrame;
import com.service.UserSession;
import com.UI.login.LoginScreen;
import com.UI.register.RegisterScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

public class StoreHeader extends JPanel {
    private AppFrame appFrame;
    private JButton productsBtn;
    private JButton loginBtn;
    private JButton registerBtn;
    private JLabel userLabel;
    private JLabel cartIcon;

    public StoreHeader(AppFrame appFrame) {
        this.appFrame = appFrame;
        setLayout(null);
        setBackground(new Color(245, 245, 245));
        setPreferredSize(new Dimension(1024, 70));
        initUI();
    }

    private void initUI() {
        // Brand logo
        JLabel brandLabel = new JLabel("AutoParts Pro");
        brandLabel.setFont(new Font("Arial", Font.BOLD, 24));
        brandLabel.setForeground(Color.BLACK);
        brandLabel.setBounds(30, 20, 200, 30);
        add(brandLabel);

        // Search bar
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBounds(280, 18, 350, 34);
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));

        JTextField searchField = new JTextField("Hinted search text");
        searchField.setFont(new Font("Arial", Font.PLAIN, 13));
        searchField.setForeground(new Color(150, 150, 150));
        searchField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        searchPanel.add(searchField, BorderLayout.CENTER);

        JLabel searchIcon = new JLabel("🔍");
        searchIcon.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 10));
        searchPanel.add(searchIcon, BorderLayout.EAST);

        add(searchPanel);

        // Right side buttons
        int rightX = 660;

        // Products button
        productsBtn = new JButton("Products");
        productsBtn.setFont(new Font("Arial", Font.PLAIN, 14));
        productsBtn.setForeground(Color.BLACK);
        productsBtn.setBackground(new Color(245, 245, 245));
        productsBtn.setBorder(BorderFactory.createEmptyBorder());
        productsBtn.setFocusPainted(false);
        productsBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        productsBtn.setBounds(rightX, 23, 70, 24);
        productsBtn.addActionListener(e -> navigateToStore());
        add(productsBtn);

        rightX += 80;

        // Check if user is logged in
        UserSession session = UserSession.getInstance();
        if (session.isLoggedIn()) {
            // Show Admin button for admin users
            if (session.isAdmin()) {
                JButton adminBtn = new JButton("Admin");
                adminBtn.setFont(new Font("Arial", Font.PLAIN, 14));
                adminBtn.setForeground(Color.BLACK);
                adminBtn.setBackground(new Color(245, 245, 245));
                adminBtn.setBorder(BorderFactory.createEmptyBorder());
                adminBtn.setFocusPainted(false);
                adminBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                adminBtn.setBounds(rightX, 23, 50, 24);
                // adminBtn.addActionListener(e -> navigateToAdminDashboard());
                add(adminBtn);
                
                rightX += 60;
            }
            
            // Show user info (clickable to go to profile)
            String displayName = session.isAdmin() ? "Admin" : session.getCurrentUser().getUsername();
            
            userLabel = new JLabel(displayName);
            userLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            userLabel.setForeground(Color.BLACK);
            userLabel.setBounds(rightX + 30, 23, 100, 24);
            userLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            userLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    navigateToProfile();
                }
            });
            add(userLabel);

            JLabel profileIcon = new JLabel();
            ImageIcon userIcon = loadImage("/images/Sample_User_Icon.png", 24, 24);
            if (userIcon != null) {
                profileIcon.setIcon(userIcon);
            } else {
                profileIcon.setText("👤");
                profileIcon.setFont(new Font("Arial", Font.PLAIN, 20));
            }
            profileIcon.setBounds(rightX, 20, 30, 30);
            profileIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
            profileIcon.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    navigateToProfile();
                }
            });
            add(profileIcon);

            rightX += 140;
        } else {
            // Show login/register buttons
            loginBtn = new JButton("Login");
            loginBtn.setFont(new Font("Arial", Font.PLAIN, 14));
            loginBtn.setForeground(Color.BLACK);
            loginBtn.setBackground(new Color(245, 245, 245));
            loginBtn.setBorder(BorderFactory.createEmptyBorder());
            loginBtn.setFocusPainted(false);
            loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            loginBtn.setBounds(rightX, 23, 50, 24);
            loginBtn.addActionListener(e -> navigateToLogin());
            add(loginBtn);

            rightX += 60;

            registerBtn = new JButton("Register");
            registerBtn.setFont(new Font("Arial", Font.PLAIN, 14));
            registerBtn.setForeground(Color.WHITE);
            registerBtn.setBackground(new Color(45, 45, 45));
            registerBtn.setBorder(BorderFactory.createEmptyBorder(6, 16, 6, 16));
            registerBtn.setFocusPainted(false);
            registerBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            registerBtn.setBounds(rightX, 18, 90, 34);
            registerBtn.addActionListener(e -> navigateToRegister());
            add(registerBtn);

            rightX += 100;
        }

        // Cart icon
        cartIcon = new JLabel();
        ImageIcon cartImage = loadImage("/images/cart.jpg", 28, 28);
        if (cartImage != null) {
            cartIcon.setIcon(cartImage);
        } else {
            cartIcon.setText("🛒");
            cartIcon.setFont(new Font("Arial", Font.PLAIN, 24));
        }
        cartIcon.setBounds(rightX, 20, 30, 30);
        cartIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cartIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleCartClick();
            }
        });
        add(cartIcon);
    }

    private ImageIcon loadImage(String path, int width, int height) {
        try {
            URL url = getClass().getResource(path);
            if (url != null) {
                ImageIcon icon = new ImageIcon(url);
                Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                return new ImageIcon(scaledImage);
            }
        } catch (Exception e) {
            System.err.println("Failed to load image: " + path);
        }
        return null;
    }

    private void navigateToStore() {
        appFrame.setScreen(new StoreScreen(appFrame));
    }

    private void navigateToLogin() {
        appFrame.setScreen(new LoginScreen(appFrame));
    }

    private void navigateToRegister() {
        appFrame.setScreen(new RegisterScreen(appFrame));
    }

    private void navigateToProfile() {
        appFrame.setScreen(new com.UI.Profile.ProfilePage(appFrame));
    }

    // // Navigate to admin dashboard
    // private void navigateToAdminDashboard() {
    //     appFrame.setScreen(new com.UI.admin.AdminDashboard());
    // }

    private void handleCartClick() {
        if (!UserSession.getInstance().isLoggedIn()) {
            navigateToLogin();
        } else {
            // Navigate to cart page (to be implemented)
            JOptionPane.showMessageDialog(this, "Cart functionality coming soon!");
        }
    }
}
