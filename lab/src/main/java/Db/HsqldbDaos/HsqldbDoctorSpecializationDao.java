package Db.HsqldbDaos;

import Dao.HsqldbDao;
import Db.Daos.DoctorSpecializationDao;
import Db.Dtos.DoctorSpecialization;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import static Db.DbDao.DOCTOR_SPECIALIZATION;
import static Db.Dtos.DoctorSpecialization.NAME;
import static Db.SqlHelper.*;


public class HsqldbDoctorSpecializationDao extends HsqldbDao implements DoctorSpecializationDao {
    public HsqldbDoctorSpecializationDao(String dbUrl, String user, String password) {
        super(dbUrl, user, password);
    }

    public DoctorSpecialization findDoctorSpecialization(long id) throws SQLException, ClassNotFoundException {
        connect();

        final String query = String.format("%s * %s %s %s %s = %s", SELECT, FROM, DOCTOR_SPECIALIZATION, WHERE, DoctorSpecialization.ID, Long.toString(id));
        ResultSet resultSet = executeQuery(query);

        DoctorSpecialization doctorSpecialization = null;
        while (resultSet.next()) {
            doctorSpecialization = new DoctorSpecialization(
                    resultSet.getLong(DoctorSpecialization.ID),
                    resultSet.getString(NAME));
        }

        disconnect();
        return doctorSpecialization;
    }

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

    public int updateDoctorSpecialization(DoctorSpecialization doctorSpecialization) throws SQLException, ClassNotFoundException {
        connect();

        final String query = String.format("%s %s %s " +
                        "%s = \'%s\'" +
                        "%s %s = %s;",
                UPDATE, DOCTOR_SPECIALIZATION, SET,
                NAME, doctorSpecialization.getName(),
                WHERE, DoctorSpecialization.ID, doctorSpecialization.getId());

        int changedRowsNum = executeUpdate(query);

        disconnect();
        return changedRowsNum;
    }

    public int deleteDoctorSpecialization(long id) throws SQLException, ClassNotFoundException {
        connect();

        final String query = "DELETE FROM doctor_specialization WHERE ID = ?";
        PreparedStatement preparedStatement = getConnection().prepareStatement(query);
        preparedStatement.setLong(1, id);
        int changedRowsNum = preparedStatement.executeUpdate();

        disconnect();
        return changedRowsNum;
    }

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
                    resultSet.getLong(DoctorSpecialization.ID),
                    resultSet.getString(NAME));

            doctorSpecializations.add(doctorSpecialization);
        }

        return doctorSpecializations;
    }

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
