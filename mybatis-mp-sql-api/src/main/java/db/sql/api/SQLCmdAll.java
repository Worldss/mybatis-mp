package db.sql.api;

public class SQLCmdAll implements Cmd {

    public final static SQLCmdAll INSTANCE = new SQLCmdAll();

    @Override
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = sqlBuilder.append(" * ");
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return false;
    }
}
