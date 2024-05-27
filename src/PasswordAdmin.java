import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PasswordAdmin extends JDialog {
    public static String PASSWORD;

    public PasswordAdmin(JFrame parentWindow, boolean showDialog) {

        if (showDialog) {
            setTitle("Підтвердження");
            setSize(300, 90);
            setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            setLayout(new GridLayout(2,2));
            setModal(true);

            JLabel passwordLabel = new JLabel("Введіть пароль:");
            JPasswordField passwordField = new JPasswordField(10);
            JButton submitButton = new JButton("Увійти");

            submitButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String enteredPassword = new String(passwordField.getPassword());
                    readPasswordFromFile();
                    if (PASSWORD.equals(enteredPassword)) {
                        String hello = readHelloFromFile();
                        JOptionPane.showMessageDialog(null, hello);
                        dispose();
                        parentWindow.dispose();
                        new AdminController();
                    } else {
                        JOptionPane.showMessageDialog(null, "Не правильний пароль", "Помилка", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            add(passwordLabel);
            add(passwordField);
            add(new JLabel(""));
            add(submitButton);

            setLocationRelativeTo(null);
            setVisible(true);
        }
    }

    void readPasswordFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/resources/admin/password.txt"))) {
            PASSWORD = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String readHelloFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/resources/admin/hello.txt"))) {
            return reader.readLine();
        } catch (IOException e) {
            return null;
        }
    }
}
