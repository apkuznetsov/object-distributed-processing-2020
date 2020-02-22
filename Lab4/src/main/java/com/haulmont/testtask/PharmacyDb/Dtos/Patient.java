package com.haulmont.testtask.PharmacyDb.Dtos;

public class Patient {
    public static final String ID = "id";
    public static final String FORENAME = "forename";
    public static final String PATRONYMIC = "patronymic";
    public static final String SURNAME = "surname";
    public static final String PHONE = "phone";

    private long id;
    private String forename;
    private String patronymic;
    private String surname;
    private String phone;

    public Patient() {
        id = -1;
        forename = "";
        patronymic = "";
        surname = "";
        phone = "";
    }

    public Patient(String forename, String patronymic, String surname, String phone) {
        this.id = -1;
        this.forename = forename;
        this.patronymic = patronymic;
        this.surname = surname;
        this.phone = phone;
    }

    public Patient(long id, String forename, String patronymic, String surname, String phone) {
        this.id = id;
        this.forename = forename;
        this.patronymic = patronymic;
        this.surname = surname;
        this.phone = phone;
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

    public String getPhone() {
        return phone;
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

    public void setPhone(String newPhone) {
        phone = newPhone;
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
                phone;
    }
}
