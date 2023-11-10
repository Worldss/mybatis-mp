package db.sql.core.api.cmd.executor;

import db.sql.api.Cmd;
import db.sql.api.JoinMode;
import db.sql.api.Joins;
import db.sql.api.SqlBuilderContext;
import db.sql.api.executor.Delete;
import db.sql.core.api.cmd.*;

import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public abstract class AbstractDelete<SELF extends AbstractDelete, CMD_FACTORY extends CmdFactory> extends BaseExecutor<SELF, CMD_FACTORY> implements Delete<SELF, Dataset, Cmd, Object, ConditionChain, DeleteTable, From, Join, On, Where> {

    protected DeleteTable deleteTable;

    protected From from;

    protected Where where;

    protected Joins joins;

    protected final ConditionFaction conditionFaction;

    protected final CMD_FACTORY $;

    public AbstractDelete(CMD_FACTORY $) {
        this.$ = $;
        this.conditionFaction = new ConditionFaction($);
    }

    @Override
    public CMD_FACTORY $() {
        return $;
    }

    void initCmdSorts(Map<Class<? extends Cmd>, Integer> cmdSorts) {
        int i = 0;
        cmdSorts.put(DeleteTable.class, ++i);
        cmdSorts.put(From.class, ++i);
        cmdSorts.put(Joins.class, ++i);
        cmdSorts.put(Where.class, ++i);
    }

    @Override
    public DeleteTable $delete(Dataset... tables) {
        if (this.deleteTable == null) {
            this.deleteTable = new DeleteTable(tables);
        }
        this.append(this.deleteTable);
        return this.deleteTable;
    }

    @Override
    public SELF delete(Class... entities) {
        int length = entities.length;
        Table[] tables=new Table[length];
        for(int i=0;i<length;i++){
            Class entity = entities[i];
            tables[i]=$().table(entity,1);
        }
        return this.delete(tables);
    }

    @Override
    public From $from(Dataset... tables) {
        if (this.from == null) {
            from = new From();
            this.append(from);
        }
        this.from.append(tables);
        return from;
    }

    @Override
    
    public SELF from(Class entity, int storey, Consumer<Dataset> consumer) {
        Table table = this.$.table(entity, storey);
        this.from(table);
        return (SELF) this;
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
        consumer.accept(join.getOn());
        return (SELF) this;
    }

    @Override
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        if (this.deleteTable == null) {
            if (this.from != null && this.from.getTables() != null) {
                this.from.getTables().stream().forEach(dataset -> {
                    $delete(dataset);
                });
            } else {
                $delete();
            }
        }
        return super.sql(user, context, sqlBuilder);
    }
}
