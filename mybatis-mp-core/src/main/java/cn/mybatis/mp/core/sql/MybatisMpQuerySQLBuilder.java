package cn.mybatis.mp.core.sql;

import db.sql.api.SqlBuilderContext;
import db.sql.api.cmd.executor.IQuery;
import db.sql.api.impl.tookit.SQLOptimizeUtils;

public class MybatisMpQuerySQLBuilder implements QuerySQLBuilder {

    /**
     * 构建query sql
     *
     * @param query    查询
     * @param context  上下文
     * @param optimize 是否优化
     * @return
     */
    @Override
    public StringBuilder buildQuerySQl(IQuery query, SqlBuilderContext context, boolean optimize) {
        if (optimize) {
            return SQLOptimizeUtils.getOptimizedSql(query, context);
        }
        return query.sql(context, new StringBuilder());
    }

    /**
     * 构建count查询sql
     *
     * @param query    查询
     * @param context  上下文
     * @param optimize 是否优化
     * @return
     */
    @Override
    public StringBuilder buildCountQuerySQl(IQuery query, SqlBuilderContext context, boolean optimize) {
        if (optimize) {
            return SQLOptimizeUtils.getOptimizedCountSql(query, context);
        }
        return query.sql(context, new StringBuilder());
    }

    /**
     * 从query中构建count sql，一般用于分页时使用
     *
     * @param query    查询
     * @param context  上下文
     * @param optimize 是否优化
     * @return
     */
    @Override
    public StringBuilder buildCountSQLFromQuery(IQuery query, SqlBuilderContext context, boolean optimize) {
        return SQLOptimizeUtils.getCountSqlFromQuery(query, context, optimize);
    }
}
