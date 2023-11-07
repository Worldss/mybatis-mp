package db.sql.core.api.cmd;

import db.sql.api.Cmd;
import db.sql.api.JoinMode;
import db.sql.api.SqlBuilderContext;
import db.sql.core.api.tookit.CmdUtils;
import db.sql.core.api.tookit.SqlConst;

public class Join implements db.sql.api.Join<Join, Dataset, On>, Cmd {

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
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = sqlBuilder.append(SqlConst.BLANK).append(this.mode.getSql());
        sqlBuilder = getSecondTable().sql(this, context, sqlBuilder);
        sqlBuilder = getOn().sql(this, context, sqlBuilder);
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.mainTable, this.secondTable, this.on);
    }
}
