package org.mybatis.mp.core.sql.executor;

import db.sql.core.api.cmd.ConditionFaction;
import db.sql.core.api.cmd.Table;
import db.sql.core.api.cmd.TableField;
import org.mybatis.mp.core.db.reflect.FieldInfo;
import org.mybatis.mp.core.db.reflect.TableInfo;
import org.mybatis.mp.core.db.reflect.TableInfos;
import org.mybatis.mp.core.util.Getter;
import org.mybatis.mp.core.util.LambdaUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class LambdaQuery<R> extends db.sql.core.api.cmd.executor.AbstractQuery<LambdaQuery,MybatisCmdFactory> {

    private final Class<R> returnType;

    private Map<Class, Table> tableCache = new HashMap<>();

    private int tableNums = 0;

    public LambdaQuery(Class<R> returnType) {
        super(MybatisCmdFactory.INSTANCE, ConditionFaction.INSTANCE);
        this.returnType = returnType;
    }

    public Class<R> getReturnType() {
        return returnType;
    }

    public Table table(Class clazz, boolean useCache) {
        Table table;
        if (useCache) {
            table = tableCache.computeIfAbsent(clazz, key -> {
                tableNums++;
                Table t = $.table(TableInfos.get(clazz).getBasic().getSchemaAndTableName());
                if (tableNums == 1) {
                    t.as("t");
                } else {
                    t.as("t" + tableNums);
                }
                return t;
            });
        } else {
            table = $.table(TableInfos.get(clazz).getBasic().getSchemaAndTableName());
            tableNums++;
            if (tableNums == 1) {
                table.as("t");
            } else {
                table.as("t" + tableNums);
            }
        }
        return table;
    }
    public TableField field(Getter getter){
        return field(getter,true);
    }
    public TableField field(Getter getter,boolean useCache){
        Class clazz=LambdaUtil.getClass(getter);
        TableInfo tableInfo = TableInfos.get(clazz);
        Table table = table(clazz, useCache);
        FieldInfo fieldInfo = tableInfo.getFieldInfo(LambdaUtil.getName(getter));
        return $.field(table,fieldInfo.getColumnName());
    }

    public LambdaQuery<R> from(Class clazz, boolean useCache, Consumer<Table> consumer) {
        Table table = table(clazz, useCache);
        this.from();
        if (consumer != null) {
            consumer.accept(table);
        }
        return this;
    }

    public LambdaQuery<R> from(Class clazz, Consumer<Table> consumer) {
        return this.from(clazz, true, consumer);
    }

    public LambdaQuery<R> from(Class clazz) {
        return this.from(clazz, true, null);
    }


    public LambdaQuery<R> select(Getter... getters) {
        return this.select(true, getters);
    }

    public LambdaQuery<R> select(boolean useCache, Getter... getters) {
        if (getters == null) {
            return this;
        }
        for (Getter getter : getters) {
            this.select(this.field(getter));
        }
        return this;
    }

}
