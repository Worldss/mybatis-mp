package cn.mybatis.mp.core.sql.executor;

import cn.mybatis.mp.core.db.reflect.ForeignInfo;
import cn.mybatis.mp.core.db.reflect.TableFieldInfo;
import cn.mybatis.mp.core.db.reflect.TableInfo;
import cn.mybatis.mp.core.db.reflect.Tables;
import cn.mybatis.mp.core.tenant.TenantContext;
import cn.mybatis.mp.core.tenant.TenantInfo;
import cn.mybatis.mp.core.tenant.TenantUtil;
import cn.mybatis.mp.core.util.ForeignKeyUtil;
import db.sql.api.cmd.JoinMode;
import db.sql.core.api.cmd.basic.Dataset;
import db.sql.core.api.cmd.executor.AbstractUpdate;
import db.sql.core.api.cmd.struct.On;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Consumer;

public class BaseUpdate<T extends BaseUpdate> extends AbstractUpdate<T, MybatisCmdFactory> {

    public BaseUpdate() {
        super(new MybatisCmdFactory());
    }

    @Override
    public T update(Class... entities) {
        for (Class entity : entities) {
            this.addTenantCondition(entity, 1);
        }
        return super.update(entities);
    }

    protected void addTenantCondition(Class entity, int storey) {
        TenantUtil.addTenantCondition(this, this.$(), entity, storey);
    }


    @Override
    public T join(JoinMode mode, Class mainTable, int mainTableStorey, Class secondTable, int secondTableStorey, Consumer<On> consumer) {
        this.addTenantCondition(secondTable, secondTableStorey);
        if (Objects.isNull(consumer)) {
            consumer = (on) -> {
            };
        }
        //自动加上外键连接条件
        consumer = consumer.andThen(ForeignKeyUtil.buildForeignKeyOnConsumer(this.$, mainTable, mainTableStorey, secondTable, secondTableStorey));
        this.join(mode, $().table(mainTable, mainTableStorey), $().table(secondTable, secondTableStorey), consumer);
        return (T) this;
    }
}
