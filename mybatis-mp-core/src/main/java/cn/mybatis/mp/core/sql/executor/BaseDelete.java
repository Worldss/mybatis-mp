package cn.mybatis.mp.core.sql.executor;

import cn.mybatis.mp.core.tenant.TenantUtil;
import cn.mybatis.mp.core.util.ForeignKeyUtil;
import db.sql.api.cmd.JoinMode;
import db.sql.api.impl.cmd.basic.Dataset;
import db.sql.api.impl.cmd.executor.AbstractDelete;
import db.sql.api.impl.cmd.struct.On;

import java.util.Objects;
import java.util.function.Consumer;

public class BaseDelete<T extends BaseDelete> extends AbstractDelete<T, MybatisCmdFactory> {

    public BaseDelete() {
        super(new MybatisCmdFactory());
    }

    protected void addTenantCondition(Class entity, int storey) {
        TenantUtil.addTenantCondition(this, this.$(), entity, storey);
    }

    @Override
public T from(Class entity, int storey, Consumer<Dataset> consumer) {
        this.addTenantCondition(entity, storey);
        return super.from(entity, storey, consumer);
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
