package com.UI.admin;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class AdminDBProducts extends JFrame {
    // Color constants
    private static final Color COLOR_BACKGROUND = Color.WHITE;
    private static final Color COLOR_BORDER = new Color(230, 234, 240);
    private static final Color COLOR_TEXT = new Color(33, 43, 54);
    private static final Color COLOR_TEXT_SECONDARY = new Color(99, 115, 129);
    private static final Color COLOR_SUCCESS = new Color(34, 197, 94);
    private static final Color COLOR_ERROR = new Color(255, 72, 66);
    private static final Color COLOR_TEXT_LIGHT = new Color(144, 158, 171);
    private static final Color COLOR_PRIMARY = new Color(0, 120, 212);
    private static final Color COLOR_BUTTON_DARK = new Color(45, 45, 45); // Dark button color

    public AdminDBProducts() {
        setTitle("Admin Dashboard - Products");
        setSize(1024, 1200);
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
        final String placeholder = " \uD83D\uDD0D Hinted search text";
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

        JButton overviewBtn = createTabButton("Overview", false);
        JButton productsBtn = createTabButton("Products", true);  // Products tab is selected
        JButton ordersBtn = createTabButton("Orders", false);

        // Add action listeners for tab switching
        overviewBtn.addActionListener(e -> {
            this.dispose();
            new AdminDBOverview().setVisible(true);
        });
        productsBtn.addActionListener(e -> {
            // Already on products page, do nothing
        });
        ordersBtn.addActionListener(e -> {
            this.dispose();
            new AdminDBOrders().setVisible(true);
        });

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

        // Add product content (the existing product management interface)
        mainPanel.add(createProductContent(), BorderLayout.CENTER);

        return mainPanel;
    }

    private JPanel createProductContent() {
        JPanel contentPanel = new JPanel(new BorderLayout(0, 20));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // --- Top Controls Row ---
        JPanel controlsPanel = new JPanel();
        controlsPanel.setLayout(new BoxLayout(controlsPanel, BoxLayout.X_AXIS));
        controlsPanel.setBackground(Color.WHITE);

        // "Product Management" Title
        JLabel sectionTitle = new JLabel("Product Management");
        sectionTitle.setFont(new Font("Arial", Font.BOLD, 18));
        sectionTitle.setForeground(COLOR_TEXT);
        controlsPanel.add(sectionTitle);
        controlsPanel.add(Box.createRigidArea(new Dimension(30, 0)));

        // Search Products Input
        JTextField productSearch = new JTextField(" Search products...");
        productSearch.setFont(new Font("Arial", Font.PLAIN, 14));
        productSearch.setForeground(COLOR_TEXT_SECONDARY);
        productSearch.setBackground(new Color(245, 245, 245));
        productSearch.setBorder(BorderFactory.createLineBorder(COLOR_BORDER));
        productSearch.setMaximumSize(new Dimension(200, 35));
        productSearch.setPreferredSize(new Dimension(200, 35));
        controlsPanel.add(productSearch);
        controlsPanel.add(Box.createRigidArea(new Dimension(15, 0)));

        // Filter Dropdown
        String[] categories = {"Filter by category", "Electronics", "Engine", "Interior", "Body"};
        JComboBox<String> filterBox = new JComboBox<>(categories);
        filterBox.setFont(new Font("Arial", Font.PLAIN, 14));
        filterBox.setBackground(new Color(245, 245, 245));
        filterBox.setMaximumSize(new Dimension(150, 35));
        filterBox.setPreferredSize(new Dimension(150, 35));
        controlsPanel.add(filterBox);

        controlsPanel.add(Box.createHorizontalGlue()); // Push button to the right

        // "Add New Product" Button
        JButton addBtn = new JButton("Add New Product");
        addBtn.setFont(new Font("Arial", Font.BOLD, 14));
        addBtn.setForeground(Color.WHITE);
        addBtn.setBackground(COLOR_BUTTON_DARK);
        addBtn.setFocusPainted(false);
        addBtn.setBorderPainted(false);
        addBtn.setOpaque(true);
        addBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // Add some internal padding
        addBtn.setBorder(new EmptyBorder(10, 20, 10, 20)); 
        
        controlsPanel.add(addBtn);

        contentPanel.add(controlsPanel, BorderLayout.NORTH);

        // --- Product Table (Placeholder) ---
        // Column Names
        String[] columnNames = {"ID", "Product Name", "Category", "Price", "Stock", "Status", "Actions"};
        
        // Dummy Data
        Object[][] data = {
            {"#1023", "Brake Pads", "Brakes", "$45.00", "120", "In Stock", "Edit"},
            {"#1024", "Oil Filter", "Engine", "$12.50", "50", "In Stock", "Edit"},
            {"#1025", "Headlight Bulb", "Electrical", "$22.00", "5", "Low Stock", "Edit"},
            {"#1026", "Spark Plug", "Engine", "$8.00", "200", "In Stock", "Edit"},
        };

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);
        table.setRowHeight(40);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setGridColor(COLOR_BORDER);
        table.setShowVerticalLines(false);
        
        // Style Table Header
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setBackground(new Color(245, 245, 245));
        header.setForeground(COLOR_TEXT);
        header.setPreferredSize(new Dimension(0, 40));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(COLOR_BORDER));
        scrollPane.getViewport().setBackground(Color.WHITE);

        contentPanel.add(scrollPane, BorderLayout.CENTER);

        return contentPanel;
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
        // Update selected button
        selectedBtn.setFont(new Font("Arial", Font.BOLD, 14));
        selectedBtn.setForeground(COLOR_PRIMARY);
        selectedBtn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, COLOR_PRIMARY),
            new EmptyBorder(8, 0, 6, 0)
        ));

        // Update other buttons
        for (JButton btn : otherBtns) {
            btn.setFont(new Font("Arial", Font.PLAIN, 14));
            btn.setForeground(COLOR_TEXT_SECONDARY);
            btn.setBorder(new EmptyBorder(8, 0, 8, 0));
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            AdminDBProducts frame = new AdminDBProducts();
            frame.setVisible(true);
        });
    }
}
