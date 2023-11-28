package cn.mybatis.mp.core.mybatis.mapper.context;

import cn.mybatis.mp.core.MybatisMpConfig;
import cn.mybatis.mp.core.mybatis.provider.MybatisSqlBuilderContext;
import cn.mybatis.mp.core.sql.executor.BaseQuery;
import db.sql.api.DbType;
import db.sql.api.SQLMode;

import java.util.Objects;

public class SQLCmdCountQueryContext extends SQLCmdQueryContext {


    public SQLCmdCountQueryContext(BaseQuery execution, boolean optimize) {
        super(execution, optimize);
    }

    @Override
    public StringBuilder sql(String dbType) {
        if (Objects.nonNull(sql)) {
            return sql;
        }
        sqlBuilderContext = new MybatisSqlBuilderContext(DbType.getByName(dbType), SQLMode.PREPARED);
        sql = MybatisMpConfig.getQuerySQLBuilder().buildCountQuerySQl(execution, sqlBuilderContext, this.isOptimize());
        return sql;
    }
}
