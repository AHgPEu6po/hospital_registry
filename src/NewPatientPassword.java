import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class NewPatientPassword extends JDialog {

    public NewPatientPassword() {

        setTitle("Зміна паролю");
        setSize(330, 150);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(6,2));
        setModal(true);

        JLabel surnameLabel = new JLabel("Введіть призвіще:");
        JTextField surnameField = new JTextField(10);
        JLabel nameLabel = new JLabel("Введіть ім'я:");
        JTextField nameField = new JTextField(10);
        JLabel identifityLabel = new JLabel("Введіть ідентифікатор:");
        JTextField identifityField = new JTextField(10);
        JLabel passwordLabel = new JLabel("Введіть новий пароль:");
        JTextField passwordField = new JTextField(10);
        JLabel repitPasswordLabel = new JLabel("Повторіть пароль:");
        JPasswordField repitPasswordField = new JPasswordField(10);
        JButton changeButton = new JButton("Застосувати");


        changeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String surname = surnameField.getText();
                String name = nameField.getText();
                String identifity = identifityField.getText();
                String newPassword = passwordField.getText();
                String repitPassword = new String(repitPasswordField.getPassword());

                String folderPath = "src/resources/patients/" + surname + name;
                File passwordFile = new File(folderPath + "/identificator.txt");
                if (!passwordFile.exists()) {
                    JOptionPane.showMessageDialog(null, "Користувача не знайдено", "Помилка", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (newPassword.length() < 8) {
                    JOptionPane.showMessageDialog(null, "Пароль повинен містити не менше 8 символів", "Помилка", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!newPassword.equals(repitPassword)) {
                    JOptionPane.showMessageDialog(null, "Паролі не співпадають", "Помилка", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    BufferedReader reader = new BufferedReader(new FileReader(passwordFile));
                    String identificator = reader.readLine();
                    reader.close();
                    if (!identificator.equals(identifity)) {
                        JOptionPane.showMessageDialog(null, "Неправильний ідентифікатор", "Помилка", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Помилка під час перевірки ідентифікатора", "Помилка", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                File newPasswordFile = new File(folderPath + "/password.txt");
                try {
                    FileWriter writer = new FileWriter(newPasswordFile);
                    writer.write(newPassword);
                    writer.close();
                    JOptionPane.showMessageDialog(null, "Пароль успішно змінено");
                    dispose();
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Помилка під час зміни паролю", "Помилка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(surnameLabel);
        add(surnameField);
        add(nameLabel);
        add(nameField);
        add(identifityLabel);
        add(identifityField);
        add(passwordLabel);
        add(passwordField);
        add(repitPasswordLabel);
        add(repitPasswordField);
        add(new JLabel(""));
        add(changeButton);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
