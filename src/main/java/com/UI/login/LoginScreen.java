package com.UI.login;

import com.Main.AppFrame;
import com.Main.Screen;
import com.liferay.document.library.kernel.store.Store;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

class BackgroundPanel extends JPanel {
    private final Image backgroundImage;

    public BackgroundPanel(String resourcePath) {
        setLayout(null);
        Image img = null;
        try {
            URL url = getClass().getResource(resourcePath);
            if (url != null) {
                img = new ImageIcon(url).getImage();
            }
        } catch (Exception ignored) {}
        backgroundImage = img;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}

public class LoginScreen extends Screen {

    // UI components
    private JTextField emailField;
    private JPasswordField passField;
    private JButton submitBtn;
    private JLabel errorLabel;

    public LoginScreen (AppFrame appFrame) {
        super(appFrame);
        panel = new BackgroundPanel("/images/login_bg.jpg");
        panel.setLayout(null);
        initUI();
        Runnable onSuccess = () -> appFrame.setScreen(new com.UI.store.StoreScreen(appFrame));
        new LoginController(this, onSuccess);
    }

        @Override
    protected void initUI() {
        int frameW = 1024;  // match your AppFrame size
        int brandH = 62;

        JPanel brandBar = new JPanel(null);
        brandBar.setOpaque(true);
        brandBar.setBackground(new Color(240, 240, 240)); // a light gray white
        brandBar.setBounds(0, 0, frameW, brandH);

        JLabel brand = new JLabel("AutoParts Pro");
        brand.setForeground(Color.BLACK);
        brand.setFont(new Font("Arial", Font.BOLD, 32));
        brand.setBounds(18, 14, 300, 28);
        brandBar.add(brand);

        panel.add(brandBar);

        // ===== Tiêu đề =====
        JLabel title = new JLabel("Welcome Back");
        title.setFont(new Font("Arial", Font.BOLD, 80));
        title.setForeground(Color.BLACK);
        title.setBounds(240, 90, 700, 100);
        panel.add(title);

        JPanel formPanel = new JPanel(null);
        formPanel.setOpaque(false);
        formPanel.setBounds(320, 200, 380, 250);

        // Email label
        JLabel emailLabel = new JLabel("Email address");
        emailLabel.setForeground(Color.BLACK);
        emailLabel.setFont(new Font("Arial", Font.BOLD, 20));
        emailLabel.setBounds(0, 0, 200, 25);
        formPanel.add(emailLabel);

        // Email field
        emailField = new JTextField("user123@gmail.com");
        emailField.setFont(new Font("Arial", Font.PLAIN, 14));
        emailField.setBounds(0, 30, 380, 36);
        emailField.setBackground(Color.WHITE);
        emailField.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(200, 200, 200)),
        BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        formPanel.add(emailField);

        // Password label
        JLabel passLabel = new JLabel("Password");
        passLabel.setForeground(Color.BLACK);
        passLabel.setFont(new Font("Arial", Font.BOLD, 20));
        passLabel.setBounds(0, 80, 200, 25);
        formPanel.add(passLabel);

        // Password field
        passField = new JPasswordField("Password123");
        passField.setFont(new Font("Arial", Font.PLAIN, 14));
        passField.setBounds(0, 110, 380, 36);
        passField.setBackground(Color.WHITE);
        passField.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(200, 200, 200)),
        BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        formPanel.add(passField);

        // Submit button
        submitBtn = new JButton("Login");
        submitBtn.setBounds(0, 165, 380, 40);
        submitBtn.setBackground(Color.BLACK);
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setFont(new Font("Arial", Font.BOLD, 20));
        submitBtn.setFocusPainted(false);
        submitBtn.setBorder(BorderFactory.createEmptyBorder());
        formPanel.add(submitBtn);

        // Error label (ẩn dưới button)
        errorLabel = new JLabel("", SwingConstants.CENTER);
        errorLabel.setForeground(Color.PINK);
        errorLabel.setBounds(0, 210, 380, 25);
        formPanel.add(errorLabel);

        // ===== Thêm tất cả vào panel chính =====
        panel.add(formPanel);
    }
    // === Getter cho Controller ===
    public JButton getSubmitButton() {
        return submitBtn;
    }

    public String getEmail() {
        return emailField.getText().trim();
    }

    public String getPassword() {
        return new String(passField.getPassword());
    }

    public void setError(String msg) {
        errorLabel.setText(msg);
    }

    public void clearError() {
        errorLabel.setText("");
    }
}
