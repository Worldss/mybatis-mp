package cn.mybatis.mp.core.db.reflect;


import cn.mybatis.mp.core.mybatis.configuration.MybatisConfiguration;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.UnknownTypeHandler;
import cn.mybatis.mp.db.annotations.ResultField;

import java.lang.reflect.Field;
import java.util.Objects;

public class ResultTableFieldInfo {

    /**
     * 反射的属性字段
     */
    private final java.lang.reflect.Field reflectField;

    private final ResultMapping resultMapping;

    public ResultTableFieldInfo(java.lang.reflect.Field field, FieldInfo fieldInfo, String columnPrefix, MybatisConfiguration mybatisConfiguration) {
        this(field, fieldInfo.getFieldAnnotation().jdbcType(), columnPrefix + fieldInfo.getColumnName(), fieldInfo.getFieldAnnotation().typeHandler(), mybatisConfiguration);
    }

    public ResultTableFieldInfo(java.lang.reflect.Field field, FieldInfo fieldInfo, ResultField resultField, MybatisConfiguration mybatisConfiguration) {
        this.reflectField = field;
        JdbcType jdbcType = resultField.jdbcType();
        Class<? extends TypeHandler<?>> typeHandler = resultField.typeHandler();
        if (Objects.nonNull(fieldInfo)) {
            if (jdbcType == JdbcType.UNDEFINED) {
                jdbcType = fieldInfo.getFieldAnnotation().jdbcType();
            }
            if (typeHandler == UnknownTypeHandler.class) {
                typeHandler = fieldInfo.getFieldAnnotation().typeHandler();
            }
        }
        String columnName = resultField.columnPrefix() + fieldInfo.getColumnName();

        this.resultMapping = mybatisConfiguration.buildResultMapping(field, columnName, jdbcType, typeHandler);
    }

    public ResultTableFieldInfo(java.lang.reflect.Field field, JdbcType jdbcType, String columnName, Class<? extends TypeHandler<?>> typeHandler, MybatisConfiguration mybatisConfiguration) {
        this.reflectField = field;
        this.resultMapping = mybatisConfiguration.buildResultMapping(field, columnName, jdbcType, typeHandler);
    }

    public ResultTableFieldInfo(java.lang.reflect.Field field, ResultMapping resultMapping) {
        this.reflectField = field;
        this.resultMapping = resultMapping;
    }

    public Field getReflectField() {
        return reflectField;
    }

    public ResultMapping getResultMapping() {
        return resultMapping;
    }
}
