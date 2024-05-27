import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Objects;

public class DoctorTimetableWindow extends JFrame {
    private final JButton[][] scheduleButtons = new JButton[18][6];
    private final Doctor doctor;
    private final String role;
    private static Patient patient;

    public DoctorTimetableWindow(Doctor doctor, String role, String username) {
        this.doctor = doctor;
        this.role = role;
        if (role.equals("patient")) {
            PatientController patientController = new PatientController(username, false);
            patient = patientController.getPatient();
        }
        setTitle("Розклад лікаря: " + doctor.getSurname() + " " + doctor.getName() + " " + doctor.getFathername());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(19, 6));
        setSize(1000, 500);

        String[] daysOfWeek = {"Понеділок", "Вівторок", "Середа", "Четвер", "П'ятниця", "Субота"};
        for (String day : daysOfWeek) {
            add(new JLabel(day, SwingConstants.CENTER));
        }

        for (int row = 0; row < 18; row++) {
            for (int col = 0; col < 6; col++) {
                JButton button = new JButton();
                scheduleButtons[row][col] = button;
                add(button);
            }
        }

        loadDoctorTimetable();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadDoctorTimetable() {
        String timetableDirectoryPath = "src/resources/doctors/" + doctor.getSurname() + doctor.getName() + "/timetable";
        String[] daysOfWeek = {"Понеділок", "Вівторок", "Середа", "Четвер", "П'ятниця", "Субота"};

        for (int dayIndex = 0; dayIndex < 6; dayIndex++) {
            String patientName;
            if(Objects.equals(role, "patient")){
                patientName = patient.getSurname() + " " + patient.getName() + " " + patient.getFathername();
            } else {
                patientName = "null";
            }
            String day = daysOfWeek[dayIndex];
            String filePath = timetableDirectoryPath + File.separator + day + ".txt";
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                for (int row = 0; row < 18; row++) {
                    String line = reader.readLine();
                    JButton button = scheduleButtons[row][dayIndex];
                    button.setText(line.substring(0, 5));
                    String text = line.substring(5);
                    if (!text.contains("Запис відсутній")) {
                        button.setBackground(Color.CYAN);
                        if(Objects.equals(role, "patient")){
                            button.setEnabled(false);
                            if(text.contains(patientName)){
                                button.setBackground(Color.GREEN);
                            }
                        }
                    }
                    button.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String time = button.getText();
                            if(Objects.equals(role, "patient")){
                                showAddAppointment(day, time, button);
                            } else if (!text.contains("Запис відсутній")){
                                showPatientRecordInfo(day, time, text, button);
                            } else {
                                showRecordInfo(day, time, text);
                            }
                        }
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showRecordInfo(String day, String time, String text) {
        JOptionPane pane = new JOptionPane(text, JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = pane.createDialog("Інформація про запис на " + day.toLowerCase() + " о " + time);
        dialog.setSize(350, 120);
        dialog.setVisible(true);
    }

    private void showPatientRecordInfo(String day, String time, String text, JButton button) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel messageLabel = new JLabel(text);
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(messageLabel);

        String[] parts = text.split(" ");
        String patientName = parts[2] + parts[3];
        PatientController patientController = new PatientController(patientName, false);
        patient = patientController.getPatient();

        String[] options = {"Окей", "Написати заключення", "Пацієнт", "Історія хвороби"};

        int result = JOptionPane.showOptionDialog(
                null,
                panel,
                "Інформація про запис на " + day.toLowerCase() + " о " + time,
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        switch (result) {
            case 0:
                break;
            case 1:
                new WriteConclusionWindow(patient, day, time, doctor, button);
                break;
            case 2:
                new PatientDataWindow(patient).setVisible(true);
                break;
            case 3:
                new PatientHistoryWindow(patient, "doctor").setVisible(true);
                break;
        }
    }

    private void showAddAppointment(String day, String time, JButton button) {
        int choice = JOptionPane.showConfirmDialog(this,
                "Чи дійсно ви бажаєте записатись на " + day + " о " + time + "?",
                "Підтвердження запису", JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION){
            addAppointment(day, time);
            updateDoctorTimetable(day, time, button);
        }
    }

    private void addAppointment(String day, String time) {
        String appointmentFilePath = "src/resources/patients/" + patient.getSurname() + patient.getName() + "/appointments.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(appointmentFilePath, true))) {
            writer.write(day + " " + time + " " + doctor.getSurname() + " " + doctor.getName() + " " + doctor.getFathername() + " " + doctor.getJob());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateDoctorTimetable(String day, String time, JButton button) {
        String doctorTimetableFilePath = "src/resources/doctors/" + doctor.getSurname() + doctor.getName() + "/timetable/" + day + ".txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(doctorTimetableFilePath))) {
            StringBuilder newTimetable = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(time)) {
                    newTimetable.append(time).append(" Записаний ").append(patient.getSurname()).append(" ").append(patient.getName()).append(" ").append(patient.getFathername()).append("\n");
                } else {
                    newTimetable.append(line).append("\n");
                }
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(doctorTimetableFilePath))) {
                writer.write(newTimetable.toString());
                button.setBackground(Color.GREEN);
                button.setEnabled(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
