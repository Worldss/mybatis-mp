package cn.mybatis.mp.core.mybatis.mapper.context;


import cn.mybatis.mp.core.db.reflect.TableInfo;
import cn.mybatis.mp.core.db.reflect.Tables;
import cn.mybatis.mp.core.mybatis.configuration.MybatisParameter;
import cn.mybatis.mp.core.sql.executor.Update;
import cn.mybatis.mp.db.annotations.TableField;
import db.sql.core.api.cmd.Table;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public class EntityUpdateContext<T> extends SQLCmdUpdateContext {

    private final T value;

    public EntityUpdateContext(T t) {
        this(t, Collections.emptySet());
    }

    public EntityUpdateContext(T t, Set<String> forceUpdateFields) {
        super(createCmd(t, forceUpdateFields));
        this.value = t;
    }

    private static Update createCmd(Object t, Set<String> forceUpdateFields) {
        TableInfo tableInfo = Tables.get(t.getClass());
        Update update = new Update() {{
            Table table = $.table(tableInfo.getSchemaAndTableName());
            update(table);
            tableInfo.getTableFieldInfos().stream().forEach(item -> {
                Object value = item.getValue(t);
                if (item.isTableId()) {
                    if (Objects.isNull(value)) {
                        throw new RuntimeException(item.getField().getName() + " can't be null");
                    }
                    eq($.field(table, item.getColumnName()), $.value(value));
                } else if (forceUpdateFields.contains(item.getField().getName())) {
                    set($.field(table, item.getColumnName()), Objects.isNull(value) ? $.NULL() : $.value(value));
                } else if (!item.getFieldAnnotation().update()) {
                    return;
                } else if (Objects.nonNull(value)) {
                    TableField tableField = item.getFieldAnnotation();
                    MybatisParameter mybatisParameter = new MybatisParameter(value, tableField.typeHandler(), tableField.jdbcType());
                    set($.field(table, item.getColumnName()), $.value(mybatisParameter));
                }
            });
        }};
        return update;
    }
}
