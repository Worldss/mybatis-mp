package org.mybatis.mp.core.mybatis.configuration;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.mp.core.mybatis.mapper.context.SQLCmdContext;
import org.mybatis.mp.core.mybatis.mapper.context.SQLCmdQueryContext;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLCmdParameterHandler implements ParameterHandler {

    private final SQLCmdContext cmdContext;

    private final Configuration configuration;

    public SQLCmdParameterHandler(Configuration configuration, SQLCmdContext cmdContext) {
        this.cmdContext = cmdContext;
        this.configuration = configuration;
    }

    @Override
    public Object getParameterObject() {
        return cmdContext;
    }

    @Override
    public void setParameters(PreparedStatement preparedStatement) throws SQLException {
        Object[] params = cmdContext.getSQLCmdParams();
        int length = params.length;
        for (int i = 0; i < length; i++) {
            Object value = params[i];
            TypeHandler typeHandler = configuration.getTypeHandlerRegistry().getTypeHandler(value.getClass());
            if (typeHandler != null) {
                typeHandler.setParameter(preparedStatement, i + 1, value, JdbcType.UNDEFINED);
            } else {
                preparedStatement.setObject(i + 1, value);
            }
        }
    }
}
