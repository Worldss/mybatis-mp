package db.sql.api.impl.cmd.basic;


import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.impl.tookit.SqlConst;

public class Table implements Dataset<Table, TableField> {

    private final String name;

    protected String alias;

    protected String prefix;

    public Table(String name) {
        this.name = name;
    }

    public Table(String name, String alias) {
        this(name);
        this.alias = alias;
    }

    @Override
    public TableField $(String name) {
        return new TableField(this, name);
    }

    public String getName() {
        return name;
    }

    @Override
    public String getAlias() {
        return alias;
    }

    public Table setAlias(String alias) {
        return as(alias);
    }

    public Table as(String alias) {
        this.alias = alias;
        return this;
    }

    public String getPrefix() {
        return prefix;
    }

    public Table setPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    @Override
    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = sqlBuilder.append(getName());
        if (getAlias() != null) {
            sqlBuilder = sqlBuilder.append(SqlConst.BLANK).append(getAlias());
        }
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return false;
    }
}
