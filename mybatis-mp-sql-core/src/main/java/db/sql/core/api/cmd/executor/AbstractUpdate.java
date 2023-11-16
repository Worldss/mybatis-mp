package db.sql.core.api.cmd.executor;

import db.sql.api.cmd.Cmd;
import db.sql.api.Getter;
import db.sql.api.cmd.JoinMode;
import db.sql.api.cmd.struct.Joins;
import db.sql.api.cmd.executor.Update;
import db.sql.core.api.cmd.*;
import db.sql.core.api.cmd.basic.BasicValue;
import db.sql.core.api.cmd.basic.Dataset;
import db.sql.core.api.cmd.basic.TableField;
import db.sql.core.api.cmd.basic.Value;
import db.sql.core.api.cmd.struct.ConditionChain;
import db.sql.core.api.cmd.struct.Join;
import db.sql.core.api.cmd.struct.On;
import db.sql.core.api.cmd.struct.Where;
import db.sql.core.api.cmd.struct.update.UpdateSets;
import db.sql.core.api.cmd.struct.update.UpdateTable;

import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public abstract class AbstractUpdate<SELF extends AbstractUpdate, CMD_FACTORY extends CmdFactory> extends BaseExecutor<SELF, CMD_FACTORY> implements Update<SELF, Dataset, Cmd, Object, ConditionChain, UpdateTable, Join, On, Where> {

    protected UpdateTable updateTable;

    protected UpdateSets updateSets;

    protected Where where;

    protected Joins joins;

    protected final ConditionFaction conditionFaction;

    protected final CMD_FACTORY $;

    public AbstractUpdate(CMD_FACTORY $) {
        this.$ = $;
        this.conditionFaction = new ConditionFaction($);
    }

    @Override
    public CMD_FACTORY $() {
        return $;
    }

    @Override
    void initCmdSorts(Map<Class<? extends Cmd>, Integer> cmdSorts) {
        int i = 0;
        cmdSorts.put(UpdateTable.class, ++i);
        cmdSorts.put(UpdateSets.class, ++i);
        cmdSorts.put(Joins.class, ++i);
        cmdSorts.put(Where.class, ++i);
    }

    @Override
    public UpdateTable $update(Dataset... tables) {
        if (this.updateTable == null) {
            this.updateTable = new UpdateTable(tables);
            this.append(this.updateTable);
        }
        return this.updateTable;
    }

    @Override
    public SELF update(Class... entities) {
        Dataset[] tables = new Dataset[entities.length];
        for (int i = 0; i < entities.length; i++) {
            tables[i] = $.table(entities[i]);
        }
        return this.update(tables);
    }

    @Override

    public SELF set(Cmd field, Object value) {
        Value v;
        if (value instanceof Value) {
            v = (Value) value;
        } else {
            v = new BasicValue(value);
        }
        if (this.updateSets == null) {
            this.updateSets = new UpdateSets();
            this.append(this.updateSets);
        }
        this.updateSets.set((TableField) field, v);
        return (SELF) this;
    }

    @Override

    public <T> SELF set(Getter<T> field, Object value) {
        return this.set($.field(field), value);
    }

    @Override
    public Join $join(JoinMode mode, Dataset mainTable, Dataset secondTable) {
        Join join = new Join(this.conditionFaction, mode, mainTable, secondTable);
        if (Objects.isNull(joins)) {
            joins = new Joins();
            this.append(joins);
        }
        joins.add(join);
        return join;
    }

    @Override

    public SELF join(JoinMode mode, Class mainTable, int mainTableStorey, Class secondTable, int secondTableStorey, Consumer<On> consumer) {
        return this.join(mode, this.$.table(mainTable, mainTableStorey), this.$.table(secondTable, secondTableStorey), consumer);
    }

    @Override

    public SELF join(JoinMode mode, Class mainTable, int mainTableStorey, Dataset secondTable, Consumer<On> consumer) {
        return this.join(mode, this.$.table(mainTable, mainTableStorey), secondTable, consumer);
    }

    @Override
    public Where $where() {
        if (where == null) {
            where = new Where(this.conditionFaction);
            this.append(where);
        }
        return where;
    }

    @Override

    public SELF join(JoinMode mode, Dataset mainTable, Dataset secondTable, Consumer<On> consumer) {
        Join join = $join(mode, mainTable, secondTable);
        if (consumer != null) {
            consumer.accept(join.getOn());
        }
        return (SELF) this;
    }

    public UpdateTable getUpdateTable() {
        return this.updateTable;
    }

    public UpdateSets getUpdateSets() {
        return this.updateSets;
    }


    public Joins getJoins() {
        return this.joins;
    }

    public Where getWhere() {
        return this.where;
    }
}
