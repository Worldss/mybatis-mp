package cn.mybatis.mp.core.sql.executor;

import cn.mybatis.mp.core.db.reflect.ForeignInfo;
import cn.mybatis.mp.core.db.reflect.TableFieldInfo;
import cn.mybatis.mp.core.db.reflect.TableInfo;
import cn.mybatis.mp.core.db.reflect.TableInfos;
import db.sql.api.JoinMode;
import db.sql.core.api.cmd.On;
import db.sql.core.api.cmd.executor.AbstractDelete;

import java.util.function.Consumer;

public class Delete extends AbstractDelete<Delete, MybatisCmdFactory> {

    public Delete() {
        super(new MybatisCmdFactory());
    }

    @Override
    public Delete join(JoinMode mode, Class mainTable, int mainTableStorey, Class secondTable, int secondTableStorey, Consumer<On> consumer) {
        if (consumer == null) {
            consumer = on -> {

            };
        }

        TableInfo mainTableInfo = TableInfos.get(mainTable);
        TableInfo secondTableInfo = TableInfos.get(secondTable);
        ForeignInfo foreignInfo;
        if ((foreignInfo = secondTableInfo.getForeignInfo(mainTable)) != null) {
            final TableFieldInfo foreignFieldInfo = foreignInfo.getTableFieldInfo();
            consumer = consumer.andThen(on -> {
                on.eq(this.$().field(mainTable, mainTableInfo.getIdFieldInfo().getField().getName(), mainTableStorey), this.$().field(secondTable, foreignFieldInfo.getField().getName(), secondTableStorey));
            });
        } else if ((foreignInfo = mainTableInfo.getForeignInfo(secondTable)) != null) {
            final TableFieldInfo foreignFieldInfo = foreignInfo.getTableFieldInfo();
            consumer = consumer.andThen(on -> {
                on.eq(this.$().field(secondTable, secondTableInfo.getIdFieldInfo().getField().getName(), secondTableStorey), this.$().field(mainTable, foreignFieldInfo.getField().getName(), mainTableStorey));
            });
        }

        this.join(mode, $().table(mainTable, mainTableStorey), $().table(secondTable, secondTableStorey), consumer);
        return this;
    }
}