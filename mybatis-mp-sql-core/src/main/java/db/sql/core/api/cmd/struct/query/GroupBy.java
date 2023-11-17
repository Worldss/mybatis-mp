package db.sql.core.api.cmd.struct.query;

import db.sql.api.SqlBuilderContext;
import db.sql.api.cmd.Cmd;
import db.sql.api.tookit.CmdUtils;
import db.sql.core.api.tookit.SqlConst;

import java.util.ArrayList;
import java.util.List;

public class GroupBy implements db.sql.api.cmd.struct.query.GroupBy<GroupBy, Cmd>, Cmd {

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
        CmdUtils.join(user, context, sqlBuilder, this.groupByFields, SqlConst.DELIMITER);
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.groupByFields);
    }
}
