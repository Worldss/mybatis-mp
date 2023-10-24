package org.mybatis.mp.core.mybatis.mapper.context;

import db.sql.core.api.cmd.Table;
import db.sql.core.api.cmd.executor.AbstractInsert;
import db.sql.core.api.cmd.executor.Insert;
import org.mybatis.mp.core.db.reflect.TableInfo;
import org.mybatis.mp.core.db.reflect.TableInfos;
import org.mybatis.mp.core.mybatis.configuration.MybatisParameter;
import org.mybatis.mp.db.IdAutoType;
import org.mybatis.mp.db.annotations.TableField;

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
        TableInfo tableInfo = TableInfos.get(t.getClass());
        Insert insert = new Insert() {{
            Table table = $.table(tableInfo.getBasicInfo().getSchemaAndTableName());
            insert(table);
            List<Object> values = new ArrayList<>();
            tableInfo.getFieldInfos().stream().forEach(item -> {
                boolean isNeedInsert = false;
                Object value = item.getValue(t);

                if (Objects.nonNull(value)) {
                    isNeedInsert = true;
                } else if (item.isId()) {
                    if (item.getIdAnnotation().value() != IdAutoType.AUTO && item.getIdAnnotation().executeBefore()) {
                        isNeedInsert = true;
                        if (item.getIdAnnotation().executeBefore()) {
                            Supplier supplier = () -> item.getValue(t);
                            value = supplier;
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

    public void setId(Object id) {
        try {
            TableInfos.get(this.value.getClass()).getIdInfo().getWriteFieldInvoker().invoke(this.value, new Object[]{id});
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
