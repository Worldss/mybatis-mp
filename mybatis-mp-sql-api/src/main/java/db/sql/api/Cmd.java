package db.sql.api;

public interface Cmd {
    StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder);
}
