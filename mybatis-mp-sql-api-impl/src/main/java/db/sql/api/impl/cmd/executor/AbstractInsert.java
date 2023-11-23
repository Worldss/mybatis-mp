package db.sql.api.impl.cmd.executor;

import db.sql.api.Cmd;
import db.sql.api.Getter;
import db.sql.api.cmd.executor.Insert;
import db.sql.api.impl.cmd.CmdFactory;
import db.sql.api.impl.cmd.Methods;
import db.sql.api.impl.cmd.basic.Table;
import db.sql.api.impl.cmd.basic.TableField;
import db.sql.api.impl.cmd.struct.insert.InsertFields;
import db.sql.api.impl.cmd.struct.insert.InsertTable;
import db.sql.api.impl.cmd.struct.insert.InsertValues;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public abstract class AbstractInsert<SELF extends AbstractInsert, CMD_FACTORY extends CmdFactory> extends BaseExecutor<SELF, CMD_FACTORY> implements Insert<SELF, Table, TableField, Cmd, InsertTable, InsertFields, InsertValues> {

    protected final CMD_FACTORY $;
    protected InsertTable insertTable;
    protected InsertFields insertFields;
    protected InsertValues insertValues;

    public AbstractInsert(CMD_FACTORY $) {
        this.$ = $;
    }

    @Override
    public CMD_FACTORY $() {
        return $;
    }

    @Override
    public InsertTable $insert(Table table) {
        if (this.insertTable == null) {
            this.insertTable = new InsertTable(table);
            this.append(this.insertTable);
        }
        return this.insertTable;
    }

    @Override
    public InsertFields $field(TableField... fields) {
        if (this.insertFields == null) {
            this.insertFields = new InsertFields();
            this.append(this.insertFields);
        }
        this.insertFields.filed(fields);
        return this.insertFields;
    }

    @Override
    public InsertValues $values(List<Cmd> values) {
        if (this.insertValues == null) {
            this.insertValues = new InsertValues();
            this.append(this.insertValues);
        }

        this.insertValues.add(values);
        return this.insertValues;
    }

    @Override
    public SELF insert(Class entity) {
        return this.insert($.table(entity));
    }

    @Override
    public <T> SELF field(Getter<T>... fields) {
        TableField[] tableField = new TableField[fields.length];
        for (int i = 0; i < fields.length; i++) {
            tableField[i] = $.field(fields[i]);
        }
        return this.field(tableField);
    }

    @Override
    public SELF values(List<Object> values) {
        this.$values(values.stream().map(item -> {
            return Methods.convert(item);
        }).collect(Collectors.toList()));
        return (SELF) this;
    }

    @Override
    public InsertTable getInsertTable() {
        return this.insertTable;
    }

    @Override
    public InsertFields getInsertFields() {
        return this.insertFields;
    }

    @Override
    public InsertValues getInsertValues() {
        return this.insertValues;
    }

    @Override
    void initCmdSorts(Map<Class<? extends Cmd>, Integer> cmdSorts) {
        cmdSorts.put(InsertTable.class, 1);
        cmdSorts.put(InsertFields.class, 2);
        cmdSorts.put(InsertValues.class, 3);
    }
}
