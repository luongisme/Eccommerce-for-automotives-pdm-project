package com.UI.Payment;

import javax.swing.*;
import java.awt.Desktop;
import java.net.URI;
import java.text.NumberFormat;
import java.util.Locale;


public class PaymentController {
    private double subtotal;
    private double shipping;
    private double taxRate; //  0.08 for 8%

    public PaymentController(double subtotal, double shipping, double taxRate) {
        this.subtotal = subtotal;
        this.shipping = shipping;
        this.taxRate = taxRate;
    }

    public double getSubtotal() { return round2(subtotal); }
    public double getShipping() { return round2(shipping); }
    public double getTax() { return round2(subtotal * taxRate); }
    public double getTotal() { return round2(getSubtotal() + getShipping() + getTax()); }

    public String formatUSD(double amount) {
        return NumberFormat.getCurrencyInstance(Locale.US).format(amount);
    }

    // Basic validations. Return null if valid, otherwise a user-friendly error message
    public String validateShipping(String street, String city, String district) {
        if (isEmpty(street)) return "Street address is required";
        if (isEmpty(city)) return "City is required";
        if (isEmpty(district)) return "District is required";
        return null;
    }

    public String validatePayment(String cardholder, String cardNumber, String expiry, String cvv) {
        if (isEmpty(cardholder)) return "Cardholder name is required";
        if (isEmpty(cardNumber)) return "Card number is required";
        String digits = cardNumber.replaceAll("\\s+", "");
        if (!digits.matches("\\d{13,19}")) return "Card number must be 13-19 digits";

        if (isEmpty(expiry)) return "Expiry date is required";
        if (!expiry.matches("^(0[1-9]|1[0-2])\\/(\\d{2})$")) return "Expiry must be in MM/YY format";

        if (isEmpty(cvv)) return "CVV is required";
        if (!cvv.matches("^\\d{3,4}$")) return "CVV must be 3-4 digits";
        return null;
    }

    public boolean processPaymentLocally() {
        // simulate a successful charge.
        try {
            Thread.sleep(600); //giabo lag de connect gateway
        } catch (InterruptedException ignored) {}
        return true;
    }


    public String buildDemoCheckoutUrl(String itemName, int quantity, String currency) {
        double total = getTotal();
        String amount = String.format(Locale.US, "%.2f", total);
       
        return "https://example.com/checkout?item=" + urlEncode(itemName) +
               "&qty=" + quantity +
               "&currency=" + currency +
               "&amount=" + amount;
    }

    // Try to open system browser to the gateway URL
    public void openPaymentGateway(String checkoutUrl) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(checkoutUrl));
            } else {
                JOptionPane.showMessageDialog(null,
                        "Desktop browsing is not supported on this system.\nOpen this URL manually:\n" + checkoutUrl,
                        "Open Payment Gateway",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "Failed to open browser. URL:\n" + checkoutUrl + "\n\n" + ex.getMessage(),
                    "Open Payment Gateway",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty() || s.equalsIgnoreCase("Value");
    }

    private double round2(double v) { return Math.round(v * 100.0) / 100.0; }

    private String urlEncode(String s) {
        try { return java.net.URLEncoder.encode(s, "UTF-8"); } catch (Exception e) { return s; }
    }
}
