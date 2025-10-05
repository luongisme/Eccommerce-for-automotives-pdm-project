package com.Main;

import javax.swing.SwingUtilities;
import com.UI.login.LoginScreen;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AppFrame frame = new AppFrame();
            frame.setScreen(new LoginScreen(frame));
        });
    }
}
