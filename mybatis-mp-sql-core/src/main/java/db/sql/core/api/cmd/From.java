package db.sql.core.api.cmd;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.tookit.CmdUtils;
import db.sql.core.api.tookit.Lists;
import db.sql.core.api.tookit.SqlConst;

import java.util.ArrayList;
import java.util.List;

public class From implements db.sql.api.From<Dataset>, Cmd {

    private final List<Dataset> tables = new ArrayList<>();

    public From append(Dataset... tables) {
        Lists.merge(this.tables, tables);
        return this;
    }

    @Override
    public List<Dataset> getTables() {
        return tables;
    }

    @Override
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = sqlBuilder.append(SqlConst.FROM);
        for (Dataset t : tables) {
            sqlBuilder = t.sql(user, context, sqlBuilder);
        }
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.tables);
    }
}
