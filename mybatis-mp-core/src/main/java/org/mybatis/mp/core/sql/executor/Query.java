package org.mybatis.mp.core.sql.executor;

import db.sql.api.JoinMode;
import db.sql.core.api.cmd.On;
import org.mybatis.mp.core.db.reflect.FieldInfo;
import org.mybatis.mp.core.db.reflect.ForeignInfo;
import org.mybatis.mp.core.db.reflect.TableInfo;
import org.mybatis.mp.core.db.reflect.TableInfos;

import java.util.function.Consumer;

public class Query extends db.sql.core.api.cmd.executor.AbstractQuery<Query, MybatisCmdFactory> {

    private Class returnType;

    public Query() {
        super(new MybatisCmdFactory());
    }

    public Class getReturnType() {
        return returnType;
    }

    public void setReturnType(Class returnType) {
        this.returnType = returnType;
    }

    @Override
    public Query join(JoinMode mode, Class mainTable, int mainTableStorey, Class secondTable, int secondTableStorey, Consumer<On> consumer) {
        if (consumer == null) {
            consumer = on -> {

            };
        }

        TableInfo mainTableInfo = TableInfos.get(mainTable);
        TableInfo secondTableInfo = TableInfos.get(secondTable);
        ForeignInfo foreignInfo;
        if ((foreignInfo = secondTableInfo.getForeignInfo(mainTable)) != null) {
            final FieldInfo foreignFieldInfo = foreignInfo.getFieldInfo();
            consumer = consumer.andThen(on -> {
                on.eq(this.$().field(mainTable, mainTableInfo.getIdInfo().getReflectField().getName(), mainTableStorey), this.$().field(secondTable, foreignFieldInfo.getReflectField().getName(), secondTableStorey));
            });
        } else if ((foreignInfo = mainTableInfo.getForeignInfo(secondTable)) != null) {
            final FieldInfo foreignFieldInfo = foreignInfo.getFieldInfo();
            consumer = consumer.andThen(on -> {
                on.eq(this.$().field(secondTable, secondTableInfo.getIdInfo().getReflectField().getName(), secondTableStorey), this.$().field(mainTable, foreignFieldInfo.getReflectField().getName(), mainTableStorey));
            });
        }

        this.join(mode, $().table(mainTable, mainTableStorey), $().table(secondTable, secondTableStorey), consumer);
        return this;
    }
}
