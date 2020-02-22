package com.haulmont.testtask.PharmacyDb.Dtos;

public class Doctor {
    public static final String ID = "id";
    public static final String FORENAME = "forename";
    public static final String PATRONYMIC = "patronymic";
    public static final String SURNAME = "surname";
    public static final String SPECIALIZATION_ID = "specializationId";

    private long id;
    private String forename;
    private String patronymic;
    private String surname;
    private long specializationId;

    public Doctor() {
        this.id = -1;
        this.forename = "";
        this.patronymic = "";
        this.surname = "";
        this.specializationId = -1;
    }

    public Doctor(String forename, String patronymic, String surname, long specializationId) {
        this.id = -1;
        this.forename = forename;
        this.patronymic = patronymic;
        this.surname = surname;
        this.specializationId = specializationId;
    }

    public Doctor(long id, String forename, String patronymic, String surname, long specializationId) {
        this.id = id;
        this.forename = forename;
        this.patronymic = patronymic;
        this.surname = surname;
        this.specializationId = specializationId;
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

    public long getSpecializationId() {
        return specializationId;
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

    public void setSpecializationId(long newSpecializationId) {
        specializationId = newSpecializationId;
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
                Long.toString(specializationId);
    }
}
