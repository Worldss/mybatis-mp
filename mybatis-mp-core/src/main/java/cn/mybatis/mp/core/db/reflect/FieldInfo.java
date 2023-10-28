package cn.mybatis.mp.core.db.reflect;

import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.reflection.invoker.GetFieldInvoker;

import java.lang.reflect.Field;

public class FieldInfo {

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

    public FieldInfo(Field field, String columnName, ResultMapping resultMapping) {
        this.field = field;
        this.columnName = columnName;
        this.resultMapping = resultMapping;
        this.readFieldInvoker = new GetFieldInvoker(field);
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
}
