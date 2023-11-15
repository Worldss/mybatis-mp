package db.sql.api;

public enum DbType {

    H2,

    MYSQL,

    SQL_SERVER,

    PGSQL,

    ORACLE;

    public static DbType getByName(String name) {
        DbType[] dbTypes = values();
        for (DbType dbType : dbTypes) {
            if (dbType.name().equals(name)) {
                return dbType;
            }
        }
        return MYSQL;
    }

}
