package db.sql.api.impl.cmd.struct.insert;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.cmd.struct.insert.IInsertTable;
import db.sql.api.impl.cmd.basic.Table;
import db.sql.api.impl.tookit.SqlConst;
import db.sql.api.tookit.CmdUtils;

public class InsertTable implements IInsertTable<Table> {

    protected final Table table;

    public InsertTable(Table table) {
        this.table = table;
    }

    @Override
    public Table getTable() {
        return table;
    }

    @Override
    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder.append(SqlConst.INSERT_INTO);
        sqlBuilder.append(this.table.getName());
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.table);
    }
}
