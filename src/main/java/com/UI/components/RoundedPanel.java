package com.UI.components;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * A custom JPanel with rounded corners and optional shadow effect
 */
public class RoundedPanel extends JPanel {
    private int cornerRadius;
    @SuppressWarnings("unused")
    private Color shadowColor;
    private boolean hasShadow;
    private int shadowSize;

    public RoundedPanel(int cornerRadius) {
        this(cornerRadius, false);
    }

    public RoundedPanel(int cornerRadius, boolean hasShadow) {
        this.cornerRadius = cornerRadius;
        this.hasShadow = hasShadow;
        this.shadowSize = hasShadow ? 8 : 0;
        this.shadowColor = new Color(0, 0, 0, 30);
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // Draw shadow if enabled
        if (hasShadow) {
            for (int i = 0; i < shadowSize; i++) {
                float opacity = (float) (shadowSize - i) / shadowSize * 0.1f;
                g2.setColor(new Color(0, 0, 0, (int) (opacity * 255)));
                g2.fill(new RoundRectangle2D.Double(
                    i, i, width - i * 2, height - i * 2, cornerRadius, cornerRadius
                ));
            }
        }

        // Draw main panel background
        g2.setColor(getBackground());
        g2.fill(new RoundRectangle2D.Double(
            shadowSize, shadowSize, 
            width - shadowSize * 2, 
            height - shadowSize * 2, 
            cornerRadius, cornerRadius
        ));

        g2.dispose();
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        // Custom border painting handled in paintComponent
    }

    public void setShadowEnabled(boolean enabled) {
        this.hasShadow = enabled;
        this.shadowSize = enabled ? 8 : 0;
        repaint();
    }
}
