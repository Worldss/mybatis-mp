package db.sql.api.impl.cmd.executor;

import db.sql.api.Cmd;
import db.sql.api.cmd.JoinMode;
import db.sql.api.cmd.executor.IDelete;
import db.sql.api.cmd.struct.Joins;
import db.sql.api.impl.cmd.CmdFactory;
import db.sql.api.impl.cmd.ConditionFactory;
import db.sql.api.impl.cmd.basic.Dataset;
import db.sql.api.impl.cmd.basic.DatasetField;
import db.sql.api.impl.cmd.basic.Table;
import db.sql.api.impl.cmd.basic.TableField;
import db.sql.api.impl.cmd.struct.*;
import db.sql.api.impl.cmd.struct.delete.DeleteTable;

import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public abstract class AbstractDelete<SELF extends AbstractDelete, CMD_FACTORY extends CmdFactory>
        extends BaseExecutor<SELF, CMD_FACTORY>
        implements IDelete<SELF,
        Table,
        Dataset,
        TableField,
        DatasetField,
        Cmd,
        Object,
        ConditionChain,
        DeleteTable,
        FromTable, JoinTable, OnTable, Where> {

    protected final ConditionFactory conditionFactory;
    protected final CMD_FACTORY $;
    protected DeleteTable deleteTable;
    protected FromTable from;
    protected Where where;
    protected Joins joins;

    public AbstractDelete(CMD_FACTORY $) {
        this.$ = $;
        this.conditionFactory = new ConditionFactory($);
    }

    public AbstractDelete(Where where) {
        this.$ = (CMD_FACTORY) where.getConditionFactory().getCmdFactory();
        this.conditionFactory = where.getConditionFactory();
        this.where = where;
        this.append(where);
    }


    @Override
    public CMD_FACTORY $() {
        return $;
    }

    void initCmdSorts(Map<Class<? extends Cmd>, Integer> cmdSorts) {
        int i = 0;
        cmdSorts.put(DeleteTable.class, ++i);
        cmdSorts.put(FromTable.class, ++i);
        cmdSorts.put(Joins.class, ++i);
        cmdSorts.put(Where.class, ++i);
    }

    @Override
    public DeleteTable $delete(Table... tables) {
        if (this.deleteTable == null) {
            this.deleteTable = new DeleteTable(tables);
        }
        this.append(this.deleteTable);
        return this.deleteTable;
    }

    @Override
    public SELF delete(Class... entities) {
        int length = entities.length;
        Table[] tables = new Table[length];
        for (int i = 0; i < length; i++) {
            Class entity = entities[i];
            tables[i] = $().table(entity, 1);
        }
        return this.delete(tables);
    }

    @Override
    public FromTable $from(Table... tables) {
        if (this.from == null) {
            from = new FromTable();
            this.append(from);
        }
        this.from.append(tables);
        return from;
    }

    @Override
    public SELF from(Class entity, int storey, Consumer<Table> consumer) {
        this.fromEntityIntercept(entity, storey);
        Table table = this.$.table(entity, storey);
        this.from(table);
        return (SELF) this;
    }

    @Override
    public JoinTable $join(JoinMode mode, Table mainTable, Table secondTable) {
        JoinTable join = new JoinTable(mode, mainTable, secondTable, (joinTable) -> new OnTable(conditionFactory, joinTable));
        if (Objects.isNull(joins)) {
            joins = new Joins();
            this.append(joins);
        }
        joins.add(join);
        return join;
    }

    @Override
    public SELF join(JoinMode mode, Class mainTable, int mainTableStorey, Class secondTable, int secondTableStorey, Consumer<OnTable> consumer) {
        consumer = this.joinEntityIntercept(mainTable, mainTableStorey, secondTable, secondTableStorey, consumer);
        return this.join(mode, this.$.table(mainTable, mainTableStorey), this.$.table(secondTable, secondTableStorey), consumer);
    }

    @Override
    public SELF join(JoinMode mode, Class mainTable, int mainTableStorey, Table secondTable, Consumer<OnTable> consumer) {
        return this.join(mode, this.$.table(mainTable, mainTableStorey), secondTable, consumer);
    }

    @Override
    public Where $where() {
        if (where == null) {
            where = new Where(this.conditionFactory);
            this.append(where);
        }
        return where;
    }

    @Override
    public SELF join(JoinMode mode, Table mainTable, Table secondTable, Consumer<OnTable> consumer) {
        JoinTable join = $join(mode, mainTable, secondTable);
        consumer.accept(join.getOn());
        return (SELF) this;
    }

    public DeleteTable getDeleteTable() {
        return deleteTable;
    }

    public From getFrom() {
        return from;
    }

    public Joins getJoins() {
        return joins;
    }

    public Where getWhere() {
        return where;
    }
}
