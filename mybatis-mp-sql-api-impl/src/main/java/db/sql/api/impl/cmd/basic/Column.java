package db.sql.api.impl.cmd.basic;

import db.sql.api.Cmd;
import db.sql.api.DbType;
import db.sql.api.SqlBuilderContext;
import db.sql.api.cmd.basic.IColumn;
import db.sql.api.impl.cmd.struct.query.Select;
import db.sql.api.impl.tookit.SqlConst;

import java.util.Objects;

public class Column implements IColumn<Column> {

    private final String name;

    private String alias;

    public Column(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getName(DbType dbType) {
        return dbType.wrap(this.name);
    }

    @Override
    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder.append(SqlConst.BLANK).append(getName(context.getDbType()));

        if (Objects.nonNull(this.alias)) {
            if (parent instanceof Select) {
                sqlBuilder.append(SqlConst.BLANK).append(this.alias);
            }
        }

        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return false;
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public Column as(String alias) {
        this.alias = alias;
        return this;
    }
}
