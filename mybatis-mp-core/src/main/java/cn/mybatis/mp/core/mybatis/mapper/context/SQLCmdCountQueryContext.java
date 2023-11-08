package cn.mybatis.mp.core.mybatis.mapper.context;

import cn.mybatis.mp.core.mybatis.provider.MybatisSqlBuilderContext;
import cn.mybatis.mp.core.sql.executor.Query;
import db.sql.api.DbType;
import db.sql.api.SQLMode;

import java.util.Objects;

public class SQLCmdCountQueryContext<R> extends SQLCmdQueryContext<Query> {

    private final boolean optimize;

    public SQLCmdCountQueryContext(Query execution, boolean optimize) {
        super(execution);
        this.optimize = optimize;
    }

    @Override
    public StringBuilder sql(String dbType) {
        if (Objects.nonNull(sql)) {
            return sql;
        }
        sqlBuilderContext = new MybatisSqlBuilderContext(DbType.getByName(dbType), SQLMode.PREPARED);
        sql = execution.countSqlFromQuery(sqlBuilderContext, new StringBuilder(), this.optimize);
        return sql;
    }

    public boolean isOptimize() {
        return optimize;
    }


}
