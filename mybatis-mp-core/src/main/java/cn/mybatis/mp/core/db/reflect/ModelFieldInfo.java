package cn.mybatis.mp.core.db.reflect;

import org.apache.ibatis.reflection.invoker.GetFieldInvoker;
import org.apache.ibatis.reflection.invoker.SetFieldInvoker;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.Objects;

public class ModelFieldInfo {

    /**
     * 表字段信息
     */
    private final TableFieldInfo tableFieldInfo;

    private final Field field;

    /**
     * 字段读取反射方法
     */
    private final GetFieldInvoker readFieldInvoker;


    private final SetFieldInvoker writeFieldInvoker;

    public ModelFieldInfo(Class entity, Class model, Field field) {
        TableInfo tableInfo = Tables.get(entity);
        this.tableFieldInfo = tableInfo.getFieldInfo(field.getName());
        if (Objects.isNull(this.tableFieldInfo)) {
            throw new RuntimeException(MessageFormat.format("unable match field {0} in class {1} ,The field {2} can't found in entity class {3}", field.getName(), model.getName(), field.getName(), entity.getName()));
        }
        this.field = field;
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

    public TableFieldInfo getTableFieldInfo() {
        return tableFieldInfo;
    }

    public Field getField() {
        return field;
    }

    public GetFieldInvoker getReadFieldInvoker() {
        return readFieldInvoker;
    }

    public SetFieldInvoker getWriteFieldInvoker() {
        return writeFieldInvoker;
    }
}
