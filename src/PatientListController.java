import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class PatientListController {
    private final PatientListWindow view;
    private final JTable table;
    private final DefaultTableModel tableModel;
    private final Map<String, Patient> patientMap = new HashMap<>();

    public PatientListController() {
        String[] columns = {"Пацієнт", "Стать", "Дата народження", "", "", ""};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component comp = super.prepareRenderer(renderer, row, column);
                TableColumn tableColumn = getColumnModel().getColumn(0);
                tableColumn.setPreferredWidth(200);
                return comp;
            }
        };

        new ButtonColumn(table, new PatientListController.HistoryButtonAction(), 3);
        new ButtonColumn(table, new PatientListController.AppointmentButtonAction(), 4);
        new ButtonColumn(table, new PatientListController.DeleteButtonAction(), 5);

        this.view = new PatientListWindow(table);
        populateTableWithData("");
        initController();
    }

    private void initController() {
        view.getSearchButton().addActionListener(e -> populateTableWithData(view.getSearchField().getText()));
    }

    private void populateTableWithData(String query) {

        tableModel.setRowCount(0);
        File patientsDirectory = new File("src/resources/patients");
        File[] patientFiles = patientsDirectory.listFiles();
        patientMap.clear();
        if (patientFiles != null) {
            for (File patientFile : patientFiles) {
                PatientController patientController = new PatientController(patientFile.getName(), false);
                Patient patient = patientController.getPatient();

                String fullName = patient.getSurname() + " " + patient.getName() + " " + patient.getFathername();
                if (fullName.toLowerCase().contains(query.toLowerCase())) {
                    patientMap.put(fullName, patient);
                    tableModel.addRow(new Object[]{fullName, patient.getGender(), patient.getBirthday(), "Історія хвороби", "Записи", "Видалити"});
                }
            }
        }
        view.updateTable(table);
    }

    private class HistoryButtonAction extends AbstractAction {
        public HistoryButtonAction() {
            super("Історія хвороби");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int row = table.convertRowIndexToModel(table.getEditingRow());
            String fullName = (String) tableModel.getValueAt(row, 0);
            new PatientHistoryWindow(patientMap.get(fullName), "admin");
        }
    }

    private class AppointmentButtonAction extends AbstractAction {
        public AppointmentButtonAction() {
            super("Записи");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int row = table.convertRowIndexToModel(table.getEditingRow());
            String fullName = (String) tableModel.getValueAt(row, 0);
            new PatientAppointmentsWindow(patientMap.get(fullName));
        }
    }

    private class DeleteButtonAction extends AbstractAction {
        public DeleteButtonAction() {
            super("Видалити");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int row = table.convertRowIndexToModel(table.getEditingRow());
            String fullName = (String) tableModel.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(null, "Ви впевнені, що хочете видалити користувача " + fullName + "?", "Підтвердження", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                deletePatient(patientMap.get(fullName));
                tableModel.removeRow(row);
            }
        }
    }

    private void deletePatient(Patient patient) {
        File patientDirectory = new File("src/resources/patients/" + patient.getSurname() + patient.getName());
        if (patientDirectory.exists() && patientDirectory.isDirectory()) {
            File[] doctorFiles = patientDirectory.listFiles();
            if (doctorFiles != null) {
                for (File file : doctorFiles) {
                        file.delete();
                }
            }
            patientDirectory.delete();
        }
    }
}
