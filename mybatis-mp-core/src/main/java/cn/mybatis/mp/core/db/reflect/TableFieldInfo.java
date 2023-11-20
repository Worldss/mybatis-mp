package cn.mybatis.mp.core.db.reflect;

import cn.mybatis.mp.core.util.TableInfoUtil;
import cn.mybatis.mp.db.annotations.TableField;
import cn.mybatis.mp.db.annotations.TableId;
import cn.mybatis.mp.db.annotations.TenantId;
import cn.mybatis.mp.db.annotations.Version;
import org.apache.ibatis.reflection.invoker.GetFieldInvoker;
import org.apache.ibatis.reflection.invoker.SetFieldInvoker;

import java.lang.reflect.Field;

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
     * 字段读取反射方法
     */
    private final GetFieldInvoker readFieldInvoker;

    /**
     * TableField 注解信息
     */
    private final TableField tableFieldAnnotation;


    private final boolean tableId;

    private final boolean version;

    private final boolean tenantId;

    private final SetFieldInvoker writeFieldInvoker;

    public TableFieldInfo(Field field) {
        this.field = field;
        this.tableFieldAnnotation = TableInfoUtil.getTableFieldAnnotation(field);
        this.columnName = TableInfoUtil.getFieldColumnName(field);
        this.readFieldInvoker = new GetFieldInvoker(field);
        this.tableId = field.isAnnotationPresent(TableId.class);
        this.version = field.isAnnotationPresent(Version.class);
        this.tenantId = field.isAnnotationPresent(TenantId.class);
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

    public GetFieldInvoker getReadFieldInvoker() {
        return readFieldInvoker;
    }


    public TableField getTableFieldAnnotation() {
        return tableFieldAnnotation;
    }

    public boolean isTableId() {
        return tableId;
    }

    public boolean isVersion() {
        return version;
    }

    public boolean isTenantId() {
        return tenantId;
    }

    public SetFieldInvoker getWriteFieldInvoker() {
        return writeFieldInvoker;
    }
}
