import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DoctorController {
    private static DoctorWindow view;

    private final Doctor doctor;

    public DoctorController(String doctorLogin, Boolean visible) {
        String dataFilePath = "src/resources/doctors/" + doctorLogin + "/data.txt";

        List<String> doctorData = new ArrayList<>();

        try (BufferedReader dataReader = new BufferedReader(new FileReader(dataFilePath))) {
            String line;
            while ((line = dataReader.readLine()) != null) {
                doctorData.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.doctor = new Doctor(doctorData.get(0), doctorData.get(1), doctorData.get(2), doctorData.get(3), doctorData.get(4), doctorData.get(5));
        if (visible){
            view = new DoctorWindow(doctor.getSurname() + " " + doctor.getName());
            initController();
        }
    }

    private void initController() {
        view.getDataButton().addActionListener(e -> handleViewData());
        view.getTimetableButton().addActionListener(e -> handleViewTimetableList());
        view.getChangePasswordButton().addActionListener(e -> handleViewChangePassword());
    }

    private void handleViewData() {
        new DoctorDataWindow(doctor);
    }

    private void handleViewTimetableList() {
        new DoctorTimetableWindow(doctor, "doctor", doctor.getJob()+doctor.getName());
    }

    private void handleViewChangePassword() {
        new ChangePasswordController(doctor);
    }

    public Doctor getDoctor(){
        return doctor;
    }
}
