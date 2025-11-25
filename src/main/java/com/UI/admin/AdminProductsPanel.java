package com.UI.admin;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import com.UI.components.RoundedPanel;

public class AdminProductsPanel extends JPanel {

    public AdminProductsPanel() {
        setLayout(null);
        setBackground(AdminDashboard.BG_COLOR);
        buildUI();
    }

    private void buildUI() {
        JLabel sectionTitle = new JLabel("Product Management");
        sectionTitle.setFont(new Font("Arial", Font.BOLD, 18));
        sectionTitle.setForeground(AdminDashboard.TEXT_PRIMARY);
        sectionTitle.setBounds(0, 0, 300, 26);
        add(sectionTitle);

        RoundedPanel card = new RoundedPanel(16, true);
        card.setLayout(null);
        card.setBackground(AdminDashboard.CARD_BG);
        card.setBorder(new EmptyBorder(16, 20, 16, 20));
        card.setBounds(0, 36, 976, 560);
        add(card);

        JTextField searchField = new JTextField("Search products...");
        searchField.setFont(new Font("Arial", Font.PLAIN, 12));
        searchField.setForeground(AdminDashboard.TEXT_SECONDARY);
        searchField.setBounds(20, 15, 260, 32);
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AdminDashboard.BORDER_COLOR, 1),
                new EmptyBorder(4, 8, 4, 8)
        ));
        card.add(searchField);

        JComboBox<String> categoryFilter = new JComboBox<>(new String[]{
                "All categories", "Engine", "Brakes", "Electrical", "Suspension", "Wheels & Tires"
        });
        categoryFilter.setFont(new Font("Arial", Font.PLAIN, 12));
        categoryFilter.setBounds(300, 15, 180, 32);
        card.add(categoryFilter);

        JButton addProductBtn = new JButton("Add New Product");
        addProductBtn.setFont(new Font("Arial", Font.BOLD, 12));
        addProductBtn.setBackground(Color.BLACK);
        addProductBtn.setForeground(Color.WHITE);
        addProductBtn.setFocusPainted(false);
        addProductBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addProductBtn.setBorder(new EmptyBorder(4, 18, 4, 18));
        addProductBtn.setBounds(776, 15, 180, 32);
        card.add(addProductBtn);

        String[] columns = {"Product", "Category", "Price", "Stock", "Status", "Actions"};
        Object[][] data = {
                {"Air Filter", "Engine", "$18.99", 200, "Active", "..."},
                {"Premium Oil Filter", "Engine", "$24.99", 150, "Active", "..."},
                {"LED Headlight Bulbs", "Electrical", "$79.99", 90, "Active", "..."},
                {"All-Season Tire 225/60R16", "Wheels & Tires", "$129.99", 80, "Active", "..."},
                {"Spark Plug Set (4-Pack)", "Engine", "$45.99", 75, "Active", "..."},
                {"Brake Pad Set - Front", "Brakes", "$89.99", 60, "Active", "..."},
                {"Brake Rotor - Front", "Brakes", "$125.99", 45, "Active", "..."},
                {"Shock Absorber - Front", "Suspension", "$75.99", 40, "Active", "..."},
                {"Car Battery 12V", "Electrical", "$149.99", 30, "Active", "..."},
                {"Alloy Wheel 16\"", "Wheels & Tires", "$189.99", 25, "Active", "..."}
        };

        JTable table = new JTable(data, columns) {
            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(248, 249, 251));
                }
                return c;
            }
        };
        table.setFillsViewportHeight(true);
        table.setRowHeight(32);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 1));
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(247, 248, 250));
        table.getTableHeader().setOpaque(true);

        javax.swing.table.DefaultTableCellRenderer rightRenderer = new javax.swing.table.DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        table.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);

        javax.swing.table.DefaultTableCellRenderer statusRenderer = new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                          boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setForeground(Color.WHITE);
                label.setOpaque(true);
                label.setBorder(new EmptyBorder(4, 10, 4, 10));
                label.setBackground(new Color(24, 119, 242));
                return label;
            }
        };
        table.getColumnModel().getColumn(4).setCellRenderer(statusRenderer);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(AdminDashboard.BORDER_COLOR, 1));
        scrollPane.setBounds(20, 63, 936, 468);
        card.add(scrollPane);
    }
}
