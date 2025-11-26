package com.UI.store;

import com.Main.AppFrame;
import com.Main.Screen;
import com.model.Product;
import com.service.ProductService;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;

public class StoreScreen extends Screen {
    private ProductService productService;
    private StoreSidebar sidebar;
    private JPanel productGridPanel;
    private JPanel sortPanel;
    private JComboBox<String> vehicleTypeCombo;
    private JPanel paginationPanel;
    private JScrollPane scrollPane;

    private List<Product> currentProducts;
    private int currentPage = 1;
    private int productsPerPage = 12;
    private String currentSort = "new";

    public StoreScreen(AppFrame appFrame) {
        this(appFrame, "new", 1);
    }

    public StoreScreen(AppFrame appFrame, String sortType, int page) {
        super(appFrame);
        this.currentSort = sortType != null ? sortType : "new";
        this.currentPage = page > 0 ? page : 1;
        productService = ProductService.getInstance();
        panel = new JPanel(null);
        panel.setBackground(Color.WHITE);
        initUI();
        loadProducts();
    }

    @Override
    protected void initUI() {
        // Header
        StoreHeader header = new StoreHeader(appFrame);
        header.setBounds(0, 0, 1024, 70);
        panel.add(header);

        // Sidebar
        sidebar = new StoreSidebar(
            productService.getAllCategories(),
            productService.getAllBrands()
        );
        sidebar.setBounds(20, 100, 200, 700);
        sidebar.addFilterChangeListener(this::applyFiltersAndSortWithReset);
        sidebar.addClearAllListener(this::applyFiltersAndSortWithReset);
        panel.add(sidebar);

        // Sort panel
        createSortPanel();

        // Product grid container
        JPanel gridContainer = new JPanel(new BorderLayout());
        gridContainer.setBounds(240, 170, 760, 520);
        gridContainer.setBackground(Color.WHITE);

        productGridPanel = new JPanel(new GridLayout(0, 4, 15, 15));
        productGridPanel.setBackground(Color.WHITE);

        scrollPane = new JScrollPane(productGridPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        gridContainer.add(scrollPane, BorderLayout.CENTER);

        panel.add(gridContainer);

        // Pagination
        paginationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 10));
        paginationPanel.setBackground(Color.WHITE);
        paginationPanel.setBounds(240, 700, 760, 50);
        panel.add(paginationPanel);

        // Footer
        StoreFooter footer = new StoreFooter();
        footer.setBounds(0, 837, 1024, 200);
        panel.add(footer);
    }

    private void createSortPanel() {
        sortPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        sortPanel.setBounds(240, 120, 760, 40);
        sortPanel.setBackground(Color.WHITE);

        JToggleButton newBtn = createSortButton("Featured", "new", currentSort.equals("new"));
        JToggleButton priceAscBtn = createSortButton("Price ascending", "price_asc", currentSort.equals("price_asc"));
        JToggleButton priceDescBtn = createSortButton("Price descending", "price_desc", currentSort.equals("price_desc"));
        JToggleButton ratingBtn = createSortButton("Rating", "rating", currentSort.equals("rating"));

        ButtonGroup sortGroup = new ButtonGroup();
        sortGroup.add(newBtn);
        sortGroup.add(priceAscBtn);
        sortGroup.add(priceDescBtn);
        sortGroup.add(ratingBtn);

        sortPanel.add(newBtn);
        sortPanel.add(priceAscBtn);
        sortPanel.add(priceDescBtn);
        sortPanel.add(ratingBtn);
        
    
        panel.add(sortPanel);
    }

    private JToggleButton createSortButton(String text, String sortType, boolean selected) {
        JToggleButton btn = new JToggleButton(text);
        btn.setFont(new Font("Arial", Font.PLAIN, 13));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setSelected(selected);
        
        if (selected) {
            btn.setBackground(new Color(45, 45, 45));
            btn.setForeground(Color.WHITE);
        } else {
            btn.setBackground(Color.WHITE);
            btn.setForeground(Color.BLACK);
        }
        
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(6, 12, 6, 12)
        ));

        btn.addActionListener(e -> {
            currentSort = sortType;
            currentPage = 1;
            applyFiltersAndSort();
            
            // Update button styles
            for (Component c : sortPanel.getComponents()) {
                if (c instanceof JToggleButton) {
                    JToggleButton b = (JToggleButton) c;
                    if (b.isSelected()) {
                        b.setBackground(new Color(45, 45, 45));
                        b.setForeground(Color.WHITE);
                    } else {
                        b.setBackground(Color.WHITE);
                        b.setForeground(Color.BLACK);
                    }
                }
            }
        });

        return btn;
    }

    private void loadProducts() {
        currentProducts = productService.getAllProducts();
        applyFiltersAndSort();
    }

    private void applyFiltersAndSortWithReset() {
        currentPage = 1; // Reset to first page when user changes filter
        applyFiltersAndSort();
    }

    private void applyFiltersAndSort() {
        // Get filter values
        Set<String> selectedCategories = sidebar.getSelectedCategories();
        Set<String> selectedBrands = sidebar.getSelectedBrands();
        double maxPrice = sidebar.getMaxPrice();

        // Filter products
        List<Product> filtered = productService.filterProducts(
            selectedCategories, selectedBrands, 0, maxPrice
        );

        // Sort products
        currentProducts = productService.sortProducts(filtered, currentSort);

        // Update display
        displayProducts();
        updatePagination();
    }

    private void displayProducts() {
        productGridPanel.removeAll();

        if (currentProducts.isEmpty()) {
            JLabel emptyLabel = new JLabel("No products found");
            emptyLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            emptyLabel.setForeground(new Color(150, 150, 150));
            productGridPanel.add(emptyLabel);
        } else {
            int startIndex = (currentPage - 1) * productsPerPage;
            int endIndex = Math.min(startIndex + productsPerPage, currentProducts.size());

            for (int i = startIndex; i < endIndex; i++) {
                ProductCard card = new ProductCard(currentProducts.get(i), appFrame, currentSort, currentPage);
                productGridPanel.add(card);
            }
        }

        productGridPanel.revalidate();
        productGridPanel.repaint();
    }

    private void updatePagination() {
        paginationPanel.removeAll();

        int totalPages = (int) Math.ceil((double) currentProducts.size() / productsPerPage);

        if (totalPages <= 1) {
            paginationPanel.revalidate();
            paginationPanel.repaint();
            return;
        }

        // Previous button
        JButton prevBtn = new JButton("← Previous");
        prevBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        prevBtn.setFocusPainted(false);
        prevBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        prevBtn.setEnabled(currentPage > 1);
        prevBtn.addActionListener(e -> {
            if (currentPage > 1) {
                currentPage--;
                displayProducts();
                updatePagination();
            }
        });
        paginationPanel.add(prevBtn);

        // Page numbers
        int startPage = Math.max(1, currentPage - 2);
        int endPage = Math.min(totalPages, currentPage + 2);

        for (int i = startPage; i <= endPage; i++) {
            final int page = i;
            JButton pageBtn = new JButton(String.valueOf(i));
            pageBtn.setFont(new Font("Arial", Font.PLAIN, 12));
            pageBtn.setFocusPainted(false);
            pageBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            if (i == currentPage) {
                pageBtn.setBackground(new Color(45, 45, 45));
                pageBtn.setForeground(Color.WHITE);
            } else {
                pageBtn.setBackground(Color.WHITE);
                pageBtn.setForeground(Color.BLACK);
            }
            
            pageBtn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(6, 12, 6, 12)
            ));

            pageBtn.addActionListener(e -> {
                currentPage = page;
                displayProducts();
                updatePagination();
            });
            paginationPanel.add(pageBtn);
        }

        // Ellipsis if needed
        if (endPage < totalPages - 1) {
            JLabel ellipsis = new JLabel("...");
            paginationPanel.add(ellipsis);
        }

        // Last page
        if (endPage < totalPages) {
            JButton lastPageBtn = new JButton(String.valueOf(totalPages));
            lastPageBtn.setFont(new Font("Arial", Font.PLAIN, 12));
            lastPageBtn.setFocusPainted(false);
            lastPageBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            lastPageBtn.setBackground(Color.WHITE);
            lastPageBtn.setForeground(Color.BLACK);
            lastPageBtn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(6, 12, 6, 12)
            ));
            lastPageBtn.addActionListener(e -> {
                currentPage = totalPages;
                displayProducts();
                updatePagination();
            });
            paginationPanel.add(lastPageBtn);
        }

        // Next button
        JButton nextBtn = new JButton("Next →");
        nextBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        nextBtn.setFocusPainted(false);
        nextBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        nextBtn.setEnabled(currentPage < totalPages);
        nextBtn.addActionListener(e -> {
            if (currentPage < totalPages) {
                currentPage++;
                displayProducts();
                updatePagination();
            }
        });
        paginationPanel.add(nextBtn);

        paginationPanel.revalidate();
        paginationPanel.repaint();
    }
}
