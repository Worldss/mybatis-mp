package db.sql.api.executor;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.tookit.CmdUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface Executor extends Cmd {

    Map<Class<? extends Cmd>, Integer> cmdSorts();

    List<Cmd> cmds();

    default Comparator<Cmd> comparator() {
        return (o1, o2) -> {
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
        };
    }


    default List<Cmd> sortedCmds() {
        List<Cmd> cmdList = cmds();
        if (cmdList == null || cmdList.isEmpty()) {
            return cmdList;
        }
        Comparator<Cmd> comparator = comparator();
        cmdList = cmdList.stream().sorted(comparator).collect(Collectors.toList());
        return cmdList;
    }


    @Override
    default StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        return this.sql(context, sqlBuilder);
    }

    default StringBuilder sql(SqlBuilderContext context, StringBuilder sqlBuilder) {
        List<Cmd> cmdList = cmds();
        if (cmdList == null || cmdList.isEmpty()) {
            return sqlBuilder;
        }
        return this.sql(this.sortedCmds(), context, sqlBuilder);
    }

    default StringBuilder sql(List<Cmd> sortedCmds, SqlBuilderContext context, StringBuilder sqlBuilder) {
        if (sortedCmds == null || sortedCmds.isEmpty()) {
            return sqlBuilder;
        }
        return CmdUtils.join(null, context, sqlBuilder, sortedCmds);
    }
}
