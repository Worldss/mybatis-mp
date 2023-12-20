package db.sql.api.impl.cmd.struct;

import db.sql.api.Cmd;
import db.sql.api.Getter;
import db.sql.api.SqlBuilderContext;
import db.sql.api.cmd.basic.ICondition;
import db.sql.api.cmd.struct.IOn;
import db.sql.api.impl.cmd.ConditionFactory;
import db.sql.api.impl.cmd.basic.Dataset;
import db.sql.api.impl.cmd.basic.TableField;
import db.sql.api.impl.tookit.SqlConst;
import db.sql.api.tookit.CmdUtils;

import java.util.function.Function;

public class On<SELF extends On<SELF, TABLE, JOIN>, TABLE extends Dataset, JOIN extends Join<JOIN, TABLE, SELF>> implements IOn<SELF, TABLE, TableField, Cmd, Object, JOIN, ConditionChain> {

    private final ConditionFactory conditionFactory;

    private final JOIN join;

    private final ConditionChain conditionChain;

    public On(ConditionFactory conditionFactory, JOIN join) {
        this.conditionFactory = conditionFactory;
        this.join = join;
        conditionChain = new ConditionChain(conditionFactory);
    }

    @Override
    public JOIN getJoin() {
        return join;
    }

    @Override
    public ConditionChain conditionChain() {
        return conditionChain;
    }

    @Override
    public <T> SELF and(Getter<T> column, int storey, Function<TableField, ICondition> function) {
        return null;
    }

    @Override
    public <T> SELF or(Getter<T> column, int storey, Function<TableField, ICondition> function) {
        return null;
    }

    @Override
    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = sqlBuilder.append(SqlConst.ON);
        if (!conditionChain.hasContent()) {
            throw new RuntimeException("ON has no on conditions");
        }
        sqlBuilder = conditionChain().sql(module, this, context, sqlBuilder);
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.conditionChain);
    }
}
