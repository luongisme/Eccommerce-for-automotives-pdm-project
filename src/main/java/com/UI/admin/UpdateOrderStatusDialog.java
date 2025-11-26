package com.UI.admin;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controls the logic + UI for the standalone Update Order Status dialog
 * (second screenshot).
 */
public class UpdateOrderStatusDialog extends JDialog implements ActionListener {

    private final JComboBox<String> statusCombo;
    private final String orderId;
    private final String currentStatus;
    private final StatusUpdateListener listener;

    public interface StatusUpdateListener {
        void onStatusUpdated(String newStatus);
    }

    public UpdateOrderStatusDialog(
            Window owner,
            String orderId,
            String currentStatus,
            StatusUpdateListener listener
    ) {
        super(owner, "Update Order Status", ModalityType.APPLICATION_MODAL);
        this.orderId = orderId;
        this.currentStatus = currentStatus;
        this.listener = listener;

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(540, 260);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        JPanel root = new JPanel(null);
        root.setBackground(AdminDashboard.BG_COLOR);
        root.setBorder(new EmptyBorder(20, 24, 24, 24));
        add(root, BorderLayout.CENTER);

        JPanel card = new JPanel(null);
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AdminDashboard.BORDER_COLOR, 1),
                new EmptyBorder(16, 20, 20, 20)
        ));
        card.setBounds(18, 0, 492, 214);
        root.add(card);

        JLabel title = new JLabel("Update Order Status");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(AdminDashboard.TEXT_PRIMARY);
        title.setBounds(0, 0, 300, 26);
        card.add(title);

        JLabel orderLabel = new JLabel(orderId);
        orderLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        orderLabel.setForeground(AdminDashboard.TEXT_SECONDARY);
        orderLabel.setBounds(0, 30, 300, 18);
        card.add(orderLabel);

        JButton closeIcon = new JButton("✖");
        closeIcon.setBorderPainted(false);
        closeIcon.setContentAreaFilled(false);
        closeIcon.setFocusPainted(false);
        closeIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeIcon.setBounds(452, 0, 40, 28);
        closeIcon.addActionListener(e -> dispose());
        card.add(closeIcon);

        JLabel currentTitle = new JLabel("Current Status");
        currentTitle.setFont(new Font("Arial", Font.PLAIN, 12));
        currentTitle.setForeground(AdminDashboard.TEXT_SECONDARY);
        currentTitle.setBounds(0, 64, 200, 16);
        card.add(currentTitle);

        JLabel currentBadge = new JLabel(currentStatus.toLowerCase(), SwingConstants.CENTER);
        currentBadge.setFont(new Font("Arial", Font.BOLD, 11));
        currentBadge.setOpaque(true);
        currentBadge.setForeground(new Color(37, 99, 235));
        currentBadge.setBackground(new Color(219, 234, 254));
        currentBadge.setBounds(0, 80, 90, 22);
        card.add(currentBadge);

        JLabel newStatusTitle = new JLabel("New Status *");
        newStatusTitle.setFont(new Font("Arial", Font.PLAIN, 12));
        newStatusTitle.setForeground(AdminDashboard.TEXT_SECONDARY);
        newStatusTitle.setBounds(0, 115, 200, 16);
        card.add(newStatusTitle);

        statusCombo = new JComboBox<>(new String[]{
                "Pending", "Processing", "Shipped", "Delivered", "Cancelled"
        });
        statusCombo.setFont(new Font("Arial", Font.PLAIN, 13));
        statusCombo.setBounds(0, 135, 360, 32);
        statusCombo.setSelectedItem(capitalize(currentStatus));
        card.add(statusCombo);

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setFont(new Font("Arial", Font.PLAIN, 13));
        cancelBtn.setFocusPainted(false);
        cancelBtn.setBackground(Color.WHITE);
        cancelBtn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(209, 213, 219), 1),
                new EmptyBorder(6, 24, 6, 24)
        ));
        cancelBtn.setBounds(244, 180, 100, 34);
        cancelBtn.addActionListener(e -> dispose());
        card.add(cancelBtn);

        JButton updateBtn = new JButton("Update Status");
        updateBtn.setFont(new Font("Arial", Font.BOLD, 13));
        updateBtn.setFocusPainted(false);
        updateBtn.setForeground(Color.WHITE);
        updateBtn.setBackground(Color.BLACK);
        updateBtn.setBorder(new EmptyBorder(6, 24, 6, 24));
        updateBtn.setBounds(352, 180, 140, 34);
        updateBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        updateBtn.addActionListener(this);
        card.add(updateBtn);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String selected = (String) statusCombo.getSelectedItem();
        if (selected != null && listener != null) {
            listener.onStatusUpdated(selected);
        }
        dispose();
    }

    private String capitalize(String status) {
        if (status == null || status.isEmpty()) return "";
        String lower = status.toLowerCase();
        return Character.toUpperCase(lower.charAt(0)) + lower.substring(1);
    }
}
