
package com.UI.admin;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import com.UI.admin.AdminDBOverview;
import com.UI.admin.AdminDBProducts;

public class AdminDBOrders extends JFrame {
    // Color constants
    private static final Color COLOR_BACKGROUND = Color.white;
    private static final Color COLOR_BORDER = new Color(230, 234, 240);
    private static final Color COLOR_TEXT = new Color(33, 43, 54);
    private static final Color COLOR_TEXT_SECONDARY = new Color(99, 115, 129);
    private static final Color COLOR_PRIMARY = new Color(0, 120, 212);
    private static final Color COLOR_BUTTON_DARK = new Color(45, 45, 45);
    private static final Color COLOR_SUCCESS_BG = new Color(220, 252, 231); // Light green
    private static final Color COLOR_SUCCESS_TEXT = new Color(22, 163, 74); // Dark green
    private static final Color COLOR_INFO_BG = new Color(219, 234, 254); // Light blue
    private static final Color COLOR_INFO_TEXT = new Color(37, 99, 235); // Dark blue

    public AdminDBOrders() {
        setTitle("Admin Dashboard - Orders");
        setSize(1200, 800);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_BACKGROUND);
        setLayout(new BorderLayout());
        add(createTopNavigationBar(), BorderLayout.NORTH);
        add(createMainContentPanel(), BorderLayout.CENTER);
    }

    private JPanel createTopNavigationBar() {
        JPanel topBar = new JPanel();
        topBar.setLayout(new BoxLayout(topBar, BoxLayout.X_AXIS));
        topBar.setBackground(Color.WHITE);
        topBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, COLOR_BORDER));
        topBar.setPreferredSize(new Dimension(getWidth(), 60));

        // Logo
        JLabel logo = new JLabel("AutoParts Pro");
        logo.setFont(new Font("Arial", Font.BOLD, 18));
        logo.setForeground(COLOR_TEXT);
        topBar.add(Box.createRigidArea(new Dimension(20, 0)));
        topBar.add(logo);

        // Search field
        final String placeholder = " \uD83D\uDD0D Search orders...";
        JTextField searchField = new JTextField(placeholder, 20);
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchField.setForeground(COLOR_TEXT_SECONDARY);
        searchField.setBackground(new Color(245, 245, 245));
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_BORDER, 1),
            new EmptyBorder(5, 10, 5, 10)
        ));
        searchField.setMaximumSize(new Dimension(300, 35));

        // Add placeholder behavior
        searchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals(placeholder)) {
                    searchField.setText("");
                    searchField.setForeground(COLOR_TEXT);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText(placeholder);
                    searchField.setForeground(COLOR_TEXT_SECONDARY);
                }
            }
        });

        topBar.add(Box.createRigidArea(new Dimension(20, 0)));
        topBar.add(searchField);
        topBar.add(Box.createHorizontalGlue());

        // Navigation buttons
        JButton productsBtn = createNavButton("Products");
        JButton adminBtn = createNavButton("Admin");

        // User button with icon
        JButton userBtn = new JButton("Admin");
        try {
            // Load and scale user icon
            ImageIcon userIcon = new ImageIcon("src/main/resources/images/user_icon.png");
            userBtn.setIcon(new ImageIcon(userIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
            userBtn.setHorizontalTextPosition(SwingConstants.RIGHT);
            userBtn.setIconTextGap(8);
        } catch (Exception e) {
            userBtn.setText("👤 Admin");
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

        // Cart button with icon
        JButton cartBtn = new JButton();
        try {
            // Load and scale cart icon
            ImageIcon cartIcon = new ImageIcon("src/main/resources/images/cart_icon.png");
            cartBtn.setIcon(new ImageIcon(cartIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        } catch (Exception e) {
            cartBtn.setText("🛒");
        }
        styleNavButton(cartBtn);

        // Add action listeners for navigation
        productsBtn.addActionListener(e -> {
            this.dispose();
            new AdminDBProducts().setVisible(true);
        });
        
        adminBtn.addActionListener(e -> {
            this.dispose();
            new AdminDBOverview().setVisible(true);
        });

        topBar.add(productsBtn);
        topBar.add(Box.createRigidArea(new Dimension(10, 0)));
        topBar.add(adminBtn);
        topBar.add(Box.createRigidArea(new Dimension(10, 0)));
        topBar.add(userBtn);
        topBar.add(Box.createRigidArea(new Dimension(10, 0)));
        topBar.add(cartBtn);
        topBar.add(Box.createRigidArea(new Dimension(20, 0)));

        return topBar;
    }

    private JButton createNavButton(String text) {
        JButton button = new JButton(text);
        styleNavButton(button);
        return button;
    }
    
    private void styleNavButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setForeground(COLOR_TEXT);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private JPanel createMainContentPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setOpaque(false);

        // 1. Header Area
        JPanel headerContainer = new JPanel();
        headerContainer.setLayout(new BoxLayout(headerContainer, BoxLayout.Y_AXIS));
        headerContainer.setOpaque(false);

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15));
        titlePanel.setBackground(Color.WHITE);
        JLabel titleLabel = new JLabel("Admin Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(COLOR_TEXT);
        titlePanel.add(titleLabel);
        headerContainer.add(titlePanel);

        JPanel tabPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        tabPanel.setOpaque(false);
        tabPanel.setBorder(new EmptyBorder(5, 0, 15, 0));

        JButton overviewBtn = createTabButton("Overview", false);
        JButton productsBtn = createTabButton("Products", false);
        JButton ordersBtn = createTabButton("Orders", true);  // Orders tab is selected

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
        
        headerContainer.add(tabPanel);
        mainPanel.add(headerContainer, BorderLayout.NORTH);

        // 2. Content Area
        mainPanel.add(createOrderContent(), BorderLayout.CENTER);

        return mainPanel;
    }

    private JPanel createOrderContent() {
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Outer container with border
        JPanel borderPanel = new JPanel(new BorderLayout());
        borderPanel.setBackground(Color.WHITE);
        borderPanel.setBorder(BorderFactory.createLineBorder(COLOR_BORDER));

        // --- Top Controls ---
        JPanel controlsPanel = new JPanel();
        controlsPanel.setLayout(new BoxLayout(controlsPanel, BoxLayout.Y_AXIS));
        controlsPanel.setBackground(Color.WHITE);
        controlsPanel.setBorder(new EmptyBorder(20, 20, 10, 20));

        // Section Title
        JLabel sectionTitle = new JLabel("Order Management");
        sectionTitle.setFont(new Font("Arial", Font.PLAIN, 16));
        sectionTitle.setForeground(COLOR_TEXT);
        sectionTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        controlsPanel.add(sectionTitle);
        controlsPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Search & Filter Row
        JPanel filterRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        filterRow.setBackground(Color.WHITE);
        filterRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField orderSearch = new JTextField(" Search orders...");
        orderSearch.setFont(new Font("Arial", Font.PLAIN, 14));
        orderSearch.setForeground(COLOR_TEXT_SECONDARY);
        orderSearch.setBackground(new Color(245, 245, 245));
        orderSearch.setBorder(new EmptyBorder(8, 10, 8, 10));
        orderSearch.setPreferredSize(new Dimension(300, 35));
        // Rounding workaround using border
        orderSearch.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(COLOR_BORDER, 0), // No visible border line, just bg
            new EmptyBorder(5, 10, 5, 10)
        ));
        // Using a wrapper for the grey background look
        JPanel searchWrapper = new JPanel(new BorderLayout());
        searchWrapper.setBackground(new Color(245, 245, 245));
        searchWrapper.setBorder(new LineBorder(new Color(245, 245, 245), 1)); 
        searchWrapper.add(orderSearch);
        
        filterRow.add(searchWrapper);
        filterRow.add(Box.createRigidArea(new Dimension(15, 0)));

        String[] statuses = {"Filter by status", "Delivered", "Shipped", "Pending"};
        JComboBox<String> filterBox = new JComboBox<>(statuses);
        filterBox.setFont(new Font("Arial", Font.PLAIN, 14));
        filterBox.setBackground(new Color(245, 245, 245));
        filterBox.setPreferredSize(new Dimension(150, 35));
        filterRow.add(filterBox);

        controlsPanel.add(filterRow);
        borderPanel.add(controlsPanel, BorderLayout.NORTH);

        // --- Order List ---
        JPanel listContainer = new JPanel();
        listContainer.setLayout(new BoxLayout(listContainer, BoxLayout.Y_AXIS));
        listContainer.setBackground(Color.WHITE);
        listContainer.setBorder(new EmptyBorder(10, 20, 20, 20));

        // Add Order Cards
        listContainer.add(createOrderCard(
            "Order #1", "10/1/2024 • 2 items", 
            "User #2", "Credit Card (completed)", 
            "delivered", "$139.97"
        ));
        listContainer.add(Box.createRigidArea(new Dimension(0, 15)));
        listContainer.add(createOrderCard(
            "Order #2", "20/1/2024 • 1 items", 
            "User #2", "Credit Card (completed)", 
            "shipped", "$519.96"
        ));
        // Add a filler to push items up if list is short
        listContainer.add(Box.createVerticalGlue());

        JScrollPane scrollPane = new JScrollPane(listContainer);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        borderPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.add(borderPanel, BorderLayout.CENTER);

        return contentPanel;
    }

    private JPanel createOrderCard(String orderId, String dateInfo, String user, String payment, String status, String price) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(COLOR_BORDER, 1),
            new EmptyBorder(15, 20, 15, 20)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        card.setPreferredSize(new Dimension(0, 120));

        // --- Left Side Info ---
        JPanel leftInfo = new JPanel();
        leftInfo.setLayout(new BoxLayout(leftInfo, BoxLayout.Y_AXIS));
        leftInfo.setBackground(Color.WHITE);

        JLabel lblId = new JLabel(orderId);
        lblId.setFont(new Font("Arial", Font.BOLD, 14));
        lblId.setForeground(COLOR_TEXT);
        
        JLabel lblDate = new JLabel(dateInfo);
        lblDate.setFont(new Font("Arial", Font.PLAIN, 12));
        lblDate.setForeground(COLOR_TEXT_SECONDARY);

        JPanel spacer = new JPanel(); 
        spacer.setOpaque(false);
        spacer.setPreferredSize(new Dimension(1, 15));
        spacer.setMaximumSize(new Dimension(1, 15));

        JLabel lblUser = new JLabel("Customer: " + user);
        lblUser.setFont(new Font("Arial", Font.PLAIN, 13));
        lblUser.setForeground(COLOR_TEXT_SECONDARY);

        JLabel lblPayment = new JLabel("Payment: " + payment);
        lblPayment.setFont(new Font("Arial", Font.PLAIN, 13));
        lblPayment.setForeground(COLOR_TEXT_SECONDARY);

        leftInfo.add(lblId);
        leftInfo.add(Box.createRigidArea(new Dimension(0, 2)));
        leftInfo.add(lblDate);
        leftInfo.add(spacer);
        leftInfo.add(lblUser);
        leftInfo.add(Box.createRigidArea(new Dimension(0, 2)));
        leftInfo.add(lblPayment);

        card.add(leftInfo, BorderLayout.WEST);

        // --- Right Side Info & Actions ---
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(Color.WHITE);

        // Top Right: Status and Price
        JPanel statusPricePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        statusPricePanel.setBackground(Color.WHITE);

        JLabel statusBadge = createStatusBadge(status);
        
        JLabel lblPrice = new JLabel(price);
        lblPrice.setFont(new Font("Arial", Font.BOLD, 16));
        lblPrice.setForeground(COLOR_TEXT);

        statusPricePanel.add(statusBadge);
        statusPricePanel.add(lblPrice);

        // Bottom Right: Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        // Add margin to top to separate from price line
        buttonPanel.setBorder(new EmptyBorder(15, 0, 0, 0));

        JButton btnDetails = new JButton("View Details");
        btnDetails.setFont(new Font("Arial", Font.BOLD, 12));
        btnDetails.setForeground(COLOR_TEXT);
        btnDetails.setBackground(Color.WHITE);
        btnDetails.setFocusPainted(false);
        btnDetails.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDetails.setBorder(new CompoundBorder(
            new LineBorder(COLOR_BORDER),
            new EmptyBorder(8, 15, 8, 15)
        ));

        JButton btnUpdate = new JButton("Update Status");
        btnUpdate.setFont(new Font("Arial", Font.BOLD, 12));
        btnUpdate.setForeground(Color.WHITE);
        btnUpdate.setBackground(COLOR_BUTTON_DARK);
        btnUpdate.setFocusPainted(false);
        btnUpdate.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnUpdate.setBorder(new EmptyBorder(8, 15, 8, 15));
        btnUpdate.setOpaque(true);
        btnUpdate.setBorderPainted(false);

        buttonPanel.add(btnDetails);
        buttonPanel.add(btnUpdate);

        rightPanel.add(statusPricePanel, BorderLayout.NORTH);
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);

        card.add(rightPanel, BorderLayout.EAST);

        return card;
    }

    private JLabel createStatusBadge(String status) {
        JLabel badge = new JLabel(status);
        badge.setFont(new Font("Arial", Font.BOLD, 11));
        badge.setOpaque(true);
        badge.setBorder(new EmptyBorder(4, 8, 4, 8));
        
        if ("delivered".equalsIgnoreCase(status)) {
            badge.setBackground(COLOR_SUCCESS_BG);
            badge.setForeground(COLOR_SUCCESS_TEXT);
        } else if ("shipped".equalsIgnoreCase(status)) {
            badge.setBackground(COLOR_INFO_BG);
            badge.setForeground(COLOR_INFO_TEXT);
        } else {
            badge.setBackground(new Color(243, 244, 246)); // Gray default
            badge.setForeground(COLOR_TEXT_SECONDARY);
        }
        return badge;
    }

    private JButton createTabButton(String text, boolean selected) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", selected ? Font.BOLD : Font.PLAIN, 14));
        button.setForeground(selected ? COLOR_PRIMARY : COLOR_TEXT_SECONDARY);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
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
        // If the selected button is already active, do nothing
        if (selectedBtn.getFont().getStyle() == Font.BOLD) {
            return;
        }
        
        // Store the button text before disposing
        String selectedTab = selectedBtn.getText();
        
        // Close current frame
        this.dispose();
        
        // Open the appropriate frame based on the selected tab
        SwingUtilities.invokeLater(() -> {
            JFrame newFrame = null;
            
            switch (selectedTab) {
                case "Overview":
                    newFrame = new AdminDBOverview();
                    break;
                case "Products":
                    newFrame = new AdminDBProducts();
                    break;
                case "Orders":
                    newFrame = new AdminDBOrders();
                    break;
            }
            
            if (newFrame != null) {
                newFrame.setVisible(true);
            }
        });
    }
    
    /**
     * Main method to launch the AdminDBOrders application
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        // Use the event dispatch thread for Swing components
        SwingUtilities.invokeLater(() -> {
            try {
                // Set the system look and feel for a more native appearance
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                // If setting the look and feel fails, continue with the default
                e.printStackTrace();
            }
            
            // Create and show the AdminDBOrders window
            AdminDBOrders ordersWindow = new AdminDBOrders();
            ordersWindow.setVisible(true);
        });
    }
}
