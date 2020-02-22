package com.haulmont.testtask.PharmacyDb.Dtos;

public class DoctorSpecialization {
    public static final String ID = "id";
    public static final String NAME = "name";

    private long id;
    private String name;

    public DoctorSpecialization() {
        this.id = -1;
        this.name = "";
    }

    public DoctorSpecialization(String name) {
        this.id = -1;
        this.name = name;
    }

    public DoctorSpecialization(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        name = newName;
    }

    public boolean isPersisted() {
        return id > -1;
    }

    @Override
    public String toString() {
        return Long.toString(id) + '\n' +
                name;
    }
}
