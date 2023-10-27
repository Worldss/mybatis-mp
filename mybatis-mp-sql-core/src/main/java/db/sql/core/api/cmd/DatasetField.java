package db.sql.core.api.cmd;


import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.core.api.tookit.SqlConst;

public class DatasetField<T extends DatasetField> extends Field<DatasetField> {

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
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        // insert 直接名字
        if (user instanceof InsertFields) {
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
        if (user instanceof Select) {
            if (this.getAlias() != null || getTable().getPrefix() != null) {
                sqlBuilder = sqlBuilder.append(SqlConst.AS);
                if (getTable().getPrefix() != null) {
                    sqlBuilder = sqlBuilder.append(getTable().getPrefix());
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
}
