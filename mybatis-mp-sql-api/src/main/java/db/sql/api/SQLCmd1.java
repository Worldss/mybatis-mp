package db.sql.api;

public class SQLCmd1 implements Cmd{

    public final static SQLCmd1 INSTANCE=new SQLCmd1();

    @Override
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = sqlBuilder.append(" 1 ");
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return false;
    }
}
