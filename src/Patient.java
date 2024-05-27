public class Patient {
    private final String surname;
    private final String name;
    private final String fathername;
    private final String gender;
    private final String birthday;

    private final String id;


    public Patient(String surname, String name, String fathername, String gender, String birthday, String id) {
        this.surname = surname;
        this.name = name;
        this.fathername = fathername;
        this.gender = gender;
        this.birthday = birthday;
        this.id = id;
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

    public String getBirthday() {
        return birthday;
    }

    public String getId() {
        return id;
    }
}
