import javax.swing.*;
import java.awt.*;

public class PatientWindow extends JFrame {
    private final JButton dataButton;
    private final JButton doctorsButton;
    private final JButton appointmentsButton;
    private final JButton historyButton;
    private final JButton changePasswordButton;
    public PatientWindow(String patientName) {

        setTitle("Вікно пацієнта " + patientName);
        setSize(400, 300);
        setLayout(new GridLayout(5, 1));

        this.dataButton = new JButton("Переглянути особисті дані");
        this.doctorsButton = new JButton("Записатися на прийом до лікаря");
        this.appointmentsButton = new JButton("Переглянути записи");
        this.historyButton = new JButton("Історія хвороби");
        this.changePasswordButton = new JButton("Змінити пароль");

        add(dataButton);
        add(doctorsButton);
        add(appointmentsButton);
        add(historyButton);
        add(changePasswordButton);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public JButton getDataButton() {
        return dataButton;
    }

    public JButton getDoctorListButton() {
        return doctorsButton;
    }

    public JButton getAppointmentsButton() {
        return appointmentsButton;
    }

    public JButton getHistoryButton() {
        return historyButton;
    }

    public JButton getChangePasswordButton() {
        return changePasswordButton;
    }
}
