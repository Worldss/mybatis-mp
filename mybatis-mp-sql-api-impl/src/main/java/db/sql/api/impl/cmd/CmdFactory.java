package db.sql.api.impl.cmd;


import db.sql.api.Cmd;
import db.sql.api.Getter;
import db.sql.api.cmd.GetterField;
import db.sql.api.cmd.ICmdFactory;
import db.sql.api.impl.cmd.basic.*;
import db.sql.api.impl.cmd.condition.In;
import db.sql.api.impl.tookit.LambdaUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


public class CmdFactory extends Methods implements ICmdFactory<Table, Dataset, TableField, DatasetField> {

    protected final Map<String, Table> tableCache = new HashMap<>(5);
    private final String tableAsPrefix;
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
        String as = this.tableAsPrefix +
                (tableNums == 1 ? "" : tableNums);
        return as;
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
    public <T> String columnName(Getter<T> column) {
        return LambdaUtil.getName(column);
    }

    @Override
    public <T> TableField field(Getter<T> column, int storey) {
        LambdaUtil.LambdaFieldInfo fieldInfo = LambdaUtil.getFieldInfo(column);
        return this.field(fieldInfo.getType(), 1, fieldInfo.getName());
    }

    @Override
    public <T> TableField[] fields(int storey, Getter<T>... columns) {
        TableField[] tableFields = new TableField[columns.length];
        for (int i = 0; i < columns.length; i++) {
            tableFields[i] = field(columns[i], storey);
        }
        return tableFields;
    }

    @Override
    public TableField[] fields(GetterField... getterFields) {
        TableField[] tableFields = new TableField[getterFields.length];
        for (int i = 0; i < getterFields.length; i++) {
            GetterField getterField = getterFields[i];
            tableFields[i] = field(getterField.getGetter(), getterField.getStorey());
        }
        return tableFields;
    }

    public <T> TableField field(Table table, Getter<T> column) {
        return new TableField(table, columnName(column));
    }

    @Override
    public <T> DatasetField field(Dataset dataset, Getter<T> column) {
        String filedName = LambdaUtil.getName(column);
        return new DatasetField<>(dataset, filedName);
    }

    @Override
    public TableField field(Class entity, String filedName, int storey) {
        return this.field(entity, storey, filedName);
    }

    public TableField field(Table table, String columnName) {
        return new TableField(table, columnName);
    }

    @Override
    public DatasetField field(Dataset dataset, String columnName) {
        return new DatasetField(dataset, columnName);
    }

    @Override
    public DatasetField allField(Dataset dataset) {
        return new AllField(dataset);
    }

    @Override
    public <T, R extends Cmd> R create(Getter<T> column, int storey, Function<TableField, R> RF) {
        return RF.apply(this.field(column, storey));
    }

    protected TableField field(Class clazz, int storey, String filedName) {
        Table table = table(clazz, storey);
        return new TableField(table, filedName);
    }

    public BasicValue value(Object value) {
        return new BasicValue(value);
    }

    public AllField all(Dataset dataset) {
        return new AllField(dataset);
    }

    public In in(Cmd main) {
        return new In(main);
    }
}
