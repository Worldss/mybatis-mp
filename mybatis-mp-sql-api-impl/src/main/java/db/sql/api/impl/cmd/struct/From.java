package db.sql.api.impl.cmd.struct;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.impl.cmd.basic.Dataset;
import db.sql.api.impl.tookit.Lists;
import db.sql.api.impl.tookit.SqlConst;
import db.sql.api.tookit.CmdUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class From<TABLE extends Dataset> implements db.sql.api.cmd.struct.From<TABLE> {

    private final List<TABLE> tables = new ArrayList<>();

    public From append(TABLE... tables) {
        Lists.merge(this.tables, tables);
        return this;
    }

    @Override
    public List<TABLE> getTables() {
        return tables;
    }

    @Override
    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = sqlBuilder.append(SqlConst.FROM);
        for (Dataset t : tables) {
            sqlBuilder = t.sql(module, this, context, sqlBuilder);
        }
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.tables);
    }
}
