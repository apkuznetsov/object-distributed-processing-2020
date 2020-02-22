package com.haulmont.testtask.PharmacyDb.HsqldbDaos;

import com.haulmont.testtask.Dao.HsqldbDao;
import com.haulmont.testtask.PharmacyDb.Daos.MedicalPrescriptionDao;
import com.haulmont.testtask.PharmacyDb.Dtos.MedicalPrescription;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import static com.haulmont.testtask.Dao.SqlHelper.*;
import static com.haulmont.testtask.PharmacyDb.Daos.PharmacyDbDao.MEDICAL_PRESCRIPTION;

public class HsqldbMedicalPrescriptionDao extends HsqldbDao implements MedicalPrescriptionDao {
    public HsqldbMedicalPrescriptionDao(String dbUrl, String user, String password) {
        super(dbUrl, user, password);
    }

    @Override
    public MedicalPrescription findMedicalPrescription(long id) throws SQLException, ClassNotFoundException {
        connect();

        final String query = String.format("%s * %s %s %s %s = %s;",
                SELECT, FROM, MEDICAL_PRESCRIPTION, WHERE, ID, Long.toString(id));

        ResultSet resultSet = executeQuery(query);

        MedicalPrescription medicalPrescription = null;
        while (resultSet.next()) {
            medicalPrescription = new MedicalPrescription(
                    resultSet.getLong(ID),
                    resultSet.getString(DESCRIPTION),
                    resultSet.getLong(PATIENT_ID),
                    resultSet.getLong(DOCTOR_ID),
                    resultSet.getTimestamp(CREATION_DATE),
                    resultSet.getTimestamp(VALIDITY_DATE),
                    resultSet.getByte(PRIORITY));
        }

        disconnect();
        return medicalPrescription;
    }

    @Override
    public int insertMedicalPrescription(MedicalPrescription medicalPrescription) throws SQLException, ClassNotFoundException {
        connect();

        final String query = String.format("%s %s %s (%s, %s, %s, %s, %s, %s) %s (\'%s\', %s, %s, %s \'%s\', %s \'%s\', %s);",
                INSERT, INTO, MEDICAL_PRESCRIPTION,
                DESCRIPTION, PATIENT_ID, DOCTOR_ID, CREATION_DATE, VALIDITY_DATE, PRIORITY,
                VALUES,
                medicalPrescription.getDescription(), medicalPrescription.getPatientId(), medicalPrescription.getDoctorId(),
                TIMESTAMP, medicalPrescription.getCreationDate(),
                TIMESTAMP, medicalPrescription.getValidityDate(),
                medicalPrescription.getPriority());

        int changedRowsNum = executeUpdate(query);

        disconnect();
        return changedRowsNum;
    }

    @Override
    public int updateMedicalPrescription(MedicalPrescription medicalPrescription) throws SQLException, ClassNotFoundException {
        connect();

        final String query = String.format("%s %s %s " +
                        "%s = \'%s\', " +
                        "%s = %s, " +
                        "%s = %s, " +
                        "%s = %s \'%s\', " +
                        "%s = %s \'%s\', " +
                        "%s = %s " +
                        "%s %s = %s;",
                UPDATE, MEDICAL_PRESCRIPTION, SET,
                DESCRIPTION, medicalPrescription.getDescription(),
                PATIENT_ID, medicalPrescription.getPatientId(),
                DOCTOR_ID, medicalPrescription.getDoctorId(),
                CREATION_DATE, TIMESTAMP, medicalPrescription.getCreationDate(),
                VALIDITY_DATE, TIMESTAMP, medicalPrescription.getValidityDate(),
                PRIORITY, medicalPrescription.getPriority(),
                WHERE, ID, medicalPrescription.getId());

        int changedRowsNum = executeUpdate(query);

        disconnect();
        return changedRowsNum;
    }

    @Override
    public int deleteMedicalPrescription(long id) throws SQLException, ClassNotFoundException {
        connect();

        final String query = String.format("%s %s %s %s %s = %s",
                DELETE, FROM, MEDICAL_PRESCRIPTION, WHERE, ID, Long.toString(id));

        int changedRowsNum = executeUpdate(query);

        disconnect();
        return changedRowsNum;
    }

    @Override
    public List<MedicalPrescription> getAllMedicalPrescriptions() throws SQLException, ClassNotFoundException {
        connect();

        final String query = String.format("%s * %s %s",
                SELECT, FROM, MEDICAL_PRESCRIPTION);

        ResultSet resultSet = executeQuery(query);

        List<MedicalPrescription> medicalPrescriptions = getMedicalPrescriptionsFromResultSet(resultSet);

        disconnect();
        return medicalPrescriptions;
    }

    private List<MedicalPrescription> getMedicalPrescriptionsFromResultSet(ResultSet resultSet) throws SQLException {
        List<MedicalPrescription> medicalPrescriptions = new LinkedList<MedicalPrescription>();

        MedicalPrescription medicalPrescription;
        while (resultSet.next()) {
            medicalPrescription = new MedicalPrescription(
                    resultSet.getLong(ID),
                    resultSet.getString(DESCRIPTION),
                    resultSet.getLong(PATIENT_ID),
                    resultSet.getLong(DOCTOR_ID),
                    resultSet.getTimestamp(CREATION_DATE),
                    resultSet.getTimestamp(VALIDITY_DATE),
                    resultSet.getByte(PRIORITY));

            medicalPrescriptions.add(medicalPrescription);
        }

        return medicalPrescriptions;
    }

    @Override
    public List<MedicalPrescription> getMedicalPrescriptionsByDescription(String description) throws SQLException, ClassNotFoundException {
        connect();

        final String query = String.format("%s * %s %s " +
                        "%s %s ( %s ) %s %s (\'%s\')",
                SELECT, FROM, MEDICAL_PRESCRIPTION,
                WHERE, LOWER, DESCRIPTION, LIKE, LOWER, '%' + description + '%');

        ResultSet resultSet = executeQuery(query);

        List<MedicalPrescription> medicalPrescriptions = getMedicalPrescriptionsFromResultSet(resultSet);

        disconnect();
        return medicalPrescriptions;
    }

    @Override
    public List<MedicalPrescription> getMedicalPrescriptionsByFilters(String description, long patientId, byte priority) throws SQLException, ClassNotFoundException {
        connect();

        StringBuilder sb = new StringBuilder();

        final String descriptionPartOfQuery = String.format("%s ( %s ) %s %s (\'%s\')",
                LOWER, DESCRIPTION, LIKE, LOWER, '%' + description + '%');
        sb.append(descriptionPartOfQuery);

        if (patientId > -1) {
            final String patientIdPartOfQuery = String.format("%s = %s", PATIENT_ID, patientId);
            sb.append(' ' + AND + ' ');
            sb.append(patientIdPartOfQuery);
        }

        if (priority > 0) {
            final String priorityPartOfQuery = String.format("%s = %s", PRIORITY, priority);
            sb.append(' ' + AND + ' ');
            sb.append(priorityPartOfQuery);
        }

        final String query = String.format("%s * %s %s " +
                        "%s %s",
                SELECT, FROM, MEDICAL_PRESCRIPTION,
                WHERE, sb.toString());

        ResultSet resultSet = executeQuery(query);

        List<MedicalPrescription> medicalPrescriptions = getMedicalPrescriptionsFromResultSet(resultSet);

        disconnect();
        return medicalPrescriptions;
    }
}
