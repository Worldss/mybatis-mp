package db.sql.core.api.cmd;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.core.api.tookit.CmdJoins;
import db.sql.core.api.tookit.SqlConst;

import java.util.ArrayList;
import java.util.List;

public class GroupBy implements db.sql.api.GroupBy<GroupBy, Cmd>, Cmd {

    private final List<Cmd> groupByFields = new ArrayList<>();

    @Override
    public GroupBy groupBy(Cmd field) {
        groupByFields.add(field);
        return this;
    }

    @Override
    public List<Cmd> getGroupByFiled() {
        return groupByFields;
    }

    @Override
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder.append(SqlConst.GROUP_BY);
        CmdJoins.join(user, context, sqlBuilder, this.groupByFields, SqlConst.DELIMITER);
        return sqlBuilder;
    }
}