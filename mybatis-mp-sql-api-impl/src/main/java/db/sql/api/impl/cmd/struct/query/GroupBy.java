package db.sql.api.impl.cmd.struct.query;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.cmd.struct.query.IGroupBy;
import db.sql.api.impl.tookit.SqlConst;
import db.sql.api.tookit.CmdUtils;

import java.util.ArrayList;
import java.util.List;

public class GroupBy implements IGroupBy<GroupBy, Cmd> {

    private final List<Cmd> groupByFields = new ArrayList<>();

    @Override
    public GroupBy groupBy(Cmd field) {
        groupByFields.add(field);
        return this;
    }

    @Override
    public List<Cmd> getGroupByFields() {
        return groupByFields;
    }

    @Override
    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder.append(SqlConst.GROUP_BY);
        CmdUtils.join(module, this, context, sqlBuilder, this.groupByFields, SqlConst.DELIMITER);
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.groupByFields);
    }
}
