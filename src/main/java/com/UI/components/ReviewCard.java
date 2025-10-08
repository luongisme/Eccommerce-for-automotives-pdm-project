package com.UI.components;

import com.model.Review;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;

/**
 * A card component to display a single review
 */
public class ReviewCard extends JPanel {
    private Review review;

    public ReviewCard(Review review) {
        this.review = review;
        setLayout(null);
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        setPreferredSize(new Dimension(280, 160));
        
        initUI();
    }

    private void initUI() {
        int yPos = 0;

        // Star rating
        StarRatingPanel stars = new StarRatingPanel(review.getRating(), 16, false);
        stars.setBounds(0, yPos, 100, 20);
        add(stars);
        yPos += 28;

        // Review title
        JLabel titleLabel = new JLabel(review.getTitle());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setBounds(0, yPos, 250, 20);
        add(titleLabel);
        yPos += 25;

        // Review body (truncated if too long)
        String bodyText = review.getBody();
        if (bodyText.length() > 80) {
            bodyText = bodyText.substring(0, 77) + "...";
        }
        
        JTextArea bodyArea = new JTextArea(bodyText);
        bodyArea.setFont(new Font("Arial", Font.PLAIN, 12));
        bodyArea.setForeground(new Color(100, 100, 100));
        bodyArea.setBackground(Color.WHITE);
        bodyArea.setLineWrap(true);
        bodyArea.setWrapStyleWord(true);
        bodyArea.setEditable(false);
        bodyArea.setBounds(0, yPos, 250, 40);
        bodyArea.setBorder(BorderFactory.createEmptyBorder());
        add(bodyArea);
        yPos += 48;

        // Reviewer info panel
        JPanel reviewerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        reviewerPanel.setOpaque(false);
        reviewerPanel.setBounds(0, yPos, 250, 24);

        // Profile icon
        JLabel profileIcon = new JLabel("👤");
        profileIcon.setFont(new Font("Arial", Font.PLAIN, 16));
        reviewerPanel.add(profileIcon);

        // Reviewer name and date
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);

        JLabel nameLabel = new JLabel(review.getUsername());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 11));
        nameLabel.setForeground(Color.BLACK);
        textPanel.add(nameLabel);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
        JLabel dateLabel = new JLabel(review.getCreatedAt().format(formatter));
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        dateLabel.setForeground(new Color(150, 150, 150));
        textPanel.add(dateLabel);

        reviewerPanel.add(textPanel);
        add(reviewerPanel);
    }
}
