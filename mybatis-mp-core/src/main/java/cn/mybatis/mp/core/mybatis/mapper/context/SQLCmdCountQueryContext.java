package cn.mybatis.mp.core.mybatis.mapper.context;

import cn.mybatis.mp.core.mybatis.provider.MybatisSqlBuilderContext;
import cn.mybatis.mp.core.sql.executor.BaseQuery;
import db.sql.api.DbType;
import db.sql.api.SQLMode;
import db.sql.core.api.tookit.SQLOptimizeUtils;

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
        if (this.isOptimize()) {
            sqlBuilderContext = new MybatisSqlBuilderContext(DbType.getByName(dbType), SQLMode.PREPARED);
            sql = SQLOptimizeUtils.getOptimizedCountSql(execution, sqlBuilderContext, new StringBuilder());
        } else {
            sql = super.sql(dbType);
        }
        return sql;
    }
}
