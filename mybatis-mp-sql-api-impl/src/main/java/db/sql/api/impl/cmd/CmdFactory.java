package db.sql.api.impl.cmd;


import db.sql.api.Cmd;
import db.sql.api.Getter;
import db.sql.api.impl.cmd.basic.*;
import db.sql.api.impl.cmd.condition.In;
import db.sql.api.impl.cmd.struct.On;
import db.sql.api.impl.tookit.LambdaUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;


public class CmdFactory extends Methods implements db.sql.api.cmd.CmdFactory<Table, Dataset, TableField, DatasetField, On> {

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

    @Override
    public Table table(Class entity, int storey) {
        return tableCache.computeIfAbsent(entity.getName(), key -> {
            Table table = new Table(entity.getSimpleName());
            table.as(tableAs(storey, ++tableNums));
            return table;
        });
    }

    @Override
    public Table table(String tableName) {
        return new Table(tableName);
    }

    @Override
    public <T> String columnName(Getter<T> getter) {
        return LambdaUtil.getName(getter);
    }

    @Override
    public <T> TableField field(Getter<T> getter, int storey) {
        Class entity = LambdaUtil.getClass(getter);
        String filedName = LambdaUtil.getName(getter);
        return this.field(entity, 1, filedName);
    }

    @Override
    public Consumer<On> buildOn(Class mainTable, int mainTableStorey, Class secondTable, int secondTableStorey, Consumer<On> consumer) {
        return consumer;
    }

    @Override
    public <T> DatasetField field(Dataset dataset, Getter<T> getter) {
        String filedName = LambdaUtil.getName(getter);
        return new DatasetField<>(dataset, filedName);
    }

    public TableField field(Table table, String columnName) {
        return new TableField(table, columnName);
    }

    @Override
    public DatasetField field(Dataset dataset, String columnName) {
        return new DatasetField(dataset, columnName);
    }

    @Override
    public <T, R extends Cmd> R create(Getter<T> getter, int storey, Function<TableField, R> RF) {
        return RF.apply(this.field(getter, storey));
    }

    protected TableField field(Class clazz, int storey, String filedName) {
        return tableFieldCache.computeIfAbsent(String.format("%s.%s", clazz.getName(), filedName), key -> {
            Table table = table(clazz, storey);
            return new TableField(table, filedName);
        });
    }

    public BasicValue value(Object value) {
        return new BasicValue(value);
    }

    public AllField all(Dataset dataset) {
        return new AllField(dataset);
    }

    public In in(Cmd main) {
        In in = new In(main);
        return in;
    }
}
