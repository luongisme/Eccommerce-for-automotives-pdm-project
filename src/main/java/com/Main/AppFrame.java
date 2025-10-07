package com.Main;

import javax.swing.*;

public class AppFrame extends JFrame {
    private Screen currentScreen;

    public AppFrame() {
        setTitle("AutoParts Pro - E-Commerce Platform");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024, 1200);
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
