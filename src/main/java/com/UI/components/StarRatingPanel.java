package com.UI.components;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * A reusable component to display star ratings using images
 */
public class StarRatingPanel extends JPanel {
    private double rating;
    private int starSize;
    private boolean interactive;
    private int selectedRating;
    private RatingChangeListener listener;
    private ImageIcon fullStarIcon;
    private ImageIcon halfStarIcon;
    private ImageIcon emptyStarIcon;

    public interface RatingChangeListener {
        void onRatingChanged(int newRating);
    }

    public StarRatingPanel(double rating, int starSize, boolean interactive) {
        this.rating = rating;
        this.starSize = starSize;
        this.interactive = interactive;
        this.selectedRating = (int) Math.round(rating);
        
        setLayout(new FlowLayout(FlowLayout.LEFT, 2, 0));
        setOpaque(false);
        
        loadStarImages();
        createStars();
    }
    
    private void loadStarImages() {
        try {
            // Try to load star images from resources
            URL fullStarUrl = getClass().getResource("/images/star.webp");
            URL halfStarUrl = getClass().getResource("/images/Half_Star.png");
            
            if (fullStarUrl != null && halfStarUrl != null) {
                ImageIcon fullStar = new ImageIcon(fullStarUrl);
                ImageIcon halfStar = new ImageIcon(halfStarUrl);
                
                // Scale images to desired size
                Image scaledFull = fullStar.getImage().getScaledInstance(starSize, starSize, Image.SCALE_SMOOTH);
                Image scaledHalf = halfStar.getImage().getScaledInstance(starSize, starSize, Image.SCALE_SMOOTH);
                
                fullStarIcon = new ImageIcon(scaledFull);
                halfStarIcon = new ImageIcon(scaledHalf);
                
                // Create empty star by converting full star to grayscale
                emptyStarIcon = createEmptyStarIcon(fullStar, starSize);
            } else {
                // Fallback to text-based stars if images not found
                useFallbackStars();
            }
        } catch (Exception e) {
            System.err.println("Error loading star images: " + e.getMessage());
            useFallbackStars();
        }
    }
    
    private void useFallbackStars() {
        // Create simple colored star icons as fallback
        fullStarIcon = null;
        halfStarIcon = null;
        emptyStarIcon = null;
    }
    
    private ImageIcon createEmptyStarIcon(ImageIcon sourceIcon, int size) {
        Image img = sourceIcon.getImage();
        Image scaledImg = img.getScaledInstance(size, size, Image.SCALE_SMOOTH);
        
        // Convert to grayscale
        java.awt.image.BufferedImage grayImage = new java.awt.image.BufferedImage(
            size, size, java.awt.image.BufferedImage.TYPE_INT_ARGB
        );
        Graphics2D g2d = grayImage.createGraphics();
        g2d.drawImage(scaledImg, 0, 0, null);
        
        // Apply gray filter
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                int rgba = grayImage.getRGB(x, y);
                int alpha = (rgba >> 24) & 0xff;
                if (alpha > 0) {
                    grayImage.setRGB(x, y, new Color(220, 220, 220, alpha).getRGB());
                }
            }
        }
        g2d.dispose();
        
        return new ImageIcon(grayImage);
    }

    private void createStars() {
        removeAll();
        
        for (int i = 1; i <= 5; i++) {
            JLabel star = createStar(i);
            add(star);
        }
        
        revalidate();
        repaint();
    }

    private JLabel createStar(int position) {
        JLabel star = new JLabel();
        
        // Determine which icon to use
        if (fullStarIcon != null) {
            // Use image icons
            if (position <= Math.floor(rating)) {
                star.setIcon(fullStarIcon);
            } else if (position == Math.ceil(rating) && rating % 1 != 0) {
                star.setIcon(halfStarIcon);
            } else {
                star.setIcon(emptyStarIcon);
            }
        } else {
            // Fallback to text stars
            star.setFont(new Font("Arial", Font.PLAIN, starSize));
            if (position <= Math.floor(rating)) {
                star.setText("★");
                star.setForeground(new Color(255, 193, 7));
            } else if (position == Math.ceil(rating) && rating % 1 != 0) {
                star.setText("★");
                star.setForeground(new Color(255, 193, 7));
            } else {
                star.setText("☆");
                star.setForeground(new Color(200, 200, 200));
            }
        }
        
        if (interactive) {
            star.setCursor(new Cursor(Cursor.HAND_CURSOR));
            final int starPosition = position;
            
            star.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    selectedRating = starPosition;
                    rating = starPosition;
                    createStars();
                    if (listener != null) {
                        listener.onRatingChanged(selectedRating);
                    }
                }
                
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    highlightStars(starPosition);
                }
                
                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    createStars();
                }
            });
        }
        
        return star;
    }
    
    private void highlightStars(int upTo) {
        Component[] components = getComponents();
        for (int i = 0; i < components.length && i < 5; i++) {
            if (components[i] instanceof JLabel) {
                JLabel star = (JLabel) components[i];
                if (fullStarIcon != null) {
                    star.setIcon(i < upTo ? fullStarIcon : emptyStarIcon);
                } else {
                    star.setText(i < upTo ? "★" : "☆");
                    star.setForeground(i < upTo ? new Color(255, 193, 7) : new Color(200, 200, 200));
                }
            }
        }
        repaint();
    }

    public void setRatingChangeListener(RatingChangeListener listener) {
        this.listener = listener;
    }
    
    public int getSelectedRating() {
        return selectedRating;
    }
    
    public void setRating(double rating) {
        this.rating = rating;
        this.selectedRating = (int) Math.round(rating);
        createStars();
    }
}
