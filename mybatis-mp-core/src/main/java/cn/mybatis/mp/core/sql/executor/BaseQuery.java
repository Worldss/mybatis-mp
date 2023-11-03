package cn.mybatis.mp.core.sql.executor;

import cn.mybatis.mp.core.db.reflect.ForeignInfo;
import cn.mybatis.mp.core.db.reflect.TableFieldInfo;
import cn.mybatis.mp.core.db.reflect.TableInfo;
import cn.mybatis.mp.core.db.reflect.Tables;
import db.sql.api.Cmd;
import db.sql.api.Getter;
import db.sql.api.JoinMode;
import db.sql.core.api.cmd.On;
import db.sql.core.api.cmd.TableField;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

public class BaseQuery<Q extends BaseQuery> extends db.sql.core.api.cmd.executor.AbstractQuery<Q, MybatisCmdFactory> {

    public BaseQuery() {
        super(new MybatisCmdFactory());
    }

    @Override
    @SuppressWarnings("unchecked")
    public Q select(Class entity, int storey) {
        TableInfo tableInfo = Tables.get(entity);
        if (tableInfo == null) {
            return (Q) super.select(entity, storey);
        } else {
            tableInfo.getTableFieldInfos().stream().forEach(item -> {
                if (item.getFieldAnnotation().select()) {
                    this.select($.field(entity, item.getField().getName(), storey));
                }
            });
        }
        return (Q) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Q select(Getter<T> column, Function<TableField, Cmd> f) {
        return super.select(column, f);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Q join(JoinMode mode, Class mainTable, int mainTableStorey, Class secondTable, int secondTableStorey, Consumer<On> consumer) {
        if (Objects.isNull(consumer)) {
            consumer = on -> {
                TableInfo mainTableInfo = Tables.get(mainTable);
                TableInfo secondTableInfo = Tables.get(secondTable);
                ForeignInfo foreignInfo;
                if ((foreignInfo = secondTableInfo.getForeignInfo(mainTable)) != null) {
                    TableFieldInfo foreignFieldInfo = foreignInfo.getTableFieldInfo();
                    on.eq(this.$().field(mainTable, mainTableInfo.getIdFieldInfo().getField().getName(), mainTableStorey), this.$().field(secondTable, foreignFieldInfo.getField().getName(), secondTableStorey));
                } else if ((foreignInfo = mainTableInfo.getForeignInfo(secondTable)) != null) {
                    TableFieldInfo foreignFieldInfo = foreignInfo.getTableFieldInfo();
                    on.eq(this.$().field(secondTable, secondTableInfo.getIdFieldInfo().getField().getName(), secondTableStorey), this.$().field(mainTable, foreignFieldInfo.getField().getName(), mainTableStorey));
                }
            };
        }
        this.join(mode, $().table(mainTable, mainTableStorey), $().table(secondTable, secondTableStorey), consumer);
        return (Q) this;
    }
}
