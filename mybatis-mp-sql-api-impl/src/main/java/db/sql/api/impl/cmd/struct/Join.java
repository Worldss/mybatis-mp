package db.sql.api.impl.cmd.struct;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.cmd.JoinMode;
import db.sql.api.cmd.struct.IJoin;
import db.sql.api.impl.cmd.basic.Dataset;
import db.sql.api.impl.tookit.SqlConst;
import db.sql.api.tookit.CmdUtils;

import java.util.function.Function;

public abstract class Join<SELF extends Join<SELF, TABLE, ON>, TABLE extends Dataset, ON extends On<ON, TABLE, SELF>> implements IJoin<SELF, TABLE, ON> {

    private final TABLE mainTable;

    private final TABLE secondTable;

    private final JoinMode mode;

    private final ON on;


    public Join(JoinMode mode, TABLE mainTable, TABLE secondTable, Function<SELF, ON> onFunction) {
        this.mode = mode;
        this.mainTable = mainTable;
        this.secondTable = secondTable;
        this.on = onFunction.apply((SELF) this);
    }

    @Override
    public TABLE getMainTable() {
        return mainTable;
    }

    @Override
    public TABLE getSecondTable() {
        return secondTable;
    }

    @Override
    public JoinMode getMode() {
        return mode;
    }

    @Override
    public ON getOn() {
        return on;
    }

    @Override
    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder.append(SqlConst.BLANK).append(this.mode.getSql());
        getSecondTable().sql(module, this, context, sqlBuilder);
        getOn().sql(module, this, context, sqlBuilder);
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.mainTable, this.secondTable, this.on);
    }
}
