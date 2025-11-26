package com.UI.admin;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import com.Main.AppFrame;
import com.Main.Screen;
import com.service.UserSession;
import com.UI.Payment.PaymentScreen;
import com.UI.defaultpage.AutoPartsHomePage;
import com.UI.login.LoginScreen;
import com.UI.store.StoreScreen;

public class AdminDashboard extends Screen {

    public static final Color BG_COLOR = new Color(245, 246, 248);
    public static final Color CARD_BG = Color.WHITE;
    public static final Color BORDER_COLOR = new Color(224, 224, 224);
    public static final Color TEXT_PRIMARY = new Color(30, 30, 30);
    public static final Color TEXT_SECONDARY = new Color(120, 120, 120);

    private CardLayout cardLayout;
    private JPanel contentCards;

    private final AdminDashboardController controller;

    public AdminDashboard(AppFrame appFrame) {
        super(appFrame);
        this.controller = new AdminDashboardController();
        panel = new JPanel(null);
        panel.setBackground(BG_COLOR);
        initUI();
    }

    @Override
    protected void initUI() {

        // ===== TOP NAVIGATION BAR =====
        JPanel topBar = new JPanel();
        topBar.setLayout(new BoxLayout(topBar, BoxLayout.X_AXIS));
        topBar.setBounds(0, 0, 1024, 64);
        topBar.setBackground(Color.WHITE);
        topBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR));

        JLabel brand = new JLabel("AutoParts Pro");
        brand.setFont(new Font("Arial", Font.BOLD, 24));
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
        productsBtn.addActionListener(e -> navigateToStore());
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

        panel.add(topBar);

        // ===== PAGE TITLE =====
        JLabel title = new JLabel("Admin Dashboard");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(TEXT_PRIMARY);
        title.setBounds(32, 80, 400, 32);
        panel.add(title);

        // ===== TAB BAR =====
        JPanel tabBar = new JPanel();
        tabBar.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 0));
        tabBar.setBackground(new Color(235, 236, 240));
        tabBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR));
        tabBar.setBounds(0, 128, 1024, 44);

        JToggleButton overviewTab = createTabButton("Overview");
        JToggleButton productsTab = createTabButton("Products");

        ButtonGroup tabGroup = new ButtonGroup();
        tabGroup.add(overviewTab);
        tabGroup.add(productsTab);

        overviewTab.setSelected(true);
        styleTabSelection(overviewTab);

        tabBar.add(overviewTab);
        tabBar.add(productsTab);

        panel.add(tabBar);

        // ===== CONTENT AREA WITH CARD LAYOUT =====
        cardLayout = new CardLayout();
        contentCards = new JPanel(cardLayout);
        contentCards.setBackground(BG_COLOR);
        contentCards.setBounds(24, 184, 976, 920);

        JPanel overviewPanel = new AdminOverviewPanel(controller);
        JPanel productsPanel = new AdminProductsPanel(controller);

        contentCards.add(overviewPanel, "overview");
        contentCards.add(productsPanel, "products");

        panel.add(contentCards);

        // Tab interaction
        overviewTab.addActionListener(e -> {
            styleTabSelection(overviewTab, productsTab);
            cardLayout.show(contentCards, "overview");
        });
        productsTab.addActionListener(e -> {
            styleTabSelection(productsTab, overviewTab);
            cardLayout.show(contentCards, "products");
        });
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
        appFrame.setScreen(new com.UI.Profile.ProfilePage(appFrame));
    }

    private void handleCartClick() {
        UserSession session = UserSession.getInstance();
        if (!session.isLoggedIn()) {
            appFrame.setScreen(new LoginScreen(appFrame));
        } else {
            appFrame.setScreen(new PaymentScreen(appFrame,"ORDER001", "P001"));
        }
    }

    private void navigateToHome() {
        appFrame.setScreen(new AutoPartsHomePage(appFrame));
    }

    private void navigateToStore() {
        appFrame.setScreen(new StoreScreen(appFrame));
    }
}

