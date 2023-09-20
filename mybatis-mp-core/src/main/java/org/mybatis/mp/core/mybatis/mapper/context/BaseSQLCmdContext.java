package org.mybatis.mp.core.mybatis.mapper.context;


import db.sql.core.SQLMode;
import db.sql.core.cmd.execution.Execution;
import org.mybatis.mp.core.mybatis.provider.MybatisSqlBuilderContext;

import java.util.Objects;

public abstract class BaseSQLCmdContext<E extends Execution> implements SQLCmdContext<E> {

    private final E execution;

    private MybatisSqlBuilderContext sqlBuilderContext;

    public BaseSQLCmdContext(E execution) {
        this.execution = execution;
    }


    @Override
    public E getExecution() {
        return execution;
    }

    private StringBuilder sql;

    @Override
    public StringBuilder sql(String databaseId) {
        if (Objects.nonNull(sql)) {
            return sql;
        }
        sqlBuilderContext = new MybatisSqlBuilderContext(databaseId, SQLMode.PREPARED);
        sql = execution.sql(sqlBuilderContext, new StringBuilder());
        return sql;
    }

    @Override
    public Object[] getSQLCmdParams() {
        return sqlBuilderContext.getParams();
    }
}
