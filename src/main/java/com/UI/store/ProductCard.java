package com.UI.store;

import com.Main.AppFrame;
import com.model.Product;
import com.UI.productdetails.ProductDetailsScreenEnhanced;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setPreferredSize(new Dimension(180, 140));
        imagePanel.setBackground(Color.WHITE);

        // Try to load image using ImageIO for better error handling
        String imageUrl = product.getImageUrl();
        if (imageUrl != null && !imageUrl.trim().isEmpty()) {
            String imagePath = "/images/Product/" + imageUrl.trim();
            java.net.URL imgUrl = getClass().getResource(imagePath);
            //System.out.println("Loading image from path: " + imagePath + " | URL: " + imgUrl); debug line

            if (imgUrl != null) {
                try {
                    java.awt.image.BufferedImage bufferedImage = javax.imageio.ImageIO.read(imgUrl);
                    if (bufferedImage != null) {
                        Image scaled = bufferedImage.getScaledInstance(180, 140, Image.SCALE_SMOOTH);
                        JLabel imageLabel = new JLabel(new ImageIcon(scaled));
                        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
                        imagePanel.add(imageLabel, BorderLayout.CENTER);
                    } else {
                        // File exists but is not a valid image
                        addPlaceholder(imagePanel, "Invalid Image", Color.RED);
                    }
                } catch (Exception e) {
                    // Error reading image file
                    addPlaceholder(imagePanel, "Error Loading", Color.RED);
                }
            } else {
                // Image file not found in resources
                addPlaceholder(imagePanel, "No Image", Color.GRAY);
            }
        } else {
            // No image URL provided
            addPlaceholder(imagePanel, "No Image", Color.GRAY);
        }


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

        infoPanel.add(nameLabel, BorderLayout.NORTH);
        infoPanel.add(priceLabel, BorderLayout.CENTER);
        infoPanel.add(addToCartBtn, BorderLayout.SOUTH);

        add(imagePanel, BorderLayout.NORTH);
        add(infoPanel, BorderLayout.CENTER);
    }

    private void addPlaceholder(JPanel panel, String text, Color color) {
        JLabel placeholder = new JLabel(text, SwingConstants.CENTER);
        placeholder.setForeground(color);
        placeholder.setFont(new Font("Arial", Font.PLAIN, 10));
        panel.add(placeholder, BorderLayout.CENTER);
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
