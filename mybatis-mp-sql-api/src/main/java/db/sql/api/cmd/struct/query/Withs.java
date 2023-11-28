package db.sql.api.cmd.struct.query;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.tookit.CmdUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Withs implements Cmd {

    private List<With> withs;

    public void add(With with) {
        if (withs == null) {
            withs = new ArrayList<>();
        }
        withs.add(with);
    }

    @Override
    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        if (withs == null || withs.isEmpty()) {
            return sqlBuilder;
        }
        CmdUtils.join(module, this, context, sqlBuilder, this.withs, ",");
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.withs);
    }

    public List<With> getUnions() {
        return Collections.unmodifiableList(this.withs);
    }
}
