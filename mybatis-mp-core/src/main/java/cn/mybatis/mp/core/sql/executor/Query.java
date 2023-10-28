package cn.mybatis.mp.core.sql.executor;

import cn.mybatis.mp.core.db.reflect.ForeignInfo;
import cn.mybatis.mp.core.db.reflect.TableFieldInfo;
import cn.mybatis.mp.core.db.reflect.TableInfo;
import cn.mybatis.mp.core.db.reflect.TableInfos;
import cn.mybatis.mp.core.mybatis.configuration.MybatisConfiguration;
import db.sql.api.Cmd;
import db.sql.api.Getter;
import db.sql.api.JoinMode;
import db.sql.core.api.cmd.On;
import db.sql.core.api.cmd.TableField;

import java.util.function.Consumer;
import java.util.function.Function;

public class Query extends db.sql.core.api.cmd.executor.AbstractQuery<Query, MybatisCmdFactory> {

    private Class returnType;

    public Query() {
        super(new MybatisCmdFactory());
    }

    public Class getReturnType() {
        return returnType;
    }

    public Query setReturnType(Class returnType) {
        this.returnType = returnType;
        return this;
    }

    @Override
    public Query select(Class entity, int storey) {
        TableInfo tableInfo = TableInfos.get(entity);
        if (tableInfo == null) {
            return super.select(entity, storey);
        } else {
            tableInfo.getTableFieldInfos().stream().forEach(item -> {
                if (item.getFieldAnnotation().select()) {
                    this.select($.field(entity, item.getField().getName(), storey));
                }
            });
        }
        return this;
    }

    @Override
    public <T> Query select(Getter<T> column, Function<TableField, Cmd> f) {
        return super.select(column, f);
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
