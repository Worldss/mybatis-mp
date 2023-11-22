package db.sql.api.impl.cmd.struct;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.cmd.JoinMode;
import db.sql.api.impl.cmd.ConditionFaction;
import db.sql.api.impl.cmd.basic.Dataset;
import db.sql.api.impl.tookit.SqlConst;
import db.sql.api.tookit.CmdUtils;

public class Join implements db.sql.api.cmd.struct.Join<Join, Dataset, On>, Cmd {

    private final Dataset mainTable;

    private final Dataset secondTable;

    private final JoinMode mode;

    private final On on;

    public Join(ConditionFaction conditionFaction, JoinMode mode, Dataset mainTable, Dataset secondTable) {
        this.mode = mode;
        this.mainTable = mainTable;
        this.secondTable = secondTable;
        this.on = new On(conditionFaction, this);
    }

    @Override
    public Dataset getMainTable() {
        return mainTable;
    }

    @Override
    public Dataset getSecondTable() {
        return secondTable;
    }

    @Override
    public JoinMode getMode() {
        return mode;
    }

    @Override
    public On getOn() {
        return on;
    }

    @Override
    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = sqlBuilder.append(SqlConst.BLANK).append(this.mode.getSql());
        sqlBuilder = getSecondTable().sql(module, this, context, sqlBuilder);
        sqlBuilder = getOn().sql(module, this, context, sqlBuilder);
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.mainTable, this.secondTable, this.on);
    }
}
