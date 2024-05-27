import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;

public class AddPatientWindow extends JDialog {

    public AddPatientWindow() {

        setTitle("Реєстрація");
        setSize(330, 210);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(8,2));
        setModal(true);

        JLabel surnameLabel = new JLabel("Введіть призвіще:");
        JTextField surnameField = new JTextField(10);
        JLabel nameLabel = new JLabel("Введіть ім'я:");
        JTextField nameField = new JTextField(10);
        JLabel fathernameLabel = new JLabel("Введіть по батькові:");
        JTextField fathernameField = new JTextField(10);
        JLabel genderLabel = new JLabel("Виберіть стать:");
        JComboBox<String> genderComboBox = new JComboBox<>(new String[]{"Чоловіча", "Жіноча"});
        JLabel birthdayLabel = new JLabel("Введіть дату народження:");
        JTextField birthdayField = new JTextField(10);
        JLabel passwordLabel = new JLabel("Введіть пароль:");
        JTextField passwordField = new JTextField(10);
        JLabel repitPasswordLabel = new JLabel("Повторіть пароль:");
        JPasswordField repitPasswordField = new JPasswordField(10);
        JButton registerButton = new JButton("Створити акаунт");


        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String surname = surnameField.getText();
                String name = nameField.getText();
                String fathername = fathernameField.getText();
                String gender = Objects.requireNonNull(genderComboBox.getSelectedItem()).toString();
                String birthday = birthdayField.getText();
                String password = passwordField.getText();
                String repitPassword = new String(repitPasswordField.getPassword());

                if (!isValidDate(birthday)) {
                    JOptionPane.showMessageDialog(null, "Неправильний формат дати народження. Введіть у форматі: DD.MM.YYYY", "Помилка", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (password.length() < 8) {
                    JOptionPane.showMessageDialog(null, "Пароль повинен містити не менше 8 символів", "Помилка", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!password.equals(repitPassword)) {
                    JOptionPane.showMessageDialog(null, "Паролі не співпадають", "Помилка", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String folderPath = "src/resources/patients/" + surname + name;
                try {
                    File folder = new File(folderPath);
                    if (!folder.exists()) {
                        folder.mkdirs();
                    }
                    FileWriter passwordFile = new FileWriter(folderPath + "/password.txt");
                    passwordFile.write(password);
                    passwordFile.close();

                    FileWriter identificatorFile = new FileWriter(folderPath + "/identificator.txt");
                    identificatorFile.write(birthday.replaceAll("\\.", ""));
                    identificatorFile.close();

                    File history = new File(folderPath + "/history.txt");
                    history.createNewFile();

                    File appointment = new File(folderPath + "/appointments.txt");
                    appointment.createNewFile();

                    try (PrintWriter writer = new PrintWriter(new File(folderPath, "data.txt"))) {
                        writer.println(surname);
                        writer.println(name);
                        writer.println(fathername);
                        writer.println(gender);
                        writer.println(birthday);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    JOptionPane.showMessageDialog(null, "Акаунт успішно створено");
                    dispose();
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Помилка під час створення акаунту", "Помилка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(surnameLabel);
        add(surnameField);
        add(nameLabel);
        add(nameField);
        add(fathernameLabel);
        add(fathernameField);
        add(genderLabel);
        add(genderComboBox);
        add(birthdayLabel);
        add(birthdayField);
        add(passwordLabel);
        add(passwordField);
        add(repitPasswordLabel);
        add(repitPasswordField);
        add(new JLabel(""));
        add(registerButton);

        setLocationRelativeTo(null);
        setVisible(true);


    }

    private boolean isValidDate(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            sdf.setLenient(false);
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
