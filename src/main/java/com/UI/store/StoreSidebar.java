package com.UI.store;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class StoreSidebar extends JPanel {
    private Map<String, JCheckBox> categoryCheckBoxes;
    private Map<String, JCheckBox> brandCheckBoxes;
    private JSlider priceSlider;
    private JLabel priceRangeLabel;
    private JButton clearAllBtn;

    public StoreSidebar(List<String> categories, List<String> brands) {
        setLayout(null);
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(200, 600));
        categoryCheckBoxes = new HashMap<>();
        brandCheckBoxes = new HashMap<>();
        initUI(categories, brands);
    }

    private void initUI(List<String> categories, List<String> brands) {
        int yPos = 20;

        // Categories section
        JLabel categoriesTitle = new JLabel("Categories");
        categoriesTitle.setFont(new Font("Arial", Font.BOLD, 15));
        categoriesTitle.setBounds(20, yPos, 160, 25);
        add(categoriesTitle);
        yPos += 30;

        // Clear All button
        clearAllBtn = new JButton("Clear All ×");
        clearAllBtn.setFont(new Font("Arial", Font.PLAIN, 11));
        clearAllBtn.setForeground(new Color(100, 100, 100));
        clearAllBtn.setBackground(Color.WHITE);
        clearAllBtn.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        clearAllBtn.setFocusPainted(false);
        clearAllBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        clearAllBtn.setBounds(100, yPos - 55, 80, 22);
        add(clearAllBtn);

        // Category checkboxes
        for (String category : categories) {
            JCheckBox checkBox = new JCheckBox(category);
            checkBox.setFont(new Font("Arial", Font.PLAIN, 13));
            checkBox.setBackground(Color.WHITE);
            checkBox.setFocusPainted(false);
            checkBox.setBounds(20, yPos, 160, 25);
            checkBox.setSelected(true); // All selected by default
            categoryCheckBoxes.put(category, checkBox);
            add(checkBox);
            yPos += 28;
        }

        yPos += 10;

        // Price Range section
        JLabel priceTitle = new JLabel("Price Range");
        priceTitle.setFont(new Font("Arial", Font.BOLD, 15));
        priceTitle.setBounds(20, yPos, 160, 25);
        add(priceTitle);
        yPos += 30;

        priceRangeLabel = new JLabel("$0-10000");
        priceRangeLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        priceRangeLabel.setForeground(new Color(100, 100, 100));
        priceRangeLabel.setBounds(20, yPos, 160, 20);
        add(priceRangeLabel);
        yPos += 25;

        // Price slider
        priceSlider = new JSlider(0, 10000, 10000);
        priceSlider.setBounds(20, yPos, 160, 40);
        priceSlider.setBackground(Color.WHITE);
        priceSlider.addChangeListener(e -> {
            int value = priceSlider.getValue();
            priceRangeLabel.setText("$0-" + value);
        });
        add(priceSlider);
        yPos += 50;

        // Brands section
        JLabel brandsTitle = new JLabel("Brands");
        brandsTitle.setFont(new Font("Arial", Font.BOLD, 15));
        brandsTitle.setBounds(20, yPos, 160, 25);
        add(brandsTitle);
        yPos += 30;

        // Brand checkboxes (limit to first 10 for space)
        int brandCount = 0;
        for (String brand : brands) {
            if (brandCount >= 10) break;
            JCheckBox checkBox = new JCheckBox(brand);
            checkBox.setFont(new Font("Arial", Font.PLAIN, 13));
            checkBox.setBackground(Color.WHITE);
            checkBox.setFocusPainted(false);
            checkBox.setBounds(20, yPos, 160, 25);
            checkBox.setSelected(true); // All selected by default
            brandCheckBoxes.put(brand, checkBox);
            add(checkBox);
            yPos += 28;
            brandCount++;
        }
    }

    public Set<String> getSelectedCategories() {
        Set<String> selected = new HashSet<>();
        for (Map.Entry<String, JCheckBox> entry : categoryCheckBoxes.entrySet()) {
            if (entry.getValue().isSelected()) {
                selected.add(entry.getKey());
            }
        }
        return selected;
    }

    public Set<String> getSelectedBrands() {
        Set<String> selected = new HashSet<>();
        for (Map.Entry<String, JCheckBox> entry : brandCheckBoxes.entrySet()) {
            if (entry.getValue().isSelected()) {
                selected.add(entry.getKey());
            }
        }
        return selected;
    }

    public double getMaxPrice() {
        return priceSlider.getValue();
    }

    public void addFilterChangeListener(Runnable listener) {
        // Add listener to all checkboxes
        for (JCheckBox cb : categoryCheckBoxes.values()) {
            cb.addActionListener(e -> listener.run());
        }
        for (JCheckBox cb : brandCheckBoxes.values()) {
            cb.addActionListener(e -> listener.run());
        }
        priceSlider.addChangeListener(e -> {
            if (!priceSlider.getValueIsAdjusting()) {
                listener.run();
            }
        });
    }

    public void addClearAllListener(Runnable listener) {
        clearAllBtn.addActionListener(e -> {
            clearAllFilters();
            listener.run();
        });
    }

    private void clearAllFilters() {
        for (JCheckBox cb : categoryCheckBoxes.values()) {
            cb.setSelected(false);
        }
        for (JCheckBox cb : brandCheckBoxes.values()) {
            cb.setSelected(false);
        }
        priceSlider.setValue(10000);
    }
}
