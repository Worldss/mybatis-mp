package db.sql.core.api.cmd.struct.update;

import db.sql.api.SqlBuilderContext;
import db.sql.api.cmd.Cmd;
import db.sql.api.tookit.CmdUtils;
import db.sql.core.api.cmd.basic.Dataset;
import db.sql.core.api.cmd.basic.Table;
import db.sql.core.api.tookit.SqlConst;

public class UpdateTable implements db.sql.api.cmd.struct.update.UpdateTable<Dataset>, Cmd {

    private final Dataset[] tables;

    public UpdateTable(Dataset[] tables) {
        this.tables = tables;
    }

    @Override
    public Dataset[] getTables() {
        return tables;
    }

    @Override
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = sqlBuilder.append(SqlConst.UPDATE);
        boolean isFirst = true;
        for (Dataset table : this.tables) {
            if (!isFirst) {
                sqlBuilder = sqlBuilder.append(SqlConst.DELIMITER);
            }
            if (table instanceof Table) {
                sqlBuilder = sqlBuilder.append(((Table) table).getName());
                sqlBuilder.append(SqlConst.BLANK);
            }
            if (table.getAlias() != null) {
                sqlBuilder = sqlBuilder.append(table.getAlias());
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
