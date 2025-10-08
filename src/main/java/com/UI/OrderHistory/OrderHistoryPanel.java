package com.UI.OrderHistory;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class OrderHistoryPanel extends JPanel {
    // Header
    private final JLabel title = new JLabel("My Account");

    // Tabs (fake tabs for look & feel)
    private final JButton tabProfile = new JButton("Profile Information");
    private final JButton tabOrder = new JButton("Order History");

    // Content cards: LIST vs EMPTY
    private final CardLayout cards = new CardLayout();
    private final JPanel cardHost = new JPanel(cards);

    // LIST
    private final JTable table = new JTable();
    private final OrderTableModel tableModel = new OrderTableModel();

    // EMPTY
    private final JButton returnBtn = new JButton("Return to shop");

    public OrderHistoryPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(16, 24, 24, 24));

        // ===== Title =====
        title.setFont(title.getFont().deriveFont(Font.BOLD, 22f));
        title.setForeground(new Color(33, 37, 41));
        add(title, BorderLayout.NORTH);

        // ===== Tabs =====
        JPanel tabsWrap = new JPanel(new BorderLayout());
        tabsWrap.setOpaque(false);
        tabsWrap.setBorder(BorderFactory.createEmptyBorder(16, 0, 12, 0));

        JPanel tabsBar = new JPanel(new GridLayout(1, 2, 8, 0));
        tabsBar.setBackground(new Color(245, 247, 250));
        tabsBar.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));

        styleTabInactive(tabProfile);
        styleTabActive(tabOrder); // Order History đang active

        tabsBar.add(tabProfile);
        tabsBar.add(tabOrder);
        tabsWrap.add(tabsBar, BorderLayout.CENTER);
        add(tabsWrap, BorderLayout.CENTER); // tạm thời add, sau đó replace bằng container

        // ===== Content host (cards) =====
        JPanel content = new JPanel(new BorderLayout());
        content.setOpaque(false);

        // -- LIST state
        JPanel listPanel = new JPanel(new BorderLayout());
        table.setModel(tableModel);
        table.setRowHeight(28);
        table.setFillsViewportHeight(true);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setFont(table.getTableHeader().getFont().deriveFont(Font.BOLD));
        listPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        // -- EMPTY state
        JPanel emptyPanel = new JPanel(new GridBagLayout());
        emptyPanel.setOpaque(false);
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0; gc.gridy = 0; gc.insets = new Insets(24,0,12,0);

        JLabel heart = new JLabel(heartPng(96));
        emptyPanel.add(heart, gc);

        gc.gridy++;
        JLabel emptyMsg = new JLabel("The wishlist table is empty.");
        emptyMsg.setFont(emptyMsg.getFont().deriveFont(Font.PLAIN, 16f));
        emptyMsg.setForeground(new Color(96, 102, 112));
        emptyPanel.add(emptyMsg, gc);

        gc.gridy++; gc.insets = new Insets(20,0,0,0);
        stylePrimary(returnBtn);
        emptyPanel.add(returnBtn, gc);

        // cards
        cardHost.add(listPanel, "LIST");
        cardHost.add(emptyPanel, "EMPTY");

        // Replace center: tabs on top, cards below
        JPanel center = new JPanel(new BorderLayout());
        center.setOpaque(false);
        center.add(tabsWrap, BorderLayout.NORTH);
        center.add(cardHost, BorderLayout.CENTER);
        add(center, BorderLayout.CENTER);

        showEmpty();
    }

    // ================== Public API ==================
    public void showOrders(List<OrderVM> orders) {
        if (orders == null || orders.isEmpty()) {
            showEmpty();
        } else {
            tableModel.setData(orders);
            cards.show(cardHost, "LIST");
        }
    }

    public void onReturnToShop(Runnable action) {
        returnBtn.addActionListener(e -> { if (action != null) action.run(); });
    }

    public void onTabProfile(Runnable action) {
        tabProfile.addActionListener(e -> { if (action != null) action.run(); });
    }

    public void setOrderTabActive() {
        styleTabInactive(tabProfile);
        styleTabActive(tabOrder);
    }

    public void setProfileTabActive() {
        styleTabActive(tabProfile);
        styleTabInactive(tabOrder);
    }

    // ================== Helpers ==================
    private void showEmpty() {
        cards.show(cardHost, "EMPTY");
        setOrderTabActive();
    }

    private void styleTabActive(JButton b) {
        b.setFocusPainted(false);
        b.setContentAreaFilled(true);
        b.setOpaque(true);
        b.setBackground(new Color(212, 226, 243));
        b.setForeground(new Color(33, 37, 41));
        b.setFont(b.getFont().deriveFont(Font.BOLD, 13f));
        b.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void styleTabInactive(JButton b) {
        b.setFocusPainted(false);
        b.setContentAreaFilled(true);
        b.setOpaque(true);
        b.setBackground(new Color(245, 247, 250));
        b.setForeground(new Color(33, 37, 41));
        b.setFont(b.getFont().deriveFont(Font.PLAIN, 13f));
        b.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void stylePrimary(JButton b) {
        b.setFocusPainted(false);
        b.setBackground(new Color(0x0B5ED7)); // xanh dương đậm
        b.setForeground(Color.WHITE);
        b.setFont(b.getFont().deriveFont(Font.BOLD, 14f));
        b.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private static Icon heartPng(int size) {
    // Make sure the file is at: src/main/resources/images/heart.png
    java.net.URL url = OrderHistoryPanel.class.getResource("/images/heart.png");
    ImageIcon base = new ImageIcon(url);
    Image scaled = base.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
    return new ImageIcon(scaled);
    
    }

    // ================== ViewModel & TableModel ==================
    public static class OrderVM {
        public final String orderId;
        public final LocalDateTime createdAt;
        public final int items;
        public final double total;
        public final String status;

        public OrderVM(String orderId, LocalDateTime createdAt, int items, double total, String status) {
            this.orderId = orderId;
            this.createdAt = createdAt;
            this.items = items;
            this.total = total;
            this.status = status;
        }
    }

    private static class OrderTableModel extends AbstractTableModel {
        private final String[] cols = {"Order ID", "Date", "Items", "Total", "Status"};
        private List<OrderVM> data = java.util.Collections.emptyList();
        private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        public void setData(List<OrderVM> data) {
            this.data = (data == null) ? java.util.Collections.emptyList() : data;
            fireTableDataChanged();
        }

        @Override public int getRowCount() { return data.size(); }
        @Override public int getColumnCount() { return cols.length; }
        @Override public String getColumnName(int c) { return cols[c]; }

        @Override
        public Object getValueAt(int r, int c) {
            OrderVM v = data.get(r);
            return switch (c) {
                case 0 -> v.orderId;
                case 1 -> v.createdAt.format(fmt);
                case 2 -> v.items;
                case 3 -> String.format("$%.2f", v.total);
                case 4 -> v.status;
                default -> "";
            };
        }

        @Override public boolean isCellEditable(int r, int c) { return false; }
    }

    
    
}
