package db.sql.api.impl.cmd.struct.update;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.cmd.struct.update.IUpdateTable;
import db.sql.api.impl.cmd.basic.Table;
import db.sql.api.impl.tookit.SqlConst;
import db.sql.api.tookit.CmdUtils;

public class UpdateTable implements IUpdateTable<Table> {

    private final Table[] tables;

    public UpdateTable(Table[] tables) {
        this.tables = tables;
    }

    @Override
    public Table[] getTables() {
        return tables;
    }

    @Override
    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        if (this.tables == null || this.tables.length < 1) {
            return sqlBuilder;
        }
        sqlBuilder = sqlBuilder.append(SqlConst.UPDATE);
        int length = this.tables.length;
        for (int i = 0; i < length; i++) {
            Table table = this.tables[i];
            if (i != 0) {
                sqlBuilder = sqlBuilder.append(SqlConst.DELIMITER);
            }

            sqlBuilder = sqlBuilder.append(table.getName());
            sqlBuilder.append(SqlConst.BLANK);

            if (table.getAlias() != null) {
                sqlBuilder = sqlBuilder.append(table.getAlias());
            }
        }
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, (Object[]) this.tables);
    }
}
