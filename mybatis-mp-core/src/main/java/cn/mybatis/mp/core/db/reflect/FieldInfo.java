package cn.mybatis.mp.core.db.reflect;

import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.reflection.invoker.GetFieldInvoker;
import org.apache.ibatis.reflection.invoker.SetFieldInvoker;
import cn.mybatis.mp.core.mybatis.configuration.MybatisConfiguration;
import cn.mybatis.mp.core.util.NamingUtil;
import cn.mybatis.mp.core.util.StringPool;
import cn.mybatis.mp.db.DbType;
import cn.mybatis.mp.db.annotations.TableField;
import cn.mybatis.mp.db.annotations.TableId;

import java.util.Objects;

public class FieldInfo {

    /**
     * 对应 table的基础信息
     */
    private final TableBasicInfo tableBasicInfo;
    /**
     * 反射的属性字段
     */
    private final java.lang.reflect.Field reflectField;
    private final String columnName;
    private final TableField tableFieldAnnotation;
    private final TableId idAnnotation;
    private final boolean id;
    private final ResultMapping resultMapping;
    private final GetFieldInvoker readFieldInvoker;
    private final SetFieldInvoker writeFieldInvoker;

    public FieldInfo(TableBasicInfo tableBasicInfo, java.lang.reflect.Field field, MybatisConfiguration mybatisConfiguration) {
        this.tableBasicInfo = tableBasicInfo;
        this.reflectField = field;

        TableField tableFieldAnnotation = field.getAnnotation(TableField.class);
        if (Objects.isNull(tableFieldAnnotation)) {
            tableFieldAnnotation = Default.defaultTableFieldAnnotation();
        }
        this.tableFieldAnnotation = tableFieldAnnotation;

        String columnName = tableFieldAnnotation.value();
        if (StringPool.EMPTY.equals(columnName)) {
            columnName = field.getName();
            if (mybatisConfiguration.isColumnUnderline()) {
                columnName = NamingUtil.camelToUnderline(columnName);
            }
        }
        this.columnName = columnName;

        this.idAnnotation = getIdAnnotation(mybatisConfiguration, field);

        this.id = Objects.nonNull(this.idAnnotation);

        this.resultMapping = mybatisConfiguration.buildResultMapping(field, columnName, tableFieldAnnotation.jdbcType(), tableFieldAnnotation.typeHandler());

        this.readFieldInvoker = new GetFieldInvoker(field);
        this.writeFieldInvoker = new SetFieldInvoker(field);
    }

    public Object getValue(Object object) {
        try {
            return this.readFieldInvoker.invoke(object, null);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private final TableId getIdAnnotation(MybatisConfiguration mybatisConfiguration, java.lang.reflect.Field field) {
        TableId[] tableIdAnnotations = field.getAnnotationsByType(TableId.class);
        if (tableIdAnnotations.length < 1) {
            return null;
        }
        TableId tableId = null;
        for (TableId item : tableIdAnnotations) {
            if (Objects.isNull(tableId) && item.dbType() == DbType.DEFAULT) {
                tableId = item;
            }
            if (item.dbType().name().equals(mybatisConfiguration.getDatabaseId())) {
                tableId = item;
                break;
            }
        }
        if (Objects.isNull(tableId)) {
            tableId = tableIdAnnotations[0];
        }
        return tableId;
    }

    public TableBasicInfo getTableBasic() {
        return tableBasicInfo;
    }

    public java.lang.reflect.Field getReflectField() {
        return reflectField;
    }

    public String getColumnName() {
        return columnName;
    }

    public TableField getFieldAnnotation() {
        return tableFieldAnnotation;
    }

    public TableId getIdAnnotation() {
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

    public SetFieldInvoker getWriteFieldInvoker() {
        return writeFieldInvoker;
    }

}
