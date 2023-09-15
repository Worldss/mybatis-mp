package org.mybatis.mp.core.mybatis.configuration;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.mybatis.mp.core.mybatis.provider.SQLCmdQueryContext;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLCmdParamHandler implements ParameterHandler {

    private final SQLCmdQueryContext queryContext;

    public SQLCmdParamHandler(SQLCmdQueryContext queryContext){
        this.queryContext=queryContext;
    }

    @Override
    public Object getParameterObject() {
        return queryContext;
    }

    @Override
    public void setParameters(PreparedStatement preparedStatement) throws SQLException {
        Object[] params=queryContext.getSQLCmdParams();
        int length=params.length;
        for(int i=0;i<length;i++){
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.xxxxxxxxxxxxxxx"+params[i]);
            preparedStatement.setObject(i+1,params[i]);
        }
    }
}
