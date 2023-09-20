package org.mybatis.mp.core.mybatis.configuration;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.mybatis.mp.core.mybatis.mapper.context.SQLCmdContext;
import org.mybatis.mp.core.mybatis.mapper.context.SQLCmdQueryContext;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLCmdParameterHandler implements ParameterHandler {

    private final SQLCmdContext cmdContext;

    public SQLCmdParameterHandler(SQLCmdContext cmdContext) {
        this.cmdContext = cmdContext;
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
            preparedStatement.setObject(i + 1, params[i]);
        }
    }
}
