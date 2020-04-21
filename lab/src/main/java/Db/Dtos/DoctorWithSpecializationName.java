package Db.Dtos;

public class DoctorWithSpecializationName {
    public static final String SPECIALIZATION_NAME = "specializationName";

    private long id;
    private String forename;
    private String patronymic;
    private String surname;
    private String specializationName;

    public DoctorWithSpecializationName() {
        this.id = -1;
        this.forename = "";
        this.patronymic = "";
        this.surname = "";
        this.specializationName = "";
    }

    public DoctorWithSpecializationName(String forename, String patronymic, String surname, String specializationName) {
        this.id = -1;
        this.forename = forename;
        this.patronymic = patronymic;
        this.surname = surname;
        this.specializationName = specializationName;
    }

    public DoctorWithSpecializationName(long id, String forename, String patronymic, String surname, String specializationName) {
        this.id = id;
        this.forename = forename;
        this.patronymic = patronymic;
        this.surname = surname;
        this.specializationName = specializationName;
    }

    public long getId() {
        return id;
    }

    public String getForename() {
        return forename;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getSurname() {
        return surname;
    }

    public String getSpecializationName() {
        return specializationName;
    }

    public boolean isPersisted() {
        return id > -1;
    }

    @Override
    public String toString() {
        return Long.toString(id) + '\n' +
                forename + '\n' +
                patronymic + '\n' +
                surname + '\n' +
                specializationName;
    }
}
