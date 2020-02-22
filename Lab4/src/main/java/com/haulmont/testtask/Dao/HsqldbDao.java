package com.haulmont.testtask.Dao;

public class HsqldbDao extends Dao {
    private static final String JDBC_DRIVER = "org.hsqldb.jdbc.JDBCDriver";

    public HsqldbDao(String dbUrl, String user, String password) {
        super(JDBC_DRIVER, dbUrl, user, password);
    }
}
