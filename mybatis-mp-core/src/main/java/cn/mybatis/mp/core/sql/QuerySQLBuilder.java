package cn.mybatis.mp.core.sql;

import db.sql.api.SqlBuilderContext;
import db.sql.api.cmd.executor.IQuery;

public interface QuerySQLBuilder {

    /**
     * 构建query sql
     *
     * @param query    查询
     * @param context  上下文
     * @param optimize 是否优化
     * @return
     */
    StringBuilder buildQuerySQl(IQuery query, SqlBuilderContext context, boolean optimize);


    /**
     * 构建 count query sql
     *
     * @param query    查询
     * @param context  上下文
     * @param optimize 是否优化
     * @return
     */
    StringBuilder buildCountQuerySQl(IQuery query, SqlBuilderContext context, boolean optimize);

    /**
     * 从query 中 获取 count 查询 sql
     *
     * @param query    查询
     * @param context  上下文
     * @param optimize 是否优化
     * @return
     */
    StringBuilder buildCountSQLFromQuery(IQuery query, SqlBuilderContext context, boolean optimize);

}
