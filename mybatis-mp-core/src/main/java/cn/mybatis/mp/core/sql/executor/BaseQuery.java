package cn.mybatis.mp.core.sql.executor;

import cn.mybatis.mp.core.db.reflect.ForeignInfo;
import cn.mybatis.mp.core.db.reflect.TableFieldInfo;
import cn.mybatis.mp.core.db.reflect.TableInfo;
import cn.mybatis.mp.core.db.reflect.Tables;
import cn.mybatis.mp.core.tenant.TenantContext;
import cn.mybatis.mp.core.tenant.TenantInfo;
import db.sql.api.Getter;
import db.sql.api.cmd.Cmd;
import db.sql.api.cmd.JoinMode;
import db.sql.core.api.cmd.basic.Dataset;
import db.sql.core.api.cmd.basic.TableField;
import db.sql.core.api.cmd.struct.On;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

public class BaseQuery<Q extends BaseQuery> extends db.sql.core.api.cmd.executor.AbstractQuery<Q, MybatisCmdFactory> {

    private Class returnType;

    public BaseQuery() {
        this(new MybatisCmdFactory());
    }

    public BaseQuery(MybatisCmdFactory mybatisCmdFactory) {
        super(mybatisCmdFactory);
    }

    @Override
    public Q select(Class entity, int storey) {
        TableInfo tableInfo = Tables.get(entity);
        if (tableInfo == null) {
            return (Q) super.select(entity, storey);
        } else {
            tableInfo.getTableFieldInfos().stream().forEach(item -> {
                if (item.getTableFieldAnnotation().select()) {
                    this.select($.field(entity, item.getField().getName(), storey));
                }
            });
        }
        return (Q) this;
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
    public Q from(Class entity, int storey, Consumer<Dataset> consumer) {
        this.addTenantCondition(entity,storey);
        return super.from(entity, storey, consumer);
    }

    @Override
    public <T> Q select(Getter<T> column, Function<TableField, Cmd> f) {
        return super.select(column, f);
    }

    @Override
    public Q join(JoinMode mode, Class mainTable, int mainTableStorey, Class secondTable, int secondTableStorey, Consumer<On> consumer) {
        this.addTenantCondition(secondTable,secondTableStorey);
        if (Objects.isNull(consumer)) {
            consumer = on -> {
                TableInfo mainTableInfo = Tables.get(mainTable);
                TableInfo secondTableInfo = Tables.get(secondTable);
                ForeignInfo foreignInfo;
                if ((foreignInfo = secondTableInfo.getForeignInfo(mainTable)) != null) {
                    TableFieldInfo foreignFieldInfo = foreignInfo.getTableFieldInfo();
                    on.eq(this.$().field(mainTable, mainTableInfo.getIdFieldInfo().getField().getName(), mainTableStorey), this.$().field(secondTable, foreignFieldInfo.getField().getName(), secondTableStorey));
                } else if ((foreignInfo = mainTableInfo.getForeignInfo(secondTable)) != null) {
                    TableFieldInfo foreignFieldInfo = foreignInfo.getTableFieldInfo();
                    on.eq(this.$().field(secondTable, secondTableInfo.getIdFieldInfo().getField().getName(), secondTableStorey), this.$().field(mainTable, foreignFieldInfo.getField().getName(), mainTableStorey));
                }
            };
        }
        this.join(mode, $().table(mainTable, mainTableStorey), $().table(secondTable, secondTableStorey), consumer);
        return (Q) this;
    }

    public Class getReturnType() {
        return returnType;
    }

    public Q setReturnType(Class returnType) {
        this.returnType = returnType;
        return (Q) this;
    }
}
