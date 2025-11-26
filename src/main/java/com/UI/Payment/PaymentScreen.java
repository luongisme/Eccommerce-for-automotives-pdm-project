package com.UI.Payment;

import com.Main.AppFrame;
import com.Main.Screen;
import com.UI.components.RoundedButton;
import com.UI.components.RoundedPanel;
import com.UI.store.StoreHeader;
import com.DAO.paymentDAOimpl;
import com.DAO.productDAOimpl;
import com.DAO.orderDAOimpl;
import com.model.Payment;
import com.model.Product;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class PaymentScreen extends Screen {

    // Controller: chứa toàn bộ business logic
    private final PaymentController controller =
            new PaymentController(
                    0.08,                // taxRate
                    new paymentDAOimpl(),
                    new productDAOimpl(),
                    new orderDAOimpl()
            );

    private final String orderID;
    private final String productID;

    // Payment method selection
    private JComboBox<String> paymentMethodCombo;

    // Input fields for payment details (card)
    private JTextField cardholderField;
    private JTextField cardNumberField;
    private JTextField expiryField;
    private JTextField cvvField;

    // Table manage payment accounts
    private JTable paymentTable;
    private DefaultTableModel paymentTableModel;
    private List<Payment> paymentList;

    // Computed UI labels
    private JLabel subtotalValueLbl;
    private JLabel taxValueLbl;
    private JLabel totalValueLbl;
    private RoundedButton placeOrderBtn;

    // Flag to indicate if using cart mode (multiple products) or single product mode
    private boolean isCartMode;
    private String userID; // For cart mode

    // NEW Constructor: Load all products from user's cart
    public PaymentScreen(AppFrame appFrame, String userID, String orderID, boolean isCartMode) {
        super(appFrame);
        this.userID = userID;
        this.orderID = orderID;
        this.productID = null;
        this.isCartMode = isCartMode;

        // init logic data from cart
        controller.initDataFromCart(userID, orderID);

        panel = new JPanel(null);
        panel.setBackground(Color.WHITE);
        initUI();
    }

    // LEGACY Constructor: Single product mode
    public PaymentScreen(AppFrame appFrame, String orderID, String productID) {
        super(appFrame);
        this.orderID = orderID;
        this.productID = productID;
        this.userID = null;
        this.isCartMode = false;

        // init logic data (Order + Product + quantity + subtotal)
        controller.initData(orderID, productID);

        panel = new JPanel(null);
        panel.setBackground(Color.WHITE);
        initUI();
    }


    @Override
    protected void initUI() {
        // Header (reused from Store)
        StoreHeader header = new StoreHeader(appFrame);
        header.setBounds(0, 0, 1024, 70);
        panel.add(header);

        // Title
        JLabel title = new JLabel("Checkout");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setBounds(30, 80, 400, 32);
        panel.add(title);

        // ===== LEFT COLUMN: PAYMENT METHOD (combo, dùng account DB) =====
        RoundedPanel methodPanel = createCardPanel();
        methodPanel.setBounds(30, 120, 600, 130);
        methodPanel.setLayout(null);
        panel.add(methodPanel);

        JLabel methodIcon = new JLabel("💳");
        methodIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        methodIcon.setBounds(15, 12, 24, 24);
        methodPanel.add(methodIcon);

        JLabel methodTitle = new JLabel("Payment Account");
        methodTitle.setFont(new Font("Arial", Font.BOLD, 16));
        methodTitle.setBounds(45, 12, 300, 24);
        methodPanel.add(methodTitle);

        JLabel methodLabel = new JLabel("Select payment method:");
        methodLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        methodLabel.setBounds(30, 50, 200, 20);
        methodPanel.add(methodLabel);

        paymentMethodCombo = new JComboBox<>();
        loadPaymentMethodsIntoCombo();
        paymentMethodCombo.setBounds(30, 75, 400, 28);
        methodPanel.add(paymentMethodCombo);

        // ===== LEFT COLUMN: PAYMENT INFORMATION (CARD DETAILS) =====
        RoundedPanel paymentPanel = createCardPanel();
        paymentPanel.setBounds(30, 270, 600, 260);
        paymentPanel.setLayout(null);
        panel.add(paymentPanel);

        JLabel payIcon = new JLabel("💳");
        payIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        payIcon.setBounds(15, 12, 24, 24);
        paymentPanel.add(payIcon);

        JLabel payTitle = new JLabel("Payment Information (Credit Card)");
        payTitle.setFont(new Font("Arial", Font.BOLD, 16));
        payTitle.setBounds(45, 12, 400, 24);
        paymentPanel.add(payTitle);

        int labelX = 20, labelW = 140, fieldX = 20, fieldW = 560;
        cardholderField = addLabeledTextField(paymentPanel, "Cardholder Name",
                labelX, 52, labelW, fieldX, 78, fieldW);
        cardNumberField = addLabeledTextField(paymentPanel, "Card Number",
                labelX, 120, labelW, fieldX, 146, fieldW);

        JLabel expLbl = new JLabel("Expiry Date");
        expLbl.setFont(new Font("Arial", Font.PLAIN, 12));
        expLbl.setBounds(20, 188, 200, 16);
        paymentPanel.add(expLbl);

        expiryField = createInputField("MM/YY");
        expiryField.setBounds(20, 210, 270, 34);
        paymentPanel.add(expiryField);

        JLabel cvvLbl = new JLabel("CVV");
        cvvLbl.setFont(new Font("Arial", Font.PLAIN, 12));
        cvvLbl.setBounds(310, 188, 200, 16);
        paymentPanel.add(cvvLbl);

        cvvField = createInputField("123");
        cvvField.setBounds(310, 210, 270, 34);
        paymentPanel.add(cvvField);

        paymentMethodCombo.addActionListener(e -> updateCardFieldsEnabled());
        updateCardFieldsEnabled();

        // ===== RIGHT COLUMN: ORDER SUMMARY =====
        RoundedPanel summaryPanel = createCardPanel();

        if (isCartMode) {
            // Cart mode: Multiple products, taller panel
            // Calculate height based on number of products: 48 (header) + products * 45 + padding + summary (100)
            int numProducts = controller.getCartProducts().size();
            int panelHeight = Math.min(600, 48 + (numProducts * 45) + 20 + 120);
            summaryPanel.setBounds(660, 120, 320, panelHeight);
        } else {
            // Single product mode: Original size
            summaryPanel.setBounds(660, 120, 320, 260);
        }

        summaryPanel.setLayout(null);
        panel.add(summaryPanel);

        JLabel summaryTitle = new JLabel(isCartMode ? "Order Summary (" + controller.getTotalItems() + " items)" : "Order Summary");
        summaryTitle.setFont(new Font("Arial", Font.BOLD, 16));
        summaryTitle.setBounds(20, 12, 280, 24);
        summaryPanel.add(summaryTitle);

        int yPos = 48;

        if (isCartMode) {
            // Display all products from cart
            Map<Product, Integer> cartProducts = controller.getCartProducts();

            // Check if cart is empty
            if (cartProducts.isEmpty()) {
                // Display empty cart message
                JLabel emptyLabel = new JLabel("Your cart is empty");
                emptyLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                emptyLabel.setForeground(new Color(150, 150, 150));
                emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
                emptyLabel.setBounds(20, yPos + 30, 280, 20);
                summaryPanel.add(emptyLabel);

                JLabel emptyHint = new JLabel("Add products to continue");
                emptyHint.setFont(new Font("Arial", Font.PLAIN, 12));
                emptyHint.setForeground(new Color(180, 180, 180));
                emptyHint.setHorizontalAlignment(SwingConstants.CENTER);
                emptyHint.setBounds(20, yPos + 55, 280, 16);
                summaryPanel.add(emptyHint);

                yPos += 100;

                // Disable place order button
                placeOrderBtn = new RoundedButton("Cart is Empty", 10);
                placeOrderBtn.setForeground(Color.WHITE);
                placeOrderBtn.setBounds(20, yPos + 36, 280, 36);
                placeOrderBtn.setEnabled(false);
                summaryPanel.add(placeOrderBtn);

                return; // Skip the rest of summary rendering
            }

            for (Map.Entry<Product, Integer> entry : cartProducts.entrySet()) {
                Product p = entry.getKey();
                int qty = entry.getValue();
                int itemStartY = yPos;

                // Product name
                JLabel productName = new JLabel(p.getName());
                productName.setFont(new Font("Arial", Font.PLAIN, 11));
                productName.setBounds(20, yPos, 180, 16);
                summaryPanel.add(productName);

                // Quantity controls: - button, quantity label, + button
                JButton minusBtn = new JButton("-");
                minusBtn.setFont(new Font("Arial", Font.BOLD, 10));
                minusBtn.setMargin(new Insets(0, 4, 0, 4));
                minusBtn.setBounds(20, yPos + 18, 25, 18);
                minusBtn.addActionListener(e -> handleDecreaseQuantity(p.getPid()));
                summaryPanel.add(minusBtn);

                JLabel qtyLabel = new JLabel(String.valueOf(qty));
                qtyLabel.setFont(new Font("Arial", Font.BOLD, 11));
                qtyLabel.setHorizontalAlignment(SwingConstants.CENTER);
                qtyLabel.setBounds(47, yPos + 18, 25, 18);
                summaryPanel.add(qtyLabel);

                JButton plusBtn = new JButton("+");
                plusBtn.setFont(new Font("Arial", Font.BOLD, 10));
                plusBtn.setMargin(new Insets(0, 4, 0, 4));
                plusBtn.setBounds(74, yPos + 18, 25, 18);
                plusBtn.addActionListener(e -> handleIncreaseQuantity(p.getPid()));
                summaryPanel.add(plusBtn);

                // Price
                JLabel productPrice = new JLabel(controller.formatUSD(p.getPrice()));
                productPrice.setFont(new Font("Arial", Font.PLAIN, 10));
                productPrice.setForeground(new Color(120, 120, 120));
                productPrice.setBounds(102, yPos + 20, 60, 14);
                summaryPanel.add(productPrice);

                // Item total (price x quantity)
                JLabel itemTotal = new JLabel(controller.formatUSD(p.getPrice() * qty));
                itemTotal.setFont(new Font("Arial", Font.BOLD, 11));
                itemTotal.setHorizontalAlignment(SwingConstants.RIGHT);
                itemTotal.setBounds(165, yPos, 80, 16);
                summaryPanel.add(itemTotal);

                // Remove button (X icon)
                JButton removeBtn = new JButton("×");
                removeBtn.setFont(new Font("Arial", Font.BOLD, 16));
                removeBtn.setForeground(new Color(220, 38, 38));
                removeBtn.setMargin(new Insets(0, 0, 0, 0));
                removeBtn.setBounds(250, yPos, 30, 20);
                removeBtn.setBorderPainted(false);
                removeBtn.setContentAreaFilled(false);
                removeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                removeBtn.addActionListener(e -> handleRemoveProduct(p.getPid(), p.getName()));
                summaryPanel.add(removeBtn);

                yPos += 45;

                // Add separator if not last item
                if (yPos < 48 + (cartProducts.size() * 45)) {
                    JSeparator sep = new JSeparator();
                    sep.setBounds(20, yPos - 5, 260, 1);
                    summaryPanel.add(sep);
                }
            }

            yPos += 10;

        } else {
            // Single product mode (legacy)
            JPanel imgPlaceholder = new JPanel();
            imgPlaceholder.setBackground(new Color(240, 240, 240));
            imgPlaceholder.setBorder(new LineBorder(new Color(220, 220, 220)));
            imgPlaceholder.setBounds(20, yPos, 48, 48);
            summaryPanel.add(imgPlaceholder);

            // Tên sản phẩm từ controller
            JLabel productTitle = new JLabel(controller.getCurrentProduct().getName());
            productTitle.setFont(new Font("Arial", Font.PLAIN, 13));
            productTitle.setBounds(78, yPos, 180, 18);
            summaryPanel.add(productTitle);

            // Qty + nút +/-
            JLabel qtyLbl = new JLabel("Qty: " + controller.getQuantity());
            qtyLbl.setFont(new Font("Arial", Font.PLAIN, 12));
            qtyLbl.setForeground(new Color(120, 120, 120));
            qtyLbl.setBounds(78, yPos + 18, 100, 16);
            summaryPanel.add(qtyLbl);

            JButton minusBtn = new JButton("-");
            minusBtn.setMargin(new Insets(0, 5, 0, 5));
            minusBtn.setBounds(180, yPos + 16, 45, 20);
            minusBtn.addActionListener(e -> {
                controller.changeQuantity(-1);
                refreshSummaryLabels(qtyLbl);
            });
            summaryPanel.add(minusBtn);

            JButton plusBtn = new JButton("+");
            plusBtn.setMargin(new Insets(0, 5, 0, 5));
            plusBtn.setBounds(230, yPos + 16, 45, 20);
            plusBtn.addActionListener(e -> {
                controller.changeQuantity(1);
                refreshSummaryLabels(qtyLbl);
            });
            summaryPanel.add(plusBtn);

            // Giá 1 sản phẩm
            JLabel priceLbl = new JLabel(controller.formatUSD(controller.getUnitPrice()));
            priceLbl.setFont(new Font("Arial", Font.BOLD, 13));
            priceLbl.setHorizontalAlignment(SwingConstants.RIGHT);
            priceLbl.setBounds(220, yPos + 8, 80, 20);
            summaryPanel.add(priceLbl);

            yPos += 60;
        }

        JSeparator sep1 = new JSeparator();
        sep1.setBounds(20, yPos, 280, 1);
        summaryPanel.add(sep1);

        yPos += 10;

        subtotalValueLbl = addSummaryRow(summaryPanel, "Subtotal", yPos,
                controller.formatUSD(controller.getSubtotal()));
        yPos += 24;
        taxValueLbl = addSummaryRow(summaryPanel, "Tax", yPos,
                controller.formatUSD(controller.getTax()));
        yPos += 30;

        JLabel totalLbl = new JLabel("Total");
        totalLbl.setFont(new Font("Arial", Font.BOLD, 14));
        totalLbl.setBounds(20, yPos, 200, 20);
        summaryPanel.add(totalLbl);

        totalValueLbl = new JLabel(controller.formatUSD(controller.getTotal()));
        totalValueLbl.setFont(new Font("Arial", Font.BOLD, 16));
        totalValueLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        totalValueLbl.setBounds(180, yPos - 2, 120, 24);
        summaryPanel.add(totalValueLbl);

        placeOrderBtn = new RoundedButton("Place order - " + controller.formatUSD(controller.getTotal()), 10);
        placeOrderBtn.setForeground(Color.WHITE);
        placeOrderBtn.setBounds(20, yPos + 36, 280, 36);
        placeOrderBtn.addActionListener(e -> onPlaceOrder());
        summaryPanel.add(placeOrderBtn);


    }

    // ========== LOAD PAYMENT METHODS & TABLE ==========

    private void loadPaymentMethodsIntoCombo() {
        paymentMethodCombo.removeAllItems();
        List<String> methods = controller.getRegisteredPaymentMethods();
        if (methods.isEmpty()) {
            paymentMethodCombo.addItem("Credit Card");
            paymentMethodCombo.addItem("Bank Transfer");
            paymentMethodCombo.addItem("PayPal");
            paymentMethodCombo.addItem("Cash on Delivery (COD)");
        } else {
            for (String m : methods) {
                paymentMethodCombo.addItem(m);
            }
        }
    }

    private JScrollPane createPaymentAccountsTable() {
        String[] columns = {"PaymentID", "Method", "Status", "Amount"};
        paymentList = controller.getAllPayments();

        Object[][] data = new Object[paymentList.size()][4];
        for (int i = 0; i < paymentList.size(); i++) {
            Payment p = paymentList.get(i);
            data[i][0] = p.getPaymentID();
            data[i][1] = p.getPaymentMethod();
            data[i][2] = p.getStatus();
            data[i][3] = p.getAmount(); // BigDecimal
        }

        paymentTableModel = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return col == 1 || col == 2;
            }
        };

        paymentTable = new JTable(paymentTableModel);
        return new JScrollPane(paymentTable);
    }

    // =================== LOGIC GỌI CONTROLLER ===================

    private void onPlaceOrder() {
        String method = (String) paymentMethodCombo.getSelectedItem();

        String error = controller.validateOrder(
                method,
                cardholderField.getText(),
                cardNumberField.getText(),
                expiryField.getText(),
                cvvField.getText()
        );

        if (error != null) {
            JOptionPane.showMessageDialog(
                    panel,
                    error,
                    "Invalid Payment",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        boolean success;

        if (isCartMode) {
            // Cart mode: Save entire order with all items to database
            success = controller.saveOrderToDatabase(userID, orderID, method);
        } else {
            // Legacy single product mode
            success = controller.processPaymentAndSave(method);
            if (success) {
                controller.updateOrderInDatabase();
            }
        }

        if (success) {
            String message;
            if (isCartMode) {
                message = String.format(
                    "ORDER SUCCESSFULLY PLACED!\n\n" +
                    "Order ID: %s\n" +
                    "Total Items: %d\n" +
                    "Subtotal: %s\n" +
                    "Tax: %s\n" +
                    "Total Amount: %s\n" +
                    "Payment Method: %s\n\n" +
                    "Your order has been saved and will be processed soon.\n" +
                    "Thank you for your purchase!",
                    orderID,
                    controller.getTotalItems(),
                    controller.formatUSD(controller.getSubtotal()),
                    controller.formatUSD(controller.getTax()),
                    controller.formatUSD(controller.getTotal()),
                    method
                );
            } else {
                message = controller.buildSuccessMessage(method);
            }

            JOptionPane.showMessageDialog(
                    panel,
                    message,
                    "Purchase Successful",
                    JOptionPane.INFORMATION_MESSAGE
            );

            // Navigate back to store
            appFrame.setScreen(new com.UI.store.StoreScreen(appFrame));
        } else {
            JOptionPane.showMessageDialog(
                    panel,
                    "Payment processing failed. Please try again.",
                    "Payment Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void onSaveSelectedPayment() {
        int row = paymentTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(panel,
                    "Please select a payment row to update.",
                    "No Row Selected",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Payment original = paymentList.get(row);

        String method = (String) paymentTableModel.getValueAt(row, 1);
        String status = (String) paymentTableModel.getValueAt(row, 2);

        Payment updated = new Payment();
        updated.setPaymentID(original.getPaymentID());
        updated.setAmount(original.getAmount());
        updated.setPaymentMethod(method);
        updated.setStatus(status);

        boolean ok = controller.updatePayment(updated);
        if (ok) {
            JOptionPane.showMessageDialog(panel,
                    "Payment account updated.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            loadPaymentMethodsIntoCombo();
        } else {
            JOptionPane.showMessageDialog(panel,
                    "Failed to update payment account.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateCardFieldsEnabled() {
        String method = (String) paymentMethodCombo.getSelectedItem();
        boolean enableCardFields = "Credit Card".equals(method);
        cardholderField.setEnabled(enableCardFields);
        cardNumberField.setEnabled(enableCardFields);
        expiryField.setEnabled(enableCardFields);
        cvvField.setEnabled(enableCardFields);
    }

    /**
     * Sync lại các label Subtotal/Tax/Total + button text từ controller.
     */
    private void refreshSummaryLabels(JLabel qtyLabel) {
        qtyLabel.setText("Qty: " + controller.getQuantity());
        subtotalValueLbl.setText(controller.formatUSD(controller.getSubtotal()));
        taxValueLbl.setText(controller.formatUSD(controller.getTax()));
        totalValueLbl.setText(controller.formatUSD(controller.getTotal()));
        placeOrderBtn.setText("Place order - " + controller.formatUSD(controller.getTotal()));
    }

    /**
     * Handle increase quantity for a product in cart
     */
    private void handleIncreaseQuantity(String productID) {
        if (!isCartMode || userID == null) return;

        com.service.CartService cartService = com.service.CartService.getInstance();
        boolean success = cartService.increaseQuantity(userID, productID, 1);

        if (success) {
            // Refresh the payment screen
            refreshPaymentScreen();
        } else {
            JOptionPane.showMessageDialog(panel,
                "Failed to update quantity. Please try again.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Handle decrease quantity for a product in cart
     * Automatically removes product if quantity becomes 0
     */
    private void handleDecreaseQuantity(String productID) {
        if (!isCartMode || userID == null) return;

        com.service.CartService cartService = com.service.CartService.getInstance();

        // Get current quantity
        int currentQty = cartService.getProductQuantity(userID, productID);

        if (currentQty <= 1) {
            // Ask for confirmation before removing
            int confirm = JOptionPane.showConfirmDialog(panel,
                "This will remove the item from your cart. Continue?",
                "Remove Item",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }
        }

        boolean success = cartService.decreaseQuantity(userID, productID, 1);

        if (success) {
            // Refresh the payment screen
            refreshPaymentScreen();
        } else {
            JOptionPane.showMessageDialog(panel,
                "Failed to update quantity. Please try again.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Handle remove product from cart
     */
    private void handleRemoveProduct(String productID, String productName) {
        if (!isCartMode || userID == null) return;

        int confirm = JOptionPane.showConfirmDialog(panel,
            "Remove \"" + productName + "\" from your cart?",
            "Confirm Removal",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            com.service.CartService cartService = com.service.CartService.getInstance();
            boolean success = cartService.removeProductFromCart(userID, productID);

            if (success) {
                JOptionPane.showMessageDialog(panel,
                    "Product removed from cart.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
                refreshPaymentScreen();
            } else {
                JOptionPane.showMessageDialog(panel,
                    "Failed to remove product. Please try again.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Refresh the payment screen to reflect cart changes
     */
    private void refreshPaymentScreen() {
        // Check if cart is empty
        com.service.CartService cartService = com.service.CartService.getInstance();
        java.util.List<com.model.CartItem> items = cartService.getCartItems(userID);

        if (items.isEmpty()) {
            JOptionPane.showMessageDialog(panel,
                "Your cart is now empty.",
                "Cart Empty",
                JOptionPane.INFORMATION_MESSAGE);
            // Navigate back to store
            appFrame.setScreen(new com.UI.store.StoreScreen(appFrame));
        } else {
            // Reload the payment screen with updated cart
            appFrame.setScreen(new PaymentScreen(appFrame, userID, orderID, true));
        }
    }

    // =================== UI HELPERS ===================

    private RoundedPanel createCardPanel() {
        RoundedPanel card = new RoundedPanel(12);
        card.setBackground(Color.WHITE);
        card.setBorder(new LineBorder(new Color(220, 220, 220)));
        return card;
    }

    private JTextField addLabeledTextField(JPanel parent, String label, int lx, int ly, int lw,
                                           int fx, int fy, int fw) {
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Arial", Font.PLAIN, 12));
        lbl.setBounds(lx, ly, lw, 16);
        parent.add(lbl);

        JTextField tf = createInputField("Value");
        tf.setBounds(fx, fy, fw, 34);
        parent.add(tf);
        return tf;
    }

    private JTextField createInputField(String placeholder) {
        JTextField tf = new JTextField(placeholder);
        tf.setFont(new Font("Arial", Font.PLAIN, 13));
        tf.setForeground(new Color(110, 110, 110));
        tf.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(210, 210, 210)),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        return tf;
    }

    private JLabel addSummaryRow(JPanel parent, String name, int y, String value) {
        JLabel left = new JLabel(name);
        left.setFont(new Font("Arial", Font.PLAIN, 13));
        left.setBounds(20, y, 150, 18);
        parent.add(left);

        JLabel right = new JLabel(value);
        right.setFont(new Font("Arial", Font.PLAIN, 13));
        right.setHorizontalAlignment(SwingConstants.RIGHT);
        right.setBounds(180, y, 120, 18);
        parent.add(right);
        return right;
    }
}
