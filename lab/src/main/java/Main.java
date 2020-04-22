import DoctorDb.Daos.DoctorDao;
import DoctorDb.Daos.DoctorSpecializationDao;
import DoctorDb.DoctorDbDao;
import DoctorDb.Dtos.Doctor;
import DoctorDb.Dtos.DoctorSpecialization;
import DoctorDb.Dtos.DoctorWithSpecializationName;
import DoctorDb.HsqldbDaos.HsqldbDoctorDbDao;

import java.sql.SQLException;
import java.util.List;

public class Main {
    private static final String DB_URL = "jdbc:hsqldb:file:testdb";
    private static final String USER = "SA";
    private static final String PASSWORD = "";
    private static final DoctorDbDao dbDao = new HsqldbDoctorDbDao(DB_URL, USER, PASSWORD);
    private static final DoctorDao docDao = dbDao.getDoctorDao();
    private static final DoctorSpecializationDao specDao = dbDao.getDoctorSpecializationDao();

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        System.out.println("1 -- список врачей");
        printDocsWithSpecs();
        printSpecs();
    }

    private static void printDocs() throws SQLException, ClassNotFoundException {
        List<Doctor> doctors = docDao.getAllDoctors();

        System.out.printf("%-5s %-15s %-15s %-15s %-5s\n", "№", "Фамилия", "Имя", "Отчество", "№ специализации");
        for (Doctor d : doctors) {
            System.out.printf("%-5s %-15s %-15s %-15s %-5s\n",
                    d.getId(),
                    d.getSurname(),
                    d.getForename(),
                    d.getPatronymic(),
                    d.getSpecializationId());
        }
    }

    private static void printDocsWithSpecs() throws SQLException, ClassNotFoundException {
        List<DoctorWithSpecializationName> doctors = docDao.getAllDoctorsWithSpecializationName();

        System.out.printf("%-5s %-15s %-15s %-15s %-20s\n", "№", "Фамилия", "Имя", "Отчество", "Специализации");
        for (DoctorWithSpecializationName d : doctors) {
            System.out.printf("%-5s %-15s %-15s %-15s %-20s\n",
                    d.getId(),
                    d.getSurname(),
                    d.getForename(),
                    d.getPatronymic(),
                    d.getSpecializationName());
        }
    }

    private static void printSpecs() throws SQLException, ClassNotFoundException {
        List<DoctorSpecialization> specs = specDao.getAllDoctorSpecializations();

        System.out.printf("%-5s %-20s\n", "№", "Специализации");
        for (DoctorSpecialization s : specs) {
            System.out.printf("%-5s %-20s\n",
                    s.getId(),
                    s.getName());
        }
    }
}
