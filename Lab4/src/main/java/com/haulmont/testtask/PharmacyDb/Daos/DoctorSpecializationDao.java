package com.haulmont.testtask.PharmacyDb.Daos;

import com.haulmont.testtask.PharmacyDb.Dtos.DoctorSpecialization;

import java.sql.SQLException;
import java.util.List;

public interface DoctorSpecializationDao {
    String ID = "id";
    String NAME = "name";

    DoctorSpecialization findDoctorSpecialization(long id) throws SQLException, ClassNotFoundException;

    int insertDoctorSpecialization(DoctorSpecialization doctorSpecialization) throws SQLException, ClassNotFoundException;

    int updateDoctorSpecialization(DoctorSpecialization doctorSpecialization) throws SQLException, ClassNotFoundException;

    int deleteDoctorSpecialization(long id) throws SQLException, ClassNotFoundException;

    List<DoctorSpecialization> getAllDoctorSpecializations() throws SQLException, ClassNotFoundException;

    List<DoctorSpecialization> getDoctorSpecializationsByName(String name) throws SQLException, ClassNotFoundException;
}
