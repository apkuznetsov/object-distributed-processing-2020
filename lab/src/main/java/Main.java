import DoctorDb.Daos.DoctorDao;
import DoctorDb.Daos.DoctorSpecializationDao;
import DoctorDb.DoctorDbDao;
import DoctorDb.Dtos.Doctor;
import DoctorDb.Dtos.DoctorSpecialization;
import DoctorDb.Dtos.DoctorWithSpecializationName;
import DoctorDb.HsqldbDaos.HsqldbDoctorDbDao;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String DB_URL = "jdbc:hsqldb:file:testdb";
    private static final String USER = "SA";
    private static final String PASSWORD = "";
    private static final DoctorDbDao dbDao = new HsqldbDoctorDbDao(DB_URL, USER, PASSWORD);
    private static final DoctorDao docDao = dbDao.getDoctorDao();
    private static final DoctorSpecializationDao specDao = dbDao.getDoctorSpecializationDao();

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Scanner scan = new Scanner(System.in);
        String menuItem;

        while (true) {
            System.out.println("1 -- Список врачей (эквисоединение Врач + Специальность)\n" +
                    "2 -- Список специальностей\n" +
                    "--------------------------------------------------------\n" +
                    "3 -- Добавить врача\n" +
                    "Ваш выбор ...");
            menuItem = scan.nextLine();

            try {
                if ("1".equals(menuItem)) {
                    printDocsWithSpecs();
                } else if ("2".equals(menuItem)) {
                    printSpecs();
                } else if ("3".equals(menuItem)) {
                    printInsertDoc();
                    printDocs();
                }
            } catch (Exception exc) {
                exc.printStackTrace();
                continue;
            }
            System.out.println();
        }
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

    private static void printInsertDoc() throws SQLException, ClassNotFoundException {
        Scanner scan = new Scanner(System.in);

        String forename;
        String patronymic;
        String surname;
        String specializationId;

        System.out.println("Добавление врача:");
        System.out.println("Фамилия ... ");
        surname = scan.nextLine();
        System.out.println("Имя ... ");
        forename = scan.nextLine();
        System.out.println("Отчество ... ");
        patronymic = scan.nextLine();
        System.out.println("Номер специальности ... ");
        specializationId = scan.nextLine();

        Doctor d = new Doctor(forename, patronymic, surname, Long.parseLong(specializationId));
        docDao.insertDoctor(d);
    }
}
