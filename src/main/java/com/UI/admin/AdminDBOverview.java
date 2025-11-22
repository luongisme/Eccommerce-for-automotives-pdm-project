package com.UI.admin;

import com.UI.components.TopNavigationBar;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class AdminDBOverview extends JFrame {
    // Color constants
    private static final Color COLOR_BACKGROUND = Color.WHITE;
    private static final Color COLOR_BORDER = new Color(230, 234, 240);
    private static final Color COLOR_TEXT = new Color(33, 43, 54);
    private static final Color COLOR_TEXT_SECONDARY = new Color(99, 115, 129);
    private static final Color COLOR_SUCCESS = new Color(34, 197, 94);
    private static final Color COLOR_TEXT_LIGHT = new Color(144, 158, 171);
    private static final Color COLOR_PRIMARY = new Color(0, 120, 212);

    public AdminDBOverview() {
        setTitle("Admin Dashboard");
        setSize(1000, 800);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_BACKGROUND);
        add(createTopNavigationBar(), BorderLayout.NORTH);
        add(createMainContentPanel(), BorderLayout.CENTER);
    }

    private JPanel createTopNavigationBar() {
        // ===== TOP NAVIGATION BAR =====
        JPanel topBar = new JPanel();
        topBar.setLayout(new BoxLayout(topBar, BoxLayout.X_AXIS));
        topBar.setBackground(Color.WHITE);
        topBar.setBorder(BorderFactory.createEmptyBorder(8, 24, 8, 24));

        // Logo
        JLabel logo = new JLabel("AutoParts Pro");
        logo.setFont(new Font("Arial", Font.BOLD, 16));
        logo.setForeground(Color.BLACK);
        topBar.add(logo);

        // Search field
        JTextField searchField = new JTextField("Search...");
        searchField.setFont(new Font("Arial", Font.PLAIN, 12));
        searchField.setForeground(new Color(120, 120, 120));
        searchField.setBackground(new Color(240, 240, 240));
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        searchField.setPreferredSize(new Dimension(300, 28));
        searchField.setMaximumSize(new Dimension(300, 28));
        
        searchField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (searchField.getText().equals("Search...")) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText("Search...");
                    searchField.setForeground(new Color(120, 120, 120));
                }
            }
        });
        topBar.add(Box.createRigidArea(new Dimension(16, 0)));
        topBar.add(searchField);
        topBar.add(Box.createHorizontalGlue());

        // Products button
        JButton productsBtn = new JButton("Products");
        styleNavButton(productsBtn);
        productsBtn.addActionListener(e -> {
            this.dispose();
            new AdminDBProducts().setVisible(true);
        });
        topBar.add(productsBtn);

        // Admin button
        JButton adminBtn = new JButton("Admin");
        styleNavButton(adminBtn);
        adminBtn.setFont(adminBtn.getFont().deriveFont(Font.BOLD));
        adminBtn.setForeground(Color.BLACK);
        topBar.add(Box.createRigidArea(new Dimension(16, 0)));
        topBar.add(adminBtn);

        // User button with icon
        JButton userBtn;
        try {
            java.net.URL userUrl = getClass().getResource("/images/user_icon.png");
            if (userUrl != null) {
                ImageIcon userIconImg = new ImageIcon(userUrl);
                Image scaledUser = userIconImg.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
                userBtn = new JButton("Admin", new ImageIcon(scaledUser));
                userBtn.setHorizontalTextPosition(SwingConstants.RIGHT);
                userBtn.setIconTextGap(6);
            } else {
                userBtn = new JButton("\uD83D\uDC64  Admin");
            }
        } catch (Exception ex) {
            userBtn = new JButton("\uD83D\uDC64  Admin");
        }
        styleNavButton(userBtn);
        userBtn.addActionListener(e -> {
            JFrame profileFrame = new JFrame("Profile");
            profileFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            profileFrame.add(new com.UI.Profile.ProfileScreen());
            profileFrame.pack();
            profileFrame.setSize(700, 800);
            profileFrame.setLocationRelativeTo(null);
            profileFrame.setVisible(true);
        });
        topBar.add(Box.createRigidArea(new Dimension(16, 0)));
        topBar.add(userBtn);

        // Cart button with icon
        JButton cartBtn;
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
        topBar.add(Box.createRigidArea(new Dimension(16, 0)));
        topBar.add(cartBtn);

        return topBar;
    }

    private void styleNavButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setForeground(Color.BLACK);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private JPanel createMainContentPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setOpaque(false);

        // Header with title and tabs
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setOpaque(false);

        // Title panel with left alignment
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        titlePanel.setOpaque(false);
        titlePanel.setBorder(new EmptyBorder(0, 0, 5, 0));
        titlePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        // Admin Dashboard title
        JLabel titleLabel = new JLabel("Admin Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(COLOR_TEXT);
        titlePanel.add(titleLabel);

        // Add title panel to header
        headerPanel.add(titlePanel);

        // Tabs panel with center alignment
        JPanel tabPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        tabPanel.setOpaque(false);
        tabPanel.setBorder(new EmptyBorder(5, 0, 15, 0));

        JButton overviewBtn = createTabButton("Overview", true);
        JButton productsBtn = createTabButton("Products", false);
        JButton ordersBtn = createTabButton("Orders", false);

        // Add action listeners for tab switching
        overviewBtn.addActionListener(e -> switchTab(overviewBtn, productsBtn, ordersBtn));
        productsBtn.addActionListener(e -> switchTab(productsBtn, overviewBtn, ordersBtn));
        ordersBtn.addActionListener(e -> switchTab(ordersBtn, overviewBtn, productsBtn));

        // Add buttons with increased spacing
        tabPanel.add(overviewBtn);
        tabPanel.add(Box.createRigidArea(new Dimension(60, 0)));
        tabPanel.add(productsBtn);
        tabPanel.add(Box.createRigidArea(new Dimension(60, 0)));
        tabPanel.add(ordersBtn);

        // Add tab panel to header
        headerPanel.add(tabPanel);

        // Add header to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Add dashboard content
        mainPanel.add(createDashboardGridPanel(), BorderLayout.CENTER);

        return mainPanel;
    }

    private JPanel createDashboardGridPanel() {
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new BoxLayout(gridPanel, BoxLayout.Y_AXIS));
        gridPanel.setOpaque(false);

        // Row 1: Stat Cards
        JPanel statsRow = new JPanel(new GridLayout(1, 4, 16, 0));
        statsRow.setOpaque(false);
        statsRow.add(createStatCard("Total Revenue", "600.36", "+12% from last month", "$"));
        statsRow.add(createStatCard("Total Orders", "2", "+8% from last month", ""));
        statsRow.add(createStatCard("Active Products", "16", "0 product low in stock", ""));
        statsRow.add(createStatCard("Customers", "2,543", "+15% from last month", ""));

        // Row 2: Recent Orders & Low Stock
        JPanel middleRow = new JPanel();
        middleRow.setLayout(new BoxLayout(middleRow, BoxLayout.X_AXIS));
        middleRow.setOpaque(false);
        middleRow.setBorder(new EmptyBorder(16, 0, 16, 0));

        // Recent Orders Card
        JPanel recentOrdersCard = createCardPanel("Recent Orders");
        recentOrdersCard.add(createOrderItem("Order #2", "$519.96", "delivered"));
        recentOrdersCard.add(Box.createRigidArea(new Dimension(0, 10)));
        recentOrdersCard.add(createOrderItem("Order #1", "$129.97", "delivered"));

        // Low Stock Card
        JPanel lowStockCard = createCardPanel("Low Stock Alert");
        JLabel noItemsLabel = new JLabel("No items low in stock");
        noItemsLabel.setForeground(COLOR_TEXT_LIGHT);
        noItemsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        lowStockCard.add(noItemsLabel);

        middleRow.add(recentOrdersCard);
        middleRow.add(Box.createRigidArea(new Dimension(16, 0)));
        middleRow.add(lowStockCard);

        // Row 3: Sales Analytics & Top Selling Categories
        JPanel bottomRow = new JPanel();
        bottomRow.setLayout(new BoxLayout(bottomRow, BoxLayout.X_AXIS));
        bottomRow.setOpaque(false);

        // Sales Analytics Card
        JPanel salesCard = createCardPanel("Sales Analytics");
        salesCard.add(createStatRow("This Month", "$659.93"));
        salesCard.add(Box.createRigidArea(new Dimension(0, 10)));
        salesCard.add(createStatRow("Average Order Value", "$329.97"));
        salesCard.add(Box.createRigidArea(new Dimension(0, 10)));
        salesCard.add(createStatRow("Conversion Rate", "3.2%"));
        salesCard.add(Box.createRigidArea(new Dimension(0, 10)));
        salesCard.add(createStatRow("Customer Retention", "68%"));

        // Top Selling Categories Card
        JPanel categoriesCard = createCardPanel("Top Selling Categories");
        categoriesCard.add(createStatRow("Engine Parts", "32%"));
        categoriesCard.add(Box.createRigidArea(new Dimension(0, 10)));
        categoriesCard.add(createStatRow("Brakes", "24%"));
        categoriesCard.add(Box.createRigidArea(new Dimension(0, 10)));
        categoriesCard.add(createStatRow("Wheels & Tires", "18%"));
        categoriesCard.add(Box.createRigidArea(new Dimension(0, 10)));
        categoriesCard.add(createStatRow("Electrical", "15%"));
        categoriesCard.add(Box.createRigidArea(new Dimension(0, 10)));
        categoriesCard.add(createStatRow("Suspension", "11%"));

        bottomRow.add(salesCard);
        bottomRow.add(Box.createRigidArea(new Dimension(16, 0)));
        bottomRow.add(categoriesCard);

        // Add all rows to the main grid
        gridPanel.add(statsRow);
        gridPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        gridPanel.add(middleRow);
        gridPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        gridPanel.add(bottomRow);

        return gridPanel;
    }

    private JPanel createStatCard(String title, String value, String subtext, String prefix) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_BORDER),
            new EmptyBorder(16, 16, 16, 16)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        // Title
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        titleLabel.setForeground(COLOR_TEXT_SECONDARY);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Value with prefix (if any)
        JPanel valuePanel = new JPanel();
        valuePanel.setLayout(new BoxLayout(valuePanel, BoxLayout.X_AXIS));
        valuePanel.setOpaque(false);
        valuePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        if (!prefix.isEmpty()) {
            JLabel prefixLabel = new JLabel(prefix);
            prefixLabel.setFont(new Font("Arial", Font.BOLD, 24));
            prefixLabel.setForeground(COLOR_TEXT);
            valuePanel.add(prefixLabel);
        }

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 24));
        valueLabel.setForeground(COLOR_TEXT);
        valuePanel.add(valueLabel);

        // Subtext
        JLabel subtextLabel = new JLabel(subtext);
        subtextLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        subtextLabel.setForeground(subtext.startsWith("+") ? COLOR_SUCCESS : COLOR_TEXT_SECONDARY);
        subtextLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Add components to card
        card.add(titleLabel);
        card.add(Box.createRigidArea(new Dimension(0, 8)));
        card.add(valuePanel);
        card.add(Box.createRigidArea(new Dimension(0, 4)));
        card.add(subtextLabel);

        return card;
    }

    private JButton createTabButton(String text, boolean selected) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", selected ? Font.BOLD : Font.PLAIN, 16));
        button.setForeground(selected ? COLOR_PRIMARY : COLOR_TEXT_SECONDARY);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        if (selected) {
            button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, COLOR_PRIMARY),
                new EmptyBorder(8, 0, 6, 0)
            ));
        } else {
            button.setBorder(new EmptyBorder(8, 0, 8, 0));
        }

        return button;
    }

    private void switchTab(JButton selectedBtn, JButton... otherBtns) {
        // Update button styles
        selectedBtn.setFont(selectedBtn.getFont().deriveFont(Font.BOLD));
        selectedBtn.setForeground(COLOR_PRIMARY);
        selectedBtn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, COLOR_PRIMARY),
            new EmptyBorder(8, 0, 6, 0)
        ));
        
        for (JButton btn : otherBtns) {
            btn.setFont(btn.getFont().deriveFont(Font.PLAIN));
            btn.setForeground(COLOR_TEXT_SECONDARY);
            btn.setBorder(new EmptyBorder(8, 0, 8, 0));
        }
        
        // Handle navigation based on the selected tab
        String tabName = selectedBtn.getText();
        switch (tabName) {
            case "Overview":
                // Already on Overview
                break;
            case "Products":
                this.dispose();
                new AdminDBProducts().setVisible(true);
                break;
            case "Orders":
                this.dispose();
                new AdminDBOrders().setVisible(true);
                break;
        }
    }

    private JPanel createCardPanel(String title) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_BORDER),
            new EmptyBorder(16, 16, 16, 16)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        // Title
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(COLOR_TEXT);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        titleLabel.setBorder(new EmptyBorder(0, 0, 16, 0));
        card.add(titleLabel);

        return card;
    }

    private JPanel createOrderItem(String orderId, String price, String status) {
        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.X_AXIS));
        itemPanel.setOpaque(false);
        itemPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        itemPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        // Order ID
        JLabel orderLabel = new JLabel(orderId);
        orderLabel.setFont(new Font("Arial", Font.BOLD, 14));
        orderLabel.setForeground(COLOR_TEXT);

        // Price
        JLabel priceLabel = new JLabel(price);
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        priceLabel.setForeground(COLOR_TEXT_SECONDARY);

        // Status
        JLabel statusLabel = new JLabel(status);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        statusLabel.setForeground(COLOR_SUCCESS);
        statusLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_SUCCESS),
            new EmptyBorder(2, 8, 2, 8)
        ));

        itemPanel.add(orderLabel);
        itemPanel.add(Box.createHorizontalGlue());
        itemPanel.add(priceLabel);
        itemPanel.add(Box.createRigidArea(new Dimension(16, 0)));
        itemPanel.add(statusLabel);

        return itemPanel;
    }

    private JPanel createStatRow(String label, String value) {
        JPanel rowPanel = new JPanel();
        rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.X_AXIS));
        rowPanel.setOpaque(false);
        rowPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        rowPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(new Font("Arial", Font.PLAIN, 14));
        labelComponent.setForeground(COLOR_TEXT_SECONDARY);

        JLabel valueComponent = new JLabel(value);
        valueComponent.setFont(new Font("Arial", Font.BOLD, 14));
        valueComponent.setForeground(COLOR_TEXT);

        rowPanel.add(labelComponent);
        rowPanel.add(Box.createHorizontalGlue());
        rowPanel.add(valueComponent);

        return rowPanel;
    }

    public static void main(String[] args) {
        // Set Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Run the application
        SwingUtilities.invokeLater(() -> {
            AdminDBOverview adminDB = new AdminDBOverview();
            adminDB.setVisible(true);
        });
    }
}