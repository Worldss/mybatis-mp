package db.sql.core.api.cmd;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.core.api.tookit.SqlConst;

public class UpdateTable implements db.sql.api.UpdateTable<Dataset>, Cmd {

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
}
