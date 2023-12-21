package db.sql.api.impl.cmd.condition;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.cmd.LikeMode;
import db.sql.api.impl.cmd.Methods;
import db.sql.api.impl.tookit.SqlConst;

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
        this(key, Methods.convert(value), LikeMode.DEFAULT);
    }

    public Like(Cmd key, Cmd value, LikeMode mode) {
        this(SqlConst.LIKE, key, value, mode);
    }

    public Like(Cmd key, String value, LikeMode mode) {
        this(SqlConst.LIKE, key, Methods.convert(value), mode);
    }

    public LikeMode getMode() {
        return mode;
    }

    @Override
    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        getField().sql(module, this, context, sqlBuilder);
        sqlBuilder.append(getOperator());
        sqlBuilder.append(SqlConst.CONCAT).append(SqlConst.BRACKET_LEFT);

        boolean before = false;
        boolean after = false;

        switch (this.mode) {
            case NONE: {
                break;
            }
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
            sqlBuilder.append(SqlConst.VAGUE_SYMBOL).append(SqlConst.DELIMITER);
        }
        getValue().sql(module, this, context, sqlBuilder);
        if (after) {
            sqlBuilder.append(SqlConst.DELIMITER).append(SqlConst.VAGUE_SYMBOL);
        }
        sqlBuilder.append(SqlConst.BRACKET_RIGHT);
        return sqlBuilder;
    }
}
