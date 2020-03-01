package com.lab4.PharmacyDb;

import com.lab4.Dao.DaoFactory;
import com.lab4.PharmacyDb.HsqldbDaos.MssqlPharmacyDbDao;

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
