package cn.mybatis.mp.core.mybatis.mapper.context;

import cn.mybatis.mp.core.db.reflect.*;
import cn.mybatis.mp.core.mybatis.configuration.MybatisParameter;
import cn.mybatis.mp.db.IdAutoType;
import cn.mybatis.mp.db.Model;
import cn.mybatis.mp.db.annotations.TableField;
import cn.mybatis.mp.db.annotations.TableId;
import db.sql.core.api.cmd.basic.Table;
import db.sql.core.api.cmd.executor.AbstractInsert;
import db.sql.core.api.cmd.executor.Insert;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class ModelInsertContext<T extends Model> extends SQLCmdInsertContext<AbstractInsert> {

    private final T value;

    public ModelInsertContext(T t) {
        super(createCmd(t));
        this.value = t;
    }

    private static Insert createCmd(Object t) {
        ModelInfo modelInfo = Models.get(t.getClass());
        Insert insert = new Insert() {{
            Table table = $.table(modelInfo.getTableInfo().getSchemaAndTableName());
            insert(table);
            List<Object> values = new ArrayList<>();
            modelInfo.getModelFieldInfos().stream().forEach(item -> {
                boolean isNeedInsert = false;
                Object value = item.getValue(t);

                if (Objects.nonNull(value)) {
                    isNeedInsert = true;
                } else if (item.getTableFieldInfo().isTableId()) {
                    TableId tableId = TableIds.get(modelInfo.getTableInfo().getType());
                    if (tableId.value() != IdAutoType.AUTO && tableId.executeBefore()) {
                        isNeedInsert = true;
                        Supplier supplier = () -> item.getValue(t);
                        value = supplier;
                    }
                }

                if (isNeedInsert) {
                    field($.field(table, item.getTableFieldInfo().getColumnName()));
                    TableField tableField = item.getTableFieldInfo().getFieldAnnotation();
                    MybatisParameter mybatisParameter = new MybatisParameter(value, tableField.typeHandler(), tableField.jdbcType());
                    values.add($.value(mybatisParameter));
                }
            });
            values(values);
        }};
        return insert;
    }

    public void setId(Object id) {
        try {
            ModelInfo modelInfo = Models.get(this.value.getClass());
            if (modelInfo.getIdFieldInfo() != null) {
                modelInfo.getIdFieldInfo().getWriteFieldInvoker().invoke(this.value, new Object[]{id});
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
