package db.sql.api.impl.cmd.executor;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.impl.cmd.CmdFactory;
import db.sql.api.impl.cmd.basic.Dataset;
import db.sql.api.impl.cmd.basic.DatasetField;
import db.sql.api.impl.cmd.condition.Exists;
import db.sql.api.impl.cmd.condition.In;
import db.sql.api.impl.cmd.struct.query.With;
import db.sql.api.impl.tookit.SqlConst;

/**
 * 子查询
 */
public class SubQuery extends AbstractSubQuery<SubQuery, CmdFactory> implements Dataset<SubQuery, DatasetField> {

    private final String alias;

    public SubQuery(String alias) {
        super(new CmdFactory("st"));
        this.alias = alias;
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public SubQuery as(String alias) {
        throw new RuntimeException("not support");
    }

    @Override
    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        if (parent instanceof Exists || parent instanceof In || parent instanceof With) {
            return super.sql(module, this, context, sqlBuilder);
        }
        sqlBuilder = sqlBuilder.append(SqlConst.BRACKET_LEFT);
        sqlBuilder = super.sql(module, this, context, sqlBuilder);
        sqlBuilder.append(SqlConst.BRACKET_RIGHT).append(SqlConst.AS).append(this.alias);
        return sqlBuilder;
    }
}
