package com.Main;

import javax.swing.*;

public class AppFrame extends JFrame {
    private Screen currentScreen;

    public AppFrame() {
        setTitle("E-Commerce Platform - Automotive");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 800);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public void setScreen(Screen screen) {
        this.currentScreen = screen;
        setContentPane(currentScreen.getPanel());
        revalidate();
        repaint();
    }
}
