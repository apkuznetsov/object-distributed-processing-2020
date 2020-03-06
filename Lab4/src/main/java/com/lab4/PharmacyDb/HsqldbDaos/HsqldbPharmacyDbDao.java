package com.lab4.PharmacyDb.HsqldbDaos;

import com.lab4.Dao.HsqldbDao;
import com.haulmont.testtask.PharmacyDb.Daos.*;
import com.lab4.PharmacyDb.Daos.DoctorDao;
import com.lab4.PharmacyDb.Daos.DoctorSpecializationDao;
import com.lab4.PharmacyDb.Daos.MedicalPrescriptionDao;
import com.lab4.PharmacyDb.Daos.PatientDao;
import com.lab4.PharmacyDb.PharmacyDbDao;

public class HsqldbPharmacyDbDao extends HsqldbDao implements PharmacyDbDao {
    public HsqldbPharmacyDbDao(String dbUrl, String user, String password) {
        super(dbUrl, user, password);
    }

    @Override
    public DoctorDao getDoctorDao() {
        return new HsqldbDoctorDao(super.dbUrl, super.user, super.password);
    }

    @Override
    public DoctorSpecializationDao getDoctorSpecializationDao() {
        return new HsqldbDoctorSpecializationDao(super.dbUrl, super.user, super.password);
    }

    @Override
    public MedicalPrescriptionDao getMedicalPrescriptionDao() {
        return new HsqldbMedicalPrescriptionDao(super.dbUrl, super.user, super.password);
    }

    @Override
    public PatientDao getPatientDao() {
        return new HsqldbPatientDao(super.dbUrl, super.user, super.password);
    }
}
