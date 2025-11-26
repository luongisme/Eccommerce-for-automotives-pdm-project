package com.service;

import com.DAO.cartItemDAOimpl;
import com.DAO.shoppingcartDAOimpl;
import com.model.CartItem;
import com.model.ShoppingCart;
import com.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

public class CartService {
    private static CartService instance;
    private final shoppingcartDAOimpl cartDAO;
    private final cartItemDAOimpl cartItemDAO;
    private final Random random;

    private CartService() {
        this.cartDAO = new shoppingcartDAOimpl();
        this.cartItemDAO = new cartItemDAOimpl();
        this.random = new Random();
    }

    public static CartService getInstance() {
        if (instance == null) {
            instance = new CartService();
        }
        return instance;
    }

    /**
     * Add product to user's shopping cart
     */
    public boolean addToCart(User user, String productID, int quantity) {
        if (user == null || productID == null || quantity <= 0) {
            System.err.println("Invalid parameters: user=" + user + ", productID=" + productID + ", quantity=" + quantity);
            return false;
        }

        // Debug: Print user information
        System.out.println("Adding to cart for user: UserID=" + user.getUserID() +
                         ", Username=" + user.getUsername() +
                         ", Email=" + user.getEmail());

        // Validate that user exists in database
        if (!validateUserExists(user.getUserID())) {
            System.err.println("ERROR: User does not exist in database. UserID=" + user.getUserID());
            System.err.println("Please make sure the user is registered in the database before adding items to cart.");
            return false;
        }

        try {
            // Get or create shopping cart for user
            ShoppingCart cart = getOrCreateCart(user.getUserID());

            if (cart == null) {
                System.err.println("Failed to get or create cart for user: " + user.getUserID());
                return false;
            }

            // Check if product already in cart
            List<CartItem> existingItems = cartItemDAO.findByCartId(cart.getCartID());
            CartItem existingItem = existingItems.stream()
                    .filter(item -> item.getPid().equals(productID))
                    .findFirst()
                    .orElse(null);

            if (existingItem != null) {
                // Update quantity
                existingItem.setQuantity(existingItem.getQuantity() + quantity);
                return cartItemDAO.update(existingItem);
            } else {
                // Create new cart item
                CartItem newItem = new CartItem();
                newItem.setCiID("CI" + generateRandomId());
                newItem.setCartID(cart.getCartID());
                newItem.setPid(productID);
                newItem.setQuantity(1);

                return cartItemDAO.insert(newItem);
            }
        } catch (Exception e) {
            System.err.println("Error adding to cart: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Get or create shopping cart for user
     */
    private ShoppingCart getOrCreateCart(String userID) {
        try {
            // Try to get existing cart
            List<ShoppingCart> carts = cartDAO.findByUserId(userID);

            if (!carts.isEmpty()) {
                return carts.get(0);
            }

            // Create new cart
            ShoppingCart newCart = new ShoppingCart();
            newCart.setCartID("CART" + generateRandomId());
            newCart.setUserID(userID);
            newCart.setCreatedAt(LocalDateTime.now());

            if (cartDAO.insert(newCart)) {
                return newCart;
            }
        } catch (Exception e) {
            System.err.println("Error getting/creating cart: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get cart items for user
     */
    public List<CartItem> getCartItems(String userID) {
        try {
            List<ShoppingCart> carts = cartDAO.findByUserId(userID);
            if (!carts.isEmpty()) {
                return cartItemDAO.findByCartId(carts.get(0).getCartID());
            }
        } catch (Exception e) {
            System.err.println("Error getting cart items: " + e.getMessage());
            e.printStackTrace();
        }
        return List.of();
    }

    /**
     * Increase quantity of product in cart
     */
    public boolean increaseQuantity(String userID, String productID, int amount) {
        try {
            List<ShoppingCart> carts = cartDAO.findByUserId(userID);
            if (carts.isEmpty()) {
                System.err.println("No cart found for user: " + userID);
                return false;
            }

            String cartID = carts.get(0).getCartID();
            List<CartItem> items = cartItemDAO.findByCartId(cartID);

            CartItem item = items.stream()
                    .filter(i -> i.getPid().equals(productID))
                    .findFirst()
                    .orElse(null);

            if (item != null) {
                item.setQuantity(item.getQuantity() + amount);
                System.out.println("Increased quantity for product " + productID + " to " + item.getQuantity());
                return cartItemDAO.update(item);
            } else {
                System.err.println("Product not found in cart: " + productID);
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error increasing quantity: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Decrease quantity of product in cart
     * Automatically removes item if quantity becomes 0 or negative
     */
    public boolean decreaseQuantity(String userID, String productID, int amount) {
        try {
            List<ShoppingCart> carts = cartDAO.findByUserId(userID);
            if (carts.isEmpty()) {
                System.err.println("No cart found for user: " + userID);
                return false;
            }

            String cartID = carts.get(0).getCartID();
            List<CartItem> items = cartItemDAO.findByCartId(cartID);

            CartItem item = items.stream()
                    .filter(i -> i.getPid().equals(productID))
                    .findFirst()
                    .orElse(null);

            if (item != null) {
                int newQuantity = item.getQuantity() - amount;

                if (newQuantity <= 0) {
                    // Remove item if quantity becomes 0 or negative
                    System.out.println("Removing product from cart (quantity = 0): " + productID);
                    return cartItemDAO.delete(item.getCiID());
                } else {
                    // Update quantity
                    item.setQuantity(newQuantity);
                    System.out.println("Decreased quantity for product " + productID + " to " + newQuantity);
                    return cartItemDAO.update(item);
                }
            } else {
                System.err.println("Product not found in cart: " + productID);
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error decreasing quantity: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Update quantity of product in cart
     * Automatically removes item if quantity is set to 0 or negative
     */
    public boolean updateQuantity(String userID, String productID, int newQuantity) {
        try {
            if (newQuantity <= 0) {
                // Remove item if quantity is 0 or negative
                System.out.println("Quantity set to 0 or negative, removing product: " + productID);
                return removeProductFromCart(userID, productID);
            }

            List<ShoppingCart> carts = cartDAO.findByUserId(userID);
            if (carts.isEmpty()) {
                System.err.println("No cart found for user: " + userID);
                return false;
            }

            String cartID = carts.get(0).getCartID();
            List<CartItem> items = cartItemDAO.findByCartId(cartID);

            CartItem item = items.stream()
                    .filter(i -> i.getPid().equals(productID))
                    .findFirst()
                    .orElse(null);

            if (item != null) {
                item.setQuantity(newQuantity);
                System.out.println("Updated quantity for product " + productID + " to " + newQuantity);
                return cartItemDAO.update(item);
            } else {
                System.err.println("Product not found in cart: " + productID);
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error updating quantity: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Remove specific product from user's cart
     */
    public boolean removeProductFromCart(String userID, String productID) {
        try {
            List<ShoppingCart> carts = cartDAO.findByUserId(userID);
            if (carts.isEmpty()) {
                System.err.println("No cart found for user: " + userID);
                return false;
            }

            String cartID = carts.get(0).getCartID();
            List<CartItem> items = cartItemDAO.findByCartId(cartID);

            CartItem itemToRemove = items.stream()
                    .filter(item -> item.getPid().equals(productID))
                    .findFirst()
                    .orElse(null);

            if (itemToRemove != null) {
                System.out.println("Removing product from cart: " + productID);
                return cartItemDAO.delete(itemToRemove.getCiID());
            } else {
                System.err.println("Product not found in cart: " + productID);
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error removing product from cart: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Get quantity of specific product in cart
     */
    public int getProductQuantity(String userID, String productID) {
        try {
            List<ShoppingCart> carts = cartDAO.findByUserId(userID);
            if (carts.isEmpty()) {
                return 0;
            }

            String cartID = carts.get(0).getCartID();
            List<CartItem> items = cartItemDAO.findByCartId(cartID);

            return items.stream()
                    .filter(item -> item.getPid().equals(productID))
                    .findFirst()
                    .map(CartItem::getQuantity)
                    .orElse(0);
        } catch (Exception e) {
            System.err.println("Error getting product quantity: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Remove item from cart by CartItemID
     */
    public boolean removeFromCart(String cartItemID) {
        try {
            return cartItemDAO.delete(cartItemID);
        } catch (Exception e) {
            System.err.println("Error removing from cart: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Clear all items from user's cart
     */
    public boolean clearCart(String userID) {
        try {
            List<ShoppingCart> carts = cartDAO.findByUserId(userID);
            if (!carts.isEmpty()) {
                String cartID = carts.get(0).getCartID();
                List<CartItem> items = cartItemDAO.findByCartId(cartID);

                for (CartItem item : items) {
                    cartItemDAO.delete(item.getCiID());
                }
                System.out.println("Cleared all items from cart for user: " + userID);
                return true;
            }
        } catch (Exception e) {
            System.err.println("Error clearing cart: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Generate random ID
     */
    private String generateRandomId() {
        return String.format("%08d", random.nextInt(100000000));
    }

    /**
     * Validate that user exists in database
     */
    private boolean validateUserExists(String userID) {
        try {
            com.DAO.userDAOimpl userDAO = new com.DAO.userDAOimpl();
            com.model.User user = userDAO.findById(userID);
            return user != null;
        } catch (Exception e) {
            System.err.println("Error validating user existence: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}

