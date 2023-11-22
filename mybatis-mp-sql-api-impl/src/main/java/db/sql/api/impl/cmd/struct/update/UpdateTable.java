package db.sql.api.impl.cmd.struct.update;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.impl.cmd.basic.Dataset;
import db.sql.api.impl.cmd.basic.Table;
import db.sql.api.impl.tookit.SqlConst;
import db.sql.api.tookit.CmdUtils;

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
    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
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
