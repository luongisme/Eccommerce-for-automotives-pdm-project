package com.UI.store;

import com.Main.AppFrame;
import com.model.Product;
import com.model.User;
import com.service.CartService;
import com.service.UserSession;
import com.UI.productdetails.ProductDetailsScreenEnhanced;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class ProductCard extends JPanel {
    private Product product;
    private AppFrame appFrame;
    private String currentSort; // Store current sort type to pass to details page
    private int currentPage; // Store current page to pass to details page
    private final Dimension size = new Dimension(170, 240);


    public ProductCard(Product product, AppFrame appFrame, String sortType, int page) {
        this.product = product;
        this.appFrame = appFrame;
        this.currentSort = sortType != null ? sortType : "new";
        this.currentPage = page > 0 ? page : 1;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(new Color(106, 106, 106), 1));
        setPreferredSize(size);
        setMaximumSize(size);
        setMinimumSize(size);
        initUI();
        addClickListener();
    }

    private void initUI() {
        // Image panel
        JPanel imagePanel = new JPanel();
        imagePanel.setPreferredSize(new Dimension(180, 140));
        imagePanel.setBackground(Color.LIGHT_GRAY);
        loadProductImage(imagePanel, product.getImageUrl(), 180, 140);


        // Info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BorderLayout());
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        // Product name
        JLabel nameLabel = new JLabel("<html>" + product.getName() + "</html>");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        nameLabel.setForeground(Color.BLACK);

        // Price
        JLabel priceLabel = new JLabel("$" + String.format("%.2f", product.getPrice()));
        priceLabel.setFont(new Font("Arial", Font.BOLD, 14));
        priceLabel.setForeground(Color.BLACK);

        // Add to cart button
        JButton addToCartBtn = new JButton("Add to cart");
        addToCartBtn.setBackground(new Color(45, 45, 45));
        addToCartBtn.setForeground(Color.WHITE);
        addToCartBtn.setFont(new Font("Arial", Font.PLAIN, 11));
        addToCartBtn.setFocusPainted(false);
        addToCartBtn.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
        addToCartBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add click listener for add to cart
        addToCartBtn.addActionListener(e -> {
            UserSession session = UserSession.getInstance();
            if (!session.isLoggedIn()) {
                JOptionPane.showMessageDialog(this,
                    "Please login to add items to cart.",
                    "Login Required",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            User currentUser = session.getCurrentUser();
            CartService cartService = CartService.getInstance();

            boolean success = cartService.addToCart(currentUser, product.getPid(), 1);

            if (success) {
                JOptionPane.showMessageDialog(this,
                    "Added to cart successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Failed to add to cart. Please try again.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        infoPanel.add(nameLabel, BorderLayout.NORTH);
        infoPanel.add(priceLabel, BorderLayout.CENTER);
        infoPanel.add(addToCartBtn, BorderLayout.SOUTH);

        add(imagePanel, BorderLayout.NORTH);
        add(infoPanel, BorderLayout.CENTER);
    }

    public static void addPlaceholder(JPanel panel, String text, Color color) {
        JLabel placeholder = new JLabel(text, SwingConstants.CENTER);
        placeholder.setForeground(color);
        placeholder.setFont(new Font("Arial", Font.PLAIN, 15));
        panel.add(placeholder, BorderLayout.CENTER);
    }


    public static void loadProductImage(JPanel panel, String imageUrl, int width, int height) {
        panel.removeAll();
        panel.setLayout(new BorderLayout());

        if (imageUrl != null && !imageUrl.trim().isEmpty()) {
            String path = "/images/Product/" + imageUrl.trim();
            java.net.URL imgUrl = ProductCard.class.getResource(path);

            if (imgUrl != null) {
                try {
                    BufferedImage img = ImageIO.read(imgUrl);
                    if (img != null) {
                        Image scaled = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                        JLabel label = new JLabel(new ImageIcon(scaled));
                        label.setHorizontalAlignment(SwingConstants.CENTER);
                        panel.add(label, BorderLayout.CENTER);
                        return;
                    } else {
                        addPlaceholder(panel, "Invalid Image", Color.RED);
                        return;
                    }
                } catch (Exception e) {
                    addPlaceholder(panel, "Error Loading", Color.RED);
                    return;
                }
            } else {
                addPlaceholder(panel, "No Image", Color.GRAY);
                return;
            }
        }

        // No URL
        addPlaceholder(panel, "No Image", Color.GRAY);
    }

    private void addClickListener() {
        if (appFrame != null) {
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            MouseAdapter clickAdapter = new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Navigate to enhanced product details page with current sort type and page
                    appFrame.setScreen(new ProductDetailsScreenEnhanced(appFrame, product, currentSort, currentPage));
                }
                
                @Override
                public void mouseEntered(MouseEvent e) {
                    setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 2));
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                    setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 1));
                }
            };
            
            addMouseListener(clickAdapter);
        }
    }

    public Product getProduct() {
        return product;
    }
    
    public void setAppFrame(AppFrame appFrame) {
        this.appFrame = appFrame;
        addClickListener();
    }
}
