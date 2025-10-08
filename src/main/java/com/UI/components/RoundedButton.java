package com.UI.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

/**
 * A custom JButton with rounded corners and hover effects
 */
public class RoundedButton extends JButton {
    private int cornerRadius;
    private Color defaultColor;
    private Color hoverColor;
    private Color pressedColor;
    private boolean isHovered = false;
    private boolean isPressed = false;

    public RoundedButton(String text, int cornerRadius) {
        super(text);
        this.cornerRadius = cornerRadius;
        this.defaultColor = new Color(45, 45, 45);
        this.hoverColor = new Color(30, 30, 30);
        this.pressedColor = new Color(20, 20, 20);
        
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovered = true;
                repaint();
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                isHovered = false;
                isPressed = false;
                repaint();
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                isPressed = true;
                repaint();
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                isPressed = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Determine button color based on state
        Color bgColor = defaultColor;
        if (!isEnabled()) {
            bgColor = new Color(150, 150, 150);
        } else if (isPressed) {
            bgColor = pressedColor;
        } else if (isHovered) {
            bgColor = hoverColor;
        }

        // Draw button background with gradient
        GradientPaint gradient = new GradientPaint(
            0, 0, bgColor,
            0, getHeight(), bgColor.darker()
        );
        g2.setPaint(gradient);
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius));

        g2.dispose();

        // Draw text
        super.paintComponent(g);
    }

    public void setDefaultColor(Color color) {
        this.defaultColor = color;
        this.hoverColor = color.darker();
        this.pressedColor = color.darker().darker();
        repaint();
    }
    
    public void setColors(Color defaultColor, Color hoverColor, Color pressedColor) {
        this.defaultColor = defaultColor;
        this.hoverColor = hoverColor;
        this.pressedColor = pressedColor;
        repaint();
    }
}
