package db.sql.api;

public interface Cmd {
    /**
     * 构建sql
     *
     * @param user       使用的组件
     * @param context    sql构建上下文
     * @param sqlBuilder 构建SQL的StringBuilder
     * @return SQL
     */
    StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder);

    /**
     * 是否包含某个sql命令
     *
     * @param cmd
     * @return 是否包含
     */
    boolean contain(Cmd cmd);
}
