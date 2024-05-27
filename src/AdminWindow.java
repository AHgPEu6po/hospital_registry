import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminWindow extends JFrame {

    private final JButton doctorsButton;
    private final JButton addDoctorButton;
    private final JButton patientsButton;
    private final JButton addPatientButton;
    private final JButton changeHelloButton;
    private final JButton changePasswordButton;
    public AdminWindow() {
        setTitle("Адміністратор");
        setSize(400, 300);
        setLayout(new GridLayout(6, 1));

        this.doctorsButton = new JButton("Переглянути список лікарів");
        this.addDoctorButton = new JButton("Додати лікаря");
        this.patientsButton = new JButton("Переглянути список пацієнтів");
        this.addPatientButton = new JButton("Зареєструвати пацієнта");
        this.changeHelloButton = new JButton("Змінити вітальне вікно");
        this.changePasswordButton = new JButton("Змінити пароль");


        add(doctorsButton);
        add(addDoctorButton);
        add(patientsButton);
        add(addPatientButton);
        add(changeHelloButton);
        add(changePasswordButton);

        setLocationRelativeTo(null);
        setVisible(true);
    }


    public JButton getDoctorListButton() {
        return doctorsButton;
    }

    public JButton getAddDoctorButton() {
        return addDoctorButton;
    }

    public JButton getPatientListButton() {
        return patientsButton;
    }

    public JButton getAddPatientButton() {
        return addPatientButton;
    }

    public JButton getChangeHelloButton() {
        return changeHelloButton;
    }

    public JButton getChangePasswordButton() {
        return changePasswordButton;
    }
}
