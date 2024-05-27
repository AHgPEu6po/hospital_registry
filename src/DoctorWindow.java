import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class DoctorWindow extends JFrame {
    private final JButton dataButton;
    private final JButton timetableButton;
    private final JButton changePasswordButton;
    public DoctorWindow(String doctorName) {

        setTitle("Вікно лікаря " + doctorName);
        setSize(400, 200);
        setLayout(new GridLayout(3, 1));

        this.dataButton = new JButton("Переглянути особисті дані");
        this.timetableButton = new JButton("Переглянути свій розклад");
        this.changePasswordButton = new JButton("Змінити пароль");

        add(dataButton);
        add(timetableButton);
        add(changePasswordButton);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public JButton getDataButton() {
        return dataButton;
    }

    public JButton getTimetableButton() {
        return timetableButton;
    }

    public JButton getChangePasswordButton() {
        return changePasswordButton;
    }
}
