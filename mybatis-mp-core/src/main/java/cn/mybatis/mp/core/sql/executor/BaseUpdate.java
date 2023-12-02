package cn.mybatis.mp.core.sql.executor;

import cn.mybatis.mp.core.logicDelete.LogicDeleteUtil;
import cn.mybatis.mp.core.tenant.TenantUtil;
import cn.mybatis.mp.core.util.ForeignKeyUtil;
import db.sql.api.impl.cmd.executor.AbstractUpdate;
import db.sql.api.impl.cmd.struct.OnTable;
import db.sql.api.impl.cmd.struct.Where;

import java.util.Objects;
import java.util.function.Consumer;

public abstract class BaseUpdate<T extends BaseUpdate> extends AbstractUpdate<T, MybatisCmdFactory> {

    public BaseUpdate() {
        super(new MybatisCmdFactory());
    }

    public BaseUpdate(Where where) {
        super(where);
    }


    protected void addTenantCondition(Class entity, int storey) {
        TenantUtil.addTenantCondition(this, $, entity, storey);
    }

    protected void addLogicDeleteCondition(Class entity, int storey) {
        LogicDeleteUtil.addLogicDeleteCondition(false, this, $, entity, storey);
    }

    @Override
    public void updateEntityIntercept(Class entity) {
        this.addTenantCondition(entity, 1);
        this.addLogicDeleteCondition(entity, 1);
    }

    @Override
    public Consumer<OnTable> joinEntityIntercept(Class mainTable, int mainTableStorey, Class secondTable, int secondTableStorey, Consumer<OnTable> consumer) {
        this.addTenantCondition(secondTable, secondTableStorey);
        this.addLogicDeleteCondition(secondTable, secondTableStorey);
        if (Objects.isNull(consumer)) {
            //自动加上外键连接条件
            consumer = ForeignKeyUtil.buildForeignKeyOnConsumer($, mainTable, mainTableStorey, secondTable, secondTableStorey);
        }
        return consumer;
    }
}
