package com.UI.productdetails;

import com.Main.AppFrame;
import com.Main.Screen;
import com.UI.components.ReviewCard;
import com.UI.components.RoundedButton;
import com.UI.components.RoundedPanel;
import com.UI.components.StarRatingPanel;
import com.UI.store.StoreHeader;
import com.UI.store.StoreScreen;
import com.model.Product;
import com.model.Review;
import com.model.User;
import com.service.ReviewService;
import com.service.UserSession;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Enhanced Product Details Page with modern UI design
 * Features: rounded corners, shadows, better spacing, improved visual hierarchy
 */
public class ProductDetailsScreenEnhanced extends Screen {
    private Product product;
    private ReviewService reviewService;
    private JPanel mainContentPanel;
    private JScrollPane scrollPane;
    
    // Modern color palette
    private final Color BG_COLOR = new Color(248, 249, 250);
    private final Color CARD_BG = Color.WHITE;
    private final Color PRIMARY_COLOR = new Color(59, 130, 246);
    private final Color SUCCESS_COLOR = new Color(34, 197, 94);
    private final Color ERROR_COLOR = new Color(239, 68, 68);
    // Reserved for future use
    @SuppressWarnings("unused")
    private final Color WARNING_COLOR = new Color(245, 158, 11);
    private final Color PURPLE_COLOR = new Color(139, 92, 246);
    private final Color TEXT_PRIMARY = new Color(17, 24, 39);
    private final Color TEXT_SECONDARY = new Color(107, 114, 128);
    private final Color BORDER_COLOR = new Color(229, 231, 235);

    public ProductDetailsScreenEnhanced(AppFrame appFrame) {
        this(appFrame, null);
    }

    public ProductDetailsScreenEnhanced(AppFrame appFrame, Product product) {
        super(appFrame);
        this.product = product;
        this.reviewService = ReviewService.getInstance();
        panel.setLayout(new BorderLayout());
        panel.setBackground(BG_COLOR);
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
        mainContentPanel.setBackground(BG_COLOR);
        
        if (product == null) {
            showEmptyState();
            mainContentPanel.setPreferredSize(new Dimension(1024, 600));
        } else {
            buildProductDetails();
            int totalHeight = calculateContentHeight();
            mainContentPanel.setPreferredSize(new Dimension(1024, totalHeight));
        }

        scrollPane = new JScrollPane(mainContentPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(16);
        panel.add(scrollPane, BorderLayout.CENTER);
    }

    private void showEmptyState() {
        RoundedPanel emptyPanel = new RoundedPanel(16, true);
        emptyPanel.setLayout(new BoxLayout(emptyPanel, BoxLayout.Y_AXIS));
        emptyPanel.setBackground(CARD_BG);
        emptyPanel.setBounds(312, 150, 400, 300);
        emptyPanel.setBorder(new EmptyBorder(40, 40, 40, 40));

        JLabel emptyIcon = new JLabel("No Product");
        emptyIcon.setFont(new Font("Segoe UI", Font.BOLD, 32));
        emptyIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        emptyPanel.add(emptyIcon);

        emptyPanel.add(Box.createVerticalStrut(24));

        JLabel emptyText = new JLabel("No Product Selected");
        emptyText.setFont(new Font("Segoe UI", Font.BOLD, 26));
        emptyText.setForeground(TEXT_PRIMARY);
        emptyText.setAlignmentX(Component.CENTER_ALIGNMENT);
        emptyPanel.add(emptyText);

        emptyPanel.add(Box.createVerticalStrut(12));

        JLabel emptySubtext = new JLabel("Please select a product to view details");
        emptySubtext.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        emptySubtext.setForeground(TEXT_SECONDARY);
        emptySubtext.setAlignmentX(Component.CENTER_ALIGNMENT);
        emptyPanel.add(emptySubtext);

        mainContentPanel.add(emptyPanel);
    }

    private void buildProductDetails() {
        int yPos = 24;

        // Back button with modern styling
        RoundedButton backBtn = new RoundedButton("← Back", 8);
        backBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        backBtn.setForeground(TEXT_PRIMARY);
        backBtn.setBackground(CARD_BG);
        backBtn.setDefaultColor(CARD_BG);
        backBtn.setBounds(32, yPos, 100, 38);
        backBtn.addActionListener(e -> appFrame.setScreen(new StoreScreen(appFrame)));
        mainContentPanel.add(backBtn);
        
        yPos += 60;

        // Main product container with shadow
        RoundedPanel productContainer = new RoundedPanel(16, true);
        productContainer.setLayout(null);
        productContainer.setBackground(CARD_BG);
        productContainer.setBounds(32, yPos, 960, 420);
        
        // Product image and info inside container
        createEnhancedProductImageSection(productContainer, 24, 24);
        createEnhancedProductInfoSection(productContainer, 460, 24);
        
        mainContentPanel.add(productContainer);
        yPos += 444;

        // Specifications and Product Information with improved design
        createEnhancedSpecificationsBox(yPos);
        createEnhancedProductInformationBox(yPos);
        
        yPos += 300;

        // Reviews Section with modern cards
        createEnhancedReviewsSection(yPos);
    }

    private void createEnhancedProductImageSection(JPanel container, int xPos, int yPos) {
        RoundedPanel imagePanel = new RoundedPanel(12, false);
        imagePanel.setLayout(new BorderLayout());
        imagePanel.setBounds(xPos, yPos, 400, 372);
        imagePanel.setBackground(new Color(248, 250, 252));
        imagePanel.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));

        // Gradient background for image area
        JPanel imageContent = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth();
                int h = getHeight();
                GradientPaint gp = new GradientPaint(
                    0, 0, new Color(241, 245, 249),
                    0, h, new Color(226, 232, 240)
                );
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        imageContent.setOpaque(false);

        JLabel imageLabel = new JLabel("Product Image", SwingConstants.CENTER);
        imageLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        imageLabel.setForeground(new Color(148, 163, 184));
        imageContent.add(imageLabel, BorderLayout.CENTER);

        JLabel imageNote = new JLabel("Product Image", SwingConstants.CENTER);
        imageNote.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        imageNote.setForeground(TEXT_SECONDARY);
        imageNote.setBorder(new EmptyBorder(0, 0, 16, 0));
        imageContent.add(imageNote, BorderLayout.SOUTH);

        imagePanel.add(imageContent);
        container.add(imagePanel);
    }

    private void createEnhancedProductInfoSection(JPanel container, int xPos, int yPos) {
        int currentY = yPos;

        // Category tag with modern pill design
        JLabel categoryTag = new JLabel(product.getCategory());
        categoryTag.setFont(new Font("Segoe UI", Font.BOLD, 11));
        categoryTag.setForeground(PURPLE_COLOR);
        categoryTag.setBackground(new Color(243, 232, 255));
        categoryTag.setOpaque(true);
        categoryTag.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(233, 213, 255), 1),
            BorderFactory.createEmptyBorder(6, 14, 6, 14)
        ));
        categoryTag.setBounds(xPos, currentY, 100, 28);
        container.add(categoryTag);
        currentY += 42;

        // Product name with better typography
        JLabel productName = new JLabel("<html>" + product.getName() + "</html>");
        productName.setFont(new Font("Segoe UI", Font.BOLD, 28));
        productName.setForeground(TEXT_PRIMARY);
        productName.setBounds(xPos, currentY, 475, 70);
        container.add(productName);
        currentY += 75;

        // Star rating with review count
        JPanel ratingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        ratingPanel.setOpaque(false);
        ratingPanel.setBounds(xPos, currentY, 300, 28);
        
        StarRatingPanel stars = new StarRatingPanel(product.getRating(), 18, false);
        ratingPanel.add(stars);
        
        JLabel ratingText = new JLabel(String.format("%.1f", product.getRating()));
        ratingText.setFont(new Font("Segoe UI", Font.BOLD, 14));
        ratingText.setForeground(TEXT_PRIMARY);
        ratingPanel.add(ratingText);
        
        int reviewCount = reviewService.getReviewCount(product.getId());
        JLabel reviewCountLabel = new JLabel("(" + reviewCount + " reviews)");
        reviewCountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        reviewCountLabel.setForeground(TEXT_SECONDARY);
        ratingPanel.add(reviewCountLabel);
        
        container.add(ratingPanel);
        currentY += 36;

        // Product description with better styling
        JTextArea description = new JTextArea(product.getDescription());
        description.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        description.setForeground(TEXT_SECONDARY);
        description.setBackground(CARD_BG);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setEditable(false);
        description.setBorder(BorderFactory.createEmptyBorder());
        description.setBounds(xPos, currentY, 475, 55);
        container.add(description);
        currentY += 70;

        // Price with modern styling
        JLabel priceLabel = new JLabel(String.format("$%.2f", product.getPrice()));
        priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        priceLabel.setForeground(PRIMARY_COLOR);
        priceLabel.setBounds(xPos, currentY, 250, 44);
        container.add(priceLabel);
        currentY += 56;

        // Quantity selector with modern design
        JPanel qtyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        qtyPanel.setOpaque(false);
        qtyPanel.setBounds(xPos, currentY, 300, 40);
        
        JLabel qtyLabel = new JLabel("Quantity:");
        qtyLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        qtyLabel.setForeground(TEXT_PRIMARY);
        qtyPanel.add(qtyLabel);

        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, 
            product.isInStock() ? product.getStockQuantity() : 1, 1);
        JSpinner quantitySpinner = new JSpinner(spinnerModel);
        quantitySpinner.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        quantitySpinner.setPreferredSize(new Dimension(80, 36));
        ((JSpinner.DefaultEditor) quantitySpinner.getEditor()).getTextField().setHorizontalAlignment(JTextField.CENTER);
        qtyPanel.add(quantitySpinner);
        
        container.add(qtyPanel);
        currentY += 50;

        // Add to cart button with gradient and shadow
        RoundedButton addToCartBtn = new RoundedButton("Add to cart", 10);
        addToCartBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        addToCartBtn.setForeground(Color.WHITE);
        addToCartBtn.setDefaultColor(PRIMARY_COLOR);
        addToCartBtn.setBounds(xPos, currentY, 475, 52);
        addToCartBtn.setEnabled(product.isInStock());
        addToCartBtn.addActionListener(e -> handleAddToCart((Integer) quantitySpinner.getValue()));
        container.add(addToCartBtn);
    }

    private void createEnhancedSpecificationsBox(int yPos) {
        RoundedPanel specPanel = new RoundedPanel(16, true);
        specPanel.setLayout(null);
        specPanel.setBounds(32, yPos, 472, 300);
        specPanel.setBackground(CARD_BG);
        specPanel.setBorder(new EmptyBorder(24, 24, 24, 24));

        // Title
        JLabel specTitle = new JLabel("Specifications");
        specTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        specTitle.setForeground(TEXT_PRIMARY);
        specTitle.setBounds(0, 0, 250, 28);
        specPanel.add(specTitle);

        int currentY = 48;
        Map<String, String> specs = product.getSpecifications();
        
        if (specs.isEmpty()) {
            specs.put("SKU", product.getSku() != null ? product.getSku() : "ENG-SP-002");
            specs.put("Material", "Iridium");
            specs.put("Gap", "0.028-0.031 inches");
            specs.put("Threads", "14mm");
            specs.put("Pack Size", "4 Plugs");
        }

        int index = 0;
        for (Map.Entry<String, String> entry : specs.entrySet()) {
            if (index >= 5) break; // Limit to 5 specs for better UI
            
            // Alternate background for rows
            if (index % 2 == 0) {
                JPanel rowBg = new JPanel();
                rowBg.setBackground(new Color(249, 250, 251));
                rowBg.setBounds(0, currentY - 6, 424, 32);
                specPanel.add(rowBg);
            }
            
            // Spec key
            JLabel keyLabel = new JLabel(entry.getKey());
            keyLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            keyLabel.setForeground(TEXT_SECONDARY);
            keyLabel.setBounds(0, currentY, 180, 22);
            specPanel.add(keyLabel);

            // Spec value
            JLabel valueLabel = new JLabel(entry.getValue());
            valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
            valueLabel.setForeground(TEXT_PRIMARY);
            valueLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            valueLabel.setBounds(180, currentY, 244, 22);
            specPanel.add(valueLabel);

            currentY += 38;
            index++;
        }

        mainContentPanel.add(specPanel);
    }

    private void createEnhancedProductInformationBox(int yPos) {
        RoundedPanel infoPanel = new RoundedPanel(16, true);
        infoPanel.setLayout(null);
        infoPanel.setBounds(520, yPos, 472, 300);
        infoPanel.setBackground(CARD_BG);
        infoPanel.setBorder(new EmptyBorder(24, 24, 24, 24));

        // Title
        JLabel infoTitle = new JLabel("Product Information");
        infoTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        infoTitle.setForeground(TEXT_PRIMARY);
        infoTitle.setBounds(0, 0, 300, 28);
        infoPanel.add(infoTitle);

        int currentY = 48;

        // Brand
        addEnhancedInfoRow(infoPanel, "Brand", product.getBrand(), currentY, 0);
        currentY += 52;

        // Category
        addEnhancedInfoRow(infoPanel, "Category", product.getCategory(), currentY, 1);
        currentY += 52;

        // Stock status with badge
        JLabel stockLabel = new JLabel("Stock Status");
        stockLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        stockLabel.setForeground(TEXT_SECONDARY);
        stockLabel.setBounds(0, currentY, 180, 22);
        infoPanel.add(stockLabel);

        JLabel stockBadge = new JLabel(product.isInStock() ? "● In Stock" : "● Out of Stock");
        stockBadge.setFont(new Font("Segoe UI", Font.BOLD, 14));
        stockBadge.setForeground(product.isInStock() ? SUCCESS_COLOR : ERROR_COLOR);
        stockBadge.setHorizontalAlignment(SwingConstants.RIGHT);
        stockBadge.setBounds(180, currentY, 236, 22);
        infoPanel.add(stockBadge);
        currentY += 52;

        // Availability
        addEnhancedInfoRow(infoPanel, "Availability", 
            product.getStockQuantity() + " units", currentY, 3);

        mainContentPanel.add(infoPanel);
    }

    private void addEnhancedInfoRow(JPanel parent, String key, String value, int yPos, int index) {
        // Alternate background
        if (index % 2 == 0) {
            JPanel rowBg = new JPanel();
            rowBg.setBackground(new Color(249, 250, 251));
            rowBg.setBounds(0, yPos - 6, 424, 32);
            parent.add(rowBg);
        }
        
        JLabel keyLabel = new JLabel(key);
        keyLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        keyLabel.setForeground(TEXT_SECONDARY);
        keyLabel.setBounds(0, yPos, 180, 22);
        parent.add(keyLabel);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        valueLabel.setForeground(TEXT_PRIMARY);
        valueLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        valueLabel.setBounds(180, yPos, 244, 22);
        parent.add(valueLabel);
    }

    private void createEnhancedReviewsSection(int yPos) {
        // Reviews title
        JLabel reviewsTitle = new JLabel("Customer Reviews");
        reviewsTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        reviewsTitle.setForeground(TEXT_PRIMARY);
        reviewsTitle.setBounds(32, yPos, 300, 32);
        mainContentPanel.add(reviewsTitle);
        yPos += 48;

        List<Review> reviews = reviewService.getReviewsByProductId(product.getId());

        if (reviews.isEmpty()) {
            RoundedPanel noReviewsPanel = new RoundedPanel(12, true);
            noReviewsPanel.setBackground(CARD_BG);
            noReviewsPanel.setBounds(32, yPos, 960, 100);
            noReviewsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 30));
            
            JLabel noReviews = new JLabel("No reviews yet. Be the first to review!");
            noReviews.setFont(new Font("Segoe UI", Font.PLAIN, 15));
            noReviews.setForeground(TEXT_SECONDARY);
            noReviewsPanel.add(noReviews);
            
            mainContentPanel.add(noReviewsPanel);
            yPos += 116;
        } else {
            // Review cards grid with better spacing
            int xPos = 32;
            int cardWidth = 306;
            int cardHeight = 200;
            int gap = 21;
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

            int rows = (int) Math.ceil((double) reviews.size() / cardsPerRow);
            yPos += rows * (cardHeight + gap) + 24;
        }

        // Add review section
        if (UserSession.getInstance().isLoggedIn()) {
            createEnhancedAddReviewSection(yPos);
        }
    }

    private void createEnhancedAddReviewSection(int yPos) {
        // Container for add review
        RoundedPanel addReviewPanel = new RoundedPanel(16, true);
        addReviewPanel.setLayout(null);
        addReviewPanel.setBackground(CARD_BG);
        addReviewPanel.setBounds(32, yPos, 960, 360);
        addReviewPanel.setBorder(new EmptyBorder(32, 32, 32, 32));

        int currentY = 0;

        // Title
        JLabel addReviewTitle = new JLabel("Write a Review");
        addReviewTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        addReviewTitle.setForeground(TEXT_PRIMARY);
        addReviewTitle.setBounds(0, currentY, 300, 28);
        addReviewPanel.add(addReviewTitle);
        currentY += 44;

        // Rating
        JLabel ratingLabel = new JLabel("Your Rating:");
        ratingLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        ratingLabel.setForeground(TEXT_PRIMARY);
        ratingLabel.setBounds(0, currentY, 120, 28);
        addReviewPanel.add(ratingLabel);

        StarRatingPanel ratingSelector = new StarRatingPanel(5, 26, true);
        ratingSelector.setBounds(130, currentY, 160, 32);
        addReviewPanel.add(ratingSelector);
        currentY += 52;

        // Title
        JLabel titleLabel = new JLabel("Review Title:");
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleLabel.setForeground(TEXT_PRIMARY);
        titleLabel.setBounds(0, currentY, 120, 28);
        addReviewPanel.add(titleLabel);

        JTextField titleField = new JTextField();
        titleField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleField.setBounds(130, currentY, 766, 40);
        titleField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 2),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        addReviewPanel.add(titleField);
        currentY += 56;

        // Body
        JLabel bodyLabel = new JLabel("Your Review:");
        bodyLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        bodyLabel.setForeground(TEXT_PRIMARY);
        bodyLabel.setBounds(0, currentY, 120, 28);
        addReviewPanel.add(bodyLabel);

        JTextArea bodyArea = new JTextArea();
        bodyArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        bodyArea.setLineWrap(true);
        bodyArea.setWrapStyleWord(true);
        
        JScrollPane bodyScrollPane = new JScrollPane(bodyArea);
        bodyScrollPane.setBounds(130, currentY, 766, 100);
        bodyScrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 2),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        addReviewPanel.add(bodyScrollPane);
        currentY += 116;

        // Submit button
        RoundedButton submitBtn = new RoundedButton("Submit Review", 10);
        submitBtn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setDefaultColor(PRIMARY_COLOR);
        submitBtn.setBounds(130, currentY, 200, 44);
        submitBtn.addActionListener(e -> handleSubmitReview(
            ratingSelector.getSelectedRating(), 
            titleField.getText(), 
            bodyArea.getText()
        ));
        addReviewPanel.add(submitBtn);

        mainContentPanel.add(addReviewPanel);
    }

    private void handleAddToCart(int quantity) {
        if (!UserSession.getInstance().isLoggedIn()) {
            JOptionPane.showMessageDialog(panel,
                "Please login to add items to cart",
                "Login Required",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(panel,
            String.format("Added %d x %s to cart!\nTotal: $%.2f", 
                quantity, product.getName(), product.getPrice() * quantity),
            "Success",
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void handleSubmitReview(int rating, String title, String body) {
        if (title.trim().isEmpty() || body.trim().isEmpty()) {
            JOptionPane.showMessageDialog(panel,
                "Please fill in all fields",
                "Validation Error",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        User currentUser = UserSession.getInstance().getCurrentUser();
        Review newReview = new Review(
            0,
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
            
            appFrame.setScreen(new ProductDetailsScreenEnhanced(appFrame, product));
        } else {
            JOptionPane.showMessageDialog(panel,
                "Failed to submit review. Please try again.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private int calculateContentHeight() {
        int baseHeight = 1050;
        List<Review> reviews = reviewService.getReviewsByProductId(
            product != null ? product.getId() : 0
        );
        int reviewRows = (int) Math.ceil((double) reviews.size() / 3);
        int reviewHeight = reviews.isEmpty() ? 116 : reviewRows * 221;
        int addReviewHeight = UserSession.getInstance().isLoggedIn() ? 392 : 0;
        
        return baseHeight + reviewHeight + addReviewHeight;
    }

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
        
        SwingUtilities.invokeLater(() -> {
            scrollPane.getVerticalScrollBar().setValue(0);
        });
    }
}
