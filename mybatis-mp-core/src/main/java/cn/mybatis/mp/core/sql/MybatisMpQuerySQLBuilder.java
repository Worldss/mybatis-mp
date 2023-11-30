package cn.mybatis.mp.core.sql;

import db.sql.api.SqlBuilderContext;
import db.sql.api.cmd.executor.IQuery;
import db.sql.api.impl.tookit.SQLOptimizeUtils;

public class MybatisMpQuerySQLBuilder implements QuerySQLBuilder {
    @Override
    public StringBuilder buildQuerySQl(IQuery query, SqlBuilderContext context, boolean optimize) {
        if (optimize) {
            return SQLOptimizeUtils.getOptimizedSql(query, context);
        }
        return query.sql(context, new StringBuilder());
    }

    @Override
    public StringBuilder buildCountQuerySQl(IQuery query, SqlBuilderContext context, boolean optimize) {
        if (optimize) {
            return SQLOptimizeUtils.getOptimizedCountSql(query, context);
        }
        return query.sql(context, new StringBuilder());
    }

    @Override
    public StringBuilder buildCountSQLFromQuery(IQuery query, SqlBuilderContext context, boolean optimize) {
        return SQLOptimizeUtils.getCountSqlFromQuery(query, context, optimize);
    }
}
