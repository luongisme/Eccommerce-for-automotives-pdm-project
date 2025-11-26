package com.UI.Payment;

import com.DAO.orderDAOimpl;
import com.DAO.paymentDAOimpl;
import com.DAO.productDAOimpl;
import com.model.Order;
import com.model.Payment;
import com.model.Product;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

public class PaymentController {

    private double subtotal;
    private final double taxRate; // 0.08 = 8%

    private final paymentDAOimpl paymentDAO;
    private final productDAOimpl productDAO;
    private final orderDAOimpl orderDAO;

    // Trạng thái hiện tại của màn Payment
    private Order currentOrder;

    // Support multiple products from cart
    private Map<Product, Integer> cartProducts = new LinkedHashMap<>(); // Product -> Quantity

    // Legacy fields for backward compatibility
    private Product currentProduct;
    private int quantity = 1;
    private double unitPrice;

    public PaymentController(double taxRate,
                             paymentDAOimpl paymentDAO,
                             productDAOimpl productDAO,
                             orderDAOimpl orderDAO) {
        this.taxRate = taxRate;
        this.paymentDAO = paymentDAO;
        this.productDAO = productDAO;
        this.orderDAO = orderDAO;
    }

    // ============= INIT DATA (Order + Product) =============

    /**
     * NEW: Load all products from user's cart
     */
    public void initDataFromCart(String userID, String orderID) {
        System.out.println("PaymentController.initDataFromCart called with userID=" + userID + ", orderID=" + orderID);

        cartProducts.clear();

        try {
            // Get user's shopping cart
            com.DAO.shoppingcartDAOimpl cartDAO = new com.DAO.shoppingcartDAOimpl();
            java.util.List<com.model.ShoppingCart> carts = cartDAO.findByUserId(userID);

            if (!carts.isEmpty()) {
                String cartID = carts.get(0).getCartID();

                // Get all cart items
                com.DAO.cartItemDAOimpl cartItemDAO = new com.DAO.cartItemDAOimpl();
                java.util.List<com.model.CartItem> items = cartItemDAO.findByCartId(cartID);

                System.out.println("Found " + items.size() + " items in cart");

                // Load product details for each cart item
                for (com.model.CartItem item : items) {
                    Product product = productDAO.findById(item.getPid());
                    if (product != null) {
                        cartProducts.put(product, item.getQuantity());
                        System.out.println("  - " + product.getName() + " x " + item.getQuantity());
                    }
                }
            }

            // Calculate total
            calculateSubtotal();

            // Load or create order
            currentOrder = orderDAO.findById(orderID);
            if (currentOrder == null && orderID != null && orderID.startsWith("TEMP_")) {
                // Create new order
                currentOrder = new Order();
                currentOrder.setOrderID(orderID);
                currentOrder.setUserID(userID);
                currentOrder.setOrderDate(java.time.LocalDateTime.now());
                currentOrder.setOrderStatus("PENDING");
                currentOrder.setTotalAmount(BigDecimal.valueOf(getTotal()));
            }

            // Set legacy fields for backward compatibility (use first product)
            if (!cartProducts.isEmpty()) {
                Map.Entry<Product, Integer> first = cartProducts.entrySet().iterator().next();
                currentProduct = first.getKey();
                quantity = first.getValue();
                unitPrice = currentProduct.getPrice();
            }

        } catch (Exception e) {
            System.err.println("Error loading cart data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * LEGACY: Load product + order theo ID, tính unitPrice, quantity và subtotal.
     */
    public void initData(String orderID, String productID) {
        // Debug logging
        System.out.println("PaymentController.initData called with orderID=" + orderID + ", productID=" + productID);

        // Validate input
        if (productID == null) {
            System.err.println("ERROR: productID is null in initData!");
            throw new IllegalStateException("Product not found for ID: " + productID);
        }

        // Lấy product
        currentProduct = productDAO.findById(productID);
        if (currentProduct == null) {
            System.err.println("ERROR: Product not found in database for ID: " + productID);
            throw new IllegalStateException("Product not found for ID: " + productID);
        }

        System.out.println("Successfully loaded product: " + currentProduct.getName());
        unitPrice = currentProduct.getPrice();

        // Lấy order
        currentOrder = orderDAO.findById(orderID);

        if (currentOrder != null && currentOrder.getTotalAmount() != null) {
            double totalAmount = currentOrder.getTotalAmount().doubleValue();
            if (unitPrice > 0) {
                quantity = (int) Math.max(1, Math.round(totalAmount / unitPrice));
            }
        } else {
            quantity = 1;
        }

        setSubtotal(unitPrice * quantity);
        if (currentOrder != null) {
            currentOrder.setTotalAmount(BigDecimal.valueOf(getTotal()));
        }
    }

    public Product getCurrentProduct() {
        return currentProduct;
    }

    public Order getCurrentOrder() {
        return currentOrder;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    // ====== NEW: METHODS FOR MULTIPLE PRODUCTS ======

    public Map<Product, Integer> getCartProducts() {
        return new LinkedHashMap<>(cartProducts);
    }

    public int getTotalItems() {
        return cartProducts.values().stream().mapToInt(Integer::intValue).sum();
    }

    private void calculateSubtotal() {
        double total = 0;
        for (Map.Entry<Product, Integer> entry : cartProducts.entrySet()) {
            Product p = entry.getKey();
            int qty = entry.getValue();
            total += p.getPrice() * qty;
        }
        setSubtotal(total);
    }

    // ====== TÍNH TOÁN SỐ TIỀN ======

    public double getSubtotal() {
        return round2(subtotal);
    }

    public double getTax() {
        return round2(subtotal * taxRate);
    }

    public double getTotal() {
        return round2(getSubtotal() + getTax());
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public String formatUSD(double amount) {
        return NumberFormat.getCurrencyInstance(Locale.US).format(amount);
    }

    /**
     * Tăng/giảm quantity, tự cập nhật subtotal + Order.TotalAmount bên trong controller.
     */
    public void changeQuantity(int delta) {
        int newQty = quantity + delta;
        if (newQty < 1) {
            return; // không cho 0 hoặc âm
        }
        quantity = newQty;

        // cập nhật subtotal trong controller
        setSubtotal(unitPrice * quantity);

        // cập nhật Order.TotalAmount (giả sử total = subtotal + tax)
        if (currentOrder != null) {
            BigDecimal newTotal = BigDecimal.valueOf(getTotal());
            currentOrder.setTotalAmount(newTotal);
        }
    }

    // ====== VALIDATE THANH TOÁN ======

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

    /**
     * Validate toàn bộ order dựa trên paymentMethod.
     * - Nếu chưa chọn method -> báo lỗi.
     * - Nếu method = Credit Card -> validate card info.
     * - Method khác -> OK, không cần thẻ.
     */
    public String validateOrder(
            String paymentMethod,
            String cardholder,
            String cardNumber,
            String expiry,
            String cvv
    ) {
        if (isEmpty(paymentMethod)) {
            return "Please select a payment method.";
        }

        if ("Credit Card".equals(paymentMethod)) {
            return validatePayment(cardholder, cardNumber, expiry, cvv);
        }

        // Bank Transfer / COD / PayPal không cần thêm info ở version hiện tại
        return null;
    }

    // ====== LẤY PAYMENT ACCOUNT ĐÃ ĐĂNG KÝ TỪ DB ======

    public List<Payment> getAllPayments() {
        return paymentDAO.findAll();
    }

    public List<String> getRegisteredPaymentMethods() {
        List<Payment> all = getAllPayments();
        return all.stream()
                .map(Payment::getPaymentMethod)
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .distinct()
                .collect(Collectors.toList());
    }

    public boolean updatePayment(Payment payment) {
        return paymentDAO.update(payment);
    }

    // ====== LƯU PAYMENT VÀ UPDATE ORDER ======

    public boolean savePaymentRecord(String paymentMethod) {
        Payment payment = new Payment();
        payment.setPaymentID(generatePaymentId());
        payment.setAmount(BigDecimal.valueOf(getTotal())); // hoặc getSubtotal()
        payment.setPaymentMethod(paymentMethod);
        payment.setStatus("PAID"); // hoặc "PENDING" tuỳ flow

        return paymentDAO.insert(payment);
    }

    public String buildSuccessMessage(String paymentMethod) {
        String itemName = (currentProduct != null) ? currentProduct.getName() : "Product";
        return String.format(
                "SUCCESSFULLY PURCHASED\n\n" +
                        "Item: %s\n" +
                        "Quantity: %d\n" +
                        "Total Amount: %s\n" +
                        "Payment Method: %s\n\n" +
                        "Thank you for your purchase!",
                itemName,
                quantity,
                formatUSD(getTotal()),
                paymentMethod
        );
    }

    public boolean processPaymentAndSave(String paymentMethod) {
        // 1. Giả lập thanh toán online
        boolean gatewaySuccess = processPaymentLocally();
        if (!gatewaySuccess) {
            return false;
        }

        // 2. Lưu payment vào DB (transaction record)
        return savePaymentRecord(paymentMethod);
    }

    /**
     * Sau khi payment ok, update Order hiện tại xuống DB.
     */
    public boolean updateOrderInDatabase() {
        if (currentOrder == null) return false;
        return orderDAO.update(currentOrder);
    }

    /**
     * NEW: Save order and all order items to database
     */
    public boolean saveOrderToDatabase(String userID, String orderID, String paymentMethod) {
        try {
            // 1. Create Payment record first
            Payment payment = new Payment();
            String paymentID = "PAY" + String.format("%08d", new java.util.Random().nextInt(100000000));
            payment.setPaymentID(paymentID);
            payment.setAmount(BigDecimal.valueOf(getTotal()));
            payment.setPaymentMethod(paymentMethod);
            payment.setStatus("COMPLETED");

            boolean paymentSaved = paymentDAO.insert(payment);
            if (!paymentSaved) {
                System.err.println("Failed to save payment record");
                return false;
            }

            System.out.println("Payment saved: " + paymentID);

            // 2. Create/Update Order
            if (currentOrder == null) {
                currentOrder = new Order();
                currentOrder.setOrderID(orderID != null ? orderID : "ORD" + System.currentTimeMillis());
                currentOrder.setUserID(userID);
            }

            currentOrder.setOrderDate(java.time.LocalDateTime.now());
            currentOrder.setTotalAmount(BigDecimal.valueOf(getTotal()));
            currentOrder.setOrderStatus("CONFIRMED");
            currentOrder.setPaymentID(paymentID);

            // Insert order
            boolean orderSaved;
            if (orderDAO.findById(currentOrder.getOrderID()) == null) {
                orderSaved = orderDAO.insert(currentOrder);
            } else {
                orderSaved = orderDAO.update(currentOrder);
            }

            if (!orderSaved) {
                System.err.println("Failed to save order");
                return false;
            }

            System.out.println("Order saved: " + currentOrder.getOrderID());

            // 3. Create OrderItems for each product in cart
            com.DAO.orderitemDAOimpl orderItemDAO = new com.DAO.orderitemDAOimpl();

            for (Map.Entry<Product, Integer> entry : cartProducts.entrySet()) {
                Product p = entry.getKey();
                int qty = entry.getValue();

                com.model.OrderItem orderItem = new com.model.OrderItem();
                orderItem.setOiID("OI" + String.format("%08d", new java.util.Random().nextInt(100000000)));
                orderItem.setOrderID(currentOrder.getOrderID());
                orderItem.setPid(p.getPid());
                orderItem.setQuantity(qty);
                orderItem.setPriceAtPurchase(BigDecimal.valueOf(p.getPrice()));

                boolean itemSaved = orderItemDAO.insert(orderItem);
                if (!itemSaved) {
                    System.err.println("Failed to save order item for product: " + p.getName());
                } else {
                    System.out.println("OrderItem saved: " + p.getName() + " x " + qty);
                }
            }

            // 4. Clear user's cart after successful order
            clearUserCart(userID);

            return true;

        } catch (Exception e) {
            System.err.println("Error saving order to database: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Clear user's shopping cart after successful order
     */
    private void clearUserCart(String userID) {
        try {
            com.service.CartService cartService = com.service.CartService.getInstance();
            cartService.clearCart(userID);
            System.out.println("Cart cleared for user: " + userID);
        } catch (Exception e) {
            System.err.println("Error clearing cart: " + e.getMessage());
        }
    }

    public boolean processPaymentLocally() {
        // simulate a successful charge.
        try {
            Thread.sleep(600); // giả bộ lag để connect gateway
        } catch (InterruptedException ignored) {}
        return true;
    }

    // --- helpers ---
    private boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty() || s.equalsIgnoreCase("Value");
    }

    private double round2(double v) {
        return Math.round(v * 100.0) / 100.0;
    }

    private String generatePaymentId() {
        // Ví dụ: PAY-<UUID-8-ký-tự-đầu>
        return "PAY-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
