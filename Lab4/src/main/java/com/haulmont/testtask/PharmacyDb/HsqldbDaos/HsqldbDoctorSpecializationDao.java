package com.haulmont.testtask.PharmacyDb.HsqldbDaos;

import com.haulmont.testtask.Dao.HsqldbDao;
import com.haulmont.testtask.PharmacyDb.Daos.DoctorSpecializationDao;
import com.haulmont.testtask.PharmacyDb.Dtos.DoctorSpecialization;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import static com.haulmont.testtask.Dao.SqlHelper.*;
import static com.haulmont.testtask.PharmacyDb.Daos.PharmacyDbDao.DOCTOR_SPECIALIZATION;

public class HsqldbDoctorSpecializationDao extends HsqldbDao implements DoctorSpecializationDao {
    public HsqldbDoctorSpecializationDao(String dbUrl, String user, String password) {
        super(dbUrl, user, password);
    }

    @Override
    public DoctorSpecialization findDoctorSpecialization(long id) throws SQLException, ClassNotFoundException {
        connect();

        final String query = String.format("%s * %s %s %s %s = %s", SELECT, FROM, DOCTOR_SPECIALIZATION, WHERE, ID, Long.toString(id));
        ResultSet resultSet = executeQuery(query);

        DoctorSpecialization doctorSpecialization = null;
        while (resultSet.next()) {
            doctorSpecialization = new DoctorSpecialization(
                    resultSet.getLong(ID),
                    resultSet.getString(NAME));
        }

        disconnect();
        return doctorSpecialization;
    }

    @Override
    public int insertDoctorSpecialization(DoctorSpecialization doctorSpecialization) throws SQLException, ClassNotFoundException {
        connect();

        final String query = String.format("%s %s %s (%s) %s (\'%s\');",
                INSERT, INTO, DOCTOR_SPECIALIZATION,
                NAME,
                VALUES,
                doctorSpecialization.getName());

        int changedRowsNum = executeUpdate(query);

        disconnect();
        return changedRowsNum;
    }

    @Override
    public int updateDoctorSpecialization(DoctorSpecialization doctorSpecialization) throws SQLException, ClassNotFoundException {
        connect();

        final String query = String.format("%s %s %s " +
                        "%s = \'%s\'" +
                        "%s %s = %s;",
                UPDATE, DOCTOR_SPECIALIZATION, SET,
                NAME, doctorSpecialization.getName(),
                WHERE, ID, doctorSpecialization.getId());

        int changedRowsNum = executeUpdate(query);

        disconnect();
        return changedRowsNum;
    }

    @Override
    public int deleteDoctorSpecialization(long id) throws SQLException, ClassNotFoundException {
        connect();

        final String query = String.format("%s %s %s %s %s = %s", DELETE, FROM, DOCTOR_SPECIALIZATION, WHERE, ID, Long.toString(id));
        int changedRowsNum = executeUpdate(query);

        disconnect();
        return changedRowsNum;
    }

    @Override
    public List<DoctorSpecialization> getAllDoctorSpecializations() throws SQLException, ClassNotFoundException {
        connect();

        final String query = String.format("%s * %s %s", SELECT, FROM, DOCTOR_SPECIALIZATION);
        ResultSet resultSet = executeQuery(query);

        List<DoctorSpecialization> doctorSpecializations = getDoctorSpecializationsFromResultSet(resultSet);

        disconnect();
        return doctorSpecializations;
    }

    private List<DoctorSpecialization> getDoctorSpecializationsFromResultSet(ResultSet resultSet) throws SQLException {
        List<DoctorSpecialization> doctorSpecializations = new LinkedList<DoctorSpecialization>();

        DoctorSpecialization doctorSpecialization;
        while (resultSet.next()) {
            doctorSpecialization = new DoctorSpecialization(
                    resultSet.getLong(ID),
                    resultSet.getString(NAME));

            doctorSpecializations.add(doctorSpecialization);
        }

        return doctorSpecializations;
    }

    @Override
    public List<DoctorSpecialization> getDoctorSpecializationsByName(String name) throws SQLException, ClassNotFoundException {
        connect();

        final String query = String.format("%s * %s %s " +
                        "%s %s ( %s ) %s %s (\'%s\')",
                SELECT, FROM, DOCTOR_SPECIALIZATION,
                WHERE, LOWER, NAME, LIKE, LOWER, '%' + name + '%');

        ResultSet resultSet = executeQuery(query);

        List<DoctorSpecialization> doctorSpecializations = getDoctorSpecializationsFromResultSet(resultSet);

        disconnect();
        return doctorSpecializations;
    }
}
