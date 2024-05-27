import javax.swing.*;
import java.awt.*;

public class DoctorDataWindow extends JFrame {

    public DoctorDataWindow(Doctor doctor) {
        setTitle("Особиста інформація про лікаря");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout());

        JTextArea doctorDataTextArea = new JTextArea();
        doctorDataTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(doctorDataTextArea);
        add(scrollPane, BorderLayout.CENTER);

        String doctorData = "Призвіще: " + doctor.getSurname() + "\n" +
                "Ім'я: " + doctor.getName() + "\n" +
                "По батькові: " + doctor.getFathername() + "\n" +
                "Стать: " + doctor.getGender() + "\n" +
                "Спеціальність: " + doctor.getJob() + "\n" +
                "Зміна: " + doctor.getShift() + "\n";

        doctorDataTextArea.setText(doctorData);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
