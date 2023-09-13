package org.mybatis.mp.core.db.reflect;


import org.apache.ibatis.mapping.ResultMapping;
import org.mybatis.mp.core.mybatis.configuration.MybatisConfiguration;

import java.lang.reflect.Field;

public class ResultTableFieldInfo {

    /**
     * 反射的属性字段
     */
    private final java.lang.reflect.Field reflectField;

    private final FieldInfo fieldInfo;

    private final ResultMapping resultMapping;

    public ResultTableFieldInfo(java.lang.reflect.Field field, FieldInfo fieldInfo, MybatisConfiguration mybatisConfiguration) {
        this.reflectField = field;
        this.fieldInfo = fieldInfo;
        resultMapping = mybatisConfiguration.buildResultMapping(field, fieldInfo.getColumnName(), fieldInfo.getFieldAnnotation().jdbcType(), fieldInfo.getFieldAnnotation().typeHandler());
    }

    public Field getReflectField() {
        return reflectField;
    }

    public FieldInfo getFieldInfo() {
        return fieldInfo;
    }

    public ResultMapping getResultMapping() {
        return resultMapping;
    }
}
