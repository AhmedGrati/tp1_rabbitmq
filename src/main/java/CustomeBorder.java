import javax.swing.border.AbstractBorder;
import java.awt.*;

@SuppressWarnings("serial")
class CustomeBorder extends AbstractBorder {
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y,
                            int width, int height) {

        super.paintBorder(c, g, x, y, width, height);
        Graphics2D g2d = (Graphics2D)g;
        g2d.setStroke(new BasicStroke(12));
        g2d.setColor(Color.blue);
        g2d.drawRoundRect(x, y, width - 1, height - 1, 25, 25);
    }
}
