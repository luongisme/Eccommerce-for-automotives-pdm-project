package com.UI.admin;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import com.UI.components.RoundedPanel;

public class AdminOverviewPanel extends JPanel {

    public AdminOverviewPanel() {
        setLayout(null);
        setBackground(AdminDashboard.BG_COLOR);
        buildUI();
    }

    private void buildUI() {
        int cardW = 228;
        int cardH = 110;
        int gap = 16;
        int startX = 0;

        JPanel revenueCard = createMetricCard("Total Revenue", "$600.36", "+12% from last month");
        revenueCard.setBounds(startX, 0, cardW, cardH);
        add(revenueCard);

        JPanel ordersCard = createMetricCard("Total Orders", "2", "+8% from last month");
        ordersCard.setBounds(startX + (cardW + gap), 0, cardW, cardH);
        add(ordersCard);

        JPanel productsCard = createMetricCard("Active Products", "16", "0 product low in stock");
        productsCard.setBounds(startX + 2 * (cardW + gap), 0, cardW, cardH);
        add(productsCard);

        JPanel customersCard = createMetricCard("Customers", "2,543", "+15% from last month");
        customersCard.setBounds(startX + 3 * (cardW + gap), 0, cardW, cardH);
        add(customersCard);

        JPanel lowStock = createSimpleCard("Low Stock Alert");
        lowStock.setBounds(0, cardH + 24, 472, 170);
        add(lowStock);

        JPanel topCategories = createSimpleCard("Top Selling Categories");
        topCategories.setBounds(488, cardH + 24, 472, 170);
        add(topCategories);
    }

    private JPanel createMetricCard(String title, String value, String subtitle) {
        RoundedPanel card = new RoundedPanel(16, true);
        card.setLayout(null);
        card.setBackground(AdminDashboard.CARD_BG);
        card.setBorder(new EmptyBorder(14, 18, 14, 18));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        titleLabel.setForeground(AdminDashboard.TEXT_SECONDARY);
        titleLabel.setBounds(10, 10, 180, 18);
        card.add(titleLabel);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 20));
        valueLabel.setForeground(AdminDashboard.TEXT_PRIMARY);
        valueLabel.setBounds(10, 36, 180, 24);
        card.add(valueLabel);

        JLabel subtitleLabel = new JLabel(subtitle);
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        subtitleLabel.setForeground(AdminDashboard.TEXT_SECONDARY);
        subtitleLabel.setBounds(10, 66, 220, 18);
        card.add(subtitleLabel);

        return card;
    }

    private JPanel createSimpleCard(String title) {
        RoundedPanel card = new RoundedPanel(16, true);
        card.setLayout(null);
        card.setBackground(AdminDashboard.CARD_BG);
        card.setBorder(new EmptyBorder(14, 18, 14, 18));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(AdminDashboard.TEXT_PRIMARY);
        titleLabel.setBounds(20, 15, 300, 22);
        card.add(titleLabel);

        JLabel placeholder = new JLabel("No data yet - coming soon", SwingConstants.LEFT);
        placeholder.setFont(new Font("Arial", Font.PLAIN, 13));
        placeholder.setForeground(AdminDashboard.TEXT_SECONDARY);
        placeholder.setBounds(20, 43, 300, 18);
        card.add(placeholder);

        return card;
    }
}
