package com.UI.Cart;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class AdminCartPage extends JFrame {

    // Define Colors based on the UI design
    private static final Color BG_COLOR = Color.WHITE;
    private static final Color TEXT_DARK = new Color(30, 30, 30);
    private static final Color TEXT_GRAY = new Color(100, 100, 100);
    private static final Color PRICE_BLUE = new Color(0, 100, 255);
    private static final Color SUCCESS_GREEN = new Color(0, 166, 80);
    private static final Color BUTTON_DARK = new Color(40, 40, 40);
    private static final Color BORDER_COLOR = new Color(230, 230, 230);
    private static final Color COLOR_PRIMARY = new Color(0, 120, 212); 

    public AdminCartPage() {
        setTitle("AutoParts Pro - Admin Shopping Cart");
        setSize(1024, 1200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setBackground(BG_COLOR);

        // Main Scroll Pane (in case height exceeds screen)
        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setBackground(BG_COLOR);
        JScrollPane scrollPane = new JScrollPane(mainContent);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);

        // 1. Header (Updated with Admin buttons)
        mainContent.add(createHeader(), BorderLayout.NORTH);

        // 2. Body (Cart + Summary)
        JPanel bodyPanel = new JPanel(new GridBagLayout());
        bodyPanel.setBackground(BG_COLOR);
        bodyPanel.setBorder(new EmptyBorder(40, 60, 60, 60)); // Margins

        GridBagConstraints gbc = new GridBagConstraints();
        
        // Title: "Shopping cart"
        JLabel pageTitle = new JLabel("Shopping cart");
        pageTitle.setFont(new Font("SansSerif", Font.BOLD, 32));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 30, 0);
        bodyPanel.add(pageTitle, gbc);

        // LEFT COLUMN: Cart Items
        JPanel cartListPanel = new JPanel();
        cartListPanel.setLayout(new BoxLayout(cartListPanel, BoxLayout.Y_AXIS));
        cartListPanel.setBackground(BG_COLOR);

        // Add the specific UserCartItem 
        cartListPanel.add(new UserCartItem(
                "Spark Plug Set (4-Pack)",
                "SparkMax",
                "ENG-SP-002",
                45.99
        ));
        
        JSeparator separator = new JSeparator();
        separator.setForeground(BORDER_COLOR);
        cartListPanel.add(Box.createVerticalStrut(20));
        cartListPanel.add(separator);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        gbc.weightx = 0.65; // 65% width
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 40); 
        gbc.anchor = GridBagConstraints.NORTHWEST;
        bodyPanel.add(cartListPanel, gbc);

        // RIGHT COLUMN: Order Summary
        JPanel summaryPanel = createOrderSummary();
        gbc.gridx = 1; gbc.gridy = 1;
        gbc.weightx = 0.35; // 35% width
        gbc.weighty = 0; 
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.anchor = GridBagConstraints.NORTH;
        bodyPanel.add(summaryPanel, gbc);

        mainContent.add(bodyPanel, BorderLayout.CENTER);

        // 3. Footer
        mainContent.add(createFooter(), BorderLayout.SOUTH);
    }

    // =================================================================================
    // HEADER SECTION
    // =================================================================================
    private JPanel createHeader() {
        JPanel topBar = new JPanel();
        topBar.setLayout(new BoxLayout(topBar, BoxLayout.X_AXIS));
        topBar.setPreferredSize(new Dimension(getWidth(), 56));
        topBar.setBackground(Color.WHITE);
        topBar.setOpaque(true);
        topBar.setBorder(new MatteBorder(0, 0, 1, 0, BORDER_COLOR));

        // Logo
        JLabel logo = new JLabel("AutoParts Pro");
        logo.setFont(new Font("Arial", Font.BOLD, 16));
        logo.setForeground(Color.BLACK);
        topBar.add(Box.createRigidArea(new Dimension(20, 0)));
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

        topBar.add(Box.createRigidArea(new Dimension(16, 0)));
        topBar.add(searchField);

        topBar.add(Box.createHorizontalGlue());

        // 1. Products button
        JButton productsBtn = new JButton("Products");
        productsBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        productsBtn.setForeground(Color.BLACK);
        productsBtn.setContentAreaFilled(false);
        productsBtn.setBorderPainted(false);
        productsBtn.setFocusPainted(false);
        productsBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        productsBtn.addActionListener(e -> navigateToProducts());
        topBar.add(Box.createRigidArea(new Dimension(16, 0)));
        topBar.add(productsBtn);

        // 2. Admin button (Navigation)
        JButton adminBtn = new JButton("Admin");
        adminBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        adminBtn.setForeground(Color.BLACK);
        adminBtn.setContentAreaFilled(false);
        adminBtn.setBorderPainted(false);
        adminBtn.setFocusPainted(false);
        adminBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        adminBtn.addActionListener(e -> navigateToAdminDashboard());
        topBar.add(Box.createRigidArea(new Dimension(16, 0)));
        topBar.add(adminBtn);

        // 3. Profile button (Labeled "Admin")
        JButton userBtn;
        try {
            java.net.URL userUrl = getClass().getResource("/images/user_icon.png");
            if (userUrl != null) {
                ImageIcon userIconImg = new ImageIcon(userUrl);
                Image scaledUser = userIconImg.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
                userBtn = new JButton("Admin", new ImageIcon(scaledUser)); // Label set to "Admin"
                userBtn.setHorizontalTextPosition(SwingConstants.RIGHT);
                userBtn.setIconTextGap(6);
            } else {
                userBtn = new JButton("\uD83D\uDC64  Admin"); // Fallback label "Admin"
            }
        } catch (Exception e) {
            userBtn = new JButton("\uD83D\uDC64  Admin");
        }
        userBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        userBtn.setForeground(Color.BLACK);
        userBtn.setContentAreaFilled(false);
        userBtn.setBorderPainted(false);
        userBtn.setFocusPainted(false);
        userBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        userBtn.addActionListener(e -> showUserMenu());
        topBar.add(Box.createRigidArea(new Dimension(12, 0)));
        topBar.add(userBtn);

        // 4. Cart button
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
        } catch (Exception e) {
            cartBtn = new JButton("🛒");
            cartBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        }
        cartBtn.setContentAreaFilled(false);
        cartBtn.setBorderPainted(false);
        cartBtn.setFocusPainted(false);
        cartBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cartBtn.addActionListener(e -> navigateToCart());
        topBar.add(Box.createRigidArea(new Dimension(4, 0)));
        topBar.add(cartBtn);
        topBar.add(Box.createRigidArea(new Dimension(16, 0)));

        return topBar;
    }

    // --- Navigation Helpers ---
    private void navigateToAdminDashboard() {
        JOptionPane.showMessageDialog(this, "Navigating to Admin Dashboard...");
    }

    private void navigateToProducts() {
        JOptionPane.showMessageDialog(this, "Navigating to Products...");
    }

    private void navigateToCart() {
        // Already on cart page
    }

    private void showUserMenu() {
        JPopupMenu userMenu = new JPopupMenu();
        JMenuItem profileItem = new JMenuItem("My Profile");
        profileItem.addActionListener(e -> showProfile());
        JMenuItem ordersItem = new JMenuItem("My Orders");
        ordersItem.addActionListener(e -> showOrders());
        JMenuItem logoutItem = new JMenuItem("Logout");
        logoutItem.addActionListener(e -> logout());
        
        userMenu.add(profileItem);
        userMenu.add(ordersItem);
        userMenu.addSeparator();
        userMenu.add(logoutItem);
        
        JOptionPane.showMessageDialog(this, "Admin Menu Clicked (Profile, Orders, Logout)");
    }

    private void showProfile() {
        JOptionPane.showMessageDialog(this, "Opening Admin profile...");
    }

    private void showOrders() {
        JOptionPane.showMessageDialog(this, "Viewing orders...");
    }

    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to logout?",
            "Confirm Logout",
            JOptionPane.YES_NO_OPTION
        );
        if (confirm == JOptionPane.YES_OPTION) {
             JOptionPane.showMessageDialog(this, "Logged out.");
             this.dispose();
        }
    }


    // =================================================================================
    //  COMPONENT: UserCartItem
    // =================================================================================

    class UserCartItem extends JPanel {
        public UserCartItem(String name, String brand, String sku, double price) {
            setLayout(new GridBagLayout());
            setBackground(BG_COLOR);

            GridBagConstraints gbc = new GridBagConstraints();

            // 1. Image Placeholder
            JPanel imagePlaceholder = new JPanel();
            imagePlaceholder.setPreferredSize(new Dimension(120, 120));
            imagePlaceholder.setBackground(new Color(240, 240, 240));
            JLabel imgIcon = new JLabel("IMG"); 
            imgIcon.setForeground(Color.LIGHT_GRAY);
            imagePlaceholder.add(imgIcon);
            
            gbc.gridx = 0; gbc.gridy = 0; gbc.gridheight = 2;
            gbc.insets = new Insets(0, 0, 0, 20);
            add(imagePlaceholder, gbc);

            // 2. Product Details
            JPanel detailsPanel = new JPanel(new GridLayout(0, 1, 0, 5));
            detailsPanel.setBackground(BG_COLOR);
            
            JLabel lblName = new JLabel(name);
            lblName.setFont(new Font("SansSerif", Font.BOLD, 18));
            
            JLabel lblBrand = new JLabel(brand);
            lblBrand.setFont(new Font("SansSerif", Font.PLAIN, 14));
            lblBrand.setForeground(TEXT_GRAY);
            
            JLabel lblSku = new JLabel("SKU: " + sku);
            lblSku.setFont(new Font("SansSerif", Font.PLAIN, 14));
            lblSku.setForeground(TEXT_GRAY);
            
            JLabel lblPriceMobile = new JLabel("$" + price); 
            lblPriceMobile.setFont(new Font("SansSerif", Font.BOLD, 16));
            lblPriceMobile.setForeground(PRICE_BLUE);

            detailsPanel.add(lblName);
            detailsPanel.add(lblBrand);
            detailsPanel.add(lblSku);
            detailsPanel.add(lblPriceMobile);

            gbc.gridx = 1; gbc.gridy = 0; gbc.gridheight = 2; 
            gbc.weightx = 1.0; 
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = GridBagConstraints.NORTHWEST;
            add(detailsPanel, gbc);

            // 3. Quantity Controls
            JPanel qtyPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
            qtyPanel.setBackground(BG_COLOR);
            
            JButton btnMinus = createQtyButton("-");
            JLabel lblQty = new JLabel("1", SwingConstants.CENTER);
            lblQty.setPreferredSize(new Dimension(30, 30));
            lblQty.setFont(new Font("SansSerif", Font.BOLD, 14));
            JButton btnPlus = createQtyButton("+");

            qtyPanel.add(btnMinus);
            qtyPanel.add(lblQty);
            qtyPanel.add(btnPlus);

            gbc.gridx = 2; gbc.gridy = 0; gbc.gridheight = 1;
            gbc.weightx = 0;
            gbc.anchor = GridBagConstraints.NORTHEAST;
            add(qtyPanel, gbc);

            // 4. Remove & Subtotal
            JPanel actionPanel = new JPanel(new BorderLayout());
            actionPanel.setBackground(BG_COLOR);
            
            JLabel btnRemove = new JLabel("<html><u>Remove</u></html>"); 
            btnRemove.setForeground(Color.RED);
            btnRemove.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnRemove.setBorder(new EmptyBorder(0,0,5,0));

            JPanel subtotalPanel = new JPanel(new BorderLayout());
            subtotalPanel.setBackground(BG_COLOR);
            JLabel lblSubTitle = new JLabel("Subtotal");
            lblSubTitle.setFont(new Font("SansSerif", Font.PLAIN, 12));
            lblSubTitle.setForeground(TEXT_GRAY);
            JLabel lblSubPrice = new JLabel("$" + price);
            lblSubPrice.setFont(new Font("SansSerif", Font.BOLD, 16));
            
            subtotalPanel.add(lblSubTitle, BorderLayout.NORTH);
            subtotalPanel.add(lblSubPrice, BorderLayout.SOUTH);

            actionPanel.add(btnRemove, BorderLayout.NORTH);
            actionPanel.add(subtotalPanel, BorderLayout.SOUTH);

            gbc.gridx = 2; gbc.gridy = 1;
            gbc.anchor = GridBagConstraints.SOUTHEAST;
            gbc.insets = new Insets(10, 0, 0, 0);
            add(actionPanel, gbc);
        }

        private JButton createQtyButton(String text) {
            JButton btn = new JButton(text);
            btn.setPreferredSize(new Dimension(30, 30));
            btn.setBackground(Color.WHITE);
            btn.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            btn.setFocusPainted(false);
            return btn;
        }
    }

    // --- HELPER: Order Summary Panel ---
    private JPanel createOrderSummary() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BG_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_COLOR, 1),
                new EmptyBorder(20, 20, 20, 20)
        ));

        JLabel title = new JLabel("ORDER SUMMARY");
        title.setFont(new Font("Serif", Font.ITALIC, 20));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Calculations
        JPanel rows = new JPanel(new GridLayout(0, 2, 10, 15));
        rows.setBackground(BG_COLOR);
        rows.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        rows.add(createLabel("Items", false));
        rows.add(createLabel("$45.99", true));
        
        rows.add(createLabel("Shipping", false));
        JLabel shipPrice = createLabel("$9.99", true);
        shipPrice.setForeground(SUCCESS_GREEN);
        rows.add(shipPrice);

        JLabel freeShipNote = new JLabel("Add $4.01 more for free shipping");
        freeShipNote.setFont(new Font("SansSerif", Font.PLAIN, 12));
        freeShipNote.setForeground(TEXT_GRAY);
        freeShipNote.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JPanel taxRow = new JPanel(new BorderLayout());
        taxRow.setBackground(BG_COLOR);
        taxRow.add(createLabel("Tax", false), BorderLayout.WEST);
        taxRow.add(createLabel("$3.68", true), BorderLayout.EAST);
        taxRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        JSeparator sep = new JSeparator();
        sep.setForeground(BORDER_COLOR);
        
        JPanel totalRow = new JPanel(new BorderLayout());
        totalRow.setBackground(BG_COLOR);
        JLabel lblTotal = new JLabel("TOTAL");
        lblTotal.setFont(new Font("SansSerif", Font.BOLD, 18));
        JLabel lblTotalPrice = new JLabel("$59.66");
        lblTotalPrice.setFont(new Font("SansSerif", Font.BOLD, 24));
        totalRow.add(lblTotal, BorderLayout.WEST);
        totalRow.add(lblTotalPrice, BorderLayout.EAST);
        totalRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton checkoutBtn = new JButton("Proceed to Checkout");
        styleButton(checkoutBtn, BUTTON_DARK, Color.WHITE);
        
        JButton continueBtn = new JButton("Continue Shopping");
        styleButton(continueBtn, Color.WHITE, BUTTON_DARK);
        continueBtn.setBorder(new LineBorder(BORDER_COLOR));

        panel.add(title);
        panel.add(Box.createVerticalStrut(20));
        panel.add(rows);
        panel.add(Box.createVerticalStrut(5));
        panel.add(freeShipNote);
        panel.add(Box.createVerticalStrut(15));
        panel.add(taxRow);
        panel.add(Box.createVerticalStrut(15));
        panel.add(sep);
        panel.add(Box.createVerticalStrut(15));
        panel.add(totalRow);
        panel.add(Box.createVerticalStrut(20));
        panel.add(checkoutBtn);
        panel.add(Box.createVerticalStrut(10));
        panel.add(continueBtn);

        return panel;
    }

    // --- HELPER: Footer ---
    private JPanel createFooter() {
        JPanel footer = new JPanel(new GridLayout(1, 4, 20, 0));
        footer.setBackground(BG_COLOR);
        footer.setBorder(new CompoundBorder(
            new MatteBorder(1, 0, 0, 0, BORDER_COLOR),
            new EmptyBorder(40, 60, 60, 60)
        ));

        footer.add(createFooterColumn(null)); 
        footer.add(createFooterColumn("Use cases", "UX design", "Wireframing", "Prototyping"));
        footer.add(createFooterColumn("Explore", "Design", "Prototyping", "Development features"));
        footer.add(createFooterColumn("Resources", "Blog", "Best practices", "Colors"));

        return footer;
    }

    private JPanel createFooterColumn(String title, String... items) {
        JPanel col = new JPanel();
        col.setLayout(new BoxLayout(col, BoxLayout.Y_AXIS));
        col.setBackground(BG_COLOR);

        if (title == null) {
            JLabel logo = new JLabel("<html><div style='font-size:20px; font-weight:bold;'>⌘</div></html>");
            col.add(logo);
            col.add(Box.createVerticalStrut(20));
            col.add(new JLabel("X  O  Y  in")); 
        } else {
            JLabel lblTitle = new JLabel(title);
            lblTitle.setFont(new Font("SansSerif", Font.BOLD, 14));
            col.add(lblTitle);
            col.add(Box.createVerticalStrut(15));
            for (String item : items) {
                JLabel lblItem = new JLabel(item);
                lblItem.setFont(new Font("SansSerif", Font.PLAIN, 13));
                lblItem.setForeground(TEXT_DARK);
                col.add(lblItem);
                col.add(Box.createVerticalStrut(8));
            }
        }
        col.setAlignmentY(Component.TOP_ALIGNMENT);
        return col;
    }

    private JLabel createLabel(String text, boolean isRight) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("SansSerif", Font.PLAIN, 14));
        if (isRight) l.setHorizontalAlignment(SwingConstants.RIGHT);
        return l;
    }

    private void styleButton(JButton btn, Color bg, Color fg) {
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setPreferredSize(new Dimension(100, 45));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AutoPartsCartPage().setVisible(true);
        });
    }
}