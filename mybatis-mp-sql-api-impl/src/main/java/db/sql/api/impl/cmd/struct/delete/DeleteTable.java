package db.sql.api.impl.cmd.struct.delete;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.impl.cmd.basic.Dataset;
import db.sql.api.impl.cmd.basic.Table;
import db.sql.api.impl.tookit.SqlConst;
import db.sql.api.tookit.CmdUtils;

public class DeleteTable implements db.sql.api.cmd.struct.delete.DeleteTable<Table> {

    private final Table[] tables;

    public DeleteTable(Table[] tables) {
        this.tables = tables;
    }

    @Override
    public Table[] getTables() {
        return this.tables;
    }

    @Override
    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = sqlBuilder.append(SqlConst.DELETE);
        if (this.tables == null || this.tables.length < 1) {
            return sqlBuilder;
        }
        boolean isFirst = true;
        for (Dataset table : this.tables) {
            if (!isFirst) {
                sqlBuilder = sqlBuilder.append(SqlConst.DELIMITER);
            }
            if (table.getAlias() != null) {
                sqlBuilder = sqlBuilder.append(table.getAlias());
            } else if (table instanceof Table) {
                sqlBuilder = sqlBuilder.append(((Table) table).getName());
            }
            isFirst = false;
        }
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.tables);
    }
}
