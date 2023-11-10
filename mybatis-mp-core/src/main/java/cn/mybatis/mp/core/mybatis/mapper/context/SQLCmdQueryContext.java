package cn.mybatis.mp.core.mybatis.mapper.context;

import cn.mybatis.mp.core.mybatis.provider.MybatisSqlBuilderContext;
import cn.mybatis.mp.core.sql.executor.Query;
import db.sql.api.DbType;
import db.sql.api.SQLMode;
import db.sql.core.api.tookit.SQLOptimizeUtils;

import java.util.Objects;

public class SQLCmdQueryContext extends BaseSQLCmdContext<Query> {

    public SQLCmdQueryContext(Query execution) {
        super(execution);
    }

    @Override
    public StringBuilder sql(String dbType) {
        if (Objects.nonNull(sql)) {
            return sql;
        }
        sqlBuilderContext = new MybatisSqlBuilderContext(DbType.getByName(dbType), SQLMode.PREPARED);
        sql = SQLOptimizeUtils.getOptimizedSql(this.execution,sqlBuilderContext,new StringBuilder());
        return sql;
    }
}
