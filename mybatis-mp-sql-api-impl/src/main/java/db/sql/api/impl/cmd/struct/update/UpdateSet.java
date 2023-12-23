package db.sql.api.impl.cmd.struct.update;


import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.cmd.struct.update.IUpdateSet;
import db.sql.api.impl.cmd.basic.TableField;
import db.sql.api.impl.tookit.SqlConst;
import db.sql.api.tookit.CmdUtils;

public class UpdateSet implements IUpdateSet<TableField, Cmd> {

    private final TableField field;

    private final Cmd value;

    public UpdateSet(TableField field, Cmd value) {
        this.field = field;
        this.value = value;
    }

    @Override
    public TableField getField() {
        return this.field;
    }

    @Override
    public Cmd getValue() {
        return this.value;
    }

    @Override
    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        this.field.sql(module, this, context, sqlBuilder);
        sqlBuilder.append(SqlConst.EQ);
        this.value.sql(module, this, context, sqlBuilder);
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.field, this.value);
    }
}
