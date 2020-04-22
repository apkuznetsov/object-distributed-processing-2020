package DoctorDb.Daos;

import DoctorDb.Dtos.Doctor;
import DoctorDb.Dtos.DoctorWithSpecializationName;

import java.sql.SQLException;
import java.util.List;

public interface DoctorDao {
    Doctor findDoctor(long id) throws SQLException, ClassNotFoundException;

    int insertDoctor(Doctor doctor) throws SQLException, ClassNotFoundException;

    int updateDoctor(Doctor doctor) throws SQLException, ClassNotFoundException;

    int deleteDoctor(long id) throws SQLException, ClassNotFoundException;

    List<Doctor> getAllDoctors() throws SQLException, ClassNotFoundException;

    public List<DoctorWithSpecializationName> getAllDoctorsWithSpecializationName() throws SQLException, ClassNotFoundException;

    List<Doctor> getDoctorsBySurname(String surname) throws SQLException, ClassNotFoundException;
}
