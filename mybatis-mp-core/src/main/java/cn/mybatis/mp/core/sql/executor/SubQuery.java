package cn.mybatis.mp.core.sql.executor;

import db.sql.api.cmd.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.core.api.cmd.basic.Dataset;
import db.sql.core.api.cmd.condition.Exists;
import db.sql.core.api.cmd.condition.In;
import db.sql.core.api.tookit.SqlConst;

/**
 * 子查询
 */
public class SubQuery extends BaseQuery<SubQuery> implements Dataset<SubQuery> {

    private final String alias;

    private String prefix;

    public SubQuery() {
        this(null);
    }

    public SubQuery(String alias) {
        super(new MybatisCmdFactory("st"));
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
    public String getPrefix() {
        return prefix;
    }

    @Override
    public SubQuery setPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    @Override
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        if (user instanceof In || user instanceof Exists) {
            return super.sql(user, context, sqlBuilder);
        }
        sqlBuilder = sqlBuilder.append(SqlConst.BRACKET_LEFT);
        sqlBuilder = super.sql(user, context, sqlBuilder);
        sqlBuilder = sqlBuilder.append(SqlConst.BRACKET_RIGHT);
        if (this.alias != null) {
            sqlBuilder = sqlBuilder.append(SqlConst.AS).append(this.alias);
        }

        return sqlBuilder;
    }
}
