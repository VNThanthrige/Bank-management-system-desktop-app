package bms.components;

import javax.swing.*;
import java.awt.*;

public class RoundPasswordField extends JPasswordField {

    private final int cornerRadius = 40;
    private final String placeholder;

    public RoundPasswordField(String placeholderText, int columns) {
        super(columns);
        this.placeholder = placeholderText;

        setOpaque(false);
        setForeground(Color.WHITE);
        setFont(new Font("Segoe UI", Font.PLAIN, 16));
        setCaretColor(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        // Antialiasing
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Background fill
        g2.setColor(new Color(180, 180, 180)); // #333
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

        // Draw placeholder if empty and not focused
        if (new String(getPassword()).isEmpty() && !isFocusOwner()) {
            g2.setColor(new Color(51,51,51)); // light gray
            g2.setFont(getFont());
            g2.drawString(placeholder, 15, getHeight() / 2 + getFont().getSize() / 2 - 4);
        }

        super.paintComponent(g);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        // Optional: No border
    }
}
