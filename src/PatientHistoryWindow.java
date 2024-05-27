import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Objects;

public class PatientHistoryWindow extends JFrame {
    private final JTextArea historyTextArea;
    private final Patient patient;

    public PatientHistoryWindow(Patient patient, String role) {
        this.patient = patient;
        if(!Objects.equals(role, "patient")){
            setTitle("Історія хвороби пацієнта: " + patient.getSurname() + " " + patient.getName());
        } else {
            setTitle("Історія хвороби");
        }
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());

        historyTextArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(historyTextArea);
        add(scrollPane, BorderLayout.CENTER);

        if(Objects.equals(role, "admin")) {
            JPanel buttonPanel = new JPanel();
            JButton addRecordButton = new JButton("Додати запис");
            addRecordButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showAddRecordDialog();
                }
            });
            buttonPanel.add(addRecordButton);
            add(buttonPanel, BorderLayout.SOUTH);
        }
        loadPatientHistory();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadPatientHistory() {
        String historyFilePath = "src/resources/patients/" + patient.getSurname() + patient.getName() + "/history.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(historyFilePath))) {
            StringBuilder history = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                history.append(line).append("\n");
            }
            historyTextArea.setText(history.toString());
        } catch (IOException e) {
            e.printStackTrace();
            historyTextArea.setText("Помилка завантаження історії хвороби.");
        }
    }

    private void showAddRecordDialog() {
        JTextField recordField = new JTextField(20);
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Новий запис:"));
        panel.add(recordField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Додати запис", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String recordText = recordField.getText();
            addRecord(recordText);
        }
    }

    private void addRecord(String recordText) {
        String historyFilePath = "src/resources/patients/" + patient.getSurname() + patient.getName() + "/history.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(historyFilePath, true))) {
            writer.write("\n" + recordText);
            writer.flush();
            loadPatientHistory();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Помилка додавання запису.", "Помилка", JOptionPane.ERROR_MESSAGE);
        }
    }
}
