package org.mybatis.mp.core.mybatis.provider;

import db.sql.core.SQLMode;
import org.mybatis.mp.core.query.Query;

import java.util.Objects;

public class SQLCmdQueryContext<R> {

    private final Query<R> query;

    private MybatisSqlBuilderContext mybatisSqlBuilderContext;

    public SQLCmdQueryContext(Query<R> query) {
        this.query = query;
    }

    private volatile StringBuilder sql;
    private volatile Object[] params;

    public Query getQuery() {
        return query;
    }

    public StringBuilder sql(String databaseId) {
        if (Objects.nonNull(sql)) {
            return sql;
        }
        mybatisSqlBuilderContext = new MybatisSqlBuilderContext(databaseId, SQLMode.PREPARED);
        sql = query.sql(mybatisSqlBuilderContext, new StringBuilder());
        return sql;
    }

    public Object[] getSQLCmdParams() {
        if (params == null) {
            params = mybatisSqlBuilderContext.getParams();
        }
        return params;
    }
}
