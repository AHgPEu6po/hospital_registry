import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;

public class PatientAppointmentsWindow extends JFrame {
    private final JTable appointmentsTable;

    private final Patient patient;

    public PatientAppointmentsWindow(Patient patient) {

        this.patient = patient;
        setTitle("Записи пацієнта");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);

        String[] columns = {"День", "Час", "ФІО лікаря", ""};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        appointmentsTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(appointmentsTable);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        new ButtonColumn(appointmentsTable, new PatientAppointmentsWindow.CancelAppointmentButtonAction(), 3);

        loadPatientAppointments();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadPatientAppointments() {
        String appointmentsFilePath = "src/resources/patients/" + patient.getSurname() + patient.getName() + "/appointments.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(appointmentsFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.replaceAll(" {2}", " ");
                String[] parts = line.split(" ");
                String day = parts[0];
                String time = parts[1];
                String doctorName = parts[2] + " " + parts[3] + " " + parts[4];
                Object[] rowData = {day, time, doctorName, "Скасувати запис"};
                ((DefaultTableModel) appointmentsTable.getModel()).addRow(rowData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class CancelAppointmentButtonAction extends AbstractAction {
        public CancelAppointmentButtonAction() {
            super("Скасувати запис");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int modelRow = appointmentsTable.getSelectedRow();
            if (modelRow >= 0) {
                int confirmation = JOptionPane.showConfirmDialog(
                        PatientAppointmentsWindow.this,
                        "Чи дійсно ви бажаєте скасувати запис?",
                        "Підтвердження",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirmation == JOptionPane.YES_OPTION) {
                    DefaultTableModel model = (DefaultTableModel) appointmentsTable.getModel();
                    String day = (String) model.getValueAt(modelRow, 0);
                    String time = (String) model.getValueAt(modelRow, 1);
                    String doctorName = (String) model.getValueAt(modelRow, 2);

                    removeAppointmentFromFile(day, time, doctorName);

                    String[] parts = doctorName.split(" ");
                    doctorName = parts[0] + parts[1];
                    updateDoctorTimetable(day, time, doctorName);

                    model.removeRow(modelRow);
                }
            }
        }

        private void removeAppointmentFromFile(String day, String time, String doctorName) {
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
                    if (!(fileDay.equals(day) && fileTime.equals(time) && fileDoctorName.equals(doctorName))) {
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

        private void updateDoctorTimetable(String day, String time, String doctorName) {
            String timetableFilePath = "src/resources/doctors/" + doctorName + "/timetable/" + day + ".txt";
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
}
