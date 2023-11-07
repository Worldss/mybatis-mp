package db.sql.core.api.cmd.executor;

import db.sql.api.*;
import db.sql.core.api.cmd.ConditionChain;
import db.sql.core.api.cmd.From;
import db.sql.core.api.cmd.GroupBy;
import db.sql.core.api.cmd.Having;
import db.sql.core.api.cmd.Join;
import db.sql.core.api.cmd.On;
import db.sql.core.api.cmd.OrderBy;
import db.sql.core.api.cmd.Select;
import db.sql.core.api.cmd.Where;
import db.sql.core.api.cmd.*;
import db.sql.core.api.tookit.CmdUtils;
import db.sql.core.api.tookit.SqlConst;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class AbstractQuery<SELF extends AbstractQuery, CMD_FACTORY extends CmdFactory> extends BaseExecutor<SELF, CMD_FACTORY>
        implements db.sql.api.executor.Query<SELF, Dataset, TableField, Cmd, Object, ConditionChain, Select, From, Join, On, Where, GroupBy, Having, OrderBy>, Cmd {
    protected static final Cmd SQL_1 = new Cmd() {
        @Override
        public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
            sqlBuilder = sqlBuilder.append(" 1 ");
            return sqlBuilder;
        }

        @Override
        public boolean contain(Cmd cmd) {
            return false;
        }
    };

    protected static final Cmd SQL_ALL = new Cmd() {
        @Override
        public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
            sqlBuilder = sqlBuilder.append(" * ");
            return sqlBuilder;
        }

        @Override
        public boolean contain(Cmd cmd) {
            return false;
        }
    };

    protected Select select;

    protected From from;

    protected Where where;

    protected List<Join> joins;

    protected GroupBy groupBy;

    protected Having having;

    protected OrderBy orderBy;

    protected Limit limit;

    protected Unions unions;

    protected final ConditionFaction conditionFaction;

    protected final CMD_FACTORY $;

    public AbstractQuery(CMD_FACTORY $) {
        this.$ = $;
        this.conditionFaction = new ConditionFaction($);
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
        cmdSorts.put(Join.class, ++i);
        cmdSorts.put(Where.class, ++i);
        cmdSorts.put(GroupBy.class, ++i);
        cmdSorts.put(OrderBy.class, ++i);
        cmdSorts.put(Unions.class, i++);
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
        $select().select(SQL_1);
        return (SELF) this;
    }

    @Override
    public SELF selectAll() {
        $select().select(SQL_ALL);
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
    @SuppressWarnings("unchecked")
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
    @SuppressWarnings("unchecked")
    public SELF from(Class entity, int storey, Consumer<Dataset> consumer) {
        Table table = this.$.table(entity, storey);
        this.from(table);
        return (SELF) this;
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

    @Override
    public GroupBy $groupBy() {
        if (groupBy == null) {
            groupBy = new GroupBy();
            this.append(groupBy);
        }
        return groupBy;
    }

    @Override
    @SuppressWarnings("unchecked")
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
    @SuppressWarnings("unchecked")
    public <T> SELF havingAnd(Getter<T> getter, Function<TableField, db.sql.api.Condition> f) {
        return this.havingAnd(f.apply($.field(getter)));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> SELF havingOr(Getter<T> getter, Function<TableField, db.sql.api.Condition> f) {
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
    @SuppressWarnings("unchecked")
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
    public SELF union(Cmd query) {
        $unions().add(new Union(query));
        return (SELF) this;
    }

    @Override
    public SELF unionAll(Cmd subQuery) {
        $unions().add(new Union(SqlConst.UNION_ALL, subQuery));
        return (SELF) this;
    }

    public Select getSelect() {
        return this.select;
    }

    public From getFrom() {
        return this.from;
    }

    public List<Join> getJoins() {
        return this.joins;
    }

    public Where getWhere() {
        return this.where;
    }


    public SELF limit(int limit) {
        return limit(0, limit);
    }

    public Limit getLimit() {
        return this.limit;
    }

    public GroupBy getGroupBy() {
        return this.groupBy;
    }

    public OrderBy getOrderBy() {
        return this.orderBy;
    }

    @SuppressWarnings("unchecked")
    public SELF limit(int offset, int limit) {
        if (this.limit == null) {
            this.limit = new Limit(offset, limit);
            this.append(this.limit);
        } else {
            this.limit.set(offset, limit);
        }
        return (SELF) this;
    }

    /**
     * 不优化 count sql 构建
     *
     * @param context
     * @param sqlBuilder
     * @return
     */
    private StringBuilder countSql(SqlBuilderContext context, StringBuilder sqlBuilder) {
        try {
            if (this.limit != null) {
                this.cmds.remove(this.limit);
            }
            StringBuilder sql = this.sql((Cmd) null, context, sqlBuilder);
            return new StringBuilder("SELECT COUNT(*) FROM (").append(sql).append(") T");
        } finally {
            if (this.limit != null) {
                this.cmds.add(this.limit);
            }
        }
    }

    private StringBuilder optimizeCountSql(SqlBuilderContext context, StringBuilder sqlBuilder) {
        List<Join> removedJoins = null;
        Select newSelect = null;
        try {
            if (this.orderBy != null) {
                this.cmds.remove(this.orderBy);
            }

            //有 union 不继续优化
            if (this.unions != null) {
                return this.countSql(context, sqlBuilder);
            }

            //有排重 不继续优化
            if (this.select.isDistinct()) {
                return this.countSql(context, sqlBuilder);
            }

            newSelect = new Select();
            newSelect.select(SQL_1);
            this.cmds.remove(this.select);
            this.cmds.add(newSelect);

            //有分组 不继续优化
            if (this.groupBy != null) {
                return this.countSql(context, sqlBuilder);
            }

            if (this.joins != null) {
                for (Join join : this.joins) {
                    //有非left join 不继续优化
                    if (join.getMode() != JoinMode.LEFT) {
                        return this.countSql(context, sqlBuilder);
                    }
                }
                //查看left join的表是否在where里边包含
                for (Join join : this.joins) {
                    //有非left join 不继续优化
                    if (CmdUtils.contain(join.getSecondTable(), this.where)) {
                        return this.countSql(context, sqlBuilder);
                    }
                }
                removedJoins = new ArrayList<>(this.joins);
                for (Join join : joins) {
                    this.cmds.remove(join);
                }
            }
            return this.countSql(context, sqlBuilder);
        } finally {
            if (this.orderBy != null) {
                this.cmds.add(this.orderBy);
            }

            if (newSelect != null) {
                this.cmds.remove(newSelect);
                this.cmds.add(this.select);
            }

            if (removedJoins != null) {
                for (Join join : removedJoins) {
                    this.cmds.add(join);
                }
            }
        }
    }

    @Override
    public StringBuilder countSql(SqlBuilderContext context, StringBuilder sqlBuilder, boolean optimize) {
        if (!optimize) {
            return countSql(context, sqlBuilder);
        } else {
            // 优化 count sql
            return optimizeCountSql(context, sqlBuilder);
        }
    }
}

