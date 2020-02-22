package com.haulmont.testtask.PharmacyDb.Daos;

import com.haulmont.testtask.PharmacyDb.Dtos.Doctor;
import com.haulmont.testtask.PharmacyDb.Dtos.DoctorMedicalPrescriptionsNumber;

import java.sql.SQLException;
import java.util.List;

public interface DoctorDao {
    String ID = "id";
    String FORENAME = "forename";
    String PATRONYMIC = "patronymic";
    String SURNAME = "surname";
    String SPECIALIZATION_ID = "specialization_id";

    Doctor findDoctor(long id) throws SQLException, ClassNotFoundException;

    int insertDoctor(Doctor doctor) throws SQLException, ClassNotFoundException;

    int updateDoctor(Doctor doctor) throws SQLException, ClassNotFoundException;

    int deleteDoctor(long id) throws SQLException, ClassNotFoundException;

    List<Doctor> getAllDoctors() throws SQLException, ClassNotFoundException;

    List<Doctor> getDoctorsBySurname(String surname) throws SQLException, ClassNotFoundException;

    List<DoctorMedicalPrescriptionsNumber> getAllDoctorsMedicalPrescriptionsNumbers() throws SQLException, ClassNotFoundException;
}
