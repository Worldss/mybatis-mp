package cn.mybatis.mp.core.mybatis.mapper.context;


import cn.mybatis.mp.core.mybatis.provider.MybatisSqlBuilderContext;
import db.sql.api.DbType;
import db.sql.api.SQLMode;
import db.sql.api.impl.cmd.executor.Executor;

import java.util.Objects;

public abstract class BaseSQLCmdContext<E extends Executor> implements SQLCmdContext<E> {

    protected final E execution;

    protected MybatisSqlBuilderContext sqlBuilderContext;

    protected String sql;

    public BaseSQLCmdContext(E execution) {
        this.execution = execution;
    }

    @Override
    public E getExecution() {
        return execution;
    }

    @Override
    public String sql(String dbType) {
        if (Objects.nonNull(sql)) {
            return sql;
        }
        sqlBuilderContext = new MybatisSqlBuilderContext(DbType.getByName(dbType), SQLMode.PREPARED);
        sql = execution.sql(sqlBuilderContext, new StringBuilder()).toString();
        return sql;
    }

    @Override
    public Object[] getSQLCmdParams() {
        return sqlBuilderContext.getParams();
    }


}
