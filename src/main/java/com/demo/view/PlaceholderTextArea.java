package com.demo.view;

import javax.swing.*;
import java.awt.*;

public class PlaceholderTextArea extends JTextArea {

    private String placeholder;

    public PlaceholderTextArea(String placeholder) {
        this.placeholder = placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
        repaint();
    }

    public String getPlaceholder() {
        return placeholder;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (placeholder == null || placeholder.isBlank()) {
            return;
        }

        if (!getText().isEmpty()) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(Color.GRAY);
        g2.setFont(getFont().deriveFont(Font.ITALIC));

        Insets insets = getInsets();
        FontMetrics fm = g2.getFontMetrics();

        int x = insets.left;
        int y = insets.top + fm.getAscent();

        g2.drawString(placeholder, x, y);
        g2.dispose();
    }
}
