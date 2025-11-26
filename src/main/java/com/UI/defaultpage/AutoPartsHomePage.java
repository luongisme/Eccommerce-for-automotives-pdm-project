package com.UI.defaultpage;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import com.Main.AppFrame;
import com.Main.Screen;
import com.model.Product;
import com.service.ProductService;
import com.service.UserSession;
import com.UI.admin.AdminDashboard;
import com.UI.store.StoreScreen;
import com.UI.Payment.PaymentScreen;

public class AutoPartsHomePage extends Screen {

    private final ProductService productService = ProductService.getInstance();

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
                    navigateToLoginThenStore();
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
                    navigateToLoginThenStore();
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
                    navigateToLoginThenStore();
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
                    navigateToLoginThenStore();
                } else {
                    navigateToStore();
                }
            });
            panel.add(viewAllBtn);

            // Product grid 4x2 populated from database
            int cardW = 220;
            int cardH = 280;
            int gapX = 24;
            int gapY = 24;
            int startX = 30;
            int startY = 350;

            java.util.List<Product> allProducts = productService.getAllProducts();
            java.util.List<Product> featured = allProducts;
            if (allProducts.size() > 8) {
                featured = productService.sortProducts(allProducts, "new")
                        .subList(0, 8);
            }

            NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.US);

            for (int i = 0; i < featured.size(); i++) {
                Product p = featured.get(i);

                int row = i / 4;
                int col = i % 4;
                int x = startX + col * (cardW + gapX);
                int y = startY + row * (cardH + gapY);

                JPanel card = new JPanel(null);
                card.setBackground(Color.WHITE);
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(220, 220, 220), 5),
                    BorderFactory.createEmptyBorder(12, 12, 12, 12)
                ));
                card.setBounds(x, y, cardW, cardH);

                // Product image
                JLabel imgLabel = new JLabel();
                imgLabel.setOpaque(true);
                imgLabel.setBackground(new Color(245, 245, 245));
                imgLabel.setBounds(5, 5, cardW, 150);

                String imgFile = p.getImageUrl();
                if (imgFile != null && !imgFile.trim().isEmpty()) {
                    String path = "/images/Product/" + imgFile.trim();
                    URL url = getClass().getResource(path);
                    if (url != null) {
                        ImageIcon icon = new ImageIcon(url);
                        Image scaled = icon.getImage().getScaledInstance(cardW - 10, 150, Image.SCALE_SMOOTH);
                        imgLabel.setIcon(new ImageIcon(scaled));
                        imgLabel.setText("");
                    }
                }
                card.add(imgLabel);

                // Product name (2-line max, bold)
                String name = p.getName() != null ? p.getName() : "";
                if (name.length() > 40) {
                    name = name.substring(0, 37) + "...";
                }
                JLabel nameLabel = new JLabel(name);
                nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
                nameLabel.setForeground(new Color(30, 30, 30));
                nameLabel.setBounds(5, 156, cardW, 20);
                card.add(nameLabel);

                // Brand (small, muted)
                String brand = p.getBrand() != null ? p.getBrand() : "";
                JLabel brandLabel = new JLabel(brand);
                brandLabel.setFont(new Font("Arial", Font.PLAIN, 11));
                brandLabel.setForeground(new Color(130, 130, 130));
                brandLabel.setBounds(5, 176, cardW, 16);
                card.add(brandLabel);

                // Short description (optional)
                String desc = p.getDescription() != null ? p.getDescription() : "";
                if (desc.length() > 70) {
                    desc = desc.substring(0, 67) + "...";
                }
                JLabel descLabel = new JLabel(desc);
                descLabel.setFont(new Font("Arial", Font.PLAIN, 11));
                descLabel.setForeground(new Color(120, 120, 120));
                descLabel.setBounds(5, 194, cardW, 30);
                card.add(descLabel);

                // Price (highlighted)
                JLabel priceLabel = new JLabel(currency.format(p.getPrice()));
                priceLabel.setFont(new Font("Arial", Font.BOLD, 15));
                priceLabel.setForeground(new Color(25, 90, 230));
                priceLabel.setBounds(5, 224, cardW, 22);
                card.add(priceLabel);

                // Add to cart button (same behavior as before)
                JButton addBtn = new JButton("Add to Cart");
                addBtn.setFont(new Font("Arial", Font.BOLD, 12));
                addBtn.setBackground(Color.BLACK);
                addBtn.setForeground(Color.WHITE);
                addBtn.setFocusPainted(false);
                addBtn.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
                addBtn.setOpaque(true);
                addBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                addBtn.setBounds(0, 248, cardW, 32);
                addBtn.addActionListener(e -> {
                    if (!UserSession.getInstance().isLoggedIn()) {
                        navigateToLoginThenStore();
                    } else {
                        navigateToCart();
                    }
                });
                card.add(addBtn);

                panel.add(card);
            }
        }

        // ===== NAVIGATION HELPERS =====
        private void navigateToLogin() {
            appFrame.setScreen(new com.UI.login.LoginScreen(appFrame));
        }

        private void navigateToLoginThenStore() {
            appFrame.setScreen(new com.UI.login.LoginScreen(appFrame,
                    () -> appFrame.setScreen(new StoreScreen(appFrame))));
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
    }
