package db.sql.api.impl.cmd.basic;


import db.sql.api.SqlBuilderContext;
import db.sql.api.Cmd;

public class Limit implements Cmd {

    private int offset;

    private int limit;

    public Limit(int offset, int limit) {
        this.offset = offset;
        this.limit = limit;
    }

    public Limit(int limit) {
        this(0, limit);
    }

    @Override
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        return sqlBuilder.append(" limit ").append(this.limit).append(" offset ").append(this.offset);
    }

    public void set(int offset, int limit) {
        this.offset = offset;
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return false;
    }
}
