package cn.mybatis.mp.core.mybatis.mapper.context;

import cn.mybatis.mp.core.db.reflect.TableFieldInfo;
import cn.mybatis.mp.core.db.reflect.TableIds;
import cn.mybatis.mp.core.db.reflect.TableInfo;
import cn.mybatis.mp.core.db.reflect.Tables;
import cn.mybatis.mp.core.incrementer.IdentifierGenerator;
import cn.mybatis.mp.core.incrementer.IdentifierGeneratorFactory;
import cn.mybatis.mp.core.mybatis.configuration.MybatisParameter;
import cn.mybatis.mp.db.IdAutoType;
import cn.mybatis.mp.db.annotations.TableField;
import cn.mybatis.mp.db.annotations.TableId;
import db.sql.core.api.cmd.basic.Table;
import db.sql.core.api.cmd.executor.AbstractInsert;
import db.sql.core.api.cmd.executor.Insert;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EntityInsertContext<T> extends SQLCmdInsertContext<AbstractInsert> implements SetIdMethod {

    private final T value;

    public EntityInsertContext(T t) {
        super(createCmd(t));
        this.value = t;
    }

    private static Insert createCmd(Object t) {
        TableInfo tableInfo = Tables.get(t.getClass());
        Insert insert = new Insert() {{
            Table table = $.table(tableInfo.getSchemaAndTableName());
            insert(table);
            List<Object> values = new ArrayList<>();
            tableInfo.getTableFieldInfos().stream().forEach(item -> {
                boolean isNeedInsert = false;
                Object value = item.getValue(t);

                if (Objects.nonNull(value)) {
                    isNeedInsert = true;
                } else if (item.isTableId()) {
                    TableId tableId = TableIds.get(t.getClass());
                    if (tableId.value() == IdAutoType.GENERATOR) {
                        isNeedInsert = true;
                        IdentifierGenerator identifierGenerator = IdentifierGeneratorFactory.getIdentifierGenerator(tableId.generatorName());
                        Object id = identifierGenerator.nextId(tableInfo.getType());
                        if (tableInfo.getIdFieldInfo().getField().getType() == String.class) {
                            id = id instanceof String ? id : String.valueOf(id);
                        }
                        value = id;
                        try {
                            tableInfo.getIdFieldInfo().getWriteFieldInvoker().invoke(t, new Object[]{id});
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                if (isNeedInsert) {
                    field($.field(table, item.getColumnName()));
                    TableField tableField = item.getFieldAnnotation();
                    MybatisParameter mybatisParameter = new MybatisParameter(value, tableField.typeHandler(), tableField.jdbcType());
                    values.add($.value(mybatisParameter));
                }
            });
            values(values);
        }};
        return insert;
    }

    @Override
    public void setId(Object id) {
        try {
            TableFieldInfo idFieldInfo = Tables.get(this.value.getClass()).getIdFieldInfo();
            //如果设置了id 则不在设置
            if (idFieldInfo.getReadFieldInvoker().invoke(this, null) != null) {
                return;
            }
            if (idFieldInfo.getField().getType() == String.class) {
                id = id instanceof String ? id : String.valueOf(id);
            }
            idFieldInfo.getWriteFieldInvoker().invoke(this.value, new Object[]{id});
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
