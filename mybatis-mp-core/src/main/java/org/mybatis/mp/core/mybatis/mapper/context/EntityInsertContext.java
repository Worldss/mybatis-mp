package org.mybatis.mp.core.mybatis.mapper.context;

import db.sql.core.SqlBuilderContext;
import db.sql.core.cmd.CmdFactory;
import db.sql.core.cmd.Value;
import db.sql.core.cmd.execution.Insert;
import org.mybatis.mp.core.db.reflect.TableInfo;
import org.mybatis.mp.core.db.reflect.TableInfos;
import org.mybatis.mp.db.IdAutoType;

import java.util.Objects;

public class EntityInsertContext<T> extends SQLCmdInsertContext<Insert> {

    public static final String INSERT_MODEL_NAME = "value";

    private final T value;

    public EntityInsertContext(T t) {
        super(createCmd(t));
        this.value = t;
    }

    private static class Placeholder implements Value {

        private final String name;

        public Placeholder(String name) {
            this.name = name;
        }

        @Override
        public StringBuilder sql(SqlBuilderContext context, StringBuilder sqlBuilder) {
            sqlBuilder = sqlBuilder.append("#{").append(INSERT_MODEL_NAME).append(".").append(this.name).append("}");
            return sqlBuilder;
        }
    }

    private static Insert createCmd(Object t) {
        TableInfo tableInfo = TableInfos.get(t.getClass());
        Insert<Insert, CmdFactory> insert = new Insert() {{
            insert(tableInfo.getBasic().getTable());
            tableInfo.getFieldInfos().stream().forEach(item -> {
                boolean isNeedInsert = false;
                Object value = item.getValue(t);
                if (Objects.nonNull(value)) {
                    isNeedInsert = true;
                } else if (item.isId() && (item.getIdAnnotation().value() == IdAutoType.AUTO || item.getIdAnnotation().executeBefore())) {
                    isNeedInsert = true;
                }
                if (isNeedInsert) {
                    fields(item.getTableField());
                    values(new Placeholder(item.getReflectField().getName()));
                }
            });
        }};
        return insert;
    }

    public T getValue() {
        return value;
    }

    public void setId(Object id) {
        try {
            TableInfos.get(this.value.getClass()).getIdInfo().getWriteFieldInvoker().invoke(this.value, new Object[]{id});
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
