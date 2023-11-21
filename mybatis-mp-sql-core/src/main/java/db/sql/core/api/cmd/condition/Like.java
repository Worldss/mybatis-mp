package db.sql.core.api.cmd.condition;

import db.sql.api.DbType;
import db.sql.api.SqlBuilderContext;
import db.sql.api.cmd.Cmd;
import db.sql.api.cmd.LikeMode;
import db.sql.core.api.cmd.basic.BasicValue;
import db.sql.core.api.tookit.SqlConst;

import java.io.Serializable;

public class Like extends BasicCondition {
    private final LikeMode mode;

    public Like(String operator, Cmd key, Cmd value, LikeMode mode) {
        super(operator, key, value);
        this.mode = mode;
    }

    public Like(Cmd key, Cmd value) {
        this(key, value, LikeMode.DEFAULT);
    }

    public Like(Cmd key, String value) {
        this(key, new BasicValue(value), LikeMode.DEFAULT);
    }

    public Like(Cmd key, Cmd value, LikeMode mode) {
        this(SqlConst.LIKE, key, value, mode);
    }

    public Like(Cmd key, String value, LikeMode mode) {
        this(SqlConst.LIKE, key, new BasicValue(value), mode);
    }

    @Override
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = getField().sql(user, context, sqlBuilder);
        sqlBuilder = sqlBuilder.append(getOperator());
        if (context.getDbType() == DbType.MYSQL) {

        }
        sqlBuilder = sqlBuilder.append(SqlConst.CONCAT).append(SqlConst.BRACKET_LEFT);

        boolean before = false;
        boolean after = false;

        switch (this.mode) {
            case DEFAULT: {
                before = true;
                after = true;
                break;
            }
            case RIGHT: {
                after = true;
                break;
            }
            default: {
                before = true;
            }
        }
        if (before) {
            sqlBuilder = sqlBuilder.append(SqlConst.VAGUE_SYMBOL).append(SqlConst.DELIMITER);
        }
        sqlBuilder = getValue().sql(user, context, sqlBuilder);
        if (after) {
            sqlBuilder = sqlBuilder.append(SqlConst.DELIMITER).append(SqlConst.VAGUE_SYMBOL);
        }
        sqlBuilder = sqlBuilder.append(SqlConst.BRACKET_RIGHT);
        return sqlBuilder;
    }
}
