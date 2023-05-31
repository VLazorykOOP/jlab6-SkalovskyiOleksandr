import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class RotatingLineApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(RotatingLineApp::start);
    }

    public static void start() {
        JFrame frame = new JFrame("Rotating Line");

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2d = (Graphics2D) g;

                int width = getWidth();
                int height = getHeight();

                int startX = width / 4;
                int startY = height / 2;
                int endX = startX * 3;
                int endY = height / 2;

                int angle = (int) ((System.currentTimeMillis() / 10) % 360); // Змінюємо кут повороту з часом

                double radianAngle = Math.toRadians(angle);
                double sinAngle = Math.sin(radianAngle);

                // Змінюємо кольору відрізка залежно від кута повороту
                Color lineColor = Color.getHSBColor(angle / 360f, 1f, 1f);
                g2d.setColor(lineColor);

                // Повертаємо координати кінця відрізка
                double rotatedEndX = startX + (endX - startX) * sinAngle;
                double rotatedEndY = startY + (endY - startY) * sinAngle;

                // Об'єкт Ellipse2D для відрізка
                double circleSize = 40; // Розмір кола
                double circleX = rotatedEndX - circleSize / 2;
                double circleY = rotatedEndY - circleSize / 2;
                Ellipse2D ellipse = new Ellipse2D.Double(circleX, circleY, circleSize, circleSize);

                g2d.draw(ellipse);
            }
        };

        frame.add(panel);

        frame.setSize(400, 300);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
