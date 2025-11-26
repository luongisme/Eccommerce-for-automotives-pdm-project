package com.UI.admin;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import com.UI.components.RoundedPanel;

public class AdminOverviewPanel extends JPanel {

    private final AdminDashboardController controller;

    public AdminOverviewPanel(AdminDashboardController controller) {
        this.controller = controller;
        setLayout(null);
        setBackground(AdminDashboard.BG_COLOR);
        buildUI();
    }

    private void buildUI() {
        int cardW = 228;
        int cardH = 110;
        int gap = 16;
        int startX = 0;

        int totalRevenue = controller.totalRevenue();
        JPanel revenueCard = createMetricCard("Total Revenue", String.valueOf(totalRevenue)+"$", "+12% from last month");
        revenueCard.setBounds(startX, 0, cardW, cardH);
        add(revenueCard);

        int totalOrders = controller.getTotalOrders();
        JPanel ordersCard = createMetricCard("Total Orders", String.valueOf(totalOrders), "+8% from last month");
        ordersCard.setBounds(startX + (cardW + gap), 0, cardW, cardH);
        add(ordersCard);

        int activeProducts = controller.getHighStockProductsCount(5);
        int lowStockCount = controller.getLowStockProductsCount(5);
        JPanel productsCard = createMetricCard("Active Products", String.valueOf(activeProducts),String.valueOf(lowStockCount)
               + " products low in stock");
        productsCard.setBounds(startX + 2 * (cardW + gap), 0, cardW, cardH);
        add(productsCard);

        int totalUsers = controller.getTotalUsers();
        JPanel customersCard = createMetricCard("Customers", String.valueOf(totalUsers), "+15% from last month");
        customersCard.setBounds(startX + 3 * (cardW + gap), 0, cardW, cardH);
        add(customersCard);

        JPanel recentOrders = createSimpleCard("Recent Orders");
        recentOrders.setBounds(0, cardH + 24, 472, 170);
        add(recentOrders);



        String lowStockText = "Products below stock threshold: " + controller.getLowStockProductsCount(15);

        JPanel lowStock = createSimpleCard("Low Stock Alert");
        lowStock.setBounds(488, cardH + 24, 472, 170);

        JLabel lowStockLabel = new JLabel(lowStockText);
        lowStockLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        lowStockLabel.setForeground(AdminDashboard.TEXT_PRIMARY);
        lowStockLabel.setBounds(20, 50, 400, 20);
        lowStock.add(lowStockLabel);

        add(lowStock);

        java.util.List<com.model.Product> underStock = controller.getUnderStockProducts(3);

        DefaultListModel<String> lowStockListModel = new DefaultListModel<>();
        for (com.model.Product p : underStock) {
            String label = p.getName() + " (stock: " + p.getStockQuantity() + ")";
            lowStockListModel.addElement(label);
        }

        JList<String> lowStockList = new JList<>(lowStockListModel);
        lowStockList.setFont(new Font("Arial", Font.PLAIN, 13));
        lowStockList.setForeground(AdminDashboard.TEXT_PRIMARY);

        JScrollPane lowScroll = new JScrollPane(lowStockList);
        lowScroll.setBounds(20, 48, 430, 100);
        lowScroll.setBorder(null);

        lowStock.add(lowScroll);


        JPanel analytics = createSimpleCard("Sales Analytics");
        analytics.setBounds(0, cardH + 24 + 170 + 24, 472, 170);
        add(analytics);

        JPanel topCategories = createSimpleCard("Top Selling Categories");
        topCategories.setBounds(488, cardH + 24 + 170 + 24, 472, 170);
        add(topCategories);

        java.util.List<com.model.OrderItem> bestItems = controller.getBestSellingItems();

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (com.model.OrderItem oi : bestItems) {
            com.model.Product p = controller.getProductById(oi.getPid());
            if (p != null) {
                String label = p.getName() + " (sold: " + oi.getQuantity() + ")";
                listModel.addElement(label);
            }
        }

        JList<String> list = new JList<>(listModel);
        list.setFont(new Font("Arial", Font.PLAIN, 13));
        list.setForeground(AdminDashboard.TEXT_PRIMARY);

        JScrollPane scroll = new JScrollPane(list);
        scroll.setBounds(20, 48, 430, 100);
        scroll.setBorder(null);

        topCategories.add(scroll);

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


        return card;
    }
}
