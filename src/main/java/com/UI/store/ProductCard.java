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

    public ProductCard(Product product) {
        this(product, null);
    }

    public ProductCard(Product product, AppFrame appFrame) {
        this.product = product;
        this.appFrame = appFrame;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1));
        setPreferredSize(new Dimension(170, 240));
        initUI();
        addClickListener();
    }

    private void initUI() {
        // Image placeholder
        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(new Color(220, 220, 220));
        imagePanel.setPreferredSize(new Dimension(180, 140));
        imagePanel.setLayout(new BorderLayout());
        
        JLabel imagePlaceholder = new JLabel("No Image", SwingConstants.CENTER);
        imagePlaceholder.setForeground(new Color(150, 150, 150));
        imagePanel.add(imagePlaceholder, BorderLayout.CENTER);

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

    private void addClickListener() {
        if (appFrame != null) {
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            MouseAdapter clickAdapter = new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Navigate to enhanced product details page
                    appFrame.setScreen(new ProductDetailsScreenEnhanced(appFrame, product));
                }
                
                @Override
                public void mouseEntered(MouseEvent e) {
                    setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100), 2));
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                    setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1));
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
