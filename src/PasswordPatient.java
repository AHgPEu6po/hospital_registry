import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PasswordPatient extends JDialog {
    public static String ADRESSA;
    public static String PASSWORD;

    public PasswordPatient(JFrame parentFrame, boolean showDialog) {
        if (showDialog) {
            setTitle("Підтвердження");
            setSize(300, 150);
            setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            setLayout(new GridLayout(5,2));
            setModal(true);

            JLabel surnameLabel = new JLabel("Введіть призвіще:");
            JTextField surnameField = new JTextField(10);
            JLabel nameLabel = new JLabel("Введіть ім'я:");
            JTextField nameField = new JTextField(10);
            JLabel passwordLabel = new JLabel("Введіть пароль:");
            JPasswordField passwordField = new JPasswordField(10);
            JButton submitButton = new JButton("Увійти");
            JButton newPasswordButton = new JButton("Забув пароль");
            JButton registerButton = new JButton("Зареєструватися");


            submitButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String enteredPassword = new String(passwordField.getPassword());
                    String enteredLogin = surnameField.getText() + nameField.getText();
                    ADRESSA = "src/resources/patients/" + enteredLogin + "/password.txt";
                    readPasswordFromFile();
                    if (PASSWORD.equals(enteredPassword)) {
                        JOptionPane.showMessageDialog(null, "Вхід успішний");
                        dispose();
                        parentFrame.dispose();
                        new PatientController(enteredLogin, true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Не правильний пароль", "Помилка", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            newPasswordButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    new NewPatientPassword().setVisible(true);
                }
            });

            registerButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    new AddPatientWindow().setVisible(true);
                }
            });

            add(surnameLabel);
            add(surnameField);
            add(nameLabel);
            add(nameField);
            add(passwordLabel);
            add(passwordField);
            add(submitButton);
            add(newPasswordButton);
            add(new JLabel(""));
            add(registerButton);

            setLocationRelativeTo(null);
            setVisible(true);
        }
    }

    void readPasswordFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ADRESSA))) {
            PASSWORD = reader.readLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Не правильні дані", "Помилка", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}

