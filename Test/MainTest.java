import org.junit.Test;
import javax.swing.*;

import java.awt.*;

import static org.junit.Assert.*;

public class MainTest {

    @Test
    public void testInitializeUI() {
        Main main = new Main();
        main.initializeUI();

        JFrame frame = findFrame("Реєстратура лікарні");
        assertNotNull(frame);

        JButton patientButton = findButton(frame, "Увійти як пацієнт");
        assertNotNull(patientButton);

        JButton doctorButton = findButton(frame, "Увійти як лікар");
        assertNotNull(doctorButton);
    }

    private JFrame findFrame(String title) {
        Frame[] frames = Frame.getFrames();
        for (Frame frame : frames) {
            if (frame instanceof JFrame) {
                JFrame jFrame = (JFrame) frame;
                if (title.equals(jFrame.getTitle())) {
                    return jFrame;
                }
            }
        }
        return null;
    }

    private JButton findButton(JFrame frame, String buttonText) {
        for (Component component : frame.getContentPane().getComponents()) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                if (buttonText.equals(button.getText())) {
                    return button;
                }
            }
        }
        return null;
    }
}
