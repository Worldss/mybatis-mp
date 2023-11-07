package db.sql.core.api.cmd;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.core.api.tookit.CmdJoins;

import java.util.ArrayList;
import java.util.List;

public class Unions implements Cmd {

    private List<Union> unions;

    public void add(Union union) {
        if (unions == null) {
            unions = new ArrayList<>();
        }
        unions.add(union);
    }

    @Override
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        if (unions == null || unions.isEmpty()) {
            return sqlBuilder;
        }
        for (Union union : unions) {
            sqlBuilder = union.sql(user, context, sqlBuilder);
        }
        return sqlBuilder;
    }
}
