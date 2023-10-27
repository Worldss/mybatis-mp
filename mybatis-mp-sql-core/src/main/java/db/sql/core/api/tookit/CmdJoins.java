package db.sql.core.api.tookit;


import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;

import java.util.Iterator;
import java.util.List;

public class CmdJoins {

    public static StringBuilder join(Cmd user, SqlBuilderContext context, StringBuilder builder, List<? extends Cmd> cmdList) {
        return join(user, context, builder, cmdList, null);
    }

    public static StringBuilder join(Cmd user, SqlBuilderContext context, StringBuilder builder, List<? extends Cmd> cmdList, CharSequence delimiter) {
        if (cmdList == null) {
            return builder;
        }
        Iterator<? extends Cmd> iterator = cmdList.iterator();
        if (!iterator.hasNext()) {
            return builder;
        }
        while (true) {
            Cmd cmd = iterator.next();
            builder = cmd.sql(user, context, builder);
            if (!iterator.hasNext()) {
                break;
            }
            if (delimiter != null) {
                builder.append(delimiter);
            }
        }
        return builder;
    }

    public static StringBuilder join(Cmd user, SqlBuilderContext context, StringBuilder builder, Cmd[] cmds, CharSequence delimiter) {
        if (cmds == null || cmds.length < 1) {
            return builder;
        }
        int length = cmds.length;
        for (int i = 0; i < length; i++) {
            if (i != 0 && delimiter != null) {
                builder.append(delimiter);
            }
            builder = cmds[i].sql(user, context, builder);
        }
        return builder;
    }
}
