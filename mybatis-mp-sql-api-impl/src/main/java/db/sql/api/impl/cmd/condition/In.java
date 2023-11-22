package db.sql.api.impl.cmd.condition;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.impl.cmd.Methods;
import db.sql.api.impl.tookit.Lists;
import db.sql.api.impl.tookit.SqlConst;
import db.sql.api.tookit.CmdUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class In extends BaseCondition<Cmd, List<Cmd>> {

    private final Cmd key;

    private final List<Cmd> values = new ArrayList<>();

    public In(Cmd key) {
        super(SqlConst.IN);
        this.key = key;
    }

    public In(Cmd key, Cmd value) {
        this(key);
        this.add(value);
    }

    public In(Cmd key, Cmd... values) {
        this(key);
        this.add(values);
    }

    public In add(Cmd value) {
        this.values.add(value);
        return this;
    }

    public In add(Cmd... values) {
        Lists.merge(this.values, values);
        return this;
    }

    public In add(List<Serializable> values) {
        for (Serializable value : values) {
            if (Objects.isNull(value)) {
                continue;
            }
            this.add(Methods.convert(value));
        }
        return this;
    }

    public In add(Serializable... values) {
        for (Serializable value : values) {
            if (Objects.isNull(value)) {
                continue;
            }
            this.add(Methods.convert(value));
        }
        return this;
    }

    @Override
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = key.sql(this, context, sqlBuilder);
        sqlBuilder = sqlBuilder.append(getOperator()).append(SqlConst.BLANK).append(SqlConst.BRACKET_LEFT);
        sqlBuilder = CmdUtils.join(this, context, sqlBuilder, this.values, SqlConst.DELIMITER);
        sqlBuilder = sqlBuilder.append(SqlConst.BRACKET_RIGHT).append(SqlConst.BLANK);
        return sqlBuilder;
    }

    @Override
    public Cmd getField() {
        return this.key;
    }

    @Override
    public List<Cmd> getValue() {
        return this.values;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.key, this.values);
    }
}
