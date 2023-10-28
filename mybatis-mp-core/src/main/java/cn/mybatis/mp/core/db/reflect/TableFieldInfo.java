package cn.mybatis.mp.core.db.reflect;

import cn.mybatis.mp.core.util.TableInfoUtil;
import cn.mybatis.mp.core.mybatis.configuration.MybatisConfiguration;
import cn.mybatis.mp.db.annotations.TableField;
import cn.mybatis.mp.db.annotations.TableId;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.reflection.invoker.GetFieldInvoker;
import org.apache.ibatis.reflection.invoker.SetFieldInvoker;

import java.lang.reflect.Field;
import java.util.Objects;

public class TableFieldInfo {

    /**
     * 字段
     */
    private final Field field;

    /**
     * 列名
     */
    private final String columnName;

    /**
     * 字段对应的结果映射
     */
    private final ResultMapping resultMapping;


    /**
     * 字段读取反射方法
     */
    private final GetFieldInvoker readFieldInvoker;

    /**
     * TableField 注解信息
     */
    private final TableField tableFieldAnnotation;

    private final TableId tableIdAnnotation;

    private final boolean tableId;

    private final SetFieldInvoker writeFieldInvoker;

    public TableFieldInfo(MybatisConfiguration configuration, Field field) {
        this.field = field;
        this.tableFieldAnnotation = TableInfoUtil.getTableFieldAnnotation(field);
        this.columnName = TableInfoUtil.getFieldColumnName(configuration, field, this.tableFieldAnnotation);
        this.resultMapping = TableInfoUtil.getFieldResultMapping(configuration, field, columnName, this.tableFieldAnnotation.jdbcType(), this.tableFieldAnnotation.typeHandler());
        this.readFieldInvoker = new GetFieldInvoker(field);

        this.tableIdAnnotation = TableInfoUtil.getTableIdAnnotation(configuration, field);
        this.tableId = Objects.nonNull(this.tableIdAnnotation);
        this.writeFieldInvoker = new SetFieldInvoker(field);
    }

    public Object getValue(Object object) {
        try {
            return this.readFieldInvoker.invoke(object, null);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public Field getField() {
        return field;
    }

    public String getColumnName() {
        return this.columnName;
    }

    public ResultMapping getResultMapping() {
        return resultMapping;
    }

    public GetFieldInvoker getReadFieldInvoker() {
        return readFieldInvoker;
    }


    public TableField getFieldAnnotation() {
        return tableFieldAnnotation;
    }

    public TableId getTableIdAnnotation() {
        return tableIdAnnotation;
    }

    public boolean isTableId() {
        return tableId;
    }


    public SetFieldInvoker getWriteFieldInvoker() {
        return writeFieldInvoker;
    }
}
