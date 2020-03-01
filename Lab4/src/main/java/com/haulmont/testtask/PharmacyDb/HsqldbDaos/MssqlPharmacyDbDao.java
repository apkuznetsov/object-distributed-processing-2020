package com.haulmont.testtask.PharmacyDb.HsqldbDaos;

import com.haulmont.testtask.Dao.MssqlDao;
import com.haulmont.testtask.PharmacyDb.Daos.*;
import com.haulmont.testtask.PharmacyDb.PharmacyDbDao;

public class MssqlPharmacyDbDao extends MssqlDao implements PharmacyDbDao {
    public MssqlPharmacyDbDao(String dbUrl, String user, String password) {
        super(dbUrl, user, password);
    }

    @Override
    public DoctorDao getDoctorDao() {
        return new MssqlDoctorDao(super.dbUrl, super.user, super.password);
    }

    @Override
    public DoctorSpecializationDao getDoctorSpecializationDao() {
        return new MssqlDoctorSpecializationDao(super.dbUrl, super.user, super.password);
    }

    @Override
    public MedicalPrescriptionDao getMedicalPrescriptionDao() {
        return new MssqlMedicalPrescriptionDao(super.dbUrl, super.user, super.password);
    }

    @Override
    public PatientDao getPatientDao() {
        return new MssqlPatientDao(super.dbUrl, super.user, super.password);
    }
}
