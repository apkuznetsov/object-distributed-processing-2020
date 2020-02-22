package com.haulmont.testtask.Dao;

public abstract class DaoFactory {
    public enum DaoTypes {HSQLDB}

    public static Dao getDao(DaoTypes daoType, String dbUrl, String user, String password) {
        switch (daoType) {
            case HSQLDB:
                return new HsqldbDao(dbUrl, user, password);
            default:
                return null;
        }
    }
}
