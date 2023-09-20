package org.mybatis.mp.core.mybatis.mapper.context;


import db.sql.core.cmd.CmdFactory;
import db.sql.core.cmd.execution.Update;
import org.mybatis.mp.core.db.reflect.TableInfo;
import org.mybatis.mp.core.db.reflect.TableInfos;

import java.util.Objects;

public class EntityUpdateContext<T> extends SQLCmdUpdateContext<Update> implements PlaceholderContext {

    private final T value;

    public EntityUpdateContext(T t) {
        super(createCmd(t));
        this.value = t;
    }

    private static Update createCmd(Object t) {
        TableInfo tableInfo = TableInfos.get(t.getClass());
        Update<Update, CmdFactory> update = new Update() {{
            update(tableInfo.getBasic().getTable());
            tableInfo.getFieldInfos().stream().forEach(item -> {
                Object value = item.getValue(t);
                if (item.isId()) {
                    if (Objects.isNull(value)) {
                        throw new RuntimeException(item.getReflectField().getName() + " can't be null");
                    }
                    where($.eq(item.getTableField(), $.value(value)));
                } else if (Objects.nonNull(value)) {
                    updateSet(item.getTableField(), new MybatisPlaceholder(PARAM_PLACEHOLDER_NAME + "." + item.getReflectField().getName(), item.getFieldAnnotation().jdbcType(), item.getFieldAnnotation().typeHandler()));
                }
            });
        }};
        return update;
    }

    @Override
    public T getValue() {
        return value;
    }

}
