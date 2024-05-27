import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PasswordDoctor extends JDialog {
    public static String ADRESSA;
    public static String PASSWORD;

    public PasswordDoctor(JFrame parentWindow, boolean showDialog) {
        if (showDialog) {
            setTitle("Підтвердження");
            setSize(300, 120);
            setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            setLayout(new GridLayout(3,2));
            setModal(true);

            JLabel loginLabel = new JLabel("Введіть логін:");
            JTextField loginField = new JTextField(10);
            JLabel passwordLabel = new JLabel("Введіть пароль:");
            JPasswordField passwordField = new JPasswordField(10);
            JButton submitButton = new JButton("Увійти");
            JButton adminButton = new JButton("Адміністратор");

            submitButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String enteredPassword = new String(passwordField.getPassword());
                    String enteredLogin = loginField.getText();
                    ADRESSA = "src/resources/doctors/" + enteredLogin + "/password.txt";
                    readPasswordFromFile();
                    if (PASSWORD.equals(enteredPassword)) {
                        JOptionPane.showMessageDialog(null, "Вхід успішний");
                        dispose();
                        parentWindow.dispose();
                        new DoctorController(enteredLogin, true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Не правильний пароль", "Помилка", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            adminButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    new PasswordAdmin(parentWindow, true);
                }
            });

            add(loginLabel);
            add(loginField);
            add(passwordLabel);
            add(passwordField);
            add(submitButton);
            add(adminButton);

            setLocationRelativeTo(null);
            setVisible(true);
        }
    }

    void readPasswordFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ADRESSA))) {
            PASSWORD = reader.readLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Не правильний логін", "Помилка", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}

