package db.sql.core.api.cmd;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.core.api.tookit.SqlConst;

public class InsertTable implements db.sql.api.InsertTable<Table>, Cmd {

    protected Table table;

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
}
