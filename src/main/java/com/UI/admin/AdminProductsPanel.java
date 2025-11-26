package com.UI.admin;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.UI.components.RoundedPanel;
import com.service.AdminProductService;

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
        addProductBtn.addActionListener(e -> openAddProductDialog());
        card.add(addProductBtn);

        AdminProductService productService = new AdminProductService();
        DefaultTableModel model = productService.createProductTableModel();

        JTable table = new JTable(model) {
            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                // Preserve custom backgrounds for Status (col 4) and Actions (col 5)
                if (!isRowSelected(row) && column != 4 && column != 5) {
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

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        table.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);

        DefaultTableCellRenderer statusRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                          boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setOpaque(true);
                label.setBorder(new EmptyBorder(4, 10, 4, 10));

                String statusText = value != null ? value.toString() : "";
                if ("Active".equalsIgnoreCase(statusText)) {
                    label.setForeground(Color.WHITE);
                    label.setBackground(new Color(24, 119, 242)); // blue pill
                } else {
                    label.setForeground(new Color(60, 60, 60));
                    label.setBackground(new Color(230, 232, 236)); // gray pill for inactive
                }
                return label;
            }
        };
        table.getColumnModel().getColumn(4).setCellRenderer(statusRenderer);

        // Actions column: custom renderer & editor with three buttons
        table.getColumnModel().getColumn(5).setCellRenderer(new ActionsRenderer());
        table.getColumnModel().getColumn(5).setCellEditor(new ActionsEditor(table, model));
        table.getColumnModel().getColumn(5).setPreferredWidth(120);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(AdminDashboard.BORDER_COLOR, 1));
        scrollPane.setBounds(20, 63, 936, 468);
        card.add(scrollPane);
    }

    private void openAddProductDialog() {
        Window window = SwingUtilities.getWindowAncestor(this);
        JDialog dialog = new JDialog(window, "Add New Product", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setSize(740, 540);
        dialog.setLocationRelativeTo(window);
        dialog.setLayout(null);

        JPanel content = new JPanel(null);
        content.setBackground(Color.WHITE);
        content.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 227, 232), 1),
                new EmptyBorder(24, 32, 24, 32)
        ));
        content.setBounds(8, 8, 710, 510);
        dialog.add(content);

        JLabel titleLabel = new JLabel("Add New Product");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(AdminDashboard.TEXT_PRIMARY);
        titleLabel.setBounds(10, 0, 400, 28);
        content.add(titleLabel);

        // Row 1: Product Name, Brand
        JLabel nameLabel = new JLabel("Product Name *");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        nameLabel.setBounds(10, 50, 200, 16);
        content.add(nameLabel);

        JTextField nameField = new JTextField("Enter product name");
        nameField.setFont(new Font("Arial", Font.PLAIN, 13));
        nameField.setForeground(AdminDashboard.TEXT_SECONDARY);
        nameField.setBounds(10, 72, 320, 34);
        nameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 232, 236), 1),
                new EmptyBorder(0, 10, 0, 10)
        ));
        content.add(nameField);

        JLabel brandLabel = new JLabel("Brand *");
        brandLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        brandLabel.setBounds(344, 50, 200, 16);
        content.add(brandLabel);

        JTextField brandField = new JTextField("Enter brand name");
        brandField.setFont(new Font("Arial", Font.PLAIN, 13));
        brandField.setForeground(AdminDashboard.TEXT_SECONDARY);
        brandField.setBounds(344, 72, 320, 34);
        brandField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 232, 236), 1),
                new EmptyBorder(0, 10, 0, 10)
        ));
        content.add(brandField);

        // Row 2: Category, Price, Stock
        JLabel categoryLabel = new JLabel("Category *");
        categoryLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        categoryLabel.setBounds(10, 124, 200, 16);
        content.add(categoryLabel);

        JComboBox<String> categoryBox = new JComboBox<>(new String[]{
                "Engine", "Brakes", "Electrical", "Suspension", "Wheels & Tires"
        });
        categoryBox.setFont(new Font("Arial", Font.PLAIN, 13));
        categoryBox.setBounds(10, 144, 200, 34);
        content.add(categoryBox);

        JLabel priceLabel = new JLabel("Price *");
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        priceLabel.setBounds(220, 124, 200, 16);
        content.add(priceLabel);

        JTextField priceField = new JTextField("0.00");
        priceField.setFont(new Font("Arial", Font.PLAIN, 13));
        priceField.setForeground(AdminDashboard.TEXT_SECONDARY);
        priceField.setBounds(220, 144, 150, 34);
        priceField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 232, 236), 1),
                new EmptyBorder(0, 10, 0, 10)
        ));
        content.add(priceField);

        JLabel stockLabel = new JLabel("Stock *");
        stockLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        stockLabel.setBounds(390, 124, 200, 16);
        content.add(stockLabel);

        JTextField stockField = new JTextField("0");
        stockField.setFont(new Font("Arial", Font.PLAIN, 13));
        stockField.setForeground(AdminDashboard.TEXT_SECONDARY);
        stockField.setBounds(390, 144, 150, 34);
        stockField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 232, 236), 1),
                new EmptyBorder(0, 10, 0, 10)
        ));
        content.add(stockField);

        // Description
        JLabel descLabel = new JLabel("Description");
        descLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        descLabel.setBounds(10, 196, 200, 16);
        content.add(descLabel);

        JTextArea descArea = new JTextArea("Enter product description");
        descArea.setFont(new Font("Arial", Font.PLAIN, 13));
        descArea.setForeground(AdminDashboard.TEXT_SECONDARY);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        JScrollPane descScroll = new JScrollPane(descArea);
        descScroll.setBounds(10, 216, 660, 90);
        descScroll.setBorder(BorderFactory.createLineBorder(new Color(230, 232, 236), 1));
        content.add(descScroll);

        // Image URL
        JLabel imageLabel = new JLabel("Image URL");
        imageLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        imageLabel.setBounds(10, 318, 200, 16);
        content.add(imageLabel);

        JTextField imageField = new JTextField("https://example.com/image.jpg");
        imageField.setFont(new Font("Arial", Font.PLAIN, 13));
        imageField.setForeground(AdminDashboard.TEXT_SECONDARY);
        imageField.setBounds(10, 338, 660, 34);
        imageField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 232, 236), 1),
                new EmptyBorder(0, 10, 0, 10)
        ));
        content.add(imageField);

        // Active checkbox
        JCheckBox activeCheck = new JCheckBox("Product is active", true);
        activeCheck.setFont(new Font("Arial", Font.PLAIN, 13));
        activeCheck.setBackground(Color.WHITE);
        activeCheck.setBounds(10, 386, 200, 24);
        content.add(activeCheck);

        // Buttons row
        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setFont(new Font("Arial", Font.PLAIN, 13));
        cancelBtn.setFocusPainted(false);
        cancelBtn.setBackground(Color.WHITE);
        cancelBtn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210), 1),
                new EmptyBorder(4, 18, 4, 18)
        ));
        cancelBtn.setBounds(396, 428, 110, 34);
        cancelBtn.addActionListener(e -> dialog.dispose());
        content.add(cancelBtn);

        JButton confirmBtn = new JButton("+  Add Product");
        confirmBtn.setFont(new Font("Arial", Font.BOLD, 13));
        confirmBtn.setForeground(Color.WHITE);
        confirmBtn.setBackground(Color.BLACK);
        confirmBtn.setFocusPainted(false);
        confirmBtn.setBorder(new EmptyBorder(4, 22, 4, 22));
        confirmBtn.setBounds(516, 428, 150, 34);
        // For now, simply close the dialog; hook up real save logic later
        confirmBtn.addActionListener(e -> dialog.dispose());
        content.add(confirmBtn);

        dialog.setResizable(false);
        dialog.setVisible(true);
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

    private static class ActionsEditor extends AbstractCellEditor implements javax.swing.table.TableCellEditor, ActionListener {

        private final JPanel panel;
        private final JButton viewBtn;
        private final JButton editBtn;
        private final JButton deleteBtn;
        private final JTable table;
        private final DefaultTableModel model;
        private final AdminProductService productService;
        private int row;

        public ActionsEditor(JTable table, DefaultTableModel model) {
            this.table = table;
            this.model = model;
            this.productService = new AdminProductService();

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
            String command = e.getActionCommand();

            String productName = String.valueOf(model.getValueAt(row, 0));
            String category = String.valueOf(model.getValueAt(row, 1));
            String price = String.valueOf(model.getValueAt(row, 2));
            String stock = String.valueOf(model.getValueAt(row, 3));
            String status = String.valueOf(model.getValueAt(row, 4));

            if ("view".equals(command)) {
                JOptionPane.showMessageDialog(table,
                        "Product: " + productName + "\n" +
                                "Category: " + category + "\n" +
                                "Price: " + price + "\n" +
                                "Stock: " + stock + "\n" +
                                "Status: " + status,
                        "Product Details",
                        JOptionPane.INFORMATION_MESSAGE);
            } else if ("edit".equals(command)) {
                JOptionPane.showMessageDialog(table,
                        "Edit product: " + productName + " (placeholder action)",
                        "Edit Product",
                        JOptionPane.INFORMATION_MESSAGE);
            } else if ("delete".equals(command)) {
                int confirm = JOptionPane.showConfirmDialog(table,
                        "Delete product '" + productName + "'?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);
                if (confirm == JOptionPane.YES_OPTION) {
                    productService.deleteRow(model, row);
                }
            }

            fireEditingStopped();
        }
    }
}
