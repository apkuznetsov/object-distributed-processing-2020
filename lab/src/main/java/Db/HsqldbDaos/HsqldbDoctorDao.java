package Db.HsqldbDaos;

import Dao.HsqldbDao;
import Db.Daos.DoctorDao;
import Db.Dtos.Doctor;
import Db.Dtos.DoctorSpecialization;
import Db.Dtos.DoctorWithSpecializationName;
import Db.SqlHelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import static Db.DbDao.DOCTOR;
import static Db.DbDao.DOCTOR_SPECIALIZATION;
import static Db.Dtos.Doctor.*;
import static Db.SqlHelper.*;


public class HsqldbDoctorDao extends HsqldbDao implements DoctorDao {
    public HsqldbDoctorDao(String dbUrl, String user, String password) {
        super(dbUrl, user, password);
    }

    public Doctor findDoctor(long id) throws SQLException, ClassNotFoundException {
        connect();

        final String query = String.format("%s * %s %s %s %s = %s;", SELECT, FROM, DOCTOR, WHERE, ID, Long.toString(id));
        ResultSet resultSet = executeQuery(query);

        Doctor doctor = null;
        while (resultSet.next()) {
            doctor = new Doctor(
                    resultSet.getLong(ID),
                    resultSet.getString(FORENAME),
                    resultSet.getString(PATRONYMIC),
                    resultSet.getString(SURNAME),
                    resultSet.getLong(SPECIALIZATION_ID));
        }

        disconnect();
        return doctor;
    }

    public int insertDoctor(Doctor doctor) throws SQLException, ClassNotFoundException {
        connect();

        final String query = String.format("%s %s %s (%s, %s, %s, %s) %s (\'%s\', \'%s\', \'%s\', %s);",
                INSERT, INTO, DOCTOR,
                FORENAME, PATRONYMIC, SURNAME, SPECIALIZATION_ID,
                VALUES,
                doctor.getForename(), doctor.getPatronymic(), doctor.getSurname(), Long.toString(doctor.getSpecializationId()));

        int changedRowsNum = executeUpdate(query);

        disconnect();
        return changedRowsNum;
    }

    public int updateDoctor(Doctor doctor) throws SQLException, ClassNotFoundException {
        connect();

        final String query = String.format("%s %s %s " +
                        "%s = \'%s\', " +
                        "%s = \'%s\', " +
                        "%s = \'%s\', " +
                        "%s = %s " +
                        "%s %s = %s;",
                UPDATE, DOCTOR, SET,
                FORENAME, doctor.getForename(),
                PATRONYMIC, doctor.getPatronymic(),
                SURNAME, doctor.getSurname(),
                SPECIALIZATION_ID, Long.toString(doctor.getSpecializationId()),
                WHERE, ID, doctor.getId());

        int changedRowsNum = executeUpdate(query);

        disconnect();
        return changedRowsNum;
    }

    public int deleteDoctor(long id) throws SQLException, ClassNotFoundException {
        connect();

        final String query = "DELETE FROM doctor WHERE ID = ?";
        PreparedStatement preparedStatement = getConnection().prepareStatement(query);
        preparedStatement.setLong(1, id);
        int changedRowsNum = preparedStatement.executeUpdate();

        disconnect();
        return changedRowsNum;
    }

    public List<Doctor> getAllDoctors() throws SQLException, ClassNotFoundException {
        connect();

        final String query = String.format("%s * %s %s", SELECT, FROM, DOCTOR);
        ResultSet resultSet = executeQuery(query);

        List<Doctor> doctors = getDoctorsFromResultSet(resultSet);

        disconnect();
        return doctors;
    }

    private List<Doctor> getDoctorsFromResultSet(ResultSet resultSet) throws SQLException {
        List<Doctor> doctors = new LinkedList<Doctor>();

        Doctor doctor;
        while (resultSet.next()) {
            doctor = new Doctor(
                    resultSet.getLong(ID),
                    resultSet.getString(FORENAME),
                    resultSet.getString(PATRONYMIC),
                    resultSet.getString(SURNAME),
                    resultSet.getLong(SPECIALIZATION_ID));

            doctors.add(doctor);
        }

        return doctors;
    }

    public List<DoctorWithSpecializationName> getAllDoctorsWithSpecializationName() throws SQLException, ClassNotFoundException {
        connect();

        final String query = String.format("%s %s, %s, %s, %s, %s.%s " +
                        "%s %s " +
                        "%s %s " +
                        "%s %s.%s = %s.%s",
                SELECT, ID, FORENAME, PATRONYMIC, Doctor.SURNAME, DOCTOR_SPECIALIZATION, DoctorSpecialization.NAME,
                FROM, DOCTOR,
                SqlHelper.JOIN, DOCTOR_SPECIALIZATION,
                SqlHelper.ON, DOCTOR, "specialization_id", DOCTOR_SPECIALIZATION, DoctorSpecialization.ID);
        ResultSet resultSet = executeQuery(query);

        List<DoctorWithSpecializationName> doctors = new LinkedList<DoctorWithSpecializationName>();

        DoctorWithSpecializationName doctor;
        while (resultSet.next()) {
            doctor = new DoctorWithSpecializationName(
                    resultSet.getLong(ID),
                    resultSet.getString(FORENAME),
                    resultSet.getString(PATRONYMIC),
                    resultSet.getString(Doctor.SURNAME),
                    resultSet.getString(DoctorSpecialization.NAME));

            doctors.add(doctor);
        }

        disconnect();
        return doctors;
    }

    public List<Doctor> getDoctorsBySurname(String surname) throws SQLException, ClassNotFoundException {
        connect();

        final String query = String.format("%s * %s %s " +
                        "%s %s ( %s ) %s %s (\'%s\')",
                SELECT, FROM, DOCTOR,
                WHERE, LOWER, SURNAME, LIKE, LOWER, '%' + surname + '%');

        ResultSet resultSet = executeQuery(query);

        List<Doctor> doctors = getDoctorsFromResultSet(resultSet);

        disconnect();
        return doctors;
    }
}
