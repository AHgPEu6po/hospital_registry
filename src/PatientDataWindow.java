import javax.swing.*;
import java.awt.*;

public class PatientDataWindow extends JFrame {

    public PatientDataWindow(Patient patient) {
        setTitle("Особиста інформація про пацієнта");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout());

        JTextArea patientDataTextArea = new JTextArea();
        patientDataTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(patientDataTextArea);
        add(scrollPane, BorderLayout.CENTER);

        String patientData = "Призвіще: " + patient.getSurname() + "\n" +
                "Ім'я: " + patient.getName() + "\n" +
                "По батькові: " + patient.getFathername() + "\n" +
                "Стать: " + patient.getGender() + "\n" +
                "Дата народження: " + patient.getBirthday() + "\n" +
                "Ідентифікатор: " + patient.getId() + "\n";

        patientDataTextArea.setText(patientData);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
