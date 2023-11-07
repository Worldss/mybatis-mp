package db.sql.api;

public class Count1 implements Cmd {

    public final static Count1 INSTANCE=new Count1();

    @Override
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        return sqlBuilder.append(" COUNT(1) ");
    }

    @Override
    public boolean contain(Cmd cmd) {
        return false;
    }
}
