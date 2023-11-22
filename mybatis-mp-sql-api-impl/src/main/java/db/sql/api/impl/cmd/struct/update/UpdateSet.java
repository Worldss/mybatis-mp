package db.sql.api.impl.cmd.struct.update;


import db.sql.api.SqlBuilderContext;
import db.sql.api.Cmd;
import db.sql.api.impl.cmd.basic.TableField;
import db.sql.api.impl.cmd.basic.Value;
import db.sql.api.tookit.CmdUtils;
import db.sql.api.impl.tookit.SqlConst;

public class UpdateSet implements db.sql.api.cmd.struct.update.UpdateSet<TableField, Value>, Cmd {

    private final TableField field;

    private final Value value;

    public UpdateSet(TableField field, Value value) {
        this.field = field;
        this.value = value;
    }

    @Override
    public TableField getField() {
        return this.field;
    }

    @Override
    public Value getValue() {
        return this.value;
    }

    @Override
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = this.field.sql(this, context, sqlBuilder);
        sqlBuilder = sqlBuilder.append(SqlConst.EQ);
        sqlBuilder = this.value.sql(this, context, sqlBuilder);
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.field, this.value);
    }
}
