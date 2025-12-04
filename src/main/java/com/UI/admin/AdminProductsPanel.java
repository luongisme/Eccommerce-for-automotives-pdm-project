package com.UI.admin;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.NumberFormat;
import java.util.List;
import java.util.Random;

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
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                new EmptyBorder(6, 10, 6, 10)
        ));
        card.add(searchField);

        // Clear placeholder on focus
        searchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if ("Search products...".equals(searchField.getText())) {
                    searchField.setText("");
                    searchField.setForeground(AdminDashboard.TEXT_PRIMARY);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().trim().isEmpty()) {
                    searchField.setText("Search products...");
                    searchField.setForeground(AdminDashboard.TEXT_SECONDARY);
                }
            }
        });

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
                // Chỉ cho phép cột Actions (index 5) có editor để dùng các nút View/Edit/Delete
                return column == 5;
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
        table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
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
                label.setOpaque(true);
                label.setBorder(new EmptyBorder(4, 10, 4, 10));

                String status = (value != null) ? value.toString() : "";
                if ("Inactive".equalsIgnoreCase(status)) {
                    label.setBackground(new Color(220, 53, 69));   // đỏ
                    label.setForeground(Color.WHITE);
                } else {
                    label.setBackground(new Color(24, 119, 242));  // xanh
                    label.setForeground(Color.WHITE);
                }
                label.setText(status);
                return label;
            }
        };
        table.getColumnModel().getColumn(4).setCellRenderer(statusRenderer);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setMinWidth(80);
        table.getColumnModel().getColumn(4).setMaxWidth(120);

        // Actions column: custom renderer & editor with three buttons
        table.getColumnModel().getColumn(5).setCellRenderer(new ActionsRenderer());
        table.getColumnModel().getColumn(5).setCellEditor(new ActionsEditor(table, tableModel));
        table.getColumnModel().getColumn(5).setPreferredWidth(120);

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
        JTextField brandField  = new JTextField();
        JComboBox<String> categoryCombo = new JComboBox<>(new String[]{
                "Engine", "Brakes", "Electrical", "Suspension", "Wheels & Tires"
        });
        JTextField priceField  = new JTextField();
        JTextField stockField  = new JTextField("0");
        JTextArea descriptionArea = new JTextArea(4, 20);
        JTextField imageUrlField = new JTextField("https://example.com/image.jpg");
        JTextField skuField    = new JTextField();
        JCheckBox activeCheck  = new JCheckBox("Product is active", true);

        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        JPanel form = new JPanel(new BorderLayout(10, 10));
        form.setBorder(new EmptyBorder(12, 12, 12, 12));

        JPanel grid = new JPanel(new GridLayout(0, 4, 10, 10));

        grid.add(new JLabel("Product Name *"));
        grid.add(new JLabel("Brand *"));
        grid.add(new JLabel("Category *"));
        grid.add(new JLabel("Price *"));

        grid.add(nameField);
        grid.add(brandField);
        grid.add(categoryCombo);
        grid.add(priceField);

        grid.add(new JLabel("Stock *"));
        grid.add(new JLabel());
        grid.add(new JLabel());
        grid.add(new JLabel());

        grid.add(stockField);
        grid.add(new JLabel());
        grid.add(new JLabel());
        grid.add(new JLabel());

        form.add(grid, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout(8, 8));
        JPanel descLabelPanel = new JPanel(new BorderLayout());
        descLabelPanel.add(new JLabel("Description"), BorderLayout.NORTH);
        center.add(descLabelPanel, BorderLayout.NORTH);

        JScrollPane descScroll = new JScrollPane(descriptionArea);
        center.add(descScroll, BorderLayout.CENTER);

        JPanel imagePanel = new JPanel(new GridLayout(2, 1, 4, 4));
        imagePanel.add(new JLabel("Image URL"));
        imagePanel.add(imageUrlField);
        center.add(imagePanel, BorderLayout.SOUTH);

        form.add(center, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.add(activeCheck, BorderLayout.WEST);
        // Buttons (OK/Cancel) are handled by JOptionPane
        form.add(bottom, BorderLayout.SOUTH);

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
        p.setSku("SKU-" + System.currentTimeMillis());
        p.setPrice(price);
        p.setStockQuantity(stock);
        p.setAvailable(active);

        // các field khác tạm để trống / default
        p.setDescription(descriptionArea.getText().trim());
        p.setPartNumber("PN-" + System.currentTimeMillis());
        p.setImageUrl(imageUrlField.getText().trim().isEmpty() ? null : imageUrlField.getText().trim());
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
        Random rand= new Random();
        // đơn giản: P + timestamp, đủ dùng cho đồ án
        return "P" + String.valueOf(rand.nextInt(10000000));
    }

    private Product findProductByName(String name) {
        List<Product> products = controller.searchProducts("", "All categories");
        for (Product p : products) {
            if (name.equals(p.getName())) {
                return p;
            }
        }
        return null;
    }

    // ===== EDIT PRODUCT DIALOG =====
    private void openEditProductDialog(Product product) {
        JTextField nameField = new JTextField(product.getName());
        JTextField brandField = new JTextField(product.getBrand());
        JComboBox<String> categoryCombo = new JComboBox<>(new String[]{
                "Engine", "Brakes", "Electrical", "Suspension", "Wheels & Tires"
        });
        categoryCombo.setSelectedItem(product.getCategory());
        JTextField priceField = new JTextField(String.valueOf(product.getPrice()));
        JTextField stockField = new JTextField(String.valueOf(product.getStockQuantity()));
        JTextArea descriptionArea = new JTextArea(4, 20);
        descriptionArea.setText(product.getDescription() != null ? product.getDescription() : "");
        JTextField imageUrlField = new JTextField(product.getImageUrl() != null ? product.getImageUrl() : "https://example.com/image.jpg");
        JTextField skuField = new JTextField(product.getSku());
        JCheckBox activeCheck = new JCheckBox("Product is active", product.isAvailable());

        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        JPanel form = new JPanel(new BorderLayout(10, 10));
        form.setBorder(new EmptyBorder(12, 12, 12, 12));

        JPanel grid = new JPanel(new GridLayout(0, 4, 10, 10));

        grid.add(new JLabel("Product Name *"));
        grid.add(new JLabel("Brand *"));
        grid.add(new JLabel("Category *"));
        grid.add(new JLabel("Price *"));

        grid.add(nameField);
        grid.add(brandField);
        grid.add(categoryCombo);
        grid.add(priceField);

        grid.add(new JLabel("Stock *"));
        grid.add(new JLabel());
        grid.add(new JLabel());
        grid.add(new JLabel());

        grid.add(stockField);
        grid.add(new JLabel());
        grid.add(new JLabel());
        grid.add(new JLabel());

        form.add(grid, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout(8, 8));
        JPanel descLabelPanel = new JPanel(new BorderLayout());
        descLabelPanel.add(new JLabel("Description"), BorderLayout.NORTH);
        center.add(descLabelPanel, BorderLayout.NORTH);

        JScrollPane descScroll = new JScrollPane(descriptionArea);
        center.add(descScroll, BorderLayout.CENTER);

        JPanel imagePanel = new JPanel(new GridLayout(2, 1, 4, 4));
        imagePanel.add(new JLabel("Image URL"));
        imagePanel.add(imageUrlField);
        center.add(imagePanel, BorderLayout.SOUTH);

        form.add(center, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.add(activeCheck, BorderLayout.WEST);
        form.add(bottom, BorderLayout.SOUTH);

        int result = JOptionPane.showConfirmDialog(
                this,
                form,
                "Edit Product",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        String name = nameField.getText().trim();
        String cat = (String) categoryCombo.getSelectedItem();
        String brand = brandField.getText().trim();
        String sku = skuField.getText().trim();
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

        product.setName(name);
        product.setCategory(cat);
        product.setBrand(brand);
        product.setSku(sku);
        product.setPrice(price);
        product.setStockQuantity(stock);
        product.setAvailable(active);
        product.setDescription(descriptionArea.getText().trim());
        product.setImageUrl(imageUrlField.getText().trim().isEmpty() ? null : imageUrlField.getText().trim());

        boolean ok = controller.updateProduct(product);
        if (ok) {
            loadProductsToTable();
            JOptionPane.showMessageDialog(this, "Product updated.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update product.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ===== TABLE ACTIONS (VIEW / EDIT / DELETE) =====

    private static class ActionsRenderer extends JPanel implements TableCellRenderer {

        private final JButton viewBtn;
        private final JButton editBtn;
        private final JButton deleteBtn;

        public ActionsRenderer() {
            setLayout(new FlowLayout(FlowLayout.RIGHT, 6, 4));
            setOpaque(true);

            viewBtn = createIconButton("/images/eye.jpg");
            editBtn = createIconButton("/images/write.png");
            deleteBtn = createIconButton("/images/bin.png");

            add(viewBtn);
            add(editBtn);
            add(deleteBtn);
        }

        private JButton createIconButton(String resourcePath) {
            JButton btn = new JButton();
            btn.setMargin(new Insets(0, 0, 0, 0));
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setContentAreaFilled(false);

            java.net.URL url = getClass().getResource(resourcePath);
            if (url != null) {
                ImageIcon icon = new ImageIcon(url);
                Image scaled = icon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
                btn.setIcon(new ImageIcon(scaled));
            }
            return btn;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                setBackground(table.getSelectionBackground());
            } else {
                setBackground(row % 2 == 0 ? Color.WHITE : new Color(248, 249, 251));
            }
            return this;
        }
    }

    private class ActionsEditor extends AbstractCellEditor implements javax.swing.table.TableCellEditor, ActionListener {

        private final JPanel panel;
        private final JButton viewBtn;
        private final JButton editBtn;
        private final JButton deleteBtn;
        private final JTable table;
        private final javax.swing.table.DefaultTableModel model;
        private int row;

        public ActionsEditor(JTable table, javax.swing.table.DefaultTableModel model) {
            this.table = table;
            this.model = model;

            panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 4));

            viewBtn = createIconButton("/images/eye.jpg", "view");
            editBtn = createIconButton("/images/write.png", "edit");
            deleteBtn = createIconButton("/images/bin.png", "delete");

            panel.add(viewBtn);
            panel.add(editBtn);
            panel.add(deleteBtn);
        }

        private JButton createIconButton(String resourcePath, String command) {
            JButton btn = new JButton();
            btn.setMargin(new Insets(0, 0, 0, 0));
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setContentAreaFilled(false);
            btn.setActionCommand(command);
            btn.addActionListener(this);

            java.net.URL url = getClass().getResource(resourcePath);
            if (url != null) {
                ImageIcon icon = new ImageIcon(url);
                Image scaled = icon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
                btn.setIcon(new ImageIcon(scaled));
            }
            return btn;
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.row = row;
            panel.setBackground(table.getSelectionBackground());
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return null;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Hủy việc chỉnh sửa ngay để tránh lỗi khi bảng thay đổi
            fireEditingCanceled();

            String command = e.getActionCommand();

            String productName = String.valueOf(model.getValueAt(row, 0));

            if ("view".equals(command)) {
                String category = String.valueOf(model.getValueAt(row, 1));
                String price = String.valueOf(model.getValueAt(row, 2));
                String stock = String.valueOf(model.getValueAt(row, 3));
                String status = String.valueOf(model.getValueAt(row, 4));

                JOptionPane.showMessageDialog(table,
                        "Product: " + productName + "\n" +
                                "Category: " + category + "\n" +
                                "Price: " + price + "\n" +
                                "Stock: " + stock + "\n" +
                                "Status: " + status,
                        "Product Details",
                        JOptionPane.INFORMATION_MESSAGE);
            } else if ("edit".equals(command)) {
                Product p = findProductByName(productName);
                if (p != null) {
                    AdminProductsPanel.this.openEditProductDialog(p);
                }
            } else if ("delete".equals(command)) {
                int confirm = JOptionPane.showConfirmDialog(table,
                        "Delete product '" + productName + "'?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);
                if (confirm == JOptionPane.YES_OPTION) {
                    Product p = findProductByName(productName);
                    if (p != null) {
                        boolean ok = controller.deleteProduct(p.getPid());
                        if (ok) {
                            loadProductsToTable();
                            JOptionPane.showMessageDialog(table, "Product deleted.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(table, "Failed to delete product.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        }
    }
}
