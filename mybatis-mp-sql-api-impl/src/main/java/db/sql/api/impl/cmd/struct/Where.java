package db.sql.api.impl.cmd.struct;


import db.sql.api.Cmd;
import db.sql.api.Getter;
import db.sql.api.SqlBuilderContext;
import db.sql.api.cmd.basic.ICondition;
import db.sql.api.cmd.struct.IWhere;
import db.sql.api.impl.cmd.ConditionFactory;
import db.sql.api.impl.cmd.basic.TableField;
import db.sql.api.impl.tookit.SqlConst;
import db.sql.api.tookit.CmdUtils;

import java.util.Objects;
import java.util.function.Function;

public class Where implements IWhere<Where, TableField, Cmd, Object, ConditionChain> {

    private final ConditionFactory conditionFactory;

    private ConditionChain conditionChain;

    public Where(ConditionFactory conditionFactory) {
        this.conditionFactory = conditionFactory;
    }

    public boolean hasContent() {
        return Objects.nonNull(conditionChain) && conditionChain.hasContent();
    }

    @Override
    public ConditionChain conditionChain() {
        if (this.conditionChain == null) {
            this.conditionChain = new ConditionChain(conditionFactory);
        }
        return conditionChain;
    }

    public ConditionFactory getConditionFactory() {
        return conditionFactory;
    }

    @Override
    public <T> Where and(Getter<T> column, int storey, Function<TableField, ICondition> function) {
        conditionChain().and(column, storey, function);
        return this;
    }

    @Override
    public <T> Where or(Getter<T> column, int storey, Function<TableField, ICondition> function) {
        conditionChain().or(column, storey, function);
        return this;
    }

    @Override
    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        if (this.conditionChain == null || !this.conditionChain.hasContent()) {
            return sqlBuilder;
        }
        sqlBuilder.append(SqlConst.WHERE);
        this.conditionChain.sql(module, this, context, sqlBuilder);
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.conditionChain);
    }


}
