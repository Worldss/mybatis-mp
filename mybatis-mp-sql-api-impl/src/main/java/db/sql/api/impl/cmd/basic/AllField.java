package db.sql.api.impl.cmd.basic;


import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.impl.cmd.struct.query.Select;
import db.sql.api.impl.tookit.SqlConst;

public class AllField extends DatasetField {

    public final static AllField ALL = new AllField();

    public AllField() {
        super(null, SqlConst.ALL);
    }

    public AllField(Dataset table) {
        super(table, SqlConst.ALL);
    }

    @Override
    public DatasetField as(String alias) {
        throw new RuntimeException("AllField不能设置别名");
    }

    @Override
    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        if (module instanceof Select) {
            if (getTable() != null) {
                if (getTable().getAlias() != null) {
                    sqlBuilder.append(getTable().getAlias()).append(SqlConst.DOT);
                }
            }
        }
        return sqlBuilder.append(SqlConst.ALL);
    }
}
