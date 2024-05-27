import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().initializeUI());
    }

    void initializeUI() {
        JFrame frame = new JFrame("Реєстратура лікарні Лохвиця");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setLayout(new GridLayout(1,2));

        JButton patientButton = createRoleButton("Увійти як пацієнт", "resources/images/patient.png");
        patientButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new PasswordPatient(frame, true);
            }
        });
        frame.add(patientButton);

        JButton doctorButton = createRoleButton("Увійти як лікар", "resources/images/doctor.png");
        doctorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new PasswordDoctor(frame, true);
            }
        });
        frame.add(doctorButton);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JButton createRoleButton(String role, String imagePath) {
        JButton button = new JButton(role);
        ImageIcon icon = createCircularIcon(imagePath);
        if (icon != null) {
            button.setIcon(icon);
        }
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        styleButton(button);
        return button;
    }

    private ImageIcon createCircularIcon(String path) {
        try {
            BufferedImage originalImage = ImageIO.read(Objects.requireNonNull(getClass().getResource(path)));
            int diameter = 150;
            BufferedImage circleImage = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = circleImage.createGraphics();
            applyQualityRenderingHints(g2d);
            g2d.setClip(new Ellipse2D.Float(0, 0, diameter, diameter));
            g2d.drawImage(originalImage, 0, 0, diameter, diameter, null);
            g2d.dispose();
            return new ImageIcon(circleImage);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void styleButton(JButton button) {
        button.setUI(new BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2d = (Graphics2D) g.create();
                applyQualityRenderingHints(g2d);
                GradientPaint gp = new GradientPaint(
                        0, 0, Color.WHITE,
                        0, c.getHeight(), Color.LIGHT_GRAY,
                        true);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, c.getWidth(), c.getHeight());
                g2d.dispose();
                super.paint(g, c);
            }
        });
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setFont(new Font("Serif", Font.BOLD, 20));
        button.setForeground(Color.DARK_GRAY);
    }

    private void applyQualityRenderingHints(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    }
}
