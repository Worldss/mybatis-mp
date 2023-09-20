package org.mybatis.mp.core.mybatis.mapper.context;

import db.sql.core.SqlBuilderContext;
import db.sql.core.cmd.Value;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.UnknownTypeHandler;

public class MybatisPlaceholder implements Value {

    private final String name;

    private JdbcType jdbcType;

    private Class<? extends TypeHandler<?>> typeHandler;

    public MybatisPlaceholder(String name, JdbcType jdbcType, Class<? extends TypeHandler<?>> typeHandler) {
        this.name = name;
        this.jdbcType = jdbcType;
        this.typeHandler = typeHandler;
    }

    @Override
    public StringBuilder sql(SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = sqlBuilder.append("#{").append(name);
        if (jdbcType != JdbcType.UNDEFINED) {
            sqlBuilder.append(",jdbcType=").append(jdbcType.name());
        }
        if (typeHandler != UnknownTypeHandler.class) {
            sqlBuilder.append(",typeHandler=").append(typeHandler.getName());
        }
        sqlBuilder = sqlBuilder.append("}");
        return sqlBuilder;
    }
}
