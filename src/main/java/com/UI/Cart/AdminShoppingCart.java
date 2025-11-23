package com.UI.Cart;

import com.UI.defaultpage.AutoPartsHomePageUsername;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class AdminShoppingCart extends JFrame {

    // Color Constants
    private static final Color COLOR_HEADER_BG = Color.WHITE;
    private static final Color COLOR_HERO_BG = new Color(249, 250, 251); // Light gray/off-white
    private static final Color COLOR_FOOTER_BG = Color.WHITE;
    private static final Color COLOR_TEXT_MAIN = new Color(17, 24, 39); // Dark gray/black
    private static final Color COLOR_TEXT_SUB = new Color(107, 114, 128); // Gray text
    private static final Color COLOR_BORDER = new Color(229, 231, 235);
    private static final Color COLOR_BUTTON_BG = new Color(31, 41, 55); // Dark button
    private static final Color COLOR_BUTTON_TEXT = Color.WHITE;
    private static final Color COLOR_PRIMARY = new Color(0, 120, 212); // Primary color for hover effects

    public AdminShoppingCart() {
        setTitle("AutoParts Pro - Admin Shopping Cart");
        setSize(1024, 1200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 1. Header
        add(createHeader(), BorderLayout.NORTH);

        // 2. Main Content (Hero Section)
        add(createHeroSection(), BorderLayout.CENTER);

        // 3. Footer
        add(createFooter(), BorderLayout.SOUTH);
    }

    // --- Header Section ---
    private JPanel createHeader() {
        JPanel topBar = new JPanel();
        topBar.setLayout(new BoxLayout(topBar, BoxLayout.X_AXIS));
        topBar.setBounds(0, 0, getWidth(), 56);
        topBar.setBackground(Color.WHITE);
        topBar.setOpaque(true);

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

        // Products button
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

        // Admin button
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

    private void navigateToAdminDashboard() {
        // TODO: Replace with actual AdminDashboard navigation
        JOptionPane.showMessageDialog(this, "Navigating to Admin Dashboard...");
        // Uncomment below when AdminDashboard is available
        // AdminDashboard dashboard = new AdminDashboard();
        // dashboard.setVisible(true);
        // this.dispose();
    }

    private void navigateToProducts() {
        AutoPartsHomePageUsername productsPage = new AutoPartsHomePageUsername();
        productsPage.setVisible(true);
        this.dispose();
    }

    private void navigateToCart() {
        // Already in cart
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
        
        // Show the menu below the user button
        Component source = (Component) userMenu.getInvoker();
        userMenu.show(source, 0, source.getHeight());
    }

    private void showProfile() {
        JOptionPane.showMessageDialog(this, "Opening profile...");
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
            // Navigate to login screen or home page
            AutoPartsHomePageUsername homePage = new AutoPartsHomePageUsername();
            homePage.setVisible(true);
            this.dispose();
        }
    }

    // --- Hero Section (Empty State) ---
    private JPanel createHeroSection() {
        JPanel hero = new JPanel();
        hero.setBackground(COLOR_HERO_BG);
        hero.setLayout(new GridBagLayout()); // Center everything

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(COLOR_HERO_BG);
        content.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 1. Custom Bag Icon
        JLabel bagIcon = new JLabel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setStroke(new BasicStroke(3));
                g2.setColor(COLOR_TEXT_MAIN);

                int w = getWidth();
                int h = getHeight();
                int bagW = 60;
                int bagH = 50;
                int x = (w - bagW) / 2;
                int y = (h - bagH) / 2 + 10;

                // Bag Body
                g2.drawRoundRect(x, y, bagW, bagH, 10, 10);
                
                // Handle
                g2.drawArc(x + 15, y - 15, 30, 30, 0, 180);
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(100, 100);
            }
        };
        bagIcon.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 2. Title
        JLabel title = new JLabel("Your cart is empty");
        title.setFont(new Font("Arial", Font.BOLD, 42)); // Large font
        title.setForeground(COLOR_TEXT_MAIN);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 3. Subtitle
        JLabel subtitle = new JLabel("Start shopping to add items to your cart");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 18));
        subtitle.setForeground(COLOR_TEXT_SUB);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 4. Button
        JButton browseBtn = new JButton("Browse Products") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 8, 8));
                super.paintComponent(g);
            }
        };
        browseBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        browseBtn.setForeground(COLOR_BUTTON_TEXT);
        browseBtn.setBackground(COLOR_BUTTON_BG);
        browseBtn.setFocusPainted(false);
        browseBtn.setBorderPainted(false);
        browseBtn.setContentAreaFilled(false);
        browseBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        browseBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        browseBtn.setMaximumSize(new Dimension(180, 45));
        browseBtn.setPreferredSize(new Dimension(180, 45));

        // Add to content with spacing
        content.add(bagIcon);
        content.add(Box.createRigidArea(new Dimension(0, 20)));
        content.add(title);
        content.add(Box.createRigidArea(new Dimension(0, 10)));
        content.add(subtitle);
        content.add(Box.createRigidArea(new Dimension(0, 30)));
        content.add(browseBtn);

        hero.add(content);
        return hero;
    }

    // --- Footer Section ---
    private JPanel createFooter() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(COLOR_FOOTER_BG);
        footer.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, COLOR_BORDER),
            new EmptyBorder(40, 40, 40, 40)
        ));
        footer.setPreferredSize(new Dimension(getWidth(), 300)); // Fixed height for footer

        // Left Side: Icon + Socials
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(COLOR_FOOTER_BG);
        leftPanel.setPreferredSize(new Dimension(200, 200));

        // Logo Icon placeholder
        JLabel logoIcon = new JLabel() {
            @Override
            public void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(COLOR_TEXT_MAIN);
                // Abstract clover/logo shape from image
                g2.fillOval(0, 0, 10, 10);
                g2.fillOval(12, 0, 10, 10);
                g2.fillOval(0, 12, 10, 10);
                g2.fillOval(12, 12, 10, 10);
            }
            @Override
            public Dimension getPreferredSize() { return new Dimension(30, 30); }
            @Override
            public Dimension getMaximumSize() { return new Dimension(30, 30); }
        };
        logoIcon.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Social Icons Row
        JPanel socialRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        socialRow.setBackground(COLOR_FOOTER_BG);
        socialRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        socialRow.setBorder(new EmptyBorder(20, -5, 0, 0)); // Padding top, adjust left
        
        // Simple text representations for social icons
        // In a real app, these would be SVG icons or images
        socialRow.add(createSocialIcon("X"));
        socialRow.add(createSocialIcon("Ig"));
        socialRow.add(createSocialIcon("Yt"));
        socialRow.add(createSocialIcon("Ln"));

        leftPanel.add(logoIcon);
        leftPanel.add(socialRow);
        
        // Right Side: Links Columns
        JPanel linksPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        linksPanel.setBackground(COLOR_FOOTER_BG);

        linksPanel.add(createLinkColumn("Use cases", "UI design", "UX design", "Wireframing", "Diagramming", "Brainstorming", "Online whiteboard", "Team collaboration"));
        linksPanel.add(createLinkColumn("Explore", "Design", "Prototyping", "Development features", "Design systems", "Collaboration features", "Design process", "FigJam"));
        linksPanel.add(createLinkColumn("Resources", "Blog", "Best practices", "Colors", "Color wheel", "Support", "Developers", "Resource library"));

        footer.add(leftPanel, BorderLayout.WEST);
        footer.add(linksPanel, BorderLayout.CENTER);

        return footer;
    }

    private JLabel createSocialIcon(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Arial", Font.BOLD, 14));
        lbl.setForeground(COLOR_TEXT_MAIN);
        return lbl;
    }

    private JPanel createLinkColumn(String title, String... links) {
        JPanel col = new JPanel();
        col.setLayout(new BoxLayout(col, BoxLayout.Y_AXIS));
        col.setBackground(COLOR_FOOTER_BG);

        JLabel titleLbl = new JLabel(title);
        titleLbl.setFont(new Font("Arial", Font.BOLD, 14));
        titleLbl.setForeground(COLOR_TEXT_MAIN);
        titleLbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        col.add(titleLbl);
        col.add(Box.createRigidArea(new Dimension(0, 15)));

        for (String link : links) {
            JLabel linkLbl = new JLabel(link);
            linkLbl.setFont(new Font("Arial", Font.PLAIN, 14));
            linkLbl.setForeground(new Color(75, 85, 99)); // Slightly lighter gray
            linkLbl.setAlignmentX(Component.LEFT_ALIGNMENT);
            linkLbl.setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            // Hover effect logic
            linkLbl.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { linkLbl.setForeground(COLOR_PRIMARY); }
                public void mouseExited(MouseEvent e) { linkLbl.setForeground(new Color(75, 85, 99)); }
            });

            col.add(linkLbl);
            col.add(Box.createRigidArea(new Dimension(0, 10)));
        }
        
        // Push content up
        col.add(Box.createVerticalGlue());

        return col;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AdminShoppingCart cart = new AdminShoppingCart();
            cart.setVisible(true);
        });
    }
}
