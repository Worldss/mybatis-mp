package org.mybatis.mp.core.db.reflect;


import org.apache.ibatis.mapping.ResultMapping;
import org.mybatis.mp.core.mybatis.configuration.MybatisConfiguration;

import java.lang.reflect.Field;

public class ResultTableFieldInfo {

    /**
     * 反射的属性字段
     */
    private final java.lang.reflect.Field reflectField;

    private final ResultMapping resultMapping;

    public ResultTableFieldInfo(java.lang.reflect.Field field, FieldInfo fieldInfo, String columnPrefix, MybatisConfiguration mybatisConfiguration) {
        this.reflectField = field;
        this.resultMapping = mybatisConfiguration.buildResultMapping(field, columnPrefix + fieldInfo.getColumnName(), fieldInfo.getFieldAnnotation().jdbcType(), fieldInfo.getFieldAnnotation().typeHandler());
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
