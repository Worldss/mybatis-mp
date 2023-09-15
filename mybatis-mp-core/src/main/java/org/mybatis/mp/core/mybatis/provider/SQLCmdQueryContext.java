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

    public Query<R> getQuery() {
        return query;
    }

    private volatile String sql;

    public String sql(String databaseId) {
        if (Objects.nonNull(sql)) {
            return sql;
        }
        mybatisSqlBuilderContext = new MybatisSqlBuilderContext(databaseId, SQLMode.PREPARED);
        sql = query.sql(mybatisSqlBuilderContext, new StringBuilder()).toString();
        return sql;
    }

    public Object[] getSQLCmdParams() {
        return mybatisSqlBuilderContext.getParams();
    }
}
