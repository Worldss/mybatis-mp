package db.sql.core.api.cmd;


import db.sql.api.Cmd;
import db.sql.api.SQLMode;
import db.sql.api.SqlBuilderContext;
import db.sql.core.api.tookit.SqlConst;

public class BasicValue extends Field<BasicValue> {

    private final Object value;

    public BasicValue(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        if (context.getSqlMode() == SQLMode.PRINT) {
            if (this.value instanceof Number) {
                sqlBuilder = sqlBuilder.append(this.value);
            } else {
                sqlBuilder = sqlBuilder.append(SqlConst.SINGLE_QUOT(context.getDatabaseId())).append(this.value).append(SqlConst.SINGLE_QUOT(context.getDatabaseId()));
            }
        } else {
            sqlBuilder = sqlBuilder.append(context.addParam(this.value));
        }
        return sqlBuilder;
    }
}