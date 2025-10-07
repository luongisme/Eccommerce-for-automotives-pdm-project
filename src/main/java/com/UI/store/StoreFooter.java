package com.UI.store;

import javax.swing.*;
import java.awt.*;

public class StoreFooter extends JPanel {
    public StoreFooter() {
        setLayout(new BorderLayout());
        setBackground(new Color(37, 99, 235)); // Blue background
        setPreferredSize(new Dimension(1024, 200));
        initUI();
    }

    private void initUI() {
        JPanel contentPanel = new JPanel(null);
        contentPanel.setBackground(new Color(37, 99, 235));
        contentPanel.setPreferredSize(new Dimension(1024, 200));

        // Title
        JLabel titleLabel = new JLabel("Stay Updated with Latest Deals");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(250, 40, 550, 35);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(titleLabel);

        // Subtitle
        JLabel subtitleLabel = new JLabel("Subscribe to our newsletter and be the first to know about new products and exclusive offers");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        subtitleLabel.setForeground(Color.WHITE);
        subtitleLabel.setBounds(180, 80, 680, 20);
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(subtitleLabel);

        // Email input panel
        JPanel emailPanel = new JPanel(new BorderLayout());
        emailPanel.setBackground(Color.WHITE);
        emailPanel.setBounds(310, 115, 260, 42);
        emailPanel.setBorder(BorderFactory.createEmptyBorder());

        JTextField emailField = new JTextField("Enter your email");
        emailField.setFont(new Font("Arial", Font.PLAIN, 14));
        emailField.setForeground(new Color(150, 150, 150));
        emailField.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        emailPanel.add(emailField, BorderLayout.CENTER);

        contentPanel.add(emailPanel);

        // Subscribe button
        JButton subscribeBtn = new JButton("Subscribe");
        subscribeBtn.setFont(new Font("Arial", Font.BOLD, 14));
        subscribeBtn.setForeground(new Color(37, 99, 235));
        subscribeBtn.setBackground(Color.WHITE);
        subscribeBtn.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        subscribeBtn.setFocusPainted(false);
        subscribeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        subscribeBtn.setBounds(580, 115, 120, 42);
        subscribeBtn.addActionListener(e -> {
            String email = emailField.getText();
            if (!email.isEmpty() && !email.equals("Enter your email")) {
                JOptionPane.showMessageDialog(this, "Thank you for subscribing!", "Success", JOptionPane.INFORMATION_MESSAGE);
                emailField.setText("Enter your email");
            }
        });
        contentPanel.add(subscribeBtn);

        add(contentPanel, BorderLayout.CENTER);
    }
}
