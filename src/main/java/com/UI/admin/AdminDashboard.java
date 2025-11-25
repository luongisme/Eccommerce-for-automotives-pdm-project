package com.UI.admin;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import com.Main.AppFrame;
import com.model.User;
import com.service.UserSession;
import com.UI.Payment.PaymentScreen;
import com.UI.defaultpage.AutoPartsHomePage;
import com.UI.login.LoginScreen;
import com.UI.components.RoundedPanel;

public class AdminDashboard extends JFrame {

    private static final Color BG_COLOR = new Color(245, 246, 248);
    private static final Color CARD_BG = Color.WHITE;
    private static final Color BORDER_COLOR = new Color(224, 224, 224);
    private static final Color TEXT_PRIMARY = new Color(30, 30, 30);
    private static final Color TEXT_SECONDARY = new Color(120, 120, 120);

    private CardLayout cardLayout;
    private JPanel contentCards;

    public AdminDashboard() {
        setTitle("AutoParts Pro - Admin Dashboard");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1024, 1200);
        setLocationRelativeTo(null);
        setResizable(false);

        buildUI();
    }

    public void setScreen() {
        setVisible(true);
    }

    private void buildUI() {
        JPanel root = new JPanel(null);
        root.setBackground(BG_COLOR);

        // ===== TOP NAVIGATION BAR =====
        JPanel topBar = new JPanel();
        topBar.setLayout(new BoxLayout(topBar, BoxLayout.X_AXIS));
        topBar.setBounds(0, 0, 1024, 64);
        topBar.setBackground(Color.WHITE);
        topBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR));

        JLabel brand = new JLabel("AutoParts Pro");
        brand.setFont(new Font("Arial", Font.BOLD, 18));
        brand.setForeground(TEXT_PRIMARY);
        brand.setCursor(new Cursor(Cursor.HAND_CURSOR));
        brand.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                navigateToHome();
            }
        });
        topBar.add(Box.createRigidArea(new Dimension(20, 0)));
        topBar.add(brand);

        // Search field
        JTextField searchField = new JTextField("Hinted search text");
        searchField.setFont(new Font("Arial", Font.PLAIN, 13));
        searchField.setForeground(new Color(150, 150, 150));
        searchField.setMaximumSize(new Dimension(420, 30));
        searchField.setPreferredSize(new Dimension(420, 30));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210), 1),
                new EmptyBorder(5, 10, 5, 10)
        ));

        topBar.add(Box.createRigidArea(new Dimension(24, 0)));
        topBar.add(searchField);
        topBar.add(Box.createHorizontalGlue());

        JButton productsBtn = createTopBarTextButton("Products");
        JButton adminTextBtn = createTopBarTextButton("Admin");

        JLabel adminIcon = new JLabel();
        ImageIcon userIcon = loadImage("/images/Sample_User_Icon.png", 24, 24);
        if (userIcon != null) {
            adminIcon.setIcon(userIcon);
        } else {
            adminIcon.setText("\uD83D\uDC64");
            adminIcon.setFont(new Font("Arial", Font.PLAIN, 18));
        }
        adminIcon.setBorder(new EmptyBorder(0, 4, 0, 16));
        adminIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        adminIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                navigateToProfile();
            }
        });

        JLabel cartIcon = new JLabel();
        ImageIcon cartImage = loadImage("/images/cart.jpg", 24, 24);
        if (cartImage != null) {
            cartIcon.setIcon(cartImage);
        } else {
            cartIcon.setText("\uD83D\uDED2");
            cartIcon.setFont(new Font("Arial", Font.PLAIN, 18));
        }
        cartIcon.setBorder(new EmptyBorder(0, 0, 0, 16));
        cartIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cartIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleCartClick();
            }
        });

        topBar.add(productsBtn);
        topBar.add(Box.createRigidArea(new Dimension(12, 0)));
        topBar.add(adminTextBtn);
        topBar.add(Box.createRigidArea(new Dimension(8, 0)));
        topBar.add(adminIcon);
        topBar.add(cartIcon);

        root.add(topBar);

        // ===== PAGE TITLE =====
        JLabel title = new JLabel("Admin Dashboard");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(TEXT_PRIMARY);
        title.setBounds(32, 80, 400, 32);
        root.add(title);

        // ===== TAB BAR =====
        JPanel tabBar = new JPanel();
        tabBar.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 0));
        tabBar.setBackground(new Color(235, 236, 240));
        tabBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR));
        tabBar.setBounds(0, 128, 1024, 44);

        JToggleButton overviewTab = createTabButton("Overview");
        JToggleButton productsTab = createTabButton("Products");
        JToggleButton ordersTab = createTabButton("Orders");

        ButtonGroup tabGroup = new ButtonGroup();
        tabGroup.add(overviewTab);
        tabGroup.add(productsTab);
        tabGroup.add(ordersTab);

        overviewTab.setSelected(true);
        styleTabSelection(overviewTab);

        tabBar.add(overviewTab);
        tabBar.add(productsTab);
        tabBar.add(ordersTab);

        root.add(tabBar);

        // ===== CONTENT AREA WITH CARD LAYOUT =====
        cardLayout = new CardLayout();
        contentCards = new JPanel(cardLayout);
        contentCards.setBackground(BG_COLOR);
        contentCards.setBounds(24, 184, 976, 920);

        JPanel overviewPanel = buildOverviewPanel();
        JPanel productsPanel = buildProductsPanel();
        JPanel ordersPanel = buildOrdersPanel();

        contentCards.add(overviewPanel, "overview");
        contentCards.add(productsPanel, "products");
        contentCards.add(ordersPanel, "orders");

        root.add(contentCards);

        // Tab interaction
        overviewTab.addActionListener(e -> {
            styleTabSelection(overviewTab, productsTab, ordersTab);
            cardLayout.show(contentCards, "overview");
        });
        productsTab.addActionListener(e -> {
            styleTabSelection(productsTab, overviewTab, ordersTab);
            cardLayout.show(contentCards, "products");
        });
        ordersTab.addActionListener(e -> {
            styleTabSelection(ordersTab, overviewTab, productsTab);
            cardLayout.show(contentCards, "orders");
        });

        setContentPane(root);
    }

    private JButton createTopBarTextButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.PLAIN, 13));
        btn.setForeground(TEXT_PRIMARY);
        btn.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JToggleButton createTabButton(String text) {
        // Add a simple leading icon bullet to mimic the icon circle
        JToggleButton tab = new JToggleButton("● " + text);
        tab.setFont(new Font("Arial", Font.PLAIN, 13));
        tab.setForeground(TEXT_SECONDARY);
        tab.setBorder(BorderFactory.createEmptyBorder(6, 24, 6, 24));
        tab.setContentAreaFilled(false);
        tab.setFocusPainted(false);
        tab.setCursor(new Cursor(Cursor.HAND_CURSOR));
        tab.setOpaque(false);
        // Make each tab look like a stretched pill
        tab.setPreferredSize(new Dimension(311, 30));
        return tab;
    }

    private void styleTabSelection(JToggleButton selected, JToggleButton... others) {
        // Selected tab: white rounded pill, primary text color
        selected.setForeground(TEXT_PRIMARY);
        selected.setContentAreaFilled(true);
        selected.setOpaque(true);
        selected.setBackground(Color.WHITE);
        selected.setBorder(BorderFactory.createCompoundBorder(
                new javax.swing.border.LineBorder(new Color(220, 220, 225), 1, true),
                new EmptyBorder(4, 24, 4, 24)
        ));

        // Unselected tabs: transparent on gray bar with secondary text
        for (JToggleButton other : others) {
            other.setForeground(TEXT_SECONDARY);
            other.setContentAreaFilled(false);
            other.setOpaque(false);
            other.setBorder(BorderFactory.createEmptyBorder(6, 18, 6, 18));
        }
    }

    // ===== OVERVIEW PANEL =====
    private JPanel buildOverviewPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(BG_COLOR);

        // Metrics row (4 cards)
        int cardW = 228;
        int cardH = 110;
        int gap = 16;
        int startX = 0;

        JPanel revenueCard = createMetricCard("Total Revenue", "$600.36", "+12% from last month");
        revenueCard.setBounds(startX, 0, cardW, cardH);
        panel.add(revenueCard);

        JPanel ordersCard = createMetricCard("Total Orders", "2", "+8% from last month");
        ordersCard.setBounds(startX + (cardW + gap), 0, cardW, cardH);
        panel.add(ordersCard);

        JPanel productsCard = createMetricCard("Active Products", "16", "0 product low in stock");
        productsCard.setBounds(startX + 2 * (cardW + gap), 0, cardW, cardH);
        panel.add(productsCard);

        JPanel customersCard = createMetricCard("Customers", "2,543", "+15% from last month");
        customersCard.setBounds(startX + 3 * (cardW + gap), 0, cardW, cardH);
        panel.add(customersCard);

        // Recent Orders & Low Stock
        JPanel recentOrders = createSimpleCard("Recent Orders");
        recentOrders.setBounds(0, cardH + 24, 472, 170);
        panel.add(recentOrders);

        JPanel lowStock = createSimpleCard("Low Stock Alert");
        lowStock.setBounds(488, cardH + 24, 472, 170);
        panel.add(lowStock);

        // Sales Analytics & Top Selling Categories
        JPanel analytics = createSimpleCard("Sales Analytics");
        analytics.setBounds(0, cardH + 24 + 170 + 24, 472, 170);
        panel.add(analytics);

        JPanel topCategories = createSimpleCard("Top Selling Categories");
        topCategories.setBounds(488, cardH + 24 + 170 + 24, 472, 170);
        panel.add(topCategories);

        return panel;
    }

    private JPanel createMetricCard(String title, String value, String subtitle) {
        RoundedPanel card = new RoundedPanel(16, true);
        card.setLayout(null);
        card.setBackground(CARD_BG);
        card.setBorder(new EmptyBorder(14, 18, 14, 18));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        titleLabel.setForeground(TEXT_SECONDARY);
        titleLabel.setBounds(10, 10, 180, 18);
        card.add(titleLabel);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 20));
        valueLabel.setForeground(TEXT_PRIMARY);
        valueLabel.setBounds(10, 36, 180, 24);
        card.add(valueLabel);

        JLabel subtitleLabel = new JLabel(subtitle);
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        subtitleLabel.setForeground(TEXT_SECONDARY);
        subtitleLabel.setBounds(10, 66, 220, 18);
        card.add(subtitleLabel);

        return card;
    }

    private JPanel createSimpleCard(String title) {
        RoundedPanel card = new RoundedPanel(16, true);
        card.setLayout(null);
        card.setBackground(CARD_BG);
        card.setBorder(new EmptyBorder(14, 18, 14, 18));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(TEXT_PRIMARY);
        titleLabel.setBounds(20, 15, 300, 22);
        card.add(titleLabel);

        // Simple placeholder body so cards don't look empty
        JLabel placeholder = new JLabel("No data yet - coming soon", SwingConstants.LEFT);
        placeholder.setFont(new Font("Arial", Font.PLAIN, 13));
        placeholder.setForeground(TEXT_SECONDARY);
        placeholder.setBounds(20, 43, 300, 18);
        card.add(placeholder);

        return card;
    }

    // ===== PRODUCTS PANEL =====
    private JPanel buildProductsPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(BG_COLOR);

        JLabel sectionTitle = new JLabel("Product Management");
        sectionTitle.setFont(new Font("Arial", Font.BOLD, 18));
        sectionTitle.setForeground(TEXT_PRIMARY);
        sectionTitle.setBounds(0, 0, 300, 26);
        panel.add(sectionTitle);

        // Card container for filters + table
        RoundedPanel card = new RoundedPanel(16, true);
        card.setLayout(null);
        card.setBackground(CARD_BG);
        card.setBorder(new EmptyBorder(16, 20, 16, 20));
        card.setBounds(0, 36, 976, 560);
        panel.add(card);

        // Filters and search row
        JTextField searchField = new JTextField("Search products...");
        searchField.setFont(new Font("Arial", Font.PLAIN, 12));
        searchField.setForeground(TEXT_SECONDARY);
        searchField.setBounds(20, 15, 260, 32);
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                new EmptyBorder(4, 8, 4, 8)
        ));
        card.add(searchField);

        JComboBox<String> categoryFilter = new JComboBox<>(new String[]{
                "All categories", "Engine", "Brakes", "Electrical", "Suspension", "Wheels & Tires"
        });
        categoryFilter.setFont(new Font("Arial", Font.PLAIN, 12));
        categoryFilter.setBounds(300, 15, 180, 32);
        card.add(categoryFilter);

        JButton addProductBtn = new JButton("Add New Product");
        addProductBtn.setFont(new Font("Arial", Font.BOLD, 12));
        addProductBtn.setBackground(Color.BLACK);
        addProductBtn.setForeground(Color.WHITE);
        addProductBtn.setFocusPainted(false);
        addProductBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addProductBtn.setBorder(new EmptyBorder(4, 18, 4, 18));
        addProductBtn.setBounds(776, 15, 180, 32);
        card.add(addProductBtn);

        // Table
        String[] columns = {"Product", "Category", "Price", "Stock", "Status", "Actions"};
        Object[][] data = {
                {"Air Filter", "Engine", "$18.99", 200, "Active", "..."},
                {"Premium Oil Filter", "Engine", "$24.99", 150, "Active", "..."},
                {"LED Headlight Bulbs", "Electrical", "$79.99", 90, "Active", "..."},
                {"All-Season Tire 225/60R16", "Wheels & Tires", "$129.99", 80, "Active", "..."},
                {"Spark Plug Set (4-Pack)", "Engine", "$45.99", 75, "Active", "..."},
                {"Brake Pad Set - Front", "Brakes", "$89.99", 60, "Active", "..."},
                {"Brake Rotor - Front", "Brakes", "$125.99", 45, "Active", "..."},
                {"Shock Absorber - Front", "Suspension", "$75.99", 40, "Active", "..."},
                {"Car Battery 12V", "Electrical", "$149.99", 30, "Active", "..."},
                {"Alloy Wheel 16\"", "Wheels & Tires", "$189.99", 25, "Active", "..."}
        };

        JTable table = new JTable(data, columns) {
            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(248, 249, 251));
                }
                return c;
            }
        };
        table.setFillsViewportHeight(true);
        table.setRowHeight(32);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 1));
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(247, 248, 250));
        table.getTableHeader().setOpaque(true);

        // Right-align numeric columns
        javax.swing.table.DefaultTableCellRenderer rightRenderer = new javax.swing.table.DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        table.getColumnModel().getColumn(2).setCellRenderer(rightRenderer); // Price
        table.getColumnModel().getColumn(3).setCellRenderer(rightRenderer); // Stock

        // Center and style Status column as pill labels
        javax.swing.table.DefaultTableCellRenderer statusRenderer = new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                          boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setForeground(Color.WHITE);
                label.setOpaque(true);
                label.setBorder(new EmptyBorder(4, 10, 4, 10));
                label.setBackground(new Color(24, 119, 242));
                return label;
            }
        };
        table.getColumnModel().getColumn(4).setCellRenderer(statusRenderer);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
        scrollPane.setBounds(20, 63, 936, 468);
        card.add(scrollPane);

        return panel;
    }

    // ===== ORDERS PANEL =====
    private JPanel buildOrdersPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(BG_COLOR);

        JLabel sectionTitle = new JLabel("Order Management");
        sectionTitle.setFont(new Font("Arial", Font.BOLD, 16));
        sectionTitle.setForeground(TEXT_PRIMARY);
        sectionTitle.setBounds(0, 0, 300, 24);
        panel.add(sectionTitle);

        JTextField searchField = new JTextField("Search orders...");
        searchField.setFont(new Font("Arial", Font.PLAIN, 12));
        searchField.setForeground(TEXT_SECONDARY);
        searchField.setBounds(0, 36, 260, 30);
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                new EmptyBorder(4, 8, 4, 8)
        ));
        panel.add(searchField);

        JComboBox<String> statusFilter = new JComboBox<>(new String[]{
                "All status", "Pending", "Processing", "Shipped", "Delivered"
        });
        statusFilter.setFont(new Font("Arial", Font.PLAIN, 12));
        statusFilter.setBounds(276, 36, 180, 30);
        panel.add(statusFilter);

        // Orders list (two sample cards)
        JPanel order1 = createOrderCard("Order #1", "10/1/2024 • 2 items", "User #2", "delivered", "$139.97");
        order1.setBounds(0, 84, 976, 90);
        panel.add(order1);

        JPanel order2 = createOrderCard("Order #2", "20/1/2024 • 1 items", "User #2", "shipped", "$519.96");
        order2.setBounds(0, 186, 976, 90);
        panel.add(order2);

        return panel;
    }

    private JPanel createOrderCard(String title, String meta, String customer, String status, String amount) {
        RoundedPanel card = new RoundedPanel(16, true);
        card.setLayout(null);
        card.setBackground(CARD_BG);
        card.setBorder(new EmptyBorder(14, 18, 14, 18));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 13));
        titleLabel.setForeground(TEXT_PRIMARY);
        titleLabel.setBounds(10, 10, 300, 20);
        card.add(titleLabel);

        JLabel metaLabel = new JLabel(meta);
        metaLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        metaLabel.setForeground(TEXT_SECONDARY);
        metaLabel.setBounds(10, 36, 300, 16);
        card.add(metaLabel);

        JLabel customerLabel = new JLabel("Customer: " + customer);
        customerLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        customerLabel.setForeground(TEXT_SECONDARY);
        customerLabel.setBounds(10, 60, 300, 16);
        card.add(customerLabel);

        // Status badge and amount on the right
        JLabel amountLabel = new JLabel(amount, SwingConstants.RIGHT);
        amountLabel.setFont(new Font("Arial", Font.BOLD, 13));
        amountLabel.setForeground(TEXT_PRIMARY);
        amountLabel.setBounds(780, 10, 160, 20);
        card.add(amountLabel);

        JLabel statusBadge = new JLabel(status.toLowerCase(), SwingConstants.CENTER);
        statusBadge.setFont(new Font("Arial", Font.BOLD, 11));
        statusBadge.setOpaque(true);
        statusBadge.setForeground(Color.WHITE);
        statusBadge.setBackground("delivered".equalsIgnoreCase(status)
                ? new Color(34, 197, 94)
                : new Color(59, 130, 246));
        statusBadge.setBounds(780, 10, 80, 20);
        card.add(statusBadge);

        JButton detailsBtn = new JButton("View Details");
        detailsBtn.setFont(new Font("Arial", Font.PLAIN, 11));
        detailsBtn.setFocusPainted(false);
        detailsBtn.setBounds(750, 48, 100, 24);
        card.add(detailsBtn);

        JButton updateBtn = new JButton("Update Status");
        updateBtn.setFont(new Font("Arial", Font.PLAIN, 11));
        updateBtn.setFocusPainted(false);
        updateBtn.setBounds(856, 48, 110, 24);
        card.add(updateBtn);

        return card;
    }

    // Shared icon loader (similar to StoreHeader)
    private ImageIcon loadImage(String path, int width, int height) {
        try {
            URL url = getClass().getResource(path);
            if (url != null) {
                ImageIcon icon = new ImageIcon(url);
                Image scaled = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                return new ImageIcon(scaled);
            }
        } catch (Exception e) {
            System.err.println("Failed to load image: " + path);
        }
        return null;
    }

    // ===== TOP BAR NAV HELPERS =====
    private void navigateToProfile() {
        // Open profile page in a new AppFrame
        AppFrame frame = new AppFrame();
        frame.setScreen(new com.UI.Profile.ProfilePage(frame));
        frame.setVisible(true);
        this.dispose();
    }

    private void handleCartClick() {
        UserSession session = UserSession.getInstance();
        AppFrame frame = new AppFrame();
        if (!session.isLoggedIn()) {
            frame.setScreen(new LoginScreen(frame));
        } else {
            frame.setScreen(new PaymentScreen(frame));
        }
        frame.setVisible(true);
        this.dispose();
    }

    private void navigateToHome() {
        User currentUser = UserSession.getInstance().getCurrentUser();
        AutoPartsHomePage home = new AutoPartsHomePage(currentUser);
        home.setVisible(true);
        this.dispose();
    }
}

