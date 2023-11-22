package db.sql.api.impl.cmd;


import db.sql.api.Getter;
import db.sql.api.Cmd;
import db.sql.api.impl.cmd.basic.*;
import db.sql.api.impl.cmd.condition.In;
import db.sql.api.impl.cmd.struct.On;
import db.sql.api.impl.tookit.LambdaUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;


public class CmdFactory extends Methods {

    private final String tableAsPrefix;
    protected Map<String, Table> tableCache = new HashMap<>();
    protected Map<String, TableField> tableFieldCache = new HashMap<>();
    protected int tableNums = 0;

    public CmdFactory() {
        this("t");
    }

    public CmdFactory(String tableAsPrefix) {
        this.tableAsPrefix = tableAsPrefix;
    }

    public NULL NULL() {
        return NULL.NULL;
    }

    protected String tableAs(int storey, int tableNums) {
        StringBuilder as = new StringBuilder();
        as = as.append(this.tableAsPrefix);
        return as.append(tableNums == 1 ? "" : tableNums).toString();
    }

    public Table cacheTable(Class entity, int storey) {
        return this.tableCache.get(String.format("%s.%s", entity.getName(), storey));
    }

    public Table table(Class entity) {
        return this.table(entity, 1);
    }

    public Table table(Class entity, int storey) {
        return tableCache.computeIfAbsent(entity.getName(), key -> {
            Table table = new Table(entity.getSimpleName());
            table.as(tableAs(storey, ++tableNums));
            return table;
        });
    }

    public <R extends Cmd> R create(Class entity, Function<Table, R> RF) {
        return RF.apply(this.table(entity));
    }

    public <R extends Cmd> R create(Class entity, int storey, Function<Table, R> RF) {
        return RF.apply(this.table(entity, storey));
    }

    public <T> String columnName(Getter<T> getter) {
        return LambdaUtil.getName(getter);
    }

    public <T, R extends Cmd> R create(Getter<T> getter, Function<TableField, R> RF) {
        return RF.apply(this.field(getter));
    }

    public <T> TableField field(Getter<T> getter) {
        return this.field(getter, 1);
    }

    public <T, R> R create(Getter<T> getter, int storey, Function<TableField, R> RF) {
        return RF.apply(this.field(getter, storey));
    }

    public <T> TableField field(Getter<T> getter, int storey) {
        Class clazz = LambdaUtil.getClass(getter);
        String filedName = LambdaUtil.getName(getter);
        return this.field(clazz, filedName, storey);
    }

    protected TableField field(Class clazz, String filedName, int storey) {
        return tableFieldCache.computeIfAbsent(String.format("%s.%s", clazz.getName(), filedName), key -> {
            Table table = table(clazz, storey);
            return new TableField(table, filedName);
        });
    }

    public <T> DatasetField field(Dataset dataset, Getter<T> getter) {
        String filedName = LambdaUtil.getName(getter);
        return new DatasetField<>(dataset, filedName);
    }

    public Consumer<On> on(Class mainTable, Class secondTable, Consumer<On> consumer) {
        return consumer;
    }

    public Table table(String name) {
        return new Table(name);
    }

    public BasicValue value(Object value) {
        return new BasicValue(value);
    }

    public DatasetField field(Dataset table, String name) {
        return new DatasetField(table, name);
    }

    public TableField field(Table table, String name) {
        return new TableField(table, name);
    }

    public AllField all(Dataset table) {
        return new AllField(table);
    }

    public In in(Cmd main) {
        In in = new In(main);
        return in;
    }
}
