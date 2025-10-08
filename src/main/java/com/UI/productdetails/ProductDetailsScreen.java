package com.UI.productdetails;

import com.Main.AppFrame;
import com.Main.Screen;
import com.UI.components.ReviewCard;
import com.UI.components.StarRatingPanel;
import com.UI.store.StoreHeader;
import com.UI.store.StoreScreen;
import com.model.Product;
import com.model.Review;
import com.model.User;
import com.service.ReviewService;
import com.service.UserSession;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Product Details Page - displays comprehensive product information
 * Includes: product info, specifications, stock status, reviews, and add to cart functionality
 */
public class ProductDetailsScreen extends Screen {
    private Product product;
    private ReviewService reviewService;
    private JPanel mainContentPanel;
    private JScrollPane scrollPane;

    public ProductDetailsScreen(AppFrame appFrame) {
        this(appFrame, null);
    }

    public ProductDetailsScreen(AppFrame appFrame, Product product) {
        super(appFrame);
        this.product = product;
        this.reviewService = ReviewService.getInstance();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);
        initUI();
    }

    @Override
    protected void initUI() {
        // Header
        StoreHeader header = new StoreHeader(appFrame);
        header.setPreferredSize(new Dimension(1024, 70));
        panel.add(header, BorderLayout.NORTH);

        // Main content with scroll
        mainContentPanel = new JPanel();
        mainContentPanel.setLayout(null);
        mainContentPanel.setBackground(Color.WHITE);
        
        if (product == null) {
            showEmptyState();
            mainContentPanel.setPreferredSize(new Dimension(1024, 600));
        } else {
            buildProductDetails();
            // Calculate total height based on content
            int totalHeight = calculateContentHeight();
            mainContentPanel.setPreferredSize(new Dimension(1024, totalHeight));
        }

        scrollPane = new JScrollPane(mainContentPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        panel.add(scrollPane, BorderLayout.CENTER);
    }

    private void showEmptyState() {
        JPanel emptyPanel = new JPanel();
        emptyPanel.setLayout(new BoxLayout(emptyPanel, BoxLayout.Y_AXIS));
        emptyPanel.setBackground(Color.WHITE);
        emptyPanel.setBounds(312, 200, 400, 200);

        JLabel emptyIcon = new JLabel("📦");
        emptyIcon.setFont(new Font("Arial", Font.PLAIN, 72));
        emptyIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        emptyPanel.add(emptyIcon);

        emptyPanel.add(Box.createVerticalStrut(20));

        JLabel emptyText = new JLabel("No Product Selected");
        emptyText.setFont(new Font("Arial", Font.BOLD, 24));
        emptyText.setForeground(new Color(150, 150, 150));
        emptyText.setAlignmentX(Component.CENTER_ALIGNMENT);
        emptyPanel.add(emptyText);

        emptyPanel.add(Box.createVerticalStrut(10));

        JLabel emptySubtext = new JLabel("Please select a product to view details");
        emptySubtext.setFont(new Font("Arial", Font.PLAIN, 14));
        emptySubtext.setForeground(new Color(180, 180, 180));
        emptySubtext.setAlignmentX(Component.CENTER_ALIGNMENT);
        emptyPanel.add(emptySubtext);

        mainContentPanel.add(emptyPanel);
    }

    private void buildProductDetails() {
        int yPos = 30;

        // Back button
        JButton backBtn = new JButton("← Back");
        backBtn.setFont(new Font("Arial", Font.PLAIN, 14));
        backBtn.setForeground(Color.BLACK);
        backBtn.setBackground(Color.WHITE);
        backBtn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(8, 16, 8, 16)
        ));
        backBtn.setFocusPainted(false);
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.setBounds(45, yPos, 90, 36);
        backBtn.addActionListener(e -> appFrame.setScreen(new StoreScreen(appFrame)));
        mainContentPanel.add(backBtn);
        
        yPos += 60;

        // Product Image and Main Info Section
        createProductImageSection(yPos);
        createProductInfoSection(yPos);
        
        yPos += 400;

        // Specifications and Product Information boxes
        createSpecificationsBox(yPos);
        createProductInformationBox(yPos);
        
        yPos += 280;

        // Reviews Section
        createReviewsSection(yPos);
    }

    private void createProductImageSection(int yPos) {
        // Product image placeholder
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new BorderLayout());
        imagePanel.setBounds(45, yPos, 400, 350);
        imagePanel.setBackground(new Color(240, 240, 240));
        imagePanel.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));

        // Try to load image or show placeholder
        JLabel imageLabel = new JLabel("🖼", SwingConstants.CENTER);
        imageLabel.setFont(new Font("Arial", Font.PLAIN, 100));
        imageLabel.setForeground(new Color(180, 180, 180));
        imagePanel.add(imageLabel, BorderLayout.CENTER);

        JLabel imageNote = new JLabel("Product Image", SwingConstants.CENTER);
        imageNote.setFont(new Font("Arial", Font.PLAIN, 12));
        imageNote.setForeground(new Color(150, 150, 150));
        imagePanel.add(imageNote, BorderLayout.SOUTH);

        mainContentPanel.add(imagePanel);
    }

    private void createProductInfoSection(int yPos) {
        int xPos = 470;
        int currentY = yPos;

        // Category tag
        JLabel categoryTag = new JLabel(product.getCategory());
        categoryTag.setFont(new Font("Arial", Font.PLAIN, 12));
        categoryTag.setForeground(new Color(139, 92, 246)); // Purple color
        categoryTag.setBackground(new Color(237, 233, 254));
        categoryTag.setOpaque(true);
        categoryTag.setBorder(BorderFactory.createEmptyBorder(4, 12, 4, 12));
        categoryTag.setBounds(xPos, currentY, 80, 24);
        mainContentPanel.add(categoryTag);
        currentY += 35;

        // Product name
        JLabel productName = new JLabel(product.getName());
        productName.setFont(new Font("Arial", Font.BOLD, 24));
        productName.setForeground(Color.BLACK);
        productName.setBounds(xPos, currentY, 450, 32);
        mainContentPanel.add(productName);
        currentY += 40;

        // Star rating
        StarRatingPanel stars = new StarRatingPanel(product.getRating(), 20, false);
        stars.setBounds(xPos, currentY, 120, 24);
        mainContentPanel.add(stars);
        currentY += 35;

        // Product description
        JTextArea description = new JTextArea(product.getDescription());
        description.setFont(new Font("Arial", Font.PLAIN, 13));
        description.setForeground(new Color(100, 100, 100));
        description.setBackground(Color.WHITE);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setEditable(false);
        description.setBorder(BorderFactory.createEmptyBorder());
        description.setBounds(xPos, currentY, 450, 50);
        mainContentPanel.add(description);
        currentY += 65;

        // Price
        JLabel priceLabel = new JLabel(String.format("$%.2f", product.getPrice()));
        priceLabel.setFont(new Font("Arial", Font.BOLD, 32));
        priceLabel.setForeground(new Color(37, 99, 235)); // Blue color
        priceLabel.setBounds(xPos, currentY, 200, 40);
        mainContentPanel.add(priceLabel);
        currentY += 55;

        // Quantity selector
        JLabel qtyLabel = new JLabel("Quantity:");
        qtyLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        qtyLabel.setForeground(Color.BLACK);
        qtyLabel.setBounds(xPos, currentY, 80, 28);
        mainContentPanel.add(qtyLabel);

        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, 
            product.isInStock() ? product.getStockQuantity() : 1, 1);
        JSpinner quantitySpinner = new JSpinner(spinnerModel);
        quantitySpinner.setFont(new Font("Arial", Font.PLAIN, 14));
        quantitySpinner.setBounds(xPos + 90, currentY, 100, 32);
        ((JSpinner.DefaultEditor) quantitySpinner.getEditor()).getTextField().setEditable(true);
        mainContentPanel.add(quantitySpinner);
        currentY += 50;

        // Add to cart button
        JButton addToCartBtn = new JButton("🛒 Add to cart");
        addToCartBtn.setFont(new Font("Arial", Font.BOLD, 16));
        addToCartBtn.setForeground(Color.WHITE);
        addToCartBtn.setBackground(new Color(45, 45, 45));
        addToCartBtn.setBorder(BorderFactory.createEmptyBorder(12, 24, 12, 24));
        addToCartBtn.setFocusPainted(false);
        addToCartBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addToCartBtn.setBounds(xPos, currentY, 450, 48);
        addToCartBtn.setEnabled(product.isInStock());
        addToCartBtn.addActionListener(e -> handleAddToCart((Integer) quantitySpinner.getValue()));
        mainContentPanel.add(addToCartBtn);
    }

    private void createSpecificationsBox(int yPos) {
        JPanel specPanel = new JPanel(null);
        specPanel.setBounds(45, yPos, 430, 250);
        specPanel.setBackground(Color.WHITE);
        specPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Title
        JLabel specTitle = new JLabel("Specifications");
        specTitle.setFont(new Font("Arial", Font.BOLD, 18));
        specTitle.setForeground(Color.BLACK);
        specTitle.setBounds(500, 500, 200, 25);
        specPanel.add(specTitle);

        int currentY = 40;
        Map<String, String> specs = product.getSpecifications();
        
        if (specs.isEmpty()) {
            // Add default specifications for demo
            specs.put("SKU", product.getSku() != null ? product.getSku() : "ENG-SP-002");
            specs.put("Material", "Iridium");
            specs.put("Gap", "0.028-0.031 inches");
            specs.put("Threads", "14mm");
            specs.put("Pack Size", "4 Plugs");
        }

        for (Map.Entry<String, String> entry : specs.entrySet()) {
            // Spec key
            JLabel keyLabel = new JLabel(entry.getKey() + ":");
            keyLabel.setFont(new Font("Arial", Font.PLAIN, 13));
            keyLabel.setForeground(new Color(100, 100, 100));
            keyLabel.setBounds(0, currentY, 150, 20);
            specPanel.add(keyLabel);

            // Spec value
            JLabel valueLabel = new JLabel(entry.getValue());
            valueLabel.setFont(new Font("Arial", Font.PLAIN, 13));
            valueLabel.setForeground(Color.BLACK);
            valueLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            valueLabel.setBounds(150, currentY, 240, 20);
            specPanel.add(valueLabel);

            currentY += 30;

            // Add separator line
            if (currentY < 200) {
                JSeparator separator = new JSeparator();
                separator.setBounds(0, currentY - 7, 390, 1);
                separator.setForeground(new Color(240, 240, 240));
                specPanel.add(separator);
            }
        }

        mainContentPanel.add(specPanel);
    }

    private void createProductInformationBox(int yPos) {
        JPanel infoPanel = new JPanel(null);
        infoPanel.setBounds(500, yPos, 445, 250);
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Title
        JLabel infoTitle = new JLabel("Product informations");
        infoTitle.setFont(new Font("Arial", Font.BOLD, 18));
        infoTitle.setForeground(Color.BLACK);
        infoTitle.setBounds(0, 0, 250, 25);
        infoPanel.add(infoTitle);

        int currentY = 40;

        // Brand
        addInfoRow(infoPanel, "Brands:", product.getBrand(), currentY);
        currentY += 50;

        // Category
        addInfoRow(infoPanel, "Category:", product.getCategory(), currentY);
        currentY += 50;

        // Stock status
        JLabel stockLabel = new JLabel("Stock status:");
        stockLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        stockLabel.setForeground(new Color(100, 100, 100));
        stockLabel.setBounds(0, currentY, 150, 20);
        infoPanel.add(stockLabel);

        JLabel stockValue = new JLabel(product.isInStock() ? "In stock" : "Out of stock");
        stockValue.setFont(new Font("Arial", Font.BOLD, 13));
        stockValue.setForeground(product.isInStock() ? new Color(34, 197, 94) : new Color(239, 68, 68));
        stockValue.setHorizontalAlignment(SwingConstants.RIGHT);
        stockValue.setBounds(150, currentY, 255, 20);
        infoPanel.add(stockValue);
        currentY += 50;

        // Availability
        addInfoRow(infoPanel, "Availability:", 
            product.getStockQuantity() + " units", currentY);

        mainContentPanel.add(infoPanel);
    }

    private void addInfoRow(JPanel parent, String key, String value, int yPos) {
        JLabel keyLabel = new JLabel(key);
        keyLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        keyLabel.setForeground(new Color(100, 100, 100));
        keyLabel.setBounds(0, yPos, 150, 20);
        parent.add(keyLabel);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        valueLabel.setForeground(Color.BLACK);
        valueLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        valueLabel.setBounds(150, yPos, 255, 20);
        parent.add(valueLabel);

        // Separator
        JSeparator separator = new JSeparator();
        separator.setBounds(0, yPos + 28, 405, 1);
        separator.setForeground(new Color(240, 240, 240));
        parent.add(separator);
    }

    private void createReviewsSection(int yPos) {
        // Reviews title
        JLabel reviewsTitle = new JLabel("Latest reviews");
        reviewsTitle.setFont(new Font("Arial", Font.BOLD, 20));
        reviewsTitle.setForeground(Color.BLACK);
        reviewsTitle.setBounds(45, yPos, 200, 30);
        mainContentPanel.add(reviewsTitle);
        yPos += 45;

        // Get reviews for this product
        List<Review> reviews = reviewService.getReviewsByProductId(product.getId());

        if (reviews.isEmpty()) {
            JLabel noReviews = new JLabel("No reviews yet. Be the first to review!");
            noReviews.setFont(new Font("Arial", Font.PLAIN, 14));
            noReviews.setForeground(new Color(150, 150, 150));
            noReviews.setBounds(45, yPos, 400, 30);
            mainContentPanel.add(noReviews);
            yPos += 40;
        } else {
            // Display review cards in a grid (3 per row)
            int xPos = 45;
            int cardWidth = 300;
            int cardHeight = 180;
            int gap = 15;
            int cardsPerRow = 3;
            int cardIndex = 0;

            for (Review review : reviews) {
                ReviewCard reviewCard = new ReviewCard(review);
                int col = cardIndex % cardsPerRow;
                int row = cardIndex / cardsPerRow;
                reviewCard.setBounds(xPos + (col * (cardWidth + gap)), 
                                   yPos + (row * (cardHeight + gap)), 
                                   cardWidth, cardHeight);
                mainContentPanel.add(reviewCard);
                cardIndex++;
            }

            // Calculate yPos for next section
            int rows = (int) Math.ceil((double) reviews.size() / cardsPerRow);
            yPos += rows * (cardHeight + gap) + 20;
        }

        // Add review section (only for logged-in users)
        if (UserSession.getInstance().isLoggedIn()) {
            createAddReviewSection(yPos);
        }
    }

    private void createAddReviewSection(int yPos) {
        // Separator
        JSeparator separator = new JSeparator();
        separator.setBounds(45, yPos, 930, 2);
        separator.setForeground(new Color(230, 230, 230));
        mainContentPanel.add(separator);
        yPos += 25;

        // Add review title
        JLabel addReviewTitle = new JLabel("Write a Review");
        addReviewTitle.setFont(new Font("Arial", Font.BOLD, 18));
        addReviewTitle.setForeground(Color.BLACK);
        addReviewTitle.setBounds(45, yPos, 200, 25);
        mainContentPanel.add(addReviewTitle);
        yPos += 35;

        // Rating selector
        JLabel ratingLabel = new JLabel("Your Rating:");
        ratingLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        ratingLabel.setForeground(Color.BLACK);
        ratingLabel.setBounds(45, yPos, 100, 24);
        mainContentPanel.add(ratingLabel);

        StarRatingPanel ratingSelector = new StarRatingPanel(5, 24, true);
        ratingSelector.setBounds(155, yPos, 150, 30);
        mainContentPanel.add(ratingSelector);
        yPos += 45;

        // Review title
        JLabel titleLabel = new JLabel("Title:");
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setBounds(45, yPos, 80, 24);
        mainContentPanel.add(titleLabel);

        JTextField titleField = new JTextField();
        titleField.setFont(new Font("Arial", Font.PLAIN, 14));
        titleField.setBounds(125, yPos, 820, 32);
        titleField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        mainContentPanel.add(titleField);
        yPos += 45;

        // Review body
        JLabel bodyLabel = new JLabel("Review:");
        bodyLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        bodyLabel.setForeground(Color.BLACK);
        bodyLabel.setBounds(45, yPos, 80, 24);
        mainContentPanel.add(bodyLabel);

        JTextArea bodyArea = new JTextArea();
        bodyArea.setFont(new Font("Arial", Font.PLAIN, 14));
        bodyArea.setLineWrap(true);
        bodyArea.setWrapStyleWord(true);
        bodyArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        
        JScrollPane bodyScrollPane = new JScrollPane(bodyArea);
        bodyScrollPane.setBounds(125, yPos, 820, 100);
        bodyScrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        mainContentPanel.add(bodyScrollPane);
        yPos += 115;

        // Submit button
        JButton submitBtn = new JButton("Submit Review");
        submitBtn.setFont(new Font("Arial", Font.BOLD, 14));
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setBackground(new Color(45, 45, 45));
        submitBtn.setBorder(BorderFactory.createEmptyBorder(10, 24, 10, 24));
        submitBtn.setFocusPainted(false);
        submitBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        submitBtn.setBounds(125, yPos, 180, 40);
        submitBtn.addActionListener(e -> handleSubmitReview(
            ratingSelector.getSelectedRating(), 
            titleField.getText(), 
            bodyArea.getText()
        ));
        mainContentPanel.add(submitBtn);
    }

    private void handleAddToCart(int quantity) {
        if (!UserSession.getInstance().isLoggedIn()) {
            JOptionPane.showMessageDialog(panel,
                "Please login to add items to cart",
                "Login Required",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // TODO: Implement cart functionality
        JOptionPane.showMessageDialog(panel,
            String.format("Added %d x %s to cart!\nTotal: $%.2f", 
                quantity, product.getName(), product.getPrice() * quantity),
            "Success",
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void handleSubmitReview(int rating, String title, String body) {
        // Validation
        if (title.trim().isEmpty() || body.trim().isEmpty()) {
            JOptionPane.showMessageDialog(panel,
                "Please fill in all fields",
                "Validation Error",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Create and add review
        User currentUser = UserSession.getInstance().getCurrentUser();
        Review newReview = new Review(
            0, // ID will be set by service
            product.getId(),
            currentUser.getId(),
            currentUser.getUsername(),
            rating,
            title,
            body,
            LocalDateTime.now()
        );

        boolean success = reviewService.addReview(newReview);

        if (success) {
            JOptionPane.showMessageDialog(panel,
                "Thank you for your review!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            
            // Refresh the screen to show new review
            appFrame.setScreen(new ProductDetailsScreen(appFrame, product));
        } else {
            JOptionPane.showMessageDialog(panel,
                "Failed to submit review. Please try again.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private int calculateContentHeight() {
        // Approximate total content height
        int baseHeight = 950; // Fixed sections
        List<Review> reviews = reviewService.getReviewsByProductId(
            product != null ? product.getId() : 0
        );
        int reviewRows = (int) Math.ceil((double) reviews.size() / 3);
        int reviewHeight = reviewRows * 195;
        int addReviewHeight = UserSession.getInstance().isLoggedIn() ? 400 : 0;
        
        return baseHeight + reviewHeight + addReviewHeight;
    }

    /**
     * Load a product and display its details
     */
    public void loadProduct(Product product) {
        this.product = product;
        mainContentPanel.removeAll();
        if (product == null) {
            showEmptyState();
        } else {
            buildProductDetails();
        }
        int totalHeight = calculateContentHeight();
        mainContentPanel.setPreferredSize(new Dimension(1024, totalHeight));
        mainContentPanel.revalidate();
        mainContentPanel.repaint();
        
        // Scroll to top
        SwingUtilities.invokeLater(() -> {
            scrollPane.getVerticalScrollBar().setValue(0);
        });
    }
}
