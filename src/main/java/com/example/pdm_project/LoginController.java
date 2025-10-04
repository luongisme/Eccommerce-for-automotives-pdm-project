package com.example.pdm_project;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;

    @FXML
    private void onSubmit() {
        String email = emailField.getText().trim();
        String pass  = passwordField.getText();

        if (email.isEmpty() || pass.isEmpty()) {
            alert("Please fill in both email and password.");
            return;
        }
        // Demo: kiểm tra giả lập
        if (email.equalsIgnoreCase("user@example.com") && pass.equals("123456")) {
            alert("Login successful! 🎉");
            // TODO: chuyển trang (Scene) qua trang chính của shop
        } else {
            alert("Invalid credentials. Try again.");
        }
    }

    private void alert(String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg).showAndWait();
    }
}
