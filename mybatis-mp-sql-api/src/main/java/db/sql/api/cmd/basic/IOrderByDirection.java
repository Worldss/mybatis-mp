package db.sql.api.cmd.basic;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;

/**
 * order by 方向
 */
public interface IOrderByDirection {
    /**
     * 构建sql
     *
     * @param module     模块的组件 例如 select ，order by
     * @param parent     使用改组件的组件
     * @param context    sql构建上下文
     * @param sqlBuilder 构建SQL的StringBuilder
     * @return SQL
     */
    StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder);

}
