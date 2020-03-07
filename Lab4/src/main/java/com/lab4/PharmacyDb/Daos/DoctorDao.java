package com.lab4.PharmacyDb.Daos;

import com.lab4.PharmacyDb.Dtos.Doctor;
import com.lab4.PharmacyDb.Dtos.DoctorMedicalPrescriptionsNumber;
import com.lab4.PharmacyDb.Dtos.DoctorWithSpecializationName;

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

    public List<DoctorWithSpecializationName> getAllDoctorsWithSpecializationName() throws SQLException, ClassNotFoundException;

    List<Doctor> getDoctorsBySurname(String surname) throws SQLException, ClassNotFoundException;

    List<DoctorMedicalPrescriptionsNumber> getAllDoctorsMedicalPrescriptionsNumbers() throws SQLException, ClassNotFoundException;
}
