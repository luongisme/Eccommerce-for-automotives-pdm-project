package com.UI.Payment;

import com.Main.AppFrame;
import com.Main.Screen;
import com.UI.components.RoundedButton;
import com.UI.components.RoundedPanel;
import com.UI.store.StoreFooter;
import com.UI.store.StoreHeader;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Checkout / Payment UI focused on displaying payable amount
 * Designed to visually match the provided mockup
 */
public class PaymentScreen extends Screen {
    // Controller holds payment logic
    private final PaymentController controller = new PaymentController(45.99, 9.99, 0.08);

    // Input fields for validation and gateway params
    private JTextField streetField;
    private JTextField cityField;
    private JTextField districtField;
    private JTextField cardholderField;
    private JTextField cardNumberField;
    private JTextField expiryField;
    private JTextField cvvField;

    // Computed UI labels
    private JLabel subtotalValueLbl;
    private JLabel shippingValueLbl;
    private JLabel taxValueLbl;
    private JLabel totalValueLbl;
    private RoundedButton placeOrderBtn;

    public PaymentScreen(AppFrame appFrame) {
        super(appFrame);
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

        // Left column: Shipping Address
        RoundedPanel shippingPanel = createCardPanel();
        shippingPanel.setBounds(30, 120, 600, 250);
        shippingPanel.setLayout(null);
        panel.add(shippingPanel);

        JLabel shippingIcon = new JLabel("📍");
        shippingIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        shippingIcon.setBounds(15, 12, 24, 24);
        shippingPanel.add(shippingIcon);

        JLabel shippingTitle = new JLabel("Shipping Address");
        shippingTitle.setFont(new Font("Arial", Font.BOLD, 16));
        shippingTitle.setBounds(45, 12, 300, 24);
        shippingPanel.add(shippingTitle);

        // Shipping form fields
        int labelX = 20, labelW = 140, fieldX = 20, fieldW = 560;
        streetField = addLabeledTextField(shippingPanel, "Street Address", labelX, 52, labelW, fieldX, 78, fieldW);
        cityField = addLabeledTextField(shippingPanel, "City", labelX, 120, labelW, fieldX, 146, fieldW);
        districtField = addLabeledTextField(shippingPanel, "District", labelX, 188, labelW, fieldX, 214, fieldW);

        // Left column: Payment Information
        RoundedPanel paymentPanel = createCardPanel();
        paymentPanel.setBounds(30, 360, 600, 300);
        paymentPanel.setLayout(null);
        panel.add(paymentPanel);

        JLabel payIcon = new JLabel("💳");
        payIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        payIcon.setBounds(15, 12, 24, 24);
        paymentPanel.add(payIcon);

        JLabel payTitle = new JLabel("Payment Information");
        payTitle.setFont(new Font("Arial", Font.BOLD, 16));
        payTitle.setBounds(45, 12, 300, 24);
        paymentPanel.add(payTitle);

        cardholderField = addLabeledTextField(paymentPanel, "Cardholder Name", labelX, 52, labelW, fieldX, 78, fieldW);
        cardNumberField = addLabeledTextField(paymentPanel, "Card Number", labelX, 120, labelW, fieldX, 146, fieldW);

        // Expiry + CVV side-by-side
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

        // Right column: Order Summary
        RoundedPanel summaryPanel = createCardPanel();
        summaryPanel.setBounds(660, 120, 320, 300);
        summaryPanel.setLayout(null);
        panel.add(summaryPanel);

        JLabel summaryTitle = new JLabel("Order Summary");
        summaryTitle.setFont(new Font("Arial", Font.BOLD, 16));
        summaryTitle.setBounds(20, 12, 200, 24);
        summaryPanel.add(summaryTitle);

        // Product row (image + info)
        JPanel imgPlaceholder = new JPanel();
        imgPlaceholder.setBackground(new Color(240, 240, 240));
        imgPlaceholder.setBorder(new LineBorder(new Color(220, 220, 220)));
        imgPlaceholder.setBounds(20, 48, 48, 48);
        summaryPanel.add(imgPlaceholder);

        JLabel productTitle = new JLabel("Spark Plug Set (4-Pack)");
        productTitle.setFont(new Font("Arial", Font.PLAIN, 13));
        productTitle.setBounds(78, 48, 180, 18);
        summaryPanel.add(productTitle);

        JLabel qtyLbl = new JLabel("Qty: 1");
        qtyLbl.setFont(new Font("Arial", Font.PLAIN, 12));
        qtyLbl.setForeground(new Color(120, 120, 120));
        qtyLbl.setBounds(78, 66, 100, 16);
        summaryPanel.add(qtyLbl);

        JLabel priceLbl = new JLabel(controller.formatUSD(controller.getSubtotal()));
        priceLbl.setFont(new Font("Arial", Font.BOLD, 13));
        priceLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        priceLbl.setBounds(220, 56, 80, 20);
        summaryPanel.add(priceLbl);

        JSeparator sep1 = new JSeparator();
        sep1.setBounds(20, 110, 280, 1);
        summaryPanel.add(sep1);

        // Summary rows
        int rowY = 120;
        subtotalValueLbl = addSummaryRow(summaryPanel, "Subtotal", rowY, controller.formatUSD(controller.getSubtotal()));
        rowY += 24;
        shippingValueLbl = addSummaryRow(summaryPanel, "Shipping", rowY, controller.formatUSD(controller.getShipping()));
        rowY += 24;
        taxValueLbl = addSummaryRow(summaryPanel, "Tax", rowY, controller.formatUSD(controller.getTax()));
        rowY += 30;

        // Total row (bold)
        JLabel totalLbl = new JLabel("Total");
        totalLbl.setFont(new Font("Arial", Font.BOLD, 14));
        totalLbl.setBounds(20, rowY, 200, 20);
        summaryPanel.add(totalLbl);

        totalValueLbl = new JLabel(controller.formatUSD(controller.getTotal()));
        totalValueLbl.setFont(new Font("Arial", Font.BOLD, 16));
        totalValueLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        totalValueLbl.setBounds(180, rowY - 2, 120, 24);
        summaryPanel.add(totalValueLbl);

        // Place order button
        placeOrderBtn = new RoundedButton("Place order - " + controller.formatUSD(controller.getTotal()), 10);
        placeOrderBtn.setForeground(Color.WHITE);
        placeOrderBtn.setBounds(20, rowY + 36, 280, 36);
        placeOrderBtn.addActionListener(e -> onPlaceOrder());
        summaryPanel.add(placeOrderBtn);

        // Footer (simple reusable footer)
        StoreFooter footer = new StoreFooter();
        footer.setBounds(0, 837, 1024, 200);
        panel.add(footer);
    }

    private void onPlaceOrder() {
        // Validate shipping
        String shipError = controller.validateShipping(
                streetField.getText(),
                cityField.getText(),
                districtField.getText()
        );
        if (shipError != null) {
            JOptionPane.showMessageDialog(panel, shipError, "Invalid Shipping", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validate payment
        String payError = controller.validatePayment(
                cardholderField.getText(),
                cardNumberField.getText(),
                expiryField.getText(),
                cvvField.getText()
        );
        if (payError != null) {
            JOptionPane.showMessageDialog(panel, payError, "Invalid Payment", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String checkoutUrl = controller.buildDemoCheckoutUrl("Spark Plug Set (4-Pack)", 1, "USD");
        controller.openPaymentGateway(checkoutUrl);

    }

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
