package org.mybatis.mp.core.mybatis.mapper.context;


import db.sql.core.api.cmd.Table;
import db.sql.core.api.cmd.executor.Update;
import org.mybatis.mp.core.db.reflect.TableInfo;
import org.mybatis.mp.core.db.reflect.TableInfos;
import org.mybatis.mp.core.mybatis.configuration.MybatisParameter;
import org.mybatis.mp.db.annotations.Field;

import java.util.Objects;

public class EntityUpdateContext<T> extends SQLCmdUpdateContext {

    private final T value;

    public EntityUpdateContext(T t) {
        super(createCmd(t));
        this.value = t;
    }

    private static Update createCmd(Object t) {
        TableInfo tableInfo = TableInfos.get(t.getClass());
        Update update = new Update() {{
            Table table = $.table(tableInfo.getBasic().getSchemaAndTableName());
            update(table);
            tableInfo.getFieldInfos().stream().forEach(item -> {
                Object value = item.getValue(t);
                if (item.isId()) {
                    if (Objects.isNull(value)) {
                        throw new RuntimeException(item.getReflectField().getName() + " can't be null");
                    }
                    eq($.field(table, item.getColumnName()), $.value(value));
                } else if (Objects.nonNull(value)) {
                    Field field = item.getFieldAnnotation();
                    MybatisParameter mybatisParameter = new MybatisParameter(value, field.typeHandler(), field.jdbcType());
                    set($.field(table, item.getColumnName()), $.value(mybatisParameter));
                }
            });
        }};
        return update;
    }


}
