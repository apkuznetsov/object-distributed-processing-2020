package com.haulmont.testtask.PharmacyDb.Daos;

import com.haulmont.testtask.PharmacyDb.Dtos.MedicalPrescription;

import java.sql.SQLException;
import java.util.List;

public interface MedicalPrescriptionDao {
    String ID = "id";
    String DESCRIPTION = "description";
    String PATIENT_ID = "patient_id";
    String DOCTOR_ID = "doctor_id";
    String CREATION_DATE = "creation_date";
    String VALIDITY_DATE = "validity_date";
    String PRIORITY = "priority";

    MedicalPrescription findMedicalPrescription(long id) throws SQLException, ClassNotFoundException;

    int insertMedicalPrescription(MedicalPrescription medicalPrescription) throws SQLException, ClassNotFoundException;

    int updateMedicalPrescription(MedicalPrescription MedicalPrescription) throws SQLException, ClassNotFoundException;

    int deleteMedicalPrescription(long id) throws SQLException, ClassNotFoundException;

    List<MedicalPrescription> getAllMedicalPrescriptions() throws SQLException, ClassNotFoundException;

    List<MedicalPrescription> getMedicalPrescriptionsByDescription(String description) throws SQLException, ClassNotFoundException;

    List<MedicalPrescription> getMedicalPrescriptionsByFilters(String description, long patientId, byte priority) throws SQLException, ClassNotFoundException;
}
