package com.haulmont.testtask.Dao;

import java.sql.*;

public abstract class Dao {
    protected String jdbcDriver = null;
    protected String dbUrl = null;

    protected String user = null;
    protected String password = null;

    private Connection connection = null;

    protected Dao(String jdbcDriver, String dbUrl, String user, String password) {
        this.jdbcDriver = jdbcDriver;
        this.dbUrl = dbUrl;
        this.user = user;
        this.password = password;
    }

    public void connect() throws ClassNotFoundException, SQLException {
        Class.forName(jdbcDriver);
        connection = DriverManager.getConnection(dbUrl, user, password);
    }

    public void disconnect() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public boolean execute(final String sqlQuery) throws SQLException {
        boolean result = false;

        if (connection != null) {
            Statement statement = connection.createStatement();
            result = statement.execute(sqlQuery);

            statement.close();
        }

        return result;
    }

    public ResultSet executeQuery(final String sqlQuery) throws SQLException {
        ResultSet resultSet = null;

        if (connection != null) {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlQuery);

            statement.close();
        }

        return resultSet;
    }

    public int executeUpdate(final String sqlQuery) throws SQLException {
        int changedRowsNum = -1;

        if (connection != null) {
            Statement statement = connection.createStatement();
            changedRowsNum = statement.executeUpdate(sqlQuery);

            statement.close();
        }

        return changedRowsNum;
    }
}