package db.sql.api.impl.cmd.basic;

import db.sql.api.Cmd;
import db.sql.api.DbType;
import db.sql.api.SqlBuilderContext;
import db.sql.api.cmd.basic.IOrderByDirection;
import db.sql.api.impl.cmd.condition.IsNull;
import db.sql.api.impl.cmd.dbFun.If;
import db.sql.api.impl.tookit.SqlConst;

import java.util.Objects;

public enum OrderByDirection implements IOrderByDirection {
    ASC(" ASC", ""),
    ASC_NULLS_FIRST(" ASC", " NULLS FIRST"),
    ASC_NULLS_LAST(" ASC", " NULLS LAST"),
    DESC(" DESC", ""),
    DESC_NULLS_FIRST(" DESC", " NULLS FIRST"),
    DESC_NULLS_LAST(" DESC", " NULLS LAST");
    private final String directionSQL;

    private final String nullsDirectionSQL;

    OrderByDirection(String directionSQL, String nullsDirectionSQL) {
        this.directionSQL = directionSQL;
        this.nullsDirectionSQL = nullsDirectionSQL;
    }


    @Override
    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder.append(this.directionSQL);
        if (context.getDbType() == DbType.MYSQL) {
            If if_ = null;
            if (this == DESC_NULLS_FIRST || this == ASC_NULLS_FIRST) {
                if_ = new If(new IsNull(parent), 1, 0);
            } else if (this == DESC_NULLS_LAST || this == DESC_NULLS_LAST) {
                if_ = new If(new IsNull(parent), 0, 1);
            }
            if (Objects.nonNull(if_)) {
                sqlBuilder.append(SqlConst.DELIMITER);
                if_.sql(module, this, context, sqlBuilder);
            }
        } else {
            sqlBuilder.append(this.nullsDirectionSQL);
        }
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return false;
    }
}
