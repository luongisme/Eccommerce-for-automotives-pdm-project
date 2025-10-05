package com.UI.login;

import com.Main.AppFrame;
import com.Main.Screen;

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

    public LoginScreen(AppFrame appFrame) {
        super(appFrame);
        panel = new BackgroundPanel("/images/login_bg.jpg");
        panel.setLayout(null);
        initUI();
    }

    @Override
    protected void initUI() {
        JLabel title = new JLabel("Login");
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setForeground(Color.BLACK);
        title.setBounds(170, 40, 120, 30);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(Color.BLACK);
        emailLabel.setFont(new Font("Arial", Font.BOLD, 16));
        emailLabel.setBounds(60, 130, 100, 22);

        emailField = new JTextField();
        emailField.setBounds(150, 125, 200, 32);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(Color.BLACK);
        passLabel.setFont(new Font("Arial", Font.BOLD, 16));
        passLabel.setBounds(60, 180, 100, 22);

        passField = new JPasswordField();
        passField.setBounds(150, 175, 200, 32);

        submitBtn = new JButton("Login");
        submitBtn.setBounds(150, 235, 200, 36);

        errorLabel = new JLabel("", SwingConstants.CENTER);
        errorLabel.setForeground(Color.PINK);
        errorLabel.setBounds(50, 290, 320, 25);

        // Overlay panel mờ
        JPanel glass = new JPanel(null);
        glass.setOpaque(true);
        glass.setBackground(new Color(0, 0, 0, 90));
        glass.setBounds(30, 100, 360, 230);
        glass.add(emailLabel);
        glass.add(emailField);
        glass.add(passLabel);
        glass.add(passField);
        glass.add(submitBtn);
        glass.add(errorLabel);

        panel.add(title);
        panel.add(glass);
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
