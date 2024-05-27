import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DoctorListController {
    private final DoctorListWindow view;
    private final JTable table;
    private final DefaultTableModel tableModel;
    private final String role;
    private final String username;
    private final Map<String, Doctor> doctorMap = new HashMap<>();

    public DoctorListController(String role, String username) {

        this.role = role;
        this.username = username;

        String[] columns;
        if (Objects.equals(role, "patient")){
            columns = new String[]{"Лікар", "Спеціальність", "Зміна", ""};
        } else{
            columns = new String[]{"Лікар", "Спеціальність", "Зміна", "", ""};
        }
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

        new ButtonColumn(table, new DoctorListController.ScheduleButtonAction(), 3);
        if (!Objects.equals(role, "patient")){
            new ButtonColumn(table, new DoctorListController.DeleteButtonAction(), 4);
        }

        this.view = new DoctorListWindow(table);
        populateTableWithData("", "");
        initController();
    }

    private void initController() {
        view.getSearchButton().addActionListener(e -> populateTableWithData(view.getSearchField().getText(), view.getSearchType()));
    }

    private void populateTableWithData(String query, String searchType) {

        tableModel.setRowCount(0);
        File doctorsDirectory = new File("src/resources/doctors");
        File[] doctorFiles = doctorsDirectory.listFiles();
        doctorMap.clear();
        if (doctorFiles != null) {
            for (File doctorFile : doctorFiles) {
                DoctorController doctorController = new DoctorController(doctorFile.getName(), false);
                Doctor doctor = doctorController.getDoctor();

                String fullName = doctor.getSurname() + " " + doctor.getName() + " " + doctor.getFathername();
                if (Objects.equals(searchType, "ФІО")) {
                    if (fullName.toLowerCase().contains(query.toLowerCase())) {
                        if (Objects.equals(role, "patient")){
                            tableModel.addRow(new Object[]{fullName, doctor.getJob(), doctor.getShift(), "Записатись"});
                        } else {
                            tableModel.addRow(new Object[]{fullName, doctor.getJob(), doctor.getShift(), "Розклад", "Звільнити"});
                        }
                        doctorMap.put(fullName, doctor);
                    }
                } else {
                    if (doctor.getJob().toLowerCase().contains(query.toLowerCase())) {
                        if (Objects.equals(role, "patient")) {
                            tableModel.addRow(new Object[]{fullName, doctor.getJob(), doctor.getShift(), "Записатись"});
                        } else {
                            tableModel.addRow(new Object[]{fullName, doctor.getJob(), doctor.getShift(), "Розклад", "Звільнити"});
                        }
                        doctorMap.put(fullName, doctor);
                    }
                }
            }
        }
        view.updateTable(table);
    }
    private class ScheduleButtonAction extends AbstractAction {
        public ScheduleButtonAction() {
            super("Розклад");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int row = table.convertRowIndexToModel(table.getEditingRow());
            String doctorName = (String) tableModel.getValueAt(row, 0);
            new DoctorTimetableWindow(doctorMap.get(doctorName), role, username);
        }
    }

    private class DeleteButtonAction extends AbstractAction {
        public DeleteButtonAction() {
            super("Звільнити");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int row = table.convertRowIndexToModel(table.getEditingRow());
            String fullName = (String) tableModel.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(null, "Ви впевнені, що хочете звільнити лікаря " + fullName + "?", "Підтвердження", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                deleteDoctor(doctorMap.get(fullName));
                tableModel.removeRow(row);
            }
        }
    }

    private void deleteDoctor(Doctor doctor) {
        File doctorDirectory = new File("src/resources/doctors/" + doctor.getSurname() + doctor.getName());
        if (doctorDirectory.exists() && doctorDirectory.isDirectory()) {
            File[] doctorFiles = doctorDirectory.listFiles();
            if (doctorFiles != null) {
                for (File file : doctorFiles) {
                    if (file.isDirectory()) {
                        deleteDirectory(file);
                    } else {
                        file.delete();
                    }
                }
            }
            doctorDirectory.delete();
        }
    }

    private void deleteDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    file.delete();
                }
            }
        }
        directory.delete();
    }
}
