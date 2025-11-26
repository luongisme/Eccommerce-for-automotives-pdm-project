package com.UI.admin;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.NumberFormat;
import java.util.List;

import com.UI.components.RoundedPanel;
import com.model.Product;

public class AdminProductsPanel extends JPanel {

    private final AdminDashboardController controller;
    private JTable table;
    private javax.swing.table.DefaultTableModel tableModel;
    private JComboBox<String> categoryFilter;
    private JTextField searchField;

    public AdminProductsPanel(AdminDashboardController controller) {
        this.controller = controller;
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

        // ===== Search + Filter =====
        searchField = new JTextField("Search products...");
        searchField.setFont(new Font("Arial", Font.PLAIN, 12));
        searchField.setForeground(AdminDashboard.TEXT_SECONDARY);
        searchField.setBounds(20, 15, 260, 32);
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AdminDashboard.BORDER_COLOR, 1),
                new EmptyBorder(4, 8, 4, 8)
        ));
        card.add(searchField);

        categoryFilter = new JComboBox<>(new String[]{
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

        // ===== BẢNG SẢN PHẨM =====
        String[] columns = {"Product", "Category", "Price", "Stock", "Status", "Actions"};

        tableModel = new javax.swing.table.DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // sau này có thể cho edit Actions, tạm thời khoá hết
                return false;
            }
        };

        table = new JTable(tableModel) {
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

        // Align phải cho Price + Stock
        javax.swing.table.DefaultTableCellRenderer rightRenderer = new javax.swing.table.DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        table.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);

        // Status badge
        javax.swing.table.DefaultTableCellRenderer statusRenderer = new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setForeground(Color.WHITE);
                label.setOpaque(true);
                label.setBorder(new EmptyBorder(4, 10, 4, 10));

                String status = (value != null) ? value.toString() : "";
                if ("Inactive".equalsIgnoreCase(status)) {
                    label.setBackground(new Color(220, 53, 69));   // đỏ
                } else {
                    label.setBackground(new Color(24, 119, 242));  // xanh
                }
                return label;
            }
        };
        table.getColumnModel().getColumn(4).setCellRenderer(statusRenderer);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(AdminDashboard.BORDER_COLOR, 1));
        scrollPane.setBounds(20, 63, 936, 468);
        card.add(scrollPane);

        // load dữ liệu ban đầu
        loadProductsToTable();

        // Event search + filter
        categoryFilter.addActionListener(e -> loadProductsToTable());
        searchField.addActionListener(e -> loadProductsToTable()); // nhấn Enter để search

        // 🔹 Nút Add New Product → mở form thêm sản phẩm
        addProductBtn.addActionListener(e -> openAddProductDialog());
    }

    // ====== LOAD DATA VÀO BẢNG ======
    private void loadProductsToTable() {
        String keyword = searchField.getText();
        if ("Search products...".equals(keyword)) {
            keyword = "";
        }
        String category = (String) categoryFilter.getSelectedItem();

        List<Product> products = controller.searchProducts(keyword, category);

        tableModel.setRowCount(0);

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(); // hoặc Locale.US

        for (Product p : products) {
            String name     = p.getName();
            String cat      = p.getCategory();
            String priceStr = currencyFormat.format(p.getPrice());
            int stock       = p.getStockQuantity();
            String status   = p.isAvailable() ? "Active" : "Inactive";

            tableModel.addRow(new Object[]{
                    name,
                    cat,
                    priceStr,
                    stock,
                    status,
                    "..."          // Actions placeholder
            });
        }
    }

    // ====== FORM THÊM PRODUCT ======
    private void openAddProductDialog() {
        JTextField nameField   = new JTextField();
        JComboBox<String> categoryCombo = new JComboBox<>(new String[]{
                "Engine", "Brakes", "Electrical", "Suspension", "Wheels & Tires"
        });
        JTextField priceField  = new JTextField();
        JTextField stockField  = new JTextField();
        JTextField brandField  = new JTextField();
        JTextField skuField    = new JTextField();
        JCheckBox activeCheck  = new JCheckBox("Active", true);

        JPanel form = new JPanel(new GridLayout(0, 2, 8, 8));
        form.setBorder(new EmptyBorder(10, 10, 10, 10));

        form.add(new JLabel("Name:"));
        form.add(nameField);

        form.add(new JLabel("Category:"));
        form.add(categoryCombo);

        form.add(new JLabel("Price:"));
        form.add(priceField);

        form.add(new JLabel("Stock:"));
        form.add(stockField);

        form.add(new JLabel("Brand:"));
        form.add(brandField);

        form.add(new JLabel("SKU:"));
        form.add(skuField);

        form.add(new JLabel("Status:"));
        form.add(activeCheck);

        int result = JOptionPane.showConfirmDialog(
                this,
                form,
                "Add New Product",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        // Validate đơn giản
        String name = nameField.getText().trim();
        String cat  = (String) categoryCombo.getSelectedItem();
        String brand = brandField.getText().trim();
        String sku   = skuField.getText().trim();
        boolean active = activeCheck.isSelected();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Product name is required.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double price;
        int stock;
        try {
            price = Double.parseDouble(priceField.getText().trim());
            stock = Integer.parseInt(stockField.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Price and Stock must be numeric.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Tạo Product mới
        Product p = new Product();
        p.setId(generateProductId());   // PID mới
        p.setName(name);
        p.setCategory(cat);
        p.setBrand(brand);
        p.setSku(sku);
        p.setPrice(price);
        p.setStockQuantity(stock);
        p.setAvailable(active);

        // các field khác tạm để trống / default
        p.setDescription("");
        p.setPartNumber("");
        p.setImageUrl(null);
        // p.setSpecifications(...) nếu bạn muốn

        boolean ok = controller.insertProduct(p);
        if (ok) {
            JOptionPane.showMessageDialog(this, "Product added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadProductsToTable();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add product.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String generateProductId() {
        // đơn giản: P + timestamp, đủ dùng cho đồ án
        return "P" + System.currentTimeMillis();
    }
}
