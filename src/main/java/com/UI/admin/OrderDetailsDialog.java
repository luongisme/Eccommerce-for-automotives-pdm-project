package com.UI.admin;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Controls the logic + UI for:
 * - View Details dialog (first screenshot)
 *
 * It does NOT touch the database.
 */
public class OrderDetailsDialog extends JDialog {

    private final JLabel statusBadge;

    public OrderDetailsDialog(
            Window owner,
            String orderNumberLabel,
            String orderDateLabel,
            String customerLabel,
            String paymentMethod,
            String paymentStatus,
            String initialStatus,
            String totalAmount,
            String productName,
            String productBrand,
            String productPrice,
            String productQty,
            String productSubtotal,
            String shippingAddress
    ) {
        super(owner, "Order Details", ModalityType.APPLICATION_MODAL);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(920, 620);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        JPanel root = new JPanel(null);
        // Soft app background to match other admin screens
        root.setBackground(AdminDashboard.BG_COLOR);
        root.setBorder(new EmptyBorder(16, 24, 24, 24));
        add(root, BorderLayout.CENTER);

        // Main white card that holds all content
        JPanel card = new JPanel(null);
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AdminDashboard.BORDER_COLOR, 1),
                new EmptyBorder(20, 24, 24, 24)
        ));
        card.setBounds(23, 0, 860, 560);
        root.add(card);

        // Header
        JLabel title = new JLabel("Order Details");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(AdminDashboard.TEXT_PRIMARY);
        title.setBounds(0, 0, 400, 30);
        card.add(title);

        JLabel orderLabel = new JLabel(orderNumberLabel);
        orderLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        orderLabel.setForeground(AdminDashboard.TEXT_SECONDARY);
        orderLabel.setBounds(0, 32, 400, 18);
        card.add(orderLabel);

        JButton closeIcon = new JButton("✖");
        closeIcon.setBorderPainted(false);
        closeIcon.setContentAreaFilled(false);
        closeIcon.setFocusPainted(false);
        closeIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeIcon.setBounds(820, 0, 40, 28);
        closeIcon.addActionListener(e -> dispose());
        card.add(closeIcon);

        // Left column
        JLabel orderDateTitle = new JLabel("Order Date");
        orderDateTitle.setFont(new Font("Arial", Font.PLAIN, 12));
        orderDateTitle.setForeground(AdminDashboard.TEXT_SECONDARY);
        orderDateTitle.setBounds(0, 72, 200, 16);
        card.add(orderDateTitle);

        JLabel orderDate = new JLabel(orderDateLabel);
        orderDate.setFont(new Font("Arial", Font.PLAIN, 13));
        orderDate.setForeground(AdminDashboard.TEXT_PRIMARY);
        orderDate.setBounds(0, 92, 260, 18);
        card.add(orderDate);

        JLabel customerTitle = new JLabel("Customer ID");
        customerTitle.setFont(new Font("Arial", Font.PLAIN, 12));
        customerTitle.setForeground(AdminDashboard.TEXT_SECONDARY);
        customerTitle.setBounds(0, 122, 200, 16);
        card.add(customerTitle);

        JLabel customerVal = new JLabel(customerLabel);
        customerVal.setFont(new Font("Arial", Font.PLAIN, 13));
        customerVal.setForeground(AdminDashboard.TEXT_PRIMARY);
        customerVal.setBounds(0, 142, 260, 18);
        card.add(customerVal);

        JLabel statusTitle = new JLabel("Status");
        statusTitle.setFont(new Font("Arial", Font.PLAIN, 12));
        statusTitle.setForeground(AdminDashboard.TEXT_SECONDARY);
        statusTitle.setBounds(0, 172, 200, 16);
        card.add(statusTitle);

        statusBadge = new JLabel(initialStatus.toLowerCase(), SwingConstants.CENTER);
        statusBadge.setFont(new Font("Arial", Font.BOLD, 11));
        statusBadge.setOpaque(true);
        statusBadge.setForeground(new Color(37, 99, 235));
        statusBadge.setBackground(new Color(219, 234, 254));
        statusBadge.setBounds(0, 194, 90, 22);
        card.add(statusBadge);

        // Right column
        JLabel payMethodTitle = new JLabel("Payment Method");
        payMethodTitle.setFont(new Font("Arial", Font.PLAIN, 12));
        payMethodTitle.setForeground(AdminDashboard.TEXT_SECONDARY);
        payMethodTitle.setBounds(420, 72, 200, 16);
        card.add(payMethodTitle);

        JLabel payMethodVal = new JLabel(paymentMethod);
        payMethodVal.setFont(new Font("Arial", Font.PLAIN, 13));
        payMethodVal.setForeground(AdminDashboard.TEXT_PRIMARY);
        payMethodVal.setBounds(420, 92, 260, 18);
        card.add(payMethodVal);

        JLabel payStatusTitle = new JLabel("Payment Status");
        payStatusTitle.setFont(new Font("Arial", Font.PLAIN, 12));
        payStatusTitle.setForeground(AdminDashboard.TEXT_SECONDARY);
        payStatusTitle.setBounds(420, 122, 200, 16);
        card.add(payStatusTitle);

        JLabel payStatusVal = new JLabel(paymentStatus);
        payStatusVal.setFont(new Font("Arial", Font.PLAIN, 13));
        payStatusVal.setForeground(AdminDashboard.TEXT_PRIMARY);
        payStatusVal.setBounds(420, 142, 260, 18);
        card.add(payStatusVal);

        JLabel totalTitle = new JLabel("Total Amount");
        totalTitle.setFont(new Font("Arial", Font.PLAIN, 12));
        totalTitle.setForeground(AdminDashboard.TEXT_SECONDARY);
        totalTitle.setBounds(420, 172, 200, 16);
        card.add(totalTitle);

        JLabel totalAmountLabel = new JLabel(totalAmount);
        totalAmountLabel.setFont(new Font("Arial", Font.BOLD, 20));
        totalAmountLabel.setForeground(new Color(37, 99, 235));
        totalAmountLabel.setBounds(420, 192, 260, 24);
        card.add(totalAmountLabel);

        // Order items section
        JLabel itemsTitle = new JLabel("Order Items");
        itemsTitle.setFont(new Font("Arial", Font.BOLD, 14));
        itemsTitle.setForeground(AdminDashboard.TEXT_PRIMARY);
        itemsTitle.setBounds(0, 238, 200, 20);
        card.add(itemsTitle);

        JPanel itemsHeader = new JPanel(null);
        itemsHeader.setBackground(new Color(248, 249, 251));
        itemsHeader.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, AdminDashboard.BORDER_COLOR));
        itemsHeader.setBounds(0, 266, 860, 30);

        addHeaderLabel(itemsHeader, "Product", 16, 8, 260);
        addHeaderLabel(itemsHeader, "Price", 320, 8, 120);
        addHeaderLabel(itemsHeader, "Quantity", 460, 8, 120);
        addHeaderLabel(itemsHeader, "Subtotal", 620, 8, 120);
        card.add(itemsHeader);

        JPanel itemRow = new JPanel(null);
        itemRow.setBackground(Color.WHITE);
        itemRow.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, AdminDashboard.BORDER_COLOR));
        itemRow.setBounds(0, 296, 860, 60);

        JLabel prodName = new JLabel(productName);
        prodName.setFont(new Font("Arial", Font.PLAIN, 13));
        prodName.setForeground(AdminDashboard.TEXT_PRIMARY);
        prodName.setBounds(16, 8, 360, 18);
        itemRow.add(prodName);

        JLabel prodBrand = new JLabel(productBrand);
        prodBrand.setFont(new Font("Arial", Font.PLAIN, 11));
        prodBrand.setForeground(AdminDashboard.TEXT_SECONDARY);
        prodBrand.setBounds(16, 30, 360, 16);
        itemRow.add(prodBrand);

        addCellLabel(itemRow, productPrice, 320, 20, 120, SwingConstants.LEFT);
        addCellLabel(itemRow, productQty, 460, 20, 120, SwingConstants.LEFT);
        addCellLabel(itemRow, productSubtotal, 620, 20, 120, SwingConstants.LEFT);
        card.add(itemRow);

        // Total row
        JLabel totalRowLabel = new JLabel("Total:");
        totalRowLabel.setFont(new Font("Arial", Font.BOLD, 13));
        totalRowLabel.setForeground(AdminDashboard.TEXT_PRIMARY);
        totalRowLabel.setBounds(620, 360, 60, 20);
        card.add(totalRowLabel);

        JLabel totalRowAmount = new JLabel(totalAmount);
        totalRowAmount.setFont(new Font("Arial", Font.BOLD, 13));
        totalRowAmount.setForeground(new Color(37, 99, 235));
        totalRowAmount.setBounds(680, 360, 160, 20);
        card.add(totalRowAmount);

        // Shipping address (moved up a bit to fit the shorter dialog)
        JLabel shippingTitle = new JLabel("Shipping Address");
        shippingTitle.setFont(new Font("Arial", Font.BOLD, 14));
        shippingTitle.setForeground(AdminDashboard.TEXT_PRIMARY);
        shippingTitle.setBounds(0, 386, 200, 20);
        card.add(shippingTitle);

        JTextArea shippingArea = new JTextArea(shippingAddress);
        shippingArea.setEditable(false);
        shippingArea.setLineWrap(true);
        shippingArea.setWrapStyleWord(true);
        shippingArea.setFont(new Font("Arial", Font.PLAIN, 13));
        shippingArea.setForeground(AdminDashboard.TEXT_PRIMARY);
        shippingArea.setBackground(new Color(248, 249, 251));

        shippingArea.setBorder(new EmptyBorder(10, 12, 10, 12));

        JScrollPane shippingScroll = new JScrollPane(shippingArea);
        shippingScroll.setBorder(BorderFactory.createLineBorder(new Color(229, 231, 235), 1));
        shippingScroll.setBounds(0, 414, 860, 80);
        card.add(shippingScroll);

        // Bottom buttons
        JButton closeBtn = new JButton("Close");

        closeBtn.setFont(new Font("Arial", Font.PLAIN, 13));
        closeBtn.setFocusPainted(false);
        closeBtn.setBackground(Color.WHITE);
        closeBtn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(209, 213, 219), 1),
                new EmptyBorder(6, 24, 6, 24)
        ));
        closeBtn.setBounds(742, 520, 90, 34);
        closeBtn.addActionListener(e -> dispose());
        card.add(closeBtn);
    }

    private void addHeaderLabel(JPanel parent, String text, int x, int y, int width) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Arial", Font.BOLD, 11));
        l.setForeground(AdminDashboard.TEXT_SECONDARY);
        l.setBounds(x, y, width, 16);
        parent.add(l);
    }

    private void addCellLabel(JPanel parent, String text, int x, int y, int width, int align) {
        JLabel l = new JLabel(text, align);
        l.setFont(new Font("Arial", Font.PLAIN, 13));
        l.setForeground(AdminDashboard.TEXT_PRIMARY);
        l.setBounds(x, y, width, 18);
        parent.add(l);
    }

    // Static helper to open dialog
    public static void showDialog(
            Component parent,
            String orderNumberLabel,
            String orderDateLabel,
            String customerLabel,
            String paymentMethod,
            String paymentStatus,
            String initialStatus,
            String totalAmount,
            String productName,
            String productBrand,
            String productPrice,
            String productQty,
            String productSubtotal,
            String shippingAddress
    ) {
        Window w = parent != null ? SwingUtilities.getWindowAncestor(parent) : null;
        OrderDetailsDialog d = new OrderDetailsDialog(
                w,
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
        d.setVisible(true);
    }
}