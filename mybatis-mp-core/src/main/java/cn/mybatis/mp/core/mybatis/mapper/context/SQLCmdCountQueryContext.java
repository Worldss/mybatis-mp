package cn.mybatis.mp.core.mybatis.mapper.context;

import cn.mybatis.mp.core.mybatis.provider.MybatisSqlBuilderContext;
import cn.mybatis.mp.core.sql.executor.Query;
import db.sql.api.DbType;
import db.sql.api.SQLMode;
import db.sql.core.api.tookit.SQLOptimizeUtils;

import java.util.Objects;

public class SQLCmdCountQueryContext extends SQLCmdQueryContext {

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
        return SQLOptimizeUtils.getOptimizedCountSql(execution,sqlBuilderContext,new StringBuilder());
    }

    public boolean isOptimize() {
        return optimize;
    }


}
