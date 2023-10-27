package db.sql.core.api.cmd.executor;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.core.api.cmd.CmdFactory;
import db.sql.core.api.tookit.CmdJoins;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface Executor<SELF extends Executor, CMD_FACTORY extends CmdFactory> extends Cmd {

    CMD_FACTORY $();

    SELF append(Cmd cmd);

    Map<Class<? extends Cmd>, Integer> cmdSorts();

    List<Cmd> cmds();


    @Override
    default StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        return this.sql(context, sqlBuilder);
    }

    default List<Cmd> sortedCmds() {
        List<Cmd> cmdList = cmds();
        if (cmdList == null || cmdList.isEmpty()) {
            return cmdList;
        }

        cmdList = cmdList.stream().sorted((o1, o2) -> {
            Integer n1 = cmdSorts().get(o1.getClass());
            Integer n2 = cmdSorts().get(o2.getClass());
            if (n1 == null && n2 == null) {
                return 0;
            }
            if (n1 == null) {
                return 1;
            }
            if (n2 == null) {
                return -1;
            }
            return n1.compareTo(n2);
        }).collect(Collectors.toList());
        return cmdList;
    }

    default StringBuilder sql(SqlBuilderContext context, StringBuilder sqlBuilder) {
        List<Cmd> cmdList = cmds();
        if (cmdList == null || cmdList.isEmpty()) {
            return sqlBuilder;
        }
        cmdList = sortedCmds();
        return CmdJoins.join(null, context, sqlBuilder, cmdList);
    }
}
