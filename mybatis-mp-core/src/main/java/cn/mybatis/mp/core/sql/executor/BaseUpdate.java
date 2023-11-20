package cn.mybatis.mp.core.sql.executor;

import cn.mybatis.mp.core.db.reflect.ForeignInfo;
import cn.mybatis.mp.core.db.reflect.TableFieldInfo;
import cn.mybatis.mp.core.db.reflect.TableInfo;
import cn.mybatis.mp.core.db.reflect.Tables;
import cn.mybatis.mp.core.tenant.TenantContext;
import cn.mybatis.mp.core.tenant.TenantInfo;
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
        TenantInfo tenantInfo = TenantContext.getTenantInfo();
        if (Objects.isNull(tenantInfo)) {
            return;
        }
        Serializable tenantId = tenantInfo.getTenantId();
        if (Objects.isNull(tenantId)) {
            return;
        }
        TableInfo tableInfo = Tables.get(entity);
        if (Objects.isNull(tableInfo.getTenantIdFieldInfo())) {
            return;
        }
        this.eq($.field(entity, tableInfo.getTenantIdFieldInfo().getField().getName(), storey), tenantId);
    }


    @Override
    public T join(JoinMode mode, Class mainTable, int mainTableStorey, Class secondTable, int secondTableStorey, Consumer<On> consumer) {
        this.addTenantCondition(secondTable, secondTableStorey);
        if (consumer == null) {
            consumer = on -> {

            };
        }

        TableInfo mainTableInfo = Tables.get(mainTable);
        TableInfo secondTableInfo = Tables.get(secondTable);
        ForeignInfo foreignInfo;
        if ((foreignInfo = secondTableInfo.getForeignInfo(mainTable)) != null) {
            final TableFieldInfo foreignFieldInfo = foreignInfo.getTableFieldInfo();
            consumer = consumer.andThen(on -> {
                on.eq(this.$().field(mainTable, mainTableInfo.getIdFieldInfo().getField().getName(), mainTableStorey), this.$().field(secondTable, foreignFieldInfo.getField().getName(), secondTableStorey));
            });
        } else if ((foreignInfo = mainTableInfo.getForeignInfo(secondTable)) != null) {
            final TableFieldInfo foreignFieldInfo = foreignInfo.getTableFieldInfo();
            consumer = consumer.andThen(on -> {
                on.eq(this.$().field(secondTable, secondTableInfo.getIdFieldInfo().getField().getName(), secondTableStorey), this.$().field(mainTable, foreignFieldInfo.getField().getName(), mainTableStorey));
            });
        }

        this.join(mode, $().table(mainTable, mainTableStorey), $().table(secondTable, secondTableStorey), consumer);
        return (T) this;
    }
}
