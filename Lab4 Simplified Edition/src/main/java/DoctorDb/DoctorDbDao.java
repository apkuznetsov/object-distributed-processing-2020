package DoctorDb;

import DoctorDb.Daos.DoctorDao;
import DoctorDb.Daos.DoctorSpecializationDao;

public interface DoctorDbDao {
    String DOCTOR = "doctor";
    String DOCTOR_SPECIALIZATION = "doctor_specialization";

    DoctorDao getDoctorDao();

    DoctorSpecializationDao getDoctorSpecializationDao();
}
