import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class AddDoctorWindow extends JDialog {

    public AddDoctorWindow() {

        setTitle("Новий лікар");
        setSize(330, 220);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(9, 2));
        setModal(true);

        JLabel surnameLabel = new JLabel("Введіть призвіще:");
        JTextField surnameField = new JTextField(10);
        JLabel nameLabel = new JLabel("Введіть ім'я:");
        JTextField nameField = new JTextField(10);
        JLabel fathernameLabel = new JLabel("Введіть по батькові:");
        JTextField fathernameField = new JTextField(10);
        JLabel genderLabel = new JLabel("Виберіть стать:");
        JComboBox<String> genderComboBox = new JComboBox<>(new String[]{"Чоловіча", "Жіноча"});
        JLabel jobLabel = new JLabel("Введіть спеціальність:");
        JTextField jobField = new JTextField(10);
        JLabel shiftLabel = new JLabel("Виберіть зміну:");
        JComboBox<String> shiftComboBox = new JComboBox<>(new String[]{"Перша", "Друга"});
        JLabel passwordLabel = new JLabel("Введіть пароль:");
        JTextField passwordField = new JTextField(10);
        JLabel repitPasswordLabel = new JLabel("Повторіть пароль:");
        JPasswordField repitPasswordField = new JPasswordField(10);
        JButton registerButton = new JButton("Додати");

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String surname = surnameField.getText();
                String name = nameField.getText();
                String fathername = fathernameField.getText();
                String gender = (String) genderComboBox.getSelectedItem();
                String job = jobField.getText();
                String shift = (String) shiftComboBox.getSelectedItem();
                String password = passwordField.getText();
                String repitPassword = new String(repitPasswordField.getPassword());

                if (password.length() < 8) {
                    JOptionPane.showMessageDialog(null, "Пароль повинен містити не менше 8 символів", "Помилка", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!password.equals(repitPassword)) {
                    JOptionPane.showMessageDialog(null, "Паролі не співпадають", "Помилка", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                File doctorDirectory = new File("src/resources/doctors/" + surname + name);
                if (!doctorDirectory.exists()) {
                    doctorDirectory.mkdirs();
                }

                try (PrintWriter writer = new PrintWriter(new File(doctorDirectory, "data.txt"))) {
                    writer.println(surname);
                    writer.println(name);
                    writer.println(fathername);
                    writer.println(gender);
                    writer.println(job);
                    writer.println(shift);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                File timetableDirectory = new File("src/resources/doctors/" + surname + name + "/timetable");
                timetableDirectory.mkdirs();
                String[] days = {"Понеділок", "Вівторок", "Середа", "Четвер", "П'ятниця", "Субота"};
                String[] hours;
                if (shift.equals("Перша")) {
                    hours = new String[]{"8:00", "8:20", "8:40", "9:00", "9:20", "9:40", "10:00", "10:20", "10:40", "11:00", "11:20", "11:40", "12:00", "12:20", "12:40", "13:00", "13:20", "13:40"};
                } else {
                    hours = new String[]{"14:00", "14:20", "14:40", "15:00", "15:20", "15:40", "16:00", "16:20", "16:40", "17:00", "17:20", "17:40", "18:00", "18:20", "18:40", "19:00", "19:20", "19:40"};
                }
                for (String day : days) {
                    try (PrintWriter writer = new PrintWriter(new File(timetableDirectory, day + ".txt"))) {
                        for (String hour : hours) {
                            writer.println(hour + " Запис відсутній");
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

                try (PrintWriter writer = new PrintWriter(new File(doctorDirectory, "password.txt"))) {
                    writer.println(password);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                JOptionPane.showMessageDialog(null, "Лікаря додано успішно.");
                dispose();
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
        add(jobLabel);
        add(jobField);
        add(shiftLabel);
        add(shiftComboBox);
        add(passwordLabel);
        add(passwordField);
        add(repitPasswordLabel);
        add(repitPasswordField);
        add(new JLabel(""));
        add(registerButton);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
