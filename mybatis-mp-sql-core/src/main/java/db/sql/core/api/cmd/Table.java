package db.sql.core.api.cmd;


import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.core.api.tookit.SqlConst;

public class Table<T extends Table> implements Dataset<T> {

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

    public T as(String alias) {
        this.alias = alias;
        return (T) this;
    }

    public T setAlias(String alias) {
        return as(alias);
    }

    @Override
    public String getPrefix() {
        return prefix;
    }

    public T setPrefix(String prefix) {
        this.prefix = prefix;
        return (T) this;
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
