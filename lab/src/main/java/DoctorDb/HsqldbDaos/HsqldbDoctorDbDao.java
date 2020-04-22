package DoctorDb.HsqldbDaos;

import Dao.HsqldbDao;
import DoctorDb.Daos.DoctorDao;
import DoctorDb.Daos.DoctorSpecializationDao;
import DoctorDb.DoctorDbDao;

public class HsqldbDoctorDbDao extends HsqldbDao implements DoctorDbDao {
    public HsqldbDoctorDbDao(String dbUrl, String user, String password) {
        super(dbUrl, user, password);
    }

    public DoctorDao getDoctorDao() {
        return new HsqldbDoctorDao(super.dbUrl, super.user, super.password);
    }

    public DoctorSpecializationDao getDoctorSpecializationDao() {
        return new HsqldbDoctorSpecializationDao(super.dbUrl, super.user, super.password);
    }
}
