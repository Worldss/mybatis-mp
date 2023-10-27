package db.sql.core.api;

public enum DatabaseId implements db.sql.api.DatabaseId {

    MYSQL;

    public static DatabaseId getByName(String name) {
        DatabaseId[] databaseIds = values();
        for (DatabaseId databaseId : databaseIds) {
            if (databaseId.name().equals(name)) {
                return databaseId;
            }
        }
        return DatabaseId.MYSQL;
    }
}
