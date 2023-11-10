package db.sql.core.api.cmd;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.tookit.CmdUtils;
import db.sql.core.api.tookit.SqlConst;

public class InsertTable implements db.sql.api.InsertTable<Table>, Cmd {

    protected final Table table;

    public InsertTable(Table table) {
        this.table = table;
    }

    @Override
    public Table getTable() {
        return table;
    }

    @Override
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = sqlBuilder.append(SqlConst.INSERT_INTO);
        sqlBuilder = sqlBuilder.append(this.table.getName());
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.table);
    }
}
