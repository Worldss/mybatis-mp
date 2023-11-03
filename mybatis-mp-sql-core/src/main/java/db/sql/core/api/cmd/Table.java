package db.sql.core.api.cmd;


import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.core.api.tookit.SqlConst;

public class Table implements Dataset<Table> {

    private final String name;

    protected String alias;

    protected String prefix;

    public TableField $(String name) {
        return new TableField(this, name);
    }

    public Table(String name) {
        this.name = name;
    }

    public Table(String name, String alias) {
        this(name);
        this.alias = alias;
    }

    public String getName() {
        return name;
    }

    public String getAlias() {
        return alias;
    }

    public Table as(String alias) {
        this.alias = alias;
        return this;
    }

    public Table setAlias(String alias) {
        return as(alias);
    }

    @Override
    public String getPrefix() {
        return prefix;
    }

    public Table setPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    @Override
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = sqlBuilder.append(getName());
        if (getAlias() != null) {
            sqlBuilder = sqlBuilder.append(SqlConst.BLANK).append(getAlias());
        }
        return sqlBuilder;
    }

}
