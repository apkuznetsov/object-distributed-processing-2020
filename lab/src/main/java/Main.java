import DoctorDb.Daos.DoctorDao;
import DoctorDb.DoctorDbDao;
import DoctorDb.HsqldbDaos.HsqldbDoctorDbDao;

public class Main {
    private static final String DB_URL = "jdbc:hsqldb:file:testdb";
    private static final String USER = "SA";
    private static final String PASSWORD = "";
    private static final DoctorDbDao dbDao = new HsqldbDoctorDbDao(DB_URL, USER, PASSWORD);
    private static final DoctorDao doctorDao = dbDao.getDoctorDao();

    public static void main(String[] args) {
    }
}
