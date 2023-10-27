package db.sql.core.api.cmd;


import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.core.api.tookit.SqlConst;

public class AllField extends DatasetField {

    public AllField(Dataset table) {
        super(table, SqlConst.ALL);
    }

    @Override
    public DatasetField as(String alias) {
        throw new RuntimeException("AllField不能设置别名");
    }

    @Override
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        if (user instanceof Select) {
            if (getTable().getAlias() != null) {
                sqlBuilder = sqlBuilder.append(getTable().getAlias()).append(SqlConst.DOT);
            }
        }
        return sqlBuilder.append(SqlConst.ALL);
    }
}
