package com.haulmont.testtask.PharmacyDb.HsqldbDaos;

import com.haulmont.testtask.Dao.HsqldbDao;
import com.haulmont.testtask.PharmacyDb.Daos.PatientDao;
import com.haulmont.testtask.PharmacyDb.Dtos.Patient;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import static com.haulmont.testtask.Dao.SqlHelper.*;
import static com.haulmont.testtask.PharmacyDb.Daos.PharmacyDbDao.PATIENT;

public class HsqldbPatientDao extends HsqldbDao implements PatientDao {
    public HsqldbPatientDao(String dbUrl, String user, String password) {
        super(dbUrl, user, password);
    }

    @Override
    public Patient findPatient(long id) throws SQLException, ClassNotFoundException {
        connect();

        final String query = String.format("%s * %s %s %s %s = %s;", SELECT, FROM, PATIENT, WHERE, ID, Long.toString(id));
        ResultSet resultSet = executeQuery(query);

        Patient patient = null;
        while (resultSet.next()) {
            patient = new Patient(
                    resultSet.getLong(ID),
                    resultSet.getString(FORENAME),
                    resultSet.getString(PATRONYMIC),
                    resultSet.getString(SURNAME),
                    resultSet.getString(PHONE));
        }

        disconnect();
        return patient;
    }

    @Override
    public int insertPatient(Patient patient) throws SQLException, ClassNotFoundException {
        connect();

        final String query = String.format("%s %s %s (%s, %s, %s, %s) %s (\'%s\', \'%s\', \'%s\', \'%s\');",
                INSERT, INTO, PATIENT,
                FORENAME, PATRONYMIC, SURNAME, PHONE,
                VALUES,
                patient.getForename(), patient.getPatronymic(), patient.getSurname(), patient.getPhone());

        int changedRowsNum = executeUpdate(query);

        disconnect();
        return changedRowsNum;
    }

    @Override
    public int updatePatient(Patient patient) throws SQLException, ClassNotFoundException {
        connect();

        final String query = String.format("%s %s %s " +
                        "%s = \'%s\', " +
                        "%s = \'%s\', " +
                        "%s = \'%s\', " +
                        "%s = \'%s\'" +
                        "%s %s = %s;",
                UPDATE, PATIENT, SET,
                FORENAME, patient.getForename(),
                PATRONYMIC, patient.getPatronymic(),
                SURNAME, patient.getSurname(),
                PHONE, patient.getPhone(),
                WHERE, ID, patient.getId());

        int changedRowsNum = executeUpdate(query);

        disconnect();
        return changedRowsNum;
    }

    @Override
    public int deletePatient(long id) throws SQLException, ClassNotFoundException {
        connect();

        final String query = String.format("%s %s %s %s %s = %s", DELETE, FROM, PATIENT, WHERE, ID, Long.toString(id));
        int changedRowsNum = executeUpdate(query);

        disconnect();
        return changedRowsNum;
    }

    @Override
    public List<Patient> getAllPatients() throws SQLException, ClassNotFoundException {
        connect();

        final String query = String.format("%s * %s %s", SELECT, FROM, PATIENT);
        ResultSet resultSet = executeQuery(query);

        List<Patient> patients = getPatientsFromResultSet(resultSet);

        disconnect();
        return patients;
    }

    private List<Patient> getPatientsFromResultSet(ResultSet resultSet) throws SQLException {
        List<Patient> patients = new LinkedList<Patient>();

        Patient patient;
        while (resultSet.next()) {
            patient = new Patient(
                    resultSet.getLong(ID),
                    resultSet.getString(FORENAME),
                    resultSet.getString(PATRONYMIC),
                    resultSet.getString(SURNAME),
                    resultSet.getString(PHONE));

            patients.add(patient);
        }

        return patients;
    }

    @Override
    public List<Patient> getPatientsBySurname(String surname) throws SQLException, ClassNotFoundException {
        connect();

        final String query = String.format("%s * %s %s " +
                        "%s %s ( %s ) %s %s (\'%s\')",
                SELECT, FROM, PATIENT,
                WHERE, LOWER, SURNAME, LIKE, LOWER, '%' + surname + '%');

        ResultSet resultSet = executeQuery(query);

        List<Patient> patients = getPatientsFromResultSet(resultSet);

        disconnect();
        return patients;
    }
}
