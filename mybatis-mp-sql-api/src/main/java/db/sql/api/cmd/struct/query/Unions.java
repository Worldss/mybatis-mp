package db.sql.api.cmd.struct.query;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.tookit.CmdUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Unions implements Cmd {

    private List<IUnion> unions;

    public void add(IUnion union) {
        if (unions == null) {
            unions = new ArrayList<>();
        }
        unions.add(union);
    }

    @Override
    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        if (unions == null || unions.isEmpty()) {
            return sqlBuilder;
        }
        for (IUnion union : unions) {
            sqlBuilder = union.sql(module, this, context, sqlBuilder);
        }
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.unions);
    }

    public List<IUnion> getUnions() {
        return Collections.unmodifiableList(unions);
    }
}
