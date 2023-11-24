package cn.mybatis.mp.core.mybatis.mapper.context;

import cn.mybatis.mp.core.mybatis.provider.MybatisSqlBuilderContext;
import cn.mybatis.mp.core.sql.executor.BaseQuery;
import db.sql.api.DbType;
import db.sql.api.SQLMode;
import db.sql.api.impl.tookit.SQLOptimizeUtils;

import java.util.Objects;

public class SQLCmdQueryContext extends BaseSQLCmdContext<BaseQuery> {

    private final boolean optimize;

    public SQLCmdQueryContext(BaseQuery execution, boolean optimize) {
        super(execution);
        this.optimize = optimize;
    }

    @Override
public StringBuilder sql(String dbType) {
        if (Objects.nonNull(sql)) {
            return sql;
        }
        sqlBuilderContext = new MybatisSqlBuilderContext(DbType.getByName(dbType), SQLMode.PREPARED);
        if (optimize) {
            sql = SQLOptimizeUtils.getOptimizedSql(this.execution, sqlBuilderContext, new StringBuilder());
        } else {
            sql = super.sql(dbType);
        }
        return sql;
    }

    public boolean isOptimize() {
        return optimize;
    }
}
