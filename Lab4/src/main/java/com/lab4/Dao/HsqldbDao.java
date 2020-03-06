package com.lab4.Dao;

public class HsqldbDao extends AbstractDao {
    private static final String JDBC_DRIVER = "org.hsqldb.jdbc.JDBCDriver";

    public HsqldbDao(String dbUrl, String user, String password) {
        super(JDBC_DRIVER, dbUrl, user, password);
    }
}
