package com.service;

import javax.swing.table.DefaultTableModel;

/**
 * Service class responsible for preparing and mutating the product table model
 * used in the admin products panel. UI classes should not hardcode table data
 * or business rules; instead they should call this service.
 */
public class AdminProductService {

    public DefaultTableModel createProductTableModel() {
        String[] columns = {"Product", "Category", "Price", "Stock", "Status", "Actions"};
        Object[][] data = {
                {"Air Filter", "Engine", "$18.99", 200, "Active", ""},
                {"Premium Oil Filter", "Engine", "$24.99", 150, "Active", ""},
                {"LED Headlight Bulbs", "Electrical", "$79.99", 90, "Active", ""},
                {"All-Season Tire 225/60R16", "Wheels & Tires", "$129.99", 80, "Active", ""},
                {"Spark Plug Set (4-Pack)", "Engine", "$45.99", 75, "Active", ""},
                {"Brake Pad Set - Front", "Brakes", "$89.99", 60, "Active", ""},
                {"Brake Rotor - Front", "Brakes", "$125.99", 45, "Active", ""},
                {"Shock Absorber - Front", "Suspension", "$75.99", 40, "Active", ""},
                {"Car Battery 12V", "Electrical", "$149.99", 30, "Active", ""},
                {"Alloy Wheel 16\"", "Wheels & Tires", "$189.99", 25, "Active", ""}
        };

        return new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Only the Actions column is editable (for the buttons)
                return column == 5;
            }
        };
    }

    public void toggleStatus(DefaultTableModel model, int row) {
        if (row < 0 || row >= model.getRowCount()) return;
        Object current = model.getValueAt(row, 4);
        String status = current != null ? current.toString() : "";
        if ("Active".equalsIgnoreCase(status)) {
            model.setValueAt("Inactive", row, 4);
        } else {
            model.setValueAt("Active", row, 4);
        }
    }

    public void deleteRow(DefaultTableModel model, int row) {
        if (row >= 0 && row < model.getRowCount()) {
            model.removeRow(row);
        }
    }
}
