package cn.mybatis.mp.core.mybatis.mapper.context;


import cn.mybatis.mp.core.mybatis.provider.MybatisSqlBuilderContext;
import db.sql.api.DbType;
import db.sql.api.SQLMode;
import db.sql.api.impl.cmd.executor.Executor;

import java.util.Objects;

public abstract class BaseSQLCmdContext<E extends Executor> implements SQLCmdContext<E> {

    protected final E execution;

    private final boolean usePlaceholder;

    protected MybatisSqlBuilderContext sqlBuilderContext;
    protected StringBuilder sql;

    public BaseSQLCmdContext(E execution) {
        this(execution, false);
    }

    public BaseSQLCmdContext(E execution, boolean usePlaceholder) {
        this.execution = execution;
        this.usePlaceholder = usePlaceholder;
    }

    @Override
public E getExecution() {
        return execution;
    }

    @Override
public StringBuilder sql(String dbType) {
        if (Objects.nonNull(sql)) {
            return sql;
        }
        sqlBuilderContext = new MybatisSqlBuilderContext(DbType.getByName(dbType), SQLMode.PREPARED);
        sql = execution.sql(sqlBuilderContext, new StringBuilder());
        return sql;
    }

    @Override
public Object[] getSQLCmdParams() {
        return sqlBuilderContext.getParams();
    }

}
