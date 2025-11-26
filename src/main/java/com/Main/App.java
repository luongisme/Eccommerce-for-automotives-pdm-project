package com.Main;

import javax.swing.SwingUtilities;

import com.UI.defaultpage.AutoPartsHomePage;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AppFrame frame = new AppFrame();
            frame.setScreen(new AutoPartsHomePage(frame));
        });
    }
}
