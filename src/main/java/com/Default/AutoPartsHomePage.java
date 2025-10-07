package com.Default;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

public class AutoPartsHomePage extends JFrame {
    
    public AutoPartsHomePage() {
        setTitle("AutoParts Pro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(688, 840);
        setResizable(false);
        setLocationRelativeTo(null);

        try {
            BufferedImage bgImage = ImageIO.read(new File("src/main/resources/images/blue_white_bg.png"));
            BackgroundPanel bgPanel = new BackgroundPanel(bgImage);
            bgPanel.setLayout(null);

            // ===== TOP NAVIGATION BAR =====
            JPanel topBar = new JPanel();
            topBar.setLayout(new BoxLayout(topBar, BoxLayout.X_AXIS));
            topBar.setBounds(0, 0, 688, 56);
            topBar.setBackground(Color.WHITE);
            topBar.setOpaque(true);

            // Logo
            JLabel logo = new JLabel("AutoParts Pro");
            logo.setFont(new Font("Arial", Font.BOLD, 16));
            logo.setForeground(Color.BLACK);
            topBar.add(Box.createRigidArea(new Dimension(20, 0)));
            topBar.add(logo);

            // Search field
            JTextField searchField = new JTextField("Hinted search text");
            searchField.setFont(new Font("Arial", Font.PLAIN, 12));
            searchField.setForeground(new Color(120, 120, 120));
            searchField.setBackground(new Color(240, 240, 240));
            searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)
            ));
            searchField.setPreferredSize(new Dimension(300, 28));
            searchField.setMaximumSize(new Dimension(300, 28));
            
            topBar.add(Box.createRigidArea(new Dimension(16, 0)));
            topBar.add(searchField);

            topBar.add(Box.createHorizontalGlue());

            // Products button
            JButton productsBtn = new JButton("Products");
            productsBtn.setFont(new Font("Arial", Font.PLAIN, 12));
            productsBtn.setForeground(Color.BLACK);
            productsBtn.setContentAreaFilled(false);
            productsBtn.setBorderPainted(false);
            productsBtn.setFocusPainted(false);
            productsBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            productsBtn.addActionListener(e -> navigateToLogin());
            topBar.add(Box.createRigidArea(new Dimension(16, 0)));
            topBar.add(productsBtn);

            // Login button
            JButton loginBtn = new JButton("Login");
            loginBtn.setFont(new Font("Arial", Font.PLAIN, 12));
            loginBtn.setForeground(Color.BLACK);
            loginBtn.setBackground(Color.WHITE);
            loginBtn.setContentAreaFilled(false);
            loginBtn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(4, 12, 4, 12)
            ));
            loginBtn.setFocusPainted(false);
            loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            loginBtn.addActionListener(e -> navigateToLogin());
            topBar.add(Box.createRigidArea(new Dimension(8, 0)));
            topBar.add(loginBtn);

            // Register button
            JButton registerBtn = new JButton("Register");
            registerBtn.setFont(new Font("Arial", Font.BOLD, 12));
            registerBtn.setBackground(Color.BLACK);
            registerBtn.setForeground(Color.WHITE);
            registerBtn.setOpaque(true);
            registerBtn.setFocusPainted(false);
            registerBtn.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));
            registerBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            registerBtn.addActionListener(e -> navigateToLogin());
            topBar.add(Box.createRigidArea(new Dimension(16, 0)));
            topBar.add(registerBtn);

            // Cart button with icon
            JButton cartBtn = null;
            try {
                java.net.URL cartUrl = getClass().getResource("/images/cart_icon.png");
                if (cartUrl != null) {
                    ImageIcon cartIconImg = new ImageIcon(cartUrl);
                    Image scaledCart = cartIconImg.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                    cartBtn = new JButton(new ImageIcon(scaledCart));
                } else {
                    cartBtn = new JButton("🛒");
                    cartBtn.setFont(new Font("Arial", Font.PLAIN, 16));
                }
            } catch (Exception ex) {
                cartBtn = new JButton("🛒");
                cartBtn.setFont(new Font("Arial", Font.PLAIN, 16));
            }
            cartBtn.setContentAreaFilled(false);
            cartBtn.setBorderPainted(false);
            cartBtn.setFocusPainted(false);
            cartBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            cartBtn.addActionListener(e -> navigateToLogin());
            topBar.add(Box.createRigidArea(new Dimension(4, 0)));
            topBar.add(cartBtn);
            topBar.add(Box.createRigidArea(new Dimension(16, 0)));

            bgPanel.add(topBar);

            // ===== HERO SECTION =====
            JLabel heroTitle = new JLabel("<html><b style='line-height: 1.1;'>Premium Auto Parts<br>for Every Vehicle</b></html>");
            heroTitle.setFont(new Font("Arial", Font.BOLD, 32));
            heroTitle.setForeground(Color.WHITE);
            heroTitle.setBounds(50, 70, 580, 75);
            bgPanel.add(heroTitle);

            JLabel heroSubtitle = new JLabel("<html><div style='line-height: 1.4;'>Discover high-quality automotive parts from trusted brands. Fast<br>shipping, competitive prices, and expert customer support.</div></html>");
            heroSubtitle.setFont(new Font("Arial", Font.PLAIN, 11));
            heroSubtitle.setForeground(new Color(220, 230, 250));
            heroSubtitle.setBounds(50, 145, 580, 36);
            bgPanel.add(heroSubtitle);

            JButton shopNowBtn = new JButton("Shop Now →");
            shopNowBtn.setFont(new Font("Arial", Font.BOLD, 12));
            shopNowBtn.setBackground(Color.WHITE);
            shopNowBtn.setForeground(new Color(30, 100, 220));
            shopNowBtn.setFocusPainted(false);
            shopNowBtn.setBorder(BorderFactory.createEmptyBorder(5, 14, 5, 14));
            shopNowBtn.setBounds(50, 190, 105, 26);
            shopNowBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            shopNowBtn.setVerticalAlignment(SwingConstants.CENTER);
            shopNowBtn.setHorizontalAlignment(SwingConstants.CENTER);
            shopNowBtn.addActionListener(e -> navigateToLogin());
            bgPanel.add(shopNowBtn);

            JButton browseCatBtn = new JButton("Browse Categories");
            browseCatBtn.setFont(new Font("Arial", Font.BOLD, 12));
            browseCatBtn.setBackground(new Color(255, 255, 255, 0));
            browseCatBtn.setForeground(Color.WHITE);
            browseCatBtn.setOpaque(false);
            browseCatBtn.setFocusPainted(false);
            browseCatBtn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1),
                BorderFactory.createEmptyBorder(4, 12, 4, 12)
            ));
            browseCatBtn.setBounds(165, 190, 145, 26);
            browseCatBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            browseCatBtn.setVerticalAlignment(SwingConstants.CENTER);
            browseCatBtn.setHorizontalAlignment(SwingConstants.CENTER);
            browseCatBtn.addActionListener(e -> navigateToLogin());
            bgPanel.add(browseCatBtn);

            // ===== FEATURED PRODUCTS SECTION =====
            JLabel featuredTitle = new JLabel("Featured Products");
            featuredTitle.setFont(new Font("Arial", Font.BOLD, 20));
            featuredTitle.setForeground(new Color(30, 30, 30));
            featuredTitle.setBounds(20, 235, 350, 28);
            bgPanel.add(featuredTitle);

            JLabel featuredSubtitle = new JLabel("Popular items from our extensive catalog");
            featuredSubtitle.setFont(new Font("Arial", Font.PLAIN, 12));
            featuredSubtitle.setForeground(new Color(120, 120, 120));
            featuredSubtitle.setBounds(20, 263, 400, 18);
            bgPanel.add(featuredSubtitle);

            JButton viewAllBtn = new JButton("View All Products →");
            viewAllBtn.setFont(new Font("Arial", Font.PLAIN, 12));
            viewAllBtn.setForeground(Color.BLACK);
            viewAllBtn.setContentAreaFilled(false);
            viewAllBtn.setBorderPainted(false);
            viewAllBtn.setFocusPainted(false);
            viewAllBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            viewAllBtn.setHorizontalAlignment(SwingConstants.RIGHT);
            viewAllBtn.setBounds(510, 238, 160, 28);
            viewAllBtn.addActionListener(e -> navigateToLogin());
            bgPanel.add(viewAllBtn);

            // Product grid 3x2
            String[][] products = {
                {"Nguyễn Việt Sơn", "Description of first product", "$10.99"},
                {"Lê Viết Hà", "Description of second product", "$10.99"},
                {"Dương Gia Lương", "Description of third product", "$10.99"},
                {"Nguyễn Đồng Nhật Huy", "Description of fourth product", "$10.99"},
                {"Lê Hoàng Phúc", "Description of fifth product", "$10.99"},
                {"Nguyên Vũ Thuần", "Description of sixth product", "$10.99"}
            };

            int startX = 20, startY = 298, cardW = 200, cardH = 145, gapX = 16, gapY = 16;
            for (int i = 0; i < products.length; i++) {
                int row = i / 3;
                int col = i % 3;
                JPanel card = createProductCard(products[i][0], products[i][1], products[i][2]);
                card.setBounds(startX + col * (cardW + gapX), startY + row * (cardH + gapY), cardW, cardH);
                bgPanel.add(card);
            }

            // ===== SUBSCRIBE SECTION =====
            JPanel subscribePanel = new JPanel(null);
            subscribePanel.setBackground(new Color(30, 100, 220));
            subscribePanel.setBounds(0, 620, 688, 220);

            JLabel subsTitle = new JLabel("Stay Updated with Latest Deals");
            subsTitle.setFont(new Font("Arial", Font.BOLD, 18));
            subsTitle.setForeground(Color.WHITE);
            subsTitle.setBounds(0, 50, 688, 28);
            subsTitle.setHorizontalAlignment(SwingConstants.CENTER);
            subscribePanel.add(subsTitle);

            JLabel subsSubtitle = new JLabel("Subscribe to our newsletter and be the first to know about new products and exclusive offers");
            subsSubtitle.setFont(new Font("Arial", Font.PLAIN, 12));
            subsSubtitle.setForeground(new Color(220, 230, 250));
            subsSubtitle.setBounds(0, 80, 688, 20);
            subsSubtitle.setHorizontalAlignment(SwingConstants.CENTER);
            subscribePanel.add(subsSubtitle);

            JTextField emailField = new JTextField("Enter your email");
            emailField.setFont(new Font("Arial", Font.PLAIN, 12));
            emailField.setForeground(new Color(120, 120, 120));
            emailField.setBounds(189, 120, 170, 34);
            emailField.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
            emailField.setHorizontalAlignment(JTextField.LEFT);
            subscribePanel.add(emailField);

            JButton subscribeBtn = new JButton("Subscribe");
            subscribeBtn.setFont(new Font("Arial", Font.BOLD, 12));
            subscribeBtn.setBackground(new Color(30, 140, 255));
            subscribeBtn.setForeground(Color.WHITE);
            subscribeBtn.setFocusPainted(false);
            subscribeBtn.setBorder(BorderFactory.createEmptyBorder(7, 16, 7, 16));
            subscribeBtn.setBounds(369, 120, 100, 34);
            subscribeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            subscribeBtn.setVerticalAlignment(SwingConstants.CENTER);
            subscribeBtn.setHorizontalAlignment(SwingConstants.CENTER);
            subscribePanel.add(subscribeBtn);

            bgPanel.add(subscribePanel);

            setContentPane(bgPanel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void navigateToLogin() {
        com.Main.AppFrame frame = new com.Main.AppFrame();
        frame.setScreen(new com.UI.login.LoginScreen(frame));
        frame.setVisible(true);
        this.dispose();
    }

    private JPanel createProductCard(String name, String description, String price) {
        JPanel card = new JPanel(null);
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(new Color(235, 235, 235), 1));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Product image placeholder
        JPanel imagePlaceholder = new JPanel();
        imagePlaceholder.setBackground(new Color(245, 245, 245));
        imagePlaceholder.setBounds(6, 6, 188, 82);
        card.add(imagePlaceholder);

        // Product name
        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 11));
        nameLabel.setForeground(new Color(30, 30, 30));
        nameLabel.setBounds(6, 94, 188, 15);
        card.add(nameLabel);

        // Product description
        JLabel descLabel = new JLabel(description);
        descLabel.setFont(new Font("Arial", Font.PLAIN, 9));
        descLabel.setForeground(new Color(120, 120, 120));
        descLabel.setBounds(6, 110, 188, 13);
        card.add(descLabel);

        // Product price
        JLabel priceLabel = new JLabel(price);
        priceLabel.setFont(new Font("Arial", Font.BOLD, 11));
        priceLabel.setForeground(new Color(30, 30, 30));
        priceLabel.setBounds(6, 125, 188, 14);
        card.add(priceLabel);

        // Click handler
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                navigateToLogin();
            }
        });

        return card;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AutoPartsHomePage frame = new AutoPartsHomePage();
            frame.setVisible(true);
        });
    }

    // Custom JPanel to paint the background image
    static class BackgroundPanel extends JPanel {
        private final Image image;

        public BackgroundPanel(Image image) {
            this.image = image;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
