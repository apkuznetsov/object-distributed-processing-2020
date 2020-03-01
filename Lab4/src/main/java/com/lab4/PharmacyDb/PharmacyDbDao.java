package com.lab4.PharmacyDb;

import com.lab4.PharmacyDb.Daos.DoctorDao;
import com.lab4.PharmacyDb.Daos.DoctorSpecializationDao;
import com.lab4.PharmacyDb.Daos.MedicalPrescriptionDao;
import com.lab4.PharmacyDb.Daos.PatientDao;

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
