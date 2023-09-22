package org.mybatis.mp.core.mybatis.mapper.context;

import db.sql.core.cmd.CmdFactory;
import db.sql.core.cmd.execution.Insert;
import org.apache.ibatis.reflection.invoker.SetFieldInvoker;
import org.mybatis.mp.core.db.reflect.TableInfo;
import org.mybatis.mp.core.db.reflect.TableInfos;
import org.mybatis.mp.core.mybatis.configuration.MybatisParameter;
import org.mybatis.mp.db.IdAutoType;
import org.mybatis.mp.db.annotations.Field;

import java.util.Objects;
import java.util.function.Supplier;

public class EntityInsertContext<T> extends SQLCmdInsertContext<Insert> {

    private final T value;

    private SetFieldInvoker idSetFieldInvoker;

    public EntityInsertContext(T t) {
        super(createCmd(t));
        this.value = t;
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
                    fields(item.getTableField());
                    Field field = item.getFieldAnnotation();
                    MybatisParameter mybatisParameter = new MybatisParameter(value, field.typeHandler(), field.jdbcType());
                    values($.value(mybatisParameter));
                }
            });
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
