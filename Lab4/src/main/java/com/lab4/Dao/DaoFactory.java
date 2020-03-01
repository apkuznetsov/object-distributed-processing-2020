package com.lab4.Dao;

public abstract class DaoFactory {
    public enum DaoTypes {MSSQL}

    private static DaoTypes daoType = DaoTypes.MSSQL;

    public static Dao createInstance(String dbUrl, String user, String password) {
        switch (daoType) {
            case MSSQL:
                return new MssqlDao(dbUrl, user, password);
            default:
                return null;
        }
    }

    public static Dao createInstance(DaoTypes daoType, String dbUrl, String user, String password) {
        switch (daoType) {
            case MSSQL:
                return new MssqlDao(dbUrl, user, password);
            default:
                return null;
        }
    }
}
