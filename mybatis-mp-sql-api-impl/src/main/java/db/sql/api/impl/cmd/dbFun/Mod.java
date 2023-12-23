package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.impl.tookit.SqlConst;

public class Mod extends BasicFunction<Mod> {

    private final Number number;

    public Mod(Cmd key, Number number) {
        super(SqlConst.MOD, key);
        this.number = number;
    }

    @Override
    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder.append(operator).append(SqlConst.BRACKET_LEFT);
        this.key.sql(module, this, context, sqlBuilder);
        sqlBuilder.append(SqlConst.DELIMITER).append(this.number);
        sqlBuilder.append(SqlConst.BRACKET_RIGHT);
        appendAlias(module, parent, context, sqlBuilder);
        return sqlBuilder;
    }

}