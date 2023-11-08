package db.sql.core.api.cmd;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.core.api.tookit.CmdUtils;

import java.util.List;

public class CmdList implements Cmd {

    private final String operator;

    private final List<Cmd> cmdList;

    public CmdList(String operator, List<Cmd> cmdList) {
        this.operator = operator;
        this.cmdList = cmdList;
    }

    @Override
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = sqlBuilder.append(this.operator);
        sqlBuilder = CmdUtils.join(user, context, sqlBuilder, this.cmdList);
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.cmdList);
    }
}
