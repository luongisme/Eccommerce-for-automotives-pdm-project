package com.UI.defaultpage;

import javax.swing.*;
import java.awt.*;
import com.Main.AppFrame;
import com.Main.Screen;
import com.service.UserSession;
import com.UI.admin.AdminDashboard;
import com.UI.store.StoreScreen;
import com.UI.Payment.PaymentScreen;

public class AutoPartsHomePage extends Screen {

    public AutoPartsHomePage(AppFrame appFrame) {
        super(appFrame);
        panel = new JPanel(null);
        panel.setBackground(Color.WHITE);

        // Initialize UI on the shared panel
        initUI();
    }

    @Override
    protected void initUI() {
        // ===== TOP NAVIGATION BAR =====
        JPanel topBar = new JPanel();
        topBar.setLayout(new BoxLayout(topBar, BoxLayout.X_AXIS));
        topBar.setBounds(0, 0, 1024, 70);
        topBar.setBackground(Color.WHITE);
        topBar.setOpaque(true);
            topBar.setOpaque(true);

            // Logo
            JLabel logo = new JLabel("AutoParts Pro");
            logo.setFont(new Font("Arial", Font.BOLD, 24));
            logo.setForeground(Color.BLACK);
            topBar.add(Box.createRigidArea(new Dimension(20, 0)));
            topBar.add(logo);

            UserSession session = UserSession.getInstance();
            boolean isGuest = !session.isLoggedIn();
            boolean isAdmin = session.isAdmin();

            // Push action buttons to the right side
            topBar.add(Box.createHorizontalGlue());

            // Products button (common)
            JButton productsBtn = new JButton("Products");
            productsBtn.setFont(new Font("Arial", Font.PLAIN, 14));
            productsBtn.setForeground(Color.BLACK);
            productsBtn.setContentAreaFilled(false);
            productsBtn.setBorderPainted(false);
            productsBtn.setFocusPainted(false);
            productsBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            productsBtn.addActionListener(e -> {
                if (isGuest) {
                    navigateToLogin();
                } else {
                    navigateToStore();
                }
            });
            topBar.add(Box.createRigidArea(new Dimension(16, 0)));
            topBar.add(productsBtn);

            if (isGuest) {
                // Login button
                JButton loginBtn = new JButton("Login");
                loginBtn.setFont(new Font("Arial", Font.PLAIN, 14));
                loginBtn.setForeground(Color.BLACK);
                loginBtn.setContentAreaFilled(false);
                loginBtn.setBorderPainted(false);
                loginBtn.setFocusPainted(false);
                loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                loginBtn.addActionListener(e -> navigateToLogin());
                topBar.add(Box.createRigidArea(new Dimension(12, 0)));
                topBar.add(loginBtn);

                // Register button
                JButton registerBtn = new JButton("Register");
                registerBtn.setFont(new Font("Arial", Font.BOLD, 14));
                registerBtn.setForeground(Color.WHITE);
                registerBtn.setBackground(Color.BLACK);
                registerBtn.setFocusPainted(false);
                registerBtn.setBorderPainted(false);
                registerBtn.setOpaque(true);
                registerBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                registerBtn.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));
                registerBtn.addActionListener(e -> navigateToRegister());
                topBar.add(Box.createRigidArea(new Dimension(8, 0)));
                topBar.add(registerBtn);
            } else {
                if (isAdmin) {
                    JButton adminBtn = new JButton("Admin");
                    adminBtn.setFont(new Font("Arial", Font.PLAIN, 14));
                    adminBtn.setForeground(Color.BLACK);
                    adminBtn.setContentAreaFilled(false);
                    adminBtn.setBorderPainted(false);
                    adminBtn.setFocusPainted(false);
                    adminBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    adminBtn.addActionListener(e -> navigateToAdminDashboard());
                    topBar.add(Box.createRigidArea(new Dimension(16, 0)));
                    topBar.add(adminBtn);
                }

                // Username/Admin button with icon
                JButton userBtn;
                try {
                    java.net.URL userUrl = getClass().getResource("/images/user_icon.png");
                    if (userUrl != null) {
                        ImageIcon userIconImg = new ImageIcon(userUrl);
                        Image scaledUser = userIconImg.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
                        String label = isAdmin ? "Admin" : session.getCurrentUser().getUsername();
                        userBtn = new JButton(label, new ImageIcon(scaledUser));
                        userBtn.setHorizontalTextPosition(SwingConstants.RIGHT);
                        userBtn.setIconTextGap(6);
                    } else {
                        String label = isAdmin ? "Admin" : session.getCurrentUser().getUsername();
                        userBtn = new JButton("\uD83D\uDC64  " + label);
                    }
                } catch (Exception ex) {
                    String label = isAdmin ? "Admin" : (session.getCurrentUser() != null ? session.getCurrentUser().getUsername() : "User");
                    userBtn = new JButton("\uD83D\uDC64  " + label);
                }
                userBtn.setFont(new Font("Arial", Font.PLAIN, 14));
                userBtn.setForeground(Color.BLACK);
                userBtn.setContentAreaFilled(false);
                userBtn.setBorderPainted(false);
                userBtn.setFocusPainted(false);
                userBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                userBtn.addActionListener(e -> navigateToProfile());
                topBar.add(Box.createRigidArea(new Dimension(12, 0)));
                topBar.add(userBtn);
            }

            topBar.add(Box.createRigidArea(new Dimension(4, 0)));
            topBar.add(Box.createRigidArea(new Dimension(16, 0)));

            panel.add(topBar);

            // ===== HERO SECTION =====
            JPanel heroPanel = new JPanel(null);
            heroPanel.setBackground(new Color(25, 90, 230));
            heroPanel.setBounds(0, 56, 1024, 220);

            JLabel heroTitle = new JLabel("<html><b style='line-height: 1.1;'>Premium Auto Parts<br>for Every Vehicle</b></html>");
            heroTitle.setFont(new Font("Arial", Font.BOLD, 32));
            heroTitle.setForeground(Color.WHITE);
            heroTitle.setBounds(50, 40, 580, 75);
            heroPanel.add(heroTitle);

            JLabel heroSubtitle = new JLabel("<html><div style='line-height: 1.4;'>Discover high-quality automotive parts from trusted brands. Fast<br>shipping, competitive prices, and expert customer support.</div></html>");
            heroSubtitle.setFont(new Font("Arial", Font.PLAIN, 12));
            heroSubtitle.setForeground(new Color(230, 240, 255));
            heroSubtitle.setBounds(50, 115, 650, 36);
            heroPanel.add(heroSubtitle);

            JButton shopNowBtn = new JButton("Shop Now →");
            shopNowBtn.setFont(new Font("Arial", Font.BOLD, 12));
            shopNowBtn.setBackground(Color.WHITE);
            shopNowBtn.setForeground(new Color(25, 90, 230));
            shopNowBtn.setFocusPainted(false);
            shopNowBtn.setBorder(BorderFactory.createEmptyBorder(6, 16, 6, 16));
            shopNowBtn.setBounds(50, 160, 115, 30);
            shopNowBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            shopNowBtn.setVerticalAlignment(SwingConstants.CENTER);
            shopNowBtn.setHorizontalAlignment(SwingConstants.CENTER);
            shopNowBtn.addActionListener(e -> {
                if (isGuest) {
                    navigateToLogin();
                } else {
                    navigateToStore();
                }
            });
            heroPanel.add(shopNowBtn);

            JButton browseCatBtn = new JButton("Browse Categories");
            browseCatBtn.setFont(new Font("Arial", Font.BOLD, 12));
            browseCatBtn.setBackground(new Color(255, 255, 255, 0));
            browseCatBtn.setForeground(Color.WHITE);
            browseCatBtn.setOpaque(false);
            browseCatBtn.setFocusPainted(false);
            browseCatBtn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1),
                BorderFactory.createEmptyBorder(6, 16, 6, 16)
            ));
            browseCatBtn.setBounds(175, 160, 155, 30);
            browseCatBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            browseCatBtn.setVerticalAlignment(SwingConstants.CENTER);
            browseCatBtn.setHorizontalAlignment(SwingConstants.CENTER);
            browseCatBtn.addActionListener(e -> {
                if (isGuest) {
                    navigateToLogin();
                } else {
                    navigateToStore();
                }
            });
            heroPanel.add(browseCatBtn);

            panel.add(heroPanel);

            // ===== FEATURED PRODUCTS SECTION =====
            JLabel featuredTitle = new JLabel("Featured Products");
            featuredTitle.setFont(new Font("Arial", Font.BOLD, 20));
            featuredTitle.setForeground(new Color(30, 30, 30));
            featuredTitle.setBounds(30, 290, 350, 28);
            panel.add(featuredTitle);

            JLabel featuredSubtitle = new JLabel("Popular items from our extensive catalog");
            featuredSubtitle.setFont(new Font("Arial", Font.PLAIN, 12));
            featuredSubtitle.setForeground(new Color(120, 120, 120));
            featuredSubtitle.setBounds(30, 318, 400, 18);
            panel.add(featuredSubtitle);

            JButton viewAllBtn = new JButton("View All Products →");
            viewAllBtn.setFont(new Font("Arial", Font.PLAIN, 12));
            viewAllBtn.setForeground(Color.BLACK);
            viewAllBtn.setContentAreaFilled(false);
            viewAllBtn.setBorderPainted(false);
            viewAllBtn.setFocusPainted(false);
            viewAllBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            viewAllBtn.setHorizontalAlignment(SwingConstants.RIGHT);
            viewAllBtn.setBounds(833, 293, 160, 28);
            viewAllBtn.addActionListener(e -> {
                if (isGuest) {
                    navigateToLogin();
                } else {
                    navigateToStore();
                }
            });
            panel.add(viewAllBtn);

            // Product grid 3x2
            String[][] products = {
                {"Nguyễn Việt Sơn", "Description of first product", "$10.99"},
                {"Lê Viết Hà", "Description of second product", "$10.99"},
                {"Dương Gia Lương", "Description of third product", "$10.99"},
                {"Nguyễn Đồng Nhật Huy", "Description of fourth product", "$10.99"},
                {"Lê Hoàng Phúc", "Description of fifth product", "$10.99"},
                    {"Nguyễn Vũ Thuần", "Description of sixth product", "$10.99"},
                    {"Nguyễn Minh Đức", "Description of seventh product", "$10.99"},
                    {"Nguyễn Hoàng Minh Khoa", "Description of eighth product", "$10.99"}
            };

            int startX = 30, startY = 350, cardW = 225, cardH = 200, gapX = 16, gapY = 16;
            for (int i = 0; i < products.length; i++) {
                int row = i / 4;
                int col = i % 4;
                JPanel card = createProductCard(products[i][0], products[i][1], products[i][2]);
                card.setBounds(startX + col * (cardW + gapX), startY + row * (cardH + gapY), cardW, cardH);
                panel.add(card);
            }

            // ===== SUBSCRIBE SECTION =====
            JPanel subscribePanel = new JPanel(null);
            subscribePanel.setBackground(new Color(30, 100, 220));
            subscribePanel.setBounds(0, 818, 1024, 220);

            JLabel subsTitle = new JLabel("Stay Updated with Latest Deals");
            subsTitle.setFont(new Font("Arial", Font.BOLD, 18));
            subsTitle.setForeground(Color.WHITE);
            subsTitle.setBounds(0, 55, 1024, 28);
            subsTitle.setHorizontalAlignment(SwingConstants.CENTER);
            subscribePanel.add(subsTitle);

            JLabel subsSubtitle = new JLabel("Subscribe to our newsletter and be the first to know about new products and exclusive offers");
            subsSubtitle.setFont(new Font("Arial", Font.PLAIN, 12));
            subsSubtitle.setForeground(new Color(220, 230, 250));
            subsSubtitle.setBounds(0, 85, 1024, 20);
            subsSubtitle.setHorizontalAlignment(SwingConstants.CENTER);
            subscribePanel.add(subsSubtitle);

            // Center the email field and subscribe button as a group
            int formWidth = 170 + 12 + 100; // email width + gap + button width
            int formStartX = (1024 - formWidth) / 2;

            JTextField emailField = new JTextField("Enter your email");
            emailField.setFont(new Font("Arial", Font.PLAIN, 12));
            emailField.setForeground(new Color(120, 120, 120));
            emailField.setBounds(formStartX, 125, 170, 34);
            emailField.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
            emailField.setHorizontalAlignment(JTextField.LEFT);
            subscribePanel.add(emailField);

            JButton subscribeBtn = new JButton("Subscribe");
            subscribeBtn.setFont(new Font("Arial", Font.BOLD, 12));
            subscribeBtn.setBackground(new Color(30, 140, 255));
            subscribeBtn.setForeground(Color.WHITE);
            subscribeBtn.setFocusPainted(false);
            subscribeBtn.setBorder(BorderFactory.createEmptyBorder(7, 16, 7, 16));
            subscribeBtn.setBounds(formStartX + 170 + 12, 125, 100, 34);
            subscribeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            subscribeBtn.setVerticalAlignment(SwingConstants.CENTER);
            subscribeBtn.setHorizontalAlignment(SwingConstants.CENTER);
            subscribePanel.add(subscribeBtn);

            panel.add(subscribePanel);
    }

    private void navigateToLogin() {
        appFrame.setScreen(new com.UI.login.LoginScreen(appFrame));
    }

    private void navigateToRegister() {
        appFrame.setScreen(new com.UI.register.RegisterScreen(appFrame));
    }

    private void navigateToAdminDashboard() {
        appFrame.setScreen(new AdminDashboard(appFrame));
    }

    private void navigateToStore() {
        appFrame.setScreen(new StoreScreen(appFrame));
    }

    private void navigateToCart() {
        appFrame.setScreen(new PaymentScreen(appFrame));
    }

    private void navigateToProfile() {
        appFrame.setScreen(new com.UI.Profile.ProfilePage(appFrame));
    }

    private JPanel createProductCard(String name, String description, String price) {
        JPanel card = new JPanel(null);
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(new Color(235, 235, 235), 5));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Product image placeholder
        JPanel imagePlaceholder = new JPanel();
        imagePlaceholder.setBackground(new Color(245, 245, 245));
        imagePlaceholder.setBounds(6, 6, 213, 110);
        card.add(imagePlaceholder);

        // Product name
        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 13));
        nameLabel.setForeground(new Color(30, 30, 30));
        nameLabel.setBounds(10, 120, 213, 15);
        card.add(nameLabel);

        // Product description
        JLabel descLabel = new JLabel(description);
        descLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        descLabel.setForeground(new Color(120, 120, 120));
        descLabel.setBounds(10, 135, 213, 13);
        card.add(descLabel);

        // Product price
        JLabel priceLabel = new JLabel(price);
        priceLabel.setFont(new Font("Arial", Font.BOLD, 17));
        priceLabel.setForeground(new Color(30, 30, 30));
        priceLabel.setBounds(10, 170, 213, 14);
        card.add(priceLabel);

        // Click handler
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (!UserSession.getInstance().isLoggedIn()) {
                    navigateToLogin();
                } else {
                    navigateToStore();
                }
            }
        });

        return card;
    }
}
