package db.sql.core.api.cmd;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.core.api.tookit.CmdUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Joins implements Cmd {

    private List<Join> joins;

    public Joins(){
        this(new ArrayList<>());
    }

    public Joins(List<Join> joins){
        this.joins=joins;
    }

    public void add(Join join){
        this.joins.add(join);
    }

    @Override
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        return CmdUtils.join(user, context, sqlBuilder, this.joins);
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.joins);
    }

    public List<Join> getJoins() {
        return Collections.unmodifiableList(joins);
    }
}
