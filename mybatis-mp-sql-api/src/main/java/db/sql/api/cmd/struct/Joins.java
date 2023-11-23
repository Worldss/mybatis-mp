package db.sql.api.cmd.struct;


import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.tookit.CmdUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Joins<JOIN extends Join> implements Cmd {

    private final List<JOIN> joins;

    public Joins() {
        this(new ArrayList<>());
    }

    public Joins(List<JOIN> joins) {
        this.joins = joins;
    }

    public void add(JOIN join) {
        this.joins.add(join);
    }

    @Override    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        return CmdUtils.join(module, this, context, sqlBuilder, this.joins);
    }

    @Override    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.joins);
    }

    public List<JOIN> getJoins() {
        return Collections.unmodifiableList(joins);
    }
}
