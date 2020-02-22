package com.haulmont.testtask.PharmacyDb.Dtos;

public class DoctorMedicalPrescriptionsNumber {
    public static final String ID = "id";
    public static final String FORENAME = "forename";
    public static final String PATRONYMIC = "patronymic";
    public static final String SURNAME = "surname";
    public static final String NUMBER = "number";
    public static final String PRESCRIPTIONS_NUMBER = "prescriptionsNumber";

    private long id;
    private String forename;
    private String patronymic;
    private String surname;
    private long prescriptionsNumber;

    public DoctorMedicalPrescriptionsNumber() {
        this.id = -1;
        this.forename = "";
        this.patronymic = "";
        this.surname = "";
        this.prescriptionsNumber = -1;
    }

    public DoctorMedicalPrescriptionsNumber(String forename, String patronymic, String surname, long prescriptionsNumber) {
        this.id = -1;
        this.forename = forename;
        this.patronymic = patronymic;
        this.surname = surname;
        this.prescriptionsNumber = prescriptionsNumber;
    }

    public DoctorMedicalPrescriptionsNumber(long id, String forename, String patronymic, String surname, long prescriptionsNumber) {
        this.id = id;
        this.forename = forename;
        this.patronymic = patronymic;
        this.surname = surname;
        this.prescriptionsNumber = prescriptionsNumber;
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

    public long getPrescriptionsNumber() {
        return prescriptionsNumber;
    }

    public void setForename(String newForename) {
        forename = newForename;
    }

    public void setPatronymic(String newPatronymic) {
        patronymic = newPatronymic;
    }

    public void setSurname(String newSurname) {
        surname = newSurname;
    }

    public void setPrescriptionsNumber(long newSpecializationId) {
        prescriptionsNumber = newSpecializationId;
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
                Long.toString(prescriptionsNumber);
    }
}
