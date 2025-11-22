package com.UI.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.GeneralPath;

/**
 * A reusable component to display star ratings using custom-drawn stars.
 * Supports both interactive (for user input) and non-interactive (for display) modes.
 */
public class StarRatingPanel extends JPanel {
    private double rating;
    private int starSize;
    private boolean interactive;
    private int selectedRating;
    private int hoverRating = 0;
    private RatingChangeListener listener;
    
    private static final Color FILLED_COLOR = new Color(255, 193, 7); // Gold color
    private static final Color EMPTY_COLOR = new Color(220, 220, 220); // Light gray
    private static final Color HOVER_COLOR = new Color(255, 215, 0); // Bright gold for hover
    private static final int STAR_SPACING = 4;

    public interface RatingChangeListener {
        void onRatingChanged(int newRating);
    }

    /**
     * Creates a new StarRatingPanel
     * @param rating The rating to display (0.0 to 5.0)
     * @param starSize The size of each star in pixels
     * @param interactive Whether the stars should be clickable (true for user input, false for display)
     */
    public StarRatingPanel(double rating, int starSize, boolean interactive) {
        this.rating = Math.max(0, Math.min(5, rating)); // Clamp between 0 and 5
        this.starSize = starSize;
        this.interactive = interactive;
        this.selectedRating = (int) Math.round(rating);
        
        setOpaque(false);
        setPreferredSize(new Dimension(starSize * 5 + STAR_SPACING * 4, starSize));
        
        if (interactive) {
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int clickedStar = getStarAtPosition(e.getX());
                    if (clickedStar > 0) {
                        selectedRating = clickedStar;
                        StarRatingPanel.this.rating = clickedStar;
                        if (listener != null) {
                            listener.onRatingChanged(selectedRating);
                        }
                        repaint();
                    }
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                    hoverRating = 0;
                    repaint();
                }
            });
            
            addMouseMotionListener(new MouseAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    int hoveredStar = getStarAtPosition(e.getX());
                    if (hoveredStar != hoverRating) {
                        hoverRating = hoveredStar;
                        repaint();
                    }
                }
            });
        }
    }
    
    /**
     * Determines which star (1-5) is at the given x position
     */
    private int getStarAtPosition(int x) {
        int starWidth = starSize + STAR_SPACING;
        int starIndex = x / starWidth;
        return Math.max(0, Math.min(5, starIndex + 1));
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        
        // Enable anti-aliasing for smooth stars
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
        int x = 0;
        for (int i = 1; i <= 5; i++) {
            drawStar(g2d, x, 0, i);
            x += starSize + STAR_SPACING;
        }
        
        g2d.dispose();
    }
    
    /**
     * Draws a single star at the specified position
     */
    private void drawStar(Graphics2D g2d, int x, int y, int position) {
        GeneralPath star = createStarShape(x, y, starSize);
        
        // Determine the fill based on rating and interaction state
        boolean shouldFill;
        Color fillColor;
        
        if (interactive && hoverRating > 0) {
            // Show hover state
            shouldFill = position <= hoverRating;
            fillColor = shouldFill ? HOVER_COLOR : EMPTY_COLOR;
        } else {
            // Show current rating
            double fullStars = Math.floor(rating);
            double fractionalPart = rating - fullStars;
            
            if (position <= fullStars) {
                shouldFill = true;
                fillColor = FILLED_COLOR;
            } else if (position == Math.ceil(rating) && fractionalPart > 0) {
                // Draw partial star for non-interactive display
                if (!interactive) {
                    drawPartialStar(g2d, star, fractionalPart);
                    return;
                } else {
                    shouldFill = false;
                    fillColor = EMPTY_COLOR;
                }
            } else {
                shouldFill = false;
                fillColor = EMPTY_COLOR;
            }
        }
        
        // Fill the star
        g2d.setColor(fillColor);
        g2d.fill(star);
        
        // Draw outline
        g2d.setColor(shouldFill ? FILLED_COLOR.darker() : EMPTY_COLOR.darker());
        g2d.setStroke(new BasicStroke(1.0f));
        g2d.draw(star);
    }
    
    /**
     * Draws a partially filled star (for non-interactive display of fractional ratings)
     */
    private void drawPartialStar(Graphics2D g2d, GeneralPath star, double fillPercentage) {
        // Get the bounds of the star
        Rectangle bounds = star.getBounds();
        
        // Draw empty star first
        g2d.setColor(EMPTY_COLOR);
        g2d.fill(star);
        
        // Clip and draw filled portion
        Shape oldClip = g2d.getClip();
        int fillWidth = (int) (bounds.width * fillPercentage);
        g2d.setClip(bounds.x, bounds.y, fillWidth, bounds.height);
        g2d.setColor(FILLED_COLOR);
        g2d.fill(star);
        g2d.setClip(oldClip);
        
        // Draw outline
        g2d.setColor(FILLED_COLOR.darker());
        g2d.setStroke(new BasicStroke(1.0f));
        g2d.draw(star);
    }
    
    /**
     * Creates a star shape using GeneralPath
     */
    private GeneralPath createStarShape(int x, int y, int size) {
        GeneralPath star = new GeneralPath();
        
        double centerX = x + size / 2.0;
        double centerY = y + size / 2.0;
        double outerRadius = size / 2.0;
        double innerRadius = outerRadius * 0.4;
        
        double angle = -Math.PI / 2; // Start from top
        double angleStep = Math.PI / 5; // 36 degrees for 10 points (5 outer, 5 inner)
        
        // Create the star with 5 points
        for (int i = 0; i < 10; i++) {
            double radius = (i % 2 == 0) ? outerRadius : innerRadius;
            double px = centerX + radius * Math.cos(angle);
            double py = centerY + radius * Math.sin(angle);
            
            if (i == 0) {
                star.moveTo(px, py);
            } else {
                star.lineTo(px, py);
            }
            
            angle += angleStep;
        }
        
        star.closePath();
        return star;
    }

    public void setRatingChangeListener(RatingChangeListener listener) {
        this.listener = listener;
    }
    
    public int getSelectedRating() {
        return selectedRating;
    }
    
    public double getRating() {
        return rating;
    }
    
    public void setRating(double rating) {
        this.rating = Math.max(0, Math.min(5, rating));
        this.selectedRating = (int) Math.round(rating);
        repaint();
    }
    
    public boolean isInteractive() {
        return interactive;
    }
}
