package com.Main;

import javax.swing.SwingUtilities;

import com.UI.store.StoreScreen;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AppFrame frame = new AppFrame();
            // Start with Store screen (guest mode)
            frame.setScreen(new StoreScreen(frame));
        });
    }
}
