package com.Main;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ScreenManager extends JPanel {
    private final CardLayout cards = new CardLayout();
    private final Map<String, JComponent> registry = new HashMap<>();

    public ScreenManager() {
        setLayout(cards);
    }

    public void register(String id, JComponent view) {
        registry.put(id, view);
        add(view, id);
    }

    public void show(String id) {
        cards.show(this, id);
        revalidate();
        repaint();
    }

    public JComponent get(String id) {
        return registry.get(id);
    }
}
