package cn.mybatis.mp.core.mybatis.mapper.context;

import cn.mybatis.mp.core.mybatis.provider.MybatisSqlBuilderContext;
import cn.mybatis.mp.core.sql.executor.BaseQuery;
import db.sql.api.DbType;
import db.sql.api.SQLMode;
import db.sql.api.impl.tookit.SQLOptimizeUtils;

import java.util.Objects;

public class SQLCmdCountFromQueryContext extends SQLCmdQueryContext {


    public SQLCmdCountFromQueryContext(BaseQuery execution, boolean optimize) {
        super(execution, optimize);
    }

    @Override
    public StringBuilder sql(String dbType) {
        if (Objects.nonNull(sql)) {
            return sql;
        }
        sqlBuilderContext = new MybatisSqlBuilderContext(DbType.getByName(dbType), SQLMode.PREPARED);
        sql = SQLOptimizeUtils.getCountSqlFromQuery(execution, sqlBuilderContext, new StringBuilder(), this.isOptimize());
        return sql;
    }
}
