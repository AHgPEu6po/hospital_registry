public class AdminController {
    private final AdminWindow view;

    public AdminController() {
        this.view = new AdminWindow();
        initController();
    }

    private void initController() {
        view.getDoctorListButton().addActionListener(e -> handleViewDoctorList());
        view.getAddDoctorButton().addActionListener(e -> handleViewAddDoctor());
        view.getPatientListButton().addActionListener(e -> handleViewPatientList());
        view.getAddPatientButton().addActionListener(e -> handleViewAddPatient());
        view.getChangeHelloButton().addActionListener(e -> handleViewChangeHello());
        view.getChangePasswordButton().addActionListener(e -> handleViewChangePassword());
    }

    void handleViewDoctorList() {
        new DoctorListController("admin", "ghjk");
    }

    void handleViewAddDoctor() {
        new AddDoctorWindow();
    }

    void handleViewPatientList() {
        new PatientListController();
    }

    void handleViewAddPatient() {
        new AddPatientWindow();
    }

    void handleViewChangeHello() {
        new ChangeHelloWindow();
    }

    void handleViewChangePassword() {
        new ChangePasswordController(null);
    }
}
