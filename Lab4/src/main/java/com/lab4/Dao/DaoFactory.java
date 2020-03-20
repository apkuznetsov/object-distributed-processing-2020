package com.lab4.Dao;

public abstract class DaoFactory {
    private static DaoTypes daoType = DaoTypes.HSQLDB;

    public static Dao createInstance(String dbUrl, String user, String password) {
        switch (daoType) {
            case HSQLDB:
                return new HsqldbDao(dbUrl, user, password);
            default:
                return null;
        }
    }

    public static Dao createInstance(DaoTypes daoType, String dbUrl, String user, String password) {
        switch (daoType) {
            case HSQLDB:
                return new HsqldbDao(dbUrl, user, password);
            default:
                return null;
        }
    }

    public enum DaoTypes {HSQLDB}
}
