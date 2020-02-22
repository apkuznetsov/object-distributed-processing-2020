package com.haulmont.testtask.PharmacyDb;

import com.haulmont.testtask.PharmacyDb.Daos.DoctorDao;
import com.haulmont.testtask.PharmacyDb.Daos.DoctorSpecializationDao;
import com.haulmont.testtask.PharmacyDb.Daos.MedicalPrescriptionDao;
import com.haulmont.testtask.PharmacyDb.Daos.PatientDao;

public interface PharmacyDbDao {
    String DOCTOR = "doctor";
    String DOCTOR_SPECIALIZATION = "doctor_specialization";
    String MEDICAL_PRESCRIPTION = "medical_prescription";
    String PATIENT = "patient";

    DoctorDao getDoctorDao();

    DoctorSpecializationDao getDoctorSpecializationDao();

    MedicalPrescriptionDao getMedicalPrescriptionDao();

    PatientDao getPatientDao();
}
