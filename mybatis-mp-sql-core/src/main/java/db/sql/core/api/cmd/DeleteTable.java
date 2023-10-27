package db.sql.core.api.cmd;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.core.api.tookit.SqlConst;

public class DeleteTable implements db.sql.api.DeleteTable<Dataset>, Cmd {

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
}
