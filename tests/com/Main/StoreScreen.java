package com.Main;

import com.model.Product;
import com.service.SearchFunctionality;
import com.UI.store.*;

import javax.swing.*;

import java.awt.Color;
import java.util.List;

public class StoreScreen extends Screen {
    private List<Product> products;

    // Default constructor (guest mode)
    public StoreScreen(AppFrame appFrame) {
        super(appFrame);
        this.products = loadAllProducts(); // fetch all products by default
        initUI();
    }

    // Constructor with search results
    public StoreScreen(AppFrame appFrame, List<Product> searchResults) {
        super(appFrame);
        this.products = searchResults;
        initUI();
    }

    @Override
    protected void initUI() {
        panel.removeAll();
        // Add StoreHeader
        panel.add(new StoreHeader(appFrame));

        // Display product cards
        int y = 100;
        for (Product p : products) {
            JPanel productCard = createProductCard(p);
            productCard.setBounds(20, y, 300, 150);
            panel.add(productCard);
            y += 160;
        }
    }

    private JPanel createProductCard(Product product) {
        JPanel card = new JPanel();
        card.setLayout(null);
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JLabel nameLabel = new JLabel(product.getName());
        nameLabel.setBounds(10, 10, 200, 20);
        card.add(nameLabel);

        JLabel priceLabel = new JLabel("$" + product.getPrice());
        priceLabel.setBounds(10, 40, 100, 20);
        card.add(priceLabel);

        return card;
    }

    private List<Product> loadAllProducts() {
        // Fetch all products from DB
        return new SearchFunctionality().searchByKeyword(""); // empty string fetches all
    }
}
