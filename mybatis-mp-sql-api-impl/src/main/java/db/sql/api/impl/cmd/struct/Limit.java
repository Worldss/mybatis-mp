package db.sql.api.impl.cmd.struct;


import db.sql.api.Cmd;
import db.sql.api.DbType;
import db.sql.api.SqlBuilderContext;

public class Limit implements db.sql.api.cmd.struct.Limit<Limit> {

    private int offset;

    private int limit;

    public Limit(int offset, int limit) {
        this.offset = offset;
        this.limit = limit;
    }

    @Override
    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        if (context.getDbType() == DbType.ORACLE || context.getDbType() == DbType.SQL_SERVER) {
            return sqlBuilder.append(" OFFSET ").append(this.offset).append(" ROWS FETCH NEXT ").append(this.limit).append(" ROWS ONLY");
        }
        return sqlBuilder.append(" LIMIT ").append(this.limit).append(" OFFSET ").append(this.offset);
    }

    public Limit set(int offset, int limit) {
        this.offset = offset;
        this.limit = limit;
        return this;
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
