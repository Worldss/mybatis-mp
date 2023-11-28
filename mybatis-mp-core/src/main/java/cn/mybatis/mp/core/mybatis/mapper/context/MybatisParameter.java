package cn.mybatis.mp.core.mybatis.mapper.context;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

public class MybatisParameter {

    private final Object value;

    private final Class<? extends TypeHandler<?>> typeHandler;

    private final JdbcType jdbcType;

    public MybatisParameter(Object value, Class<? extends TypeHandler<?>> typeHandler, JdbcType jdbcType) {
        this.typeHandler = typeHandler;
        this.jdbcType = jdbcType;
        this.value = value;
    }

    public Class<? extends TypeHandler<?>> getTypeHandler() {
        return typeHandler;
    }

    public JdbcType getJdbcType() {
        return jdbcType;
    }

    public Object getValue() {
        return value;
    }
}
