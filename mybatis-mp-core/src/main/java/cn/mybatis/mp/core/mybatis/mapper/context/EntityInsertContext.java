package cn.mybatis.mp.core.mybatis.mapper.context;

import cn.mybatis.mp.core.db.reflect.TableFieldInfo;
import cn.mybatis.mp.core.db.reflect.TableIds;
import cn.mybatis.mp.core.db.reflect.TableInfo;
import cn.mybatis.mp.core.db.reflect.Tables;
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
import java.util.function.Supplier;

public class EntityInsertContext<T> extends SQLCmdInsertContext<AbstractInsert> {

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
                    if (tableId.value() != IdAutoType.AUTO && tableId.executeBefore()) {
                        isNeedInsert = true;
                        Supplier supplier = () -> item.getValue(t);
                        value = supplier;
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

    public void setId(Object id) {
        try {
            TableFieldInfo tableFieldInfo = Tables.get(this.value.getClass()).getIdFieldInfo();
            tableFieldInfo.getWriteFieldInvoker().invoke(this.value, new Object[]{id});
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
