public class Doctor {
    private final String surname;
    private final String name;
    private final String fathername;
    private final String gender;
    private final String job;
    private final String shift;


    public Doctor(String surname, String name, String fathername, String gender, String job, String shift) {
        this.surname = surname;
        this.name = name;
        this.fathername = fathername;
        this.gender = gender;
        this.job = job;
        this.shift = shift;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getFathername() {
        return fathername;
    }

    public String getGender() {
        return gender;
    }

    public String getJob() {
        return job;
    }

    public String getShift() {
        return shift;
    }
}
