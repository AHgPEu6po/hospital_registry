import javax.swing.*;
import java.awt.*;
import java.io.*;

public class WriteConclusionWindow {

    private final Doctor doctor;
    private final Patient patient;

    public WriteConclusionWindow(Patient patient, String day, String time, Doctor doctor, JButton button) {

        this.doctor = doctor;
        this.patient = patient;

        JTextField recordField = new JTextField(20);
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Новий запис:"));
        panel.add(recordField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Додати запис", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String recordText = recordField.getText();
            button.setBackground(UIManager.getColor("Button.background"));
            addRecord(recordText);
            removeAppointmentFromFile(day, time);
            updateDoctorTimetable(day, time);
        }
    }

    private void addRecord(String recordText) {
        String historyFilePath = "src/resources/patients/" + patient.getSurname() + patient.getName() + "/history.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(historyFilePath, true))) {
            writer.write("\n" + recordText);
            writer.flush();
            JOptionPane.showMessageDialog(null, "Запис успішно доданий.", "Успіх", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Помилка додавання запису.", "Помилка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeAppointmentFromFile(String day, String time) {
        String appointmentsFilePath = "src/resources/patients/" + patient.getSurname() + patient.getName() + "/appointments.txt";
        File inputFile = new File(appointmentsFilePath);
        String tempFilePath = inputFile.getAbsolutePath().replace(".txt", ".tmp");
        File tempFile = new File(tempFilePath);

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             PrintWriter writer = new PrintWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.replaceAll(" {2}", " ");
                String[] parts = line.split(" ");
                String fileDay = parts[0];
                String fileTime = parts[1];
                String fileDoctorName = parts[2] + " " + parts[3] + " " + parts[4];
                System.out.println(line);
                if (!(fileDay.equals(day) && fileTime.equals(time) && fileDoctorName.equals(doctor.getSurname() + " " + doctor.getName() + " " + doctor.getFathername()))) {
                    writer.println(line);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        if (!inputFile.delete()) {
            System.out.println("Не вдалося видалити оригінальний файл");
            return;
        }
        if (!tempFile.renameTo(inputFile)) {
            System.out.println("Не вдалося перейменувати тимчасовий файл");
        }
    }

    private void updateDoctorTimetable(String day, String time) {
        String timetableFilePath = "src/resources/doctors/" + doctor.getSurname() + doctor.getName() + "/timetable/" + day + ".txt";
        File inputFile = new File(timetableFilePath);
        String tempFilePath = inputFile.getAbsolutePath().replace(".txt", ".tmp");
        File tempFile = new File(tempFilePath);

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             PrintWriter writer = new PrintWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(time)) {
                    writer.println(time + " Запис відсутній");
                } else {
                    writer.println(line);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        if (!inputFile.delete()) {
            System.out.println("Не вдалося видалити оригінальний файл");
            return;
        }
        if (!tempFile.renameTo(inputFile)) {
            System.out.println("Не вдалося перейменувати тимчасовий файл");
        }
    }
}
