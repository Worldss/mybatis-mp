package db.sql.api.impl.cmd.basic;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.cmd.executor.SubQuery;
import db.sql.api.impl.cmd.struct.query.Select;
import db.sql.api.impl.tookit.SqlConst;
import db.sql.api.tookit.CmdUtils;

/**
 * 子查询表列
 */
public class SubQueryTableField extends Field<SubQueryTableField> {

    private final SubQuery subQuery;

    private final TableField tableField;

    public SubQueryTableField(SubQuery subQuery, TableField tableField) {
        this.subQuery = subQuery;
        this.tableField = tableField;
    }

    @Override
    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        if (subQuery.getAlias() != null) {
            sqlBuilder = sqlBuilder.append(SqlConst.BLANK).append(subQuery.getAlias()).append(SqlConst.DOT);
        } else {
            sqlBuilder = sqlBuilder.append(SqlConst.BLANK);
        }

        String prefix = tableField.getTable().getPrefix();
        if (prefix != null) {
            sqlBuilder = sqlBuilder.append(prefix);
        }
        sqlBuilder = sqlBuilder.append(this.tableField.getName());

        //拼接 select 的别名
        if (parent instanceof Select) {
            if (this.getAlias() != null) {
                sqlBuilder = sqlBuilder.append(SqlConst.AS);
                sqlBuilder = sqlBuilder.append(this.getAlias());
            }
            return sqlBuilder;
        }

        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, tableField);
    }
}
