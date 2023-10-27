package db.sql.api;

public class SqlBuilderContext {

    private final DatabaseId databaseId;

    private final SQLMode sqlMode;

    public SqlBuilderContext(DatabaseId databaseId, SQLMode sqlMode) {
        this.databaseId = databaseId;
        this.sqlMode = sqlMode;
    }

    public DatabaseId getDatabaseId() {
        return databaseId;
    }

    public SQLMode getSqlMode() {
        return sqlMode;
    }

    /**
     * 添加设置参数 返回参数名字
     *
     * @param value
     * @return
     */
    public String addParam(Object value) {
        return "?";
    }
}
