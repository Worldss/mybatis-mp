package db.sql.api.cmd.basic;


import db.sql.api.SqlBuilderContext;
import db.sql.api.Cmd;
import db.sql.api.tookit.CmdUtils;

import java.util.List;

public class UnionsCmdLists implements Cmd {

    private final List<CmdList> cmdList;

    public UnionsCmdLists(List<CmdList> cmdList) {
        this.cmdList = cmdList;
    }

    @Override
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        return CmdUtils.join(user, context, sqlBuilder, cmdList);
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.cmdList);
    }
}
