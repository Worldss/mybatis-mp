package org.mybatis.mp.core.mybatis.typeHandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.mp.core.mybatis.provider.SQLCmdQueryContext;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLCmdQueryContextTypeHandler extends BaseTypeHandler<SQLCmdQueryContext> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, SQLCmdQueryContext queryContext, JdbcType jdbcType) throws SQLException {

    }

    @Override
    public SQLCmdQueryContext getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return null;
    }

    @Override
    public SQLCmdQueryContext getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return null;
    }

    @Override
    public SQLCmdQueryContext getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return null;
    }
}
