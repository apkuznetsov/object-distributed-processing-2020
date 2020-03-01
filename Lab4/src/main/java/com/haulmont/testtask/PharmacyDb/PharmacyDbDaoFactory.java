package com.haulmont.testtask.PharmacyDb;

import com.haulmont.testtask.Dao.DaoFactory;
import com.haulmont.testtask.PharmacyDb.HsqldbDaos.MssqlPharmacyDbDao;

public class PharmacyDbDaoFactory {
    private static DaoFactory.DaoTypes daoType = DaoFactory.DaoTypes.MSSQL;

    public static PharmacyDbDao createInstance(String dbUrl, String user, String password) {
        switch (daoType) {
            case MSSQL:
                return new MssqlPharmacyDbDao(dbUrl, user, password);
            default:
                return null;
        }
    }

    public static PharmacyDbDao createInstance(DaoFactory.DaoTypes daoType, String dbUrl, String user, String password) {
        switch (daoType) {
            case MSSQL:
                return new MssqlPharmacyDbDao(dbUrl, user, password);
            default:
                return null;
        }
    }
}
