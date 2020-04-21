package Db;

import Db.Daos.DoctorDao;
import Db.Daos.DoctorSpecializationDao;

public interface DbDao {
    String DOCTOR = "doctor";
    String DOCTOR_SPECIALIZATION = "doctor_specialization";

    DoctorDao getDoctorDao();

    DoctorSpecializationDao getDoctorSpecializationDao();
}
