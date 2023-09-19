package org.mybatis.mp.core.db.reflect;

import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.reflection.invoker.GetFieldInvoker;
import org.mybatis.mp.core.mybatis.configuration.MybatisConfiguration;
import org.mybatis.mp.core.util.NamingUtil;
import org.mybatis.mp.core.util.StringPool;
import org.mybatis.mp.db.DbType;
import org.mybatis.mp.db.annotations.Field;
import org.mybatis.mp.db.annotations.Id;

import java.util.Objects;

public class FieldInfo {

    /**
     * 对应 table的基础信息
     */
    private final TableBasic tableBasic;
    /**
     * 反射的属性字段
     */
    private final java.lang.reflect.Field reflectField;
    private final String columnName;
    private final Field fieldAnnotation;
    private final Id idAnnotation;
    private final boolean id;
    private final ResultMapping resultMapping;
    private final GetFieldInvoker readFieldInvoker;

    private final db.sql.core.cmd.Field field;

    public FieldInfo(TableBasic tableBasic, java.lang.reflect.Field field, MybatisConfiguration mybatisConfiguration) {
        this.tableBasic = tableBasic;
        this.reflectField = field;

        Field fieldAnnotation = field.getAnnotation(Field.class);
        if (Objects.isNull(fieldAnnotation)) {
            fieldAnnotation = Default.defaultFieldAnnotation();
        }
        this.fieldAnnotation = fieldAnnotation;

        String columnName = fieldAnnotation.value();
        if (StringPool.EMPTY.equals(columnName)) {
            columnName = field.getName();
            if (mybatisConfiguration.isColumnUnderline()) {
                columnName = NamingUtil.camelToUnderline(columnName);
            }
        }
        this.columnName = columnName;

        this.idAnnotation = getIdAnnotation(mybatisConfiguration, field);

        this.id = Objects.nonNull(this.idAnnotation);

        this.resultMapping = mybatisConfiguration.buildResultMapping(field, columnName, fieldAnnotation.jdbcType(), fieldAnnotation.typeHandler());

        this.readFieldInvoker = new GetFieldInvoker(field);

        this.field = tableBasic.getTable().$(this.columnName);
    }

    public Object getValue(Object object) {
        try {
            return this.readFieldInvoker.invoke(object, null);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private final Id getIdAnnotation(MybatisConfiguration mybatisConfiguration, java.lang.reflect.Field field) {
        Id[] idAnnotations = field.getAnnotationsByType(Id.class);
        if (idAnnotations.length < 1) {
            return null;
        }
        Id id = null;
        for (Id item : idAnnotations) {
            if (Objects.isNull(id) && item.dbType() == DbType.DEFAULT) {
                id = item;
            }
            if (item.dbType().name().equals(mybatisConfiguration.getDatabaseId())) {
                id = item;
                break;
            }
        }
        if (Objects.isNull(id)) {
            id = idAnnotations[0];
        }
        return id;
    }

    public TableBasic getTableBasic() {
        return tableBasic;
    }

    public java.lang.reflect.Field getReflectField() {
        return reflectField;
    }

    public String getColumnName() {
        return columnName;
    }

    public Field getFieldAnnotation() {
        return fieldAnnotation;
    }

    public Id getIdAnnotation() {
        return idAnnotation;
    }

    public boolean isId() {
        return id;
    }

    public ResultMapping getResultMapping() {
        return resultMapping;
    }

    public GetFieldInvoker getReadFieldInvoker() {
        return readFieldInvoker;
    }

    public db.sql.core.cmd.Field getField() {
        return field;
    }
}
