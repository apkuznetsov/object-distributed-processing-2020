package com.lab4.PharmacyDb.HsqldbDaos;

import com.lab4.Dao.MssqlDao;
import com.haulmont.testtask.PharmacyDb.Daos.*;
import com.lab4.PharmacyDb.Daos.DoctorDao;
import com.lab4.PharmacyDb.Daos.DoctorSpecializationDao;
import com.lab4.PharmacyDb.Daos.MedicalPrescriptionDao;
import com.lab4.PharmacyDb.Daos.PatientDao;
import com.lab4.PharmacyDb.PharmacyDbDao;

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
