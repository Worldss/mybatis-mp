package db.sql.api.impl.cmd.executor;

import db.sql.api.Cmd;
import db.sql.api.Getter;
import db.sql.api.cmd.JoinMode;
import db.sql.api.cmd.basic.*;
import db.sql.api.cmd.executor.Query;
import db.sql.api.cmd.struct.Joins;
import db.sql.api.cmd.struct.query.Unions;
import db.sql.api.impl.cmd.CmdFactory;
import db.sql.api.impl.cmd.ConditionFaction;
import db.sql.api.impl.cmd.basic.Dataset;
import db.sql.api.impl.cmd.basic.Limit;
import db.sql.api.impl.cmd.basic.Table;
import db.sql.api.impl.cmd.basic.TableField;
import db.sql.api.impl.cmd.struct.*;
import db.sql.api.impl.cmd.struct.query.*;
import db.sql.api.impl.tookit.SqlConst;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class AbstractQuery<SELF extends AbstractQuery, CMD_FACTORY extends CmdFactory> extends BaseExecutor<SELF, CMD_FACTORY>
        implements Query<SELF,
        Dataset,
        TableField,
        Cmd,
        Object,
        ConditionChain,
        Select,
        From,
        Join,
        On,
        Joins<Join>,
        Where,
        GroupBy,
        Having,
        OrderBy,
        Limit,
        Union,
        Unions<Union>
        >, Cmd {

    protected final ConditionFaction conditionFaction;
    protected final CMD_FACTORY $;
    protected Select select;
    protected From from;
    protected Where where;
    protected Joins joins;
    protected GroupBy groupBy;
    protected Having having;
    protected OrderBy orderBy;
    protected Limit limit;
    protected Unions unions;

    public AbstractQuery(CMD_FACTORY $) {
        this.$ = $;
        this.conditionFaction = new ConditionFaction($) {
            @Override
            protected boolean ignoreEmpty() {
                return true;
            }
        };
    }

    @Override
    public CMD_FACTORY $() {
        return $;
    }


    @Override
    public List<Cmd> cmds() {
        return this.cmds;
    }

    @Override
    void initCmdSorts(Map<Class<? extends Cmd>, Integer> cmdSorts) {
        int i = 0;
        cmdSorts.put(Select.class, ++i);
        cmdSorts.put(From.class, ++i);
        cmdSorts.put(Joins.class, ++i);
        cmdSorts.put(Where.class, ++i);
        cmdSorts.put(GroupBy.class, ++i);
        cmdSorts.put(Having.class, ++i);
        cmdSorts.put(OrderBy.class, ++i);
        cmdSorts.put(Limit.class, ++i);
        cmdSorts.put(Unions.class, ++i);
        cmdSorts.put(UnionsCmdLists.class, ++i);
    }


    @Override
    public Select $select() {
        if (select == null) {
            select = new Select();
            this.append(select);
        }
        return select;
    }

    @Override
    public SELF select(Class entity, int storey) {
        return this.select(this.$.all(this.$.table(entity, storey)));
    }

    @Override
    public SELF select1() {
        $select().select(SQL1.INSTANCE);
        return (SELF) this;
    }

    @Override
    public SELF selectAll() {
        $select().select(SQLCmdAll.INSTANCE);
        return (SELF) this;
    }

    @Override
    public SELF selectCount1() {
        $select().select(Count1.INSTANCE);
        return (SELF) this;
    }

    @Override
    public SELF selectCountAll() {
        $select().select(CountAll.INSTANCE);
        return (SELF) this;
    }

    @Override
    public SELF selectDistinct() {
        $select().distinct();
        return (SELF) this;
    }

    @Override

    public <T> SELF select(Getter<T> column, int storey, Function<TableField, Cmd> f) {
        TableField field = this.$.field(column, storey);
        if (f != null) {
            return this.select(f.apply(field));
        } else {
            return this.select(field);
        }
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
        if (consumer != null) {
            consumer.accept(join.getOn());
        }
        return (SELF) this;
    }

    @Override
    public GroupBy $groupBy() {
        if (groupBy == null) {
            groupBy = new GroupBy();
            this.append(groupBy);
        }
        return groupBy;
    }

    @Override

    public <T> SELF groupBy(Getter<T> column, int storey, Function<TableField, Cmd> f) {
        TableField tableField = $.field(column, storey);
        if (f != null) {
            return this.groupBy(f.apply(tableField));
        }
        return this.groupBy(tableField);
    }

    @Override
    public Having $having() {
        if (having == null) {
            having = new Having(this.$);
            this.append(having);
        }
        return having;
    }

    @Override

    public <T> SELF havingAnd(Getter<T> getter, Function<TableField, Condition> f) {
        return this.havingAnd(f.apply($.field(getter)));
    }

    @Override

    public <T> SELF havingOr(Getter<T> getter, Function<TableField, Condition> f) {
        return this.havingOr(f.apply($.field(getter)));
    }

    @Override
    public OrderBy $orderBy() {
        if (orderBy == null) {
            orderBy = new OrderBy();
            this.append(orderBy);
        }
        return orderBy;
    }

    @Override
    public Limit $limit(int offset, int limit) {
        if (this.limit == null) {
            this.limit = new Limit(offset, limit);
            this.append(this.limit);
        } else {
            this.limit.set(offset, limit);
        }
        return this.limit;
    }

    @Override

    public <T> SELF orderBy(Getter<T> column, int storey, boolean asc, Function<TableField, Cmd> f) {
        TableField tableField = $.field(column, storey);
        if (f != null) {
            return this.orderBy(f.apply(tableField), asc);
        }
        return this.orderBy(tableField, asc);
    }

    public Unions $unions() {
        if (this.unions == null) {
            this.unions = new Unions();
            this.cmds.add(unions);
        }
        return this.unions;
    }

    @Override
    public SELF union(Query unionQuery) {
        $unions().add(new Union(unionQuery));
        return (SELF) this;
    }

    @Override
    public SELF unionAll(Query unionQuery) {
        $unions().add(new Union(SqlConst.UNION_ALL, unionQuery));
        return (SELF) this;
    }

    @Override
    public Select getSelect() {
        return this.select;
    }

    @Override
    public From getFrom() {
        return this.from;
    }

    @Override
    public Joins getJoins() {
        return this.joins;
    }

    @Override
    public Where getWhere() {
        return this.where;
    }


    public SELF limit(int limit) {
        return limit(0, limit);
    }

    @Override
    public Limit getLimit() {
        return this.limit;
    }

    @Override
    public GroupBy getGroupBy() {
        return this.groupBy;
    }

    @Override
    public OrderBy getOrderBy() {
        return this.orderBy;
    }

    @Override
    public Unions getUnions() {
        return this.unions;
    }


    public SELF limit(int offset, int limit) {
        this.$limit(offset, limit);
        return (SELF) this;
    }
}

