package db.sql.core.api.cmd.executor;

import db.sql.api.Cmd;
import db.sql.api.executor.Insert;
import db.sql.core.api.cmd.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public abstract class AbstractInsert<SELF extends AbstractInsert, CMD_FACTORY extends CmdFactory> extends BaseExecutor<SELF, CMD_FACTORY> implements Insert<SELF, Table, TableField, Value, InsertTable, InsertFields, InsertValues> {

    protected InsertTable insertTable;

    protected InsertFields insertFields;

    protected InsertValues insertValues;

    protected final CMD_FACTORY $;

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
    public InsertValues $values(List<Value> values) {
        if (this.insertValues == null) {
            this.insertValues = new InsertValues();
            this.append(this.insertValues);
        }

        this.insertValues.add(values);
        return this.insertValues;
    }

    @Override
    public SELF values(List<Object> values) {
        this.$values(values.stream().map(item -> {
            if (item instanceof Value) {
                return (Value) item;
            }
            return new BasicValue(item);
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
