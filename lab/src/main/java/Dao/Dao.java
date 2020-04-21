package Dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Dao {
    void connect() throws ClassNotFoundException, SQLException;

    void disconnect() throws SQLException;

    boolean execute(final String sqlQuery) throws SQLException;

    ResultSet executeQuery(final String sqlQuery) throws SQLException;

    int executeUpdate(final String sqlQuery) throws SQLException;
}
