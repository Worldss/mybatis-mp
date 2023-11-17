package cn.mybatis.mp.core.mybatis.mapper.context;

import cn.mybatis.mp.core.db.reflect.ModelInfo;
import cn.mybatis.mp.core.db.reflect.Models;
import cn.mybatis.mp.core.db.reflect.TableIds;
import cn.mybatis.mp.core.incrementer.IdentifierGenerator;
import cn.mybatis.mp.core.incrementer.IdentifierGeneratorFactory;
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

public class ModelInsertContext<T extends Model> extends SQLCmdInsertContext<AbstractInsert> implements SetIdMethod {

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
                    if (tableId.value() == IdAutoType.GENERATOR) {
                        isNeedInsert = true;
                        IdentifierGenerator identifierGenerator = IdentifierGeneratorFactory.getIdentifierGenerator(tableId.generatorName());
                        Object id=identifierGenerator.nextId(modelInfo.getType());
                        if (modelInfo.getIdFieldInfo().getField().getType() == String.class) {
                            id = id instanceof String ? id : String.valueOf(id);
                        }
                        value = id;
                        try {
                            modelInfo.getIdFieldInfo().getWriteFieldInvoker().invoke(t, new Object[]{id});
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
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
                //如果设置了id 则不在设置
                if (modelInfo.getIdFieldInfo().getReadFieldInvoker().invoke(this, null) != null) {
                    return;
                }
                if (modelInfo.getIdFieldInfo().getField().getType() == String.class) {
                    id = id instanceof String ? id : String.valueOf(id);
                }
                modelInfo.getIdFieldInfo().getWriteFieldInvoker().invoke(this.value, new Object[]{id});
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
