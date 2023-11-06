package db.sql.api;

public class SqlBuilderContext {

    private final DbType dbType;

    private final SQLMode sqlMode;

    public SqlBuilderContext(DbType dbType, SQLMode sqlMode) {
        this.dbType = dbType;
        this.sqlMode = sqlMode;
    }

    public DbType getDbType() {
        return dbType;
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
