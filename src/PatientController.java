import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PatientController {
    private static PatientWindow view;
    private final Patient patient;

    public PatientController(String patientLogin, Boolean visible) {
        String dataFilePath = "src/resources/patients/" + patientLogin + "/data.txt";
        String idFilePath = "src/resources/patients/" + patientLogin + "/identificator.txt";

        List<String> patientData = new ArrayList<>();

        try (BufferedReader dataReader = new BufferedReader(new FileReader(dataFilePath))) {
            String line;
            while ((line = dataReader.readLine()) != null) {
                patientData.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String id = null;
        try (BufferedReader idReader = new BufferedReader(new FileReader(idFilePath))) {
            id = idReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.patient = new Patient(patientData.get(0), patientData.get(1), patientData.get(2), patientData.get(3), patientData.get(4), id);
        if (visible){
            view = new PatientWindow(patient.getSurname() + " " + patient.getName());
            initController();
        }
    }

    private void initController() {
        view.getDataButton().addActionListener(e -> handleViewData());
        view.getDoctorListButton().addActionListener(e -> handleViewDoctorList());
        view.getAppointmentsButton().addActionListener(e -> handleViewAppointments());
        view.getHistoryButton().addActionListener(e -> handleViewHistory());
        view.getChangePasswordButton().addActionListener(e -> handleViewChangePassword());
    }

    private void handleViewData() {
        new PatientDataWindow(patient);
    }

    private void handleViewDoctorList() {
        new DoctorListController("patient", patient.getSurname() + patient.getName());
    }

    private void handleViewAppointments() {
        new PatientAppointmentsWindow(patient);
    }

    private void handleViewHistory() {
        new PatientHistoryWindow(patient, "patient");
    }

    private void handleViewChangePassword() {
        new ChangePasswordController(patient);
    }

    public Patient getPatient(){
        return patient;
    }
}
