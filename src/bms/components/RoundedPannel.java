package bms.components;
import javax.swing.*;
import java.awt.*;

public class RoundedPannel extends JPanel {
    private final Color backgroundColor;
    private int cornerRadius = 30;

    public RoundedPannel(int radius, Color bgColor) {
        super();
        cornerRadius = radius;
        backgroundColor = bgColor;
        setOpaque(false); // Make sure the panel is non-opaque for smooth corners
        setLayout(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Enable anti-aliasing for smooth edges
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Paint rounded background
        g2.setColor(backgroundColor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

        g2.dispose();
    }
}
