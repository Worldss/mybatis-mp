package cn.mybatis.mp.core.sql.executor;

import cn.mybatis.mp.core.tenant.TenantUtil;
import cn.mybatis.mp.core.util.ForeignKeyUtil;
import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.cmd.JoinMode;
import db.sql.api.impl.cmd.basic.Dataset;
import db.sql.api.impl.cmd.condition.Exists;
import db.sql.api.impl.cmd.condition.In;
import db.sql.api.impl.cmd.executor.AbstractSubQuery;
import db.sql.api.impl.cmd.struct.OnDataset;
import db.sql.api.impl.tookit.SqlConst;

import java.util.Objects;
import java.util.function.Consumer;

public abstract class BaseSubQuery<Q extends BaseSubQuery> extends AbstractSubQuery<Q, MybatisCmdFactory> {
    private final String alias;

    public BaseSubQuery(String alias) {
        super(new MybatisCmdFactory("st"));
        this.alias = alias;
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public Q as(String alias) {
        throw new RuntimeException("not support");
    }

    protected void addTenantCondition(Class entity, int storey) {
        TenantUtil.addTenantCondition(this, this.$(), entity, storey);
    }

    @Override
    public Q from(Class entity, int storey, Consumer<Dataset> consumer) {
        this.addTenantCondition(entity, storey);
        return super.from(entity, storey, consumer);
    }

    @Override
    public Q join(JoinMode mode, Class mainTable, int mainTableStorey, Class secondTable, int secondTableStorey, Consumer<OnDataset> consumer) {
        this.addTenantCondition(secondTable, secondTableStorey);
        if (Objects.isNull(consumer)) {
            //自动加上外键连接条件
            consumer = ForeignKeyUtil.buildForeignKeyOnConsumer(this.$, mainTable, mainTableStorey, secondTable, secondTableStorey);
        }
        this.join(mode, $().table(mainTable, mainTableStorey), $().table(secondTable, secondTableStorey), consumer);
        return (Q) this;
    }

    @Override
    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        if (parent instanceof In || parent instanceof Exists) {
            return super.sql(module, this, context, sqlBuilder);
        }
        sqlBuilder = sqlBuilder.append(SqlConst.BRACKET_LEFT);
        sqlBuilder = super.sql(module, this, context, sqlBuilder);
        sqlBuilder = sqlBuilder.append(SqlConst.BRACKET_RIGHT);
        if (this.alias != null) {
            sqlBuilder = sqlBuilder.append(SqlConst.AS).append(this.alias);
        }

        return sqlBuilder;
    }
}
