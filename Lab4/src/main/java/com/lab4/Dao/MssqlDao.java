package com.lab4.Dao;

public class MssqlDao extends AbstractDao {
    private static final String JDBC_DRIVER = "org.hsqldb.jdbc.JDBCDriver";

    public MssqlDao(String dbUrl, String user, String password) {
        super(JDBC_DRIVER, dbUrl, user, password);
    }
}
