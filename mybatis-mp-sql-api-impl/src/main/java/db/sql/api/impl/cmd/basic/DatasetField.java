package db.sql.api.impl.cmd.basic;


import db.sql.api.Cmd;
import db.sql.api.DbType;
import db.sql.api.SqlBuilderContext;
import db.sql.api.impl.cmd.struct.insert.InsertFields;
import db.sql.api.impl.cmd.struct.query.Select;
import db.sql.api.impl.tookit.SqlConst;
import db.sql.api.tookit.CmdUtils;

import java.util.Objects;

public class DatasetField<T extends DatasetField<T, DATASET>, DATASET extends Dataset> extends Field<T> {

    private final DATASET table;

    private final String name;

    public DatasetField(DATASET table, String name) {
        this.table = table;
        this.name = name;
    }

    public DATASET getTable() {
        return table;
    }

    public String getName() {
        return name;
    }

    public String getName(DbType dbType) {
        return dbType.wrap(this.name);
    }

    @Override
    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        // insert 直接名字
        if (parent instanceof InsertFields) {
            sqlBuilder.append(getName(context.getDbType()));
            return sqlBuilder;
        }

        if (this.table.getAlias() != null) {
            sqlBuilder.append(SqlConst.BLANK).append(this.table.getAlias()).append(SqlConst.DOT);
        } else {
            sqlBuilder.append(SqlConst.BLANK);
        }
        sqlBuilder.append(getName(context.getDbType()));

        //拼接 select 的别名
        if (parent instanceof Select) {
            String prefix = null;
            if (getTable() instanceof Table) {
                prefix = ((Table) getTable()).getPrefix();
            }
            if (this.getAlias() != null || prefix != null) {
                sqlBuilder.append(SqlConst.AS(context.getDbType()));
                if (prefix != null) {
                    sqlBuilder.append(prefix);
                }
                if (this.getAlias() != null) {
                    sqlBuilder.append(this.getAlias());
                } else {
                    sqlBuilder.append(Objects.isNull(prefix) ? getName(context.getDbType()) : getName());
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
