package com.UI.admin;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import com.UI.components.RoundedPanel;


public class AdminOrdersPanel extends JPanel {

    public AdminOrdersPanel() {
        setLayout(null);
        setBackground(AdminDashboard.BG_COLOR);
        buildUI();
    }

    private void buildUI() {
        JLabel sectionTitle = new JLabel("Order Management");
        sectionTitle.setFont(new Font("Arial", Font.BOLD, 16));
        sectionTitle.setForeground(AdminDashboard.TEXT_PRIMARY);
        sectionTitle.setBounds(0, 0, 300, 24);
        add(sectionTitle);

        JTextField searchField = new JTextField("Search orders...");
        searchField.setFont(new Font("Arial", Font.PLAIN, 12));
        searchField.setForeground(AdminDashboard.TEXT_SECONDARY);
        searchField.setBounds(0, 36, 260, 30);
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AdminDashboard.BORDER_COLOR, 1),
                new EmptyBorder(4, 8, 4, 8)
        ));
        add(searchField);

        JComboBox<String> statusFilter = new JComboBox<>(new String[]{
                "All status", "Pending", "Processing", "Shipped", "Delivered"
        });
        statusFilter.setFont(new Font("Arial", Font.PLAIN, 12));
        statusFilter.setBounds(276, 36, 180, 30);
        add(statusFilter);

        JPanel order1 = createOrderCard("Order #1", "10/1/2024  2 items", "User #2", "delivered", "$139.97");
        order1.setBounds(0, 84, 976, 90);
        add(order1);

        JPanel order2 = createOrderCard("Order #2", "20/1/2024  1 item", "User #2", "shipped", "$519.96");
        order2.setBounds(0, 186, 976, 90);
        add(order2);
    }

    private JPanel createOrderCard(String title, String meta, String customer, String status, String amount) {
        RoundedPanel card = new RoundedPanel(16, true);
        card.setLayout(null);
        card.setBackground(AdminDashboard.CARD_BG);
        card.setBorder(new EmptyBorder(14, 18, 14, 18));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 13));
        titleLabel.setForeground(AdminDashboard.TEXT_PRIMARY);
        titleLabel.setBounds(10, 10, 300, 20);
        card.add(titleLabel);

        JLabel metaLabel = new JLabel(meta);
        metaLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        metaLabel.setForeground(AdminDashboard.TEXT_SECONDARY);
        metaLabel.setBounds(10, 36, 300, 16);
        card.add(metaLabel);

        JLabel customerLabel = new JLabel("Customer: " + customer);
        customerLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        customerLabel.setForeground(AdminDashboard.TEXT_SECONDARY);
        customerLabel.setBounds(10, 60, 300, 16);
        card.add(customerLabel);

        JLabel amountLabel = new JLabel(amount, SwingConstants.RIGHT);
        amountLabel.setFont(new Font("Arial", Font.BOLD, 13));
        amountLabel.setForeground(AdminDashboard.TEXT_PRIMARY);
        amountLabel.setBounds(780, 10, 160, 20);
        card.add(amountLabel);

        JLabel statusBadge = new JLabel(status.toLowerCase(), SwingConstants.CENTER);
        statusBadge.setFont(new Font("Arial", Font.BOLD, 11));
        statusBadge.setOpaque(true);
        statusBadge.setForeground(Color.WHITE);
        statusBadge.setBackground("delivered".equalsIgnoreCase(status)
                ? new Color(34, 197, 94)
                : new Color(59, 130, 246));
        statusBadge.setBounds(780, 10, 80, 20);
        card.add(statusBadge);

        JButton detailsBtn = new JButton("View Details");
        detailsBtn.setFont(new Font("Arial", Font.PLAIN, 11));
        detailsBtn.setFocusPainted(false);
        detailsBtn.setBounds(750, 48, 100, 24);
        card.add(detailsBtn);

        JButton updateBtn = new JButton("Update Status");
        updateBtn.setFont(new Font("Arial", Font.PLAIN, 11));
        updateBtn.setFocusPainted(false);
        updateBtn.setBounds(856, 48, 110, 24);
        card.add(updateBtn);

        // Wire buttons: one dialog for viewing details, one dialog for updating status
        detailsBtn.addActionListener(e -> openOrderDetails(card, title, meta, customer, status, amount, statusBadge));
        updateBtn.addActionListener(e -> openUpdateStatus(card, title, status, statusBadge));

        return card;
    }

    private void openOrderDetails(Component parent,
                                  String title,
                                  String meta,
                                  String customer,
                                  String status,
                                  String amount,
                                  JLabel statusBadge) {

        // For now these are mock values matching your screenshot
        String orderNumberLabel = title;
        String orderDateLabel = meta.split(" ")[0]; // crude extraction
        String customerLabel = customer;
        String paymentMethod = "Credit Card";
        String paymentStatus = "Completed";
        String initialStatus = status;
        String totalAmount = amount;

        String productName = "All-Season Tire 225/60R16";
        String productBrand = "TireMax";
        String productPrice = "$129.99";
        String productQty = "4";
        String productSubtotal = "$519.96";

        String shippingAddress = "123 Main St\nNew York, NY 10001";

        OrderDetailsDialog.showDialog(
                parent,
                orderNumberLabel,
                orderDateLabel,
                customerLabel,
                paymentMethod,
                paymentStatus,
                initialStatus,
                totalAmount,
                productName,
                productBrand,
                productPrice,
                productQty,
                productSubtotal,
                shippingAddress
        );
    }

    private void openUpdateStatus(Component parent,
                                  String title,
                                  String status,
                                  JLabel statusBadge) {

        String orderId = title; // e.g. "Order #2"

        Window w = parent != null ? SwingUtilities.getWindowAncestor(parent) : null;
        UpdateOrderStatusDialog dialog = new UpdateOrderStatusDialog(
                w,
                orderId,
                status,
                newStatus -> {
                    statusBadge.setText(newStatus.toLowerCase());
                    statusBadge.setBackground("delivered".equalsIgnoreCase(newStatus)
                            ? new Color(34, 197, 94)
                            : new Color(59, 130, 246));
                }
        );
        dialog.setVisible(true);
    }
}
