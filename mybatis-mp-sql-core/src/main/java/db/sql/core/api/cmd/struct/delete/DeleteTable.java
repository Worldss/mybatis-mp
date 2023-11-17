package db.sql.core.api.cmd.struct.delete;

import db.sql.api.SqlBuilderContext;
import db.sql.api.cmd.Cmd;
import db.sql.api.tookit.CmdUtils;
import db.sql.core.api.cmd.basic.Dataset;
import db.sql.core.api.cmd.basic.Table;
import db.sql.core.api.tookit.SqlConst;

public class DeleteTable implements db.sql.api.cmd.struct.delete.DeleteTable<Dataset>, Cmd {

    private final Dataset[] tables;

    public DeleteTable(Dataset[] tables) {
        this.tables = tables;
    }

    @Override
    public Dataset[] getTables() {
        return this.tables;
    }

    @Override
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
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
