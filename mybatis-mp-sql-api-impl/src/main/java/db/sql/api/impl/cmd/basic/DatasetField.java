package db.sql.api.impl.cmd.basic;


import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.impl.cmd.struct.insert.InsertFields;
import db.sql.api.impl.cmd.struct.query.Select;
import db.sql.api.impl.tookit.SqlConst;
import db.sql.api.tookit.CmdUtils;

public class DatasetField<T extends DatasetField<T>> extends Field<T> {

    private final Dataset table;

    private final String name;

    public DatasetField(Dataset table, String name) {
        this.table = table;
        this.name = name;
    }

    public Dataset getTable() {
        return table;
    }

    public String getName() {
        return name;
    }

    @Override
    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        // insert 直接名字
        if (parent instanceof InsertFields) {
            sqlBuilder = sqlBuilder.append(this.getName());
            return sqlBuilder;
        }

        if (this.table.getAlias() != null) {
            sqlBuilder = sqlBuilder.append(SqlConst.BLANK).append(this.table.getAlias()).append(SqlConst.DOT);
        } else {
            sqlBuilder = sqlBuilder.append(SqlConst.BLANK);
        }
        sqlBuilder = sqlBuilder.append(this.getName());

        //拼接 select 的别名
        if (parent instanceof Select) {
            String prefix = null;
            if (getTable() instanceof Table) {
                prefix = ((Table) getTable()).getPrefix();
            }
            if (this.getAlias() != null || prefix != null) {
                sqlBuilder = sqlBuilder.append(SqlConst.AS);
                if (prefix != null) {
                    sqlBuilder = sqlBuilder.append(prefix);
                }
                if (this.getAlias() != null) {
                    sqlBuilder = sqlBuilder.append(this.getAlias());
                } else {
                    sqlBuilder = sqlBuilder.append(this.getName());
                }
            }
            return sqlBuilder;
        }

        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.table);
    }
}
