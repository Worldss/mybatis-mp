package org.mybatis.mp.core.mybatis.configuration;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.DefaultResultSetHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.mp.core.mybatis.provider.SQLCmdQueryContext;

import java.util.Objects;

public class MpResultSetHandler extends DefaultResultSetHandler {
    private static MappedStatement create(MappedStatement ms, BoundSql boundSql) {
        if (ms.getSqlCommandType() != SqlCommandType.SELECT) {
            return ms;
        } else if (!(boundSql.getParameterObject() instanceof SQLCmdQueryContext)) {
            return ms;
        } else if (ms.getResultMaps().get(0).getType() != Object.class) {
            return ms;
        }
        SQLCmdQueryContext queryContext = (SQLCmdQueryContext) boundSql.getParameterObject();
        if (Objects.isNull(queryContext.getQuery().getReturnType())) {
            return ms;
        }
        return DynamicsMappedStatement.create(queryContext.getQuery().getReturnType(), ms);

    }

    public MpResultSetHandler(Executor executor, MappedStatement mappedStatement, ParameterHandler parameterHandler, ResultHandler<?> resultHandler, BoundSql boundSql, RowBounds rowBounds) {
        super(executor, create(mappedStatement, boundSql), parameterHandler, resultHandler, boundSql, rowBounds);
    }
}
