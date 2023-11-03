package db.sql.core.api.cmd.executor;

import db.sql.api.Cmd;
import db.sql.api.Getter;
import db.sql.api.JoinMode;
import db.sql.api.executor.Update;
import db.sql.core.api.cmd.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AbstractUpdate<SELF extends AbstractUpdate, CMD_FACTORY extends CmdFactory> extends BaseExecutor<SELF, CMD_FACTORY> implements Update<SELF, Dataset, Cmd, Object, ConditionChain, UpdateTable, Join, On, Where> {

    protected UpdateTable updateTable;

    protected UpdateSets updateSets;

    protected Where where;

    protected List<Join> joins;

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
        cmdSorts.put(Join.class, ++i);
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
    @SuppressWarnings("unchecked")
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
    @SuppressWarnings("unchecked")
    public <T> SELF set(Getter<T> field, Object value) {
        return this.set($.field(field), value);
    }

    @Override
    public Join $join(JoinMode mode, Dataset mainTable, Dataset secondTable) {
        Join join = new Join(this.conditionFaction, mode, mainTable, secondTable);
        this.append(join);
        return join;
    }

    @Override
    @SuppressWarnings("unchecked")
    public SELF join(JoinMode mode, Class mainTable, int mainTableStorey, Class secondTable, int secondTableStorey, Consumer<On> consumer) {
        return this.join(mode, this.$.table(mainTable, mainTableStorey), this.$.table(secondTable, secondTableStorey), consumer);
    }

    @Override
    @SuppressWarnings("unchecked")
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
    @SuppressWarnings("unchecked")
    public SELF join(JoinMode mode, Dataset mainTable, Dataset secondTable, Consumer<On> consumer) {
        Join join = $join(mode, mainTable, secondTable);
        if (consumer != null) {
            consumer.accept(join.getOn());
        }
        if (joins == null) {
            joins = new ArrayList<>();
        }
        joins.add(join);
        return (SELF) this;
    }

    public Where getWhere(){
        return this.where;
    }
}
