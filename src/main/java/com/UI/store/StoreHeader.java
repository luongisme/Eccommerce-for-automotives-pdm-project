package com.UI.store;

import com.Main.AppFrame;
import com.model.User;
import com.service.UserSession;
import com.UI.defaultpage.AutoPartsHomePage;
import com.UI.admin.AdminDashboard;
import com.UI.login.LoginScreen;
import com.UI.register.RegisterScreen;
import com.UI.Payment.PaymentScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.function.Consumer;

public class StoreHeader extends JPanel {
    private AppFrame appFrame;
    private JButton productsBtn;
    private JButton loginBtn;
    private JButton registerBtn;
    private JLabel userLabel;
    private JLabel cartIcon;
    private Consumer<String> searchHandler; // Add search handler

    public StoreHeader(AppFrame appFrame) {
        this(appFrame, null);
    }

    public StoreHeader(AppFrame appFrame, Consumer<String> searchHandler) {
        this.appFrame = appFrame;
        this.searchHandler = searchHandler;
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
        brandLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        brandLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                navigateToHome();
            }
        });
        add(brandLabel);

        // Search bar
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBounds(280, 18, 350, 34);
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));

        JTextField searchField = new JTextField();
        String placeholder = "Search products...";

        searchField.setText(placeholder);
        searchField.setFont(new Font("Arial", Font.PLAIN, 13));
        searchField.setForeground(new Color(150, 150, 150));
        searchField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        searchPanel.add(searchField, BorderLayout.CENTER);

        JLabel searchIcon = new JLabel("🔍");
        searchIcon.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 10));
        searchPanel.add(searchIcon, BorderLayout.EAST);

        // Placeholder behaviour
        searchField.addFocusListener(new java.awt.event.FocusAdapter() {
            // Case: Entering the search bar
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (searchField.getText().equals(placeholder)) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }

            // Case: Leaving the search field
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText(placeholder);
                    searchField.setForeground(new Color(150, 150, 150));
                }
            }
        });

        // Add Enter key listener to trigger search
        searchField.addActionListener(e -> {
            String query = searchField.getText();
            System.out.println(">>> Enter key pressed in search field");
            System.out.println(">>> Query text: \"" + query + "\"");
            System.out.println(">>> Placeholder: \"" + placeholder + "\"");
            System.out.println(">>> Is placeholder? " + query.equals(placeholder));
            System.out.println(">>> Is empty after trim? " + query.trim().isEmpty());

            if (!query.equals(placeholder) && !query.trim().isEmpty()) {
                System.out.println(">>> Triggering search with: \"" + query.trim() + "\"");
                performSearch(query.trim());
            } else {
                System.out.println(">>> Search NOT triggered (placeholder or empty)");
            }
        });

        // Add click listener to search icon
        searchIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        searchIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String query = searchField.getText();
                System.out.println(">>> Search icon clicked");
                System.out.println(">>> Query text: \"" + query + "\"");
                System.out.println(">>> Placeholder: \"" + placeholder + "\"");

                if (!query.equals(placeholder) && !query.trim().isEmpty()) {
                    System.out.println(">>> Triggering search with: \"" + query.trim() + "\"");
                    performSearch(query.trim());
                } else {
                    System.out.println(">>> Search NOT triggered (placeholder or empty)");
                }
            }
        });

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
                adminBtn.addActionListener(e -> navigateToAdminDashboard());
                add(adminBtn);

                rightX += 60;
            }

            // Show user info (clickable to go to profile)
            String displayName = session.isAdmin() ? "Admin" : session.getCurrentUser().getFullName();

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

    // Navigate to admin dashboard (same AppFrame)
    private void navigateToAdminDashboard() {
        appFrame.setScreen(new AdminDashboard(appFrame));
    }

    private void navigateToHome() {
        appFrame.setScreen(new AutoPartsHomePage(appFrame));
    }

    private void handleCartClick() {
        if (!UserSession.getInstance().isLoggedIn()) {
            navigateToLogin();
        } else {
            // Get current user
            User currentUser = UserSession.getInstance().getCurrentUser();
            String userID = currentUser.getUserID();

            // Create temporary order ID
            String orderID = "TEMP_ORDER_" + System.currentTimeMillis();

            System.out.println("Opening PaymentScreen for user: " + userID + " with orderID: " + orderID);
            // Navigate to payment screen with user's cart (allow empty cart)
            appFrame.setScreen(new PaymentScreen(appFrame, userID, orderID, true));
        }
    }

    /**
     * Perform search by calling the search handler if available
     * @param query The search query
     */
    private void performSearch(String query) {
        System.out.println(">>> StoreHeader.performSearch() called with query: \"" + query + "\"");
        if (searchHandler != null) {
            System.out.println(">>> Using search handler callback");
            searchHandler.accept(query);
        } else {
            System.out.println(">>> No handler, navigating to new StoreScreen with search");
            // If no handler provided (e.g., from non-store pages), navigate to store with search
            appFrame.setScreen(new StoreScreen(appFrame, "new", 1, query));
        }
    }
}
