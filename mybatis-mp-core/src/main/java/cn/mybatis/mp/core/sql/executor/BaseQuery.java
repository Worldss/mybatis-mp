package cn.mybatis.mp.core.sql.executor;

import cn.mybatis.mp.core.db.reflect.TableInfo;
import cn.mybatis.mp.core.db.reflect.Tables;
import cn.mybatis.mp.core.tenant.TenantUtil;
import cn.mybatis.mp.core.util.ForeignKeyUtil;
import db.sql.api.Cmd;
import db.sql.api.Getter;
import db.sql.api.cmd.JoinMode;
import db.sql.api.impl.cmd.basic.Dataset;
import db.sql.api.impl.cmd.basic.TableField;
import db.sql.api.impl.cmd.executor.AbstractQuery;
import db.sql.api.impl.cmd.struct.On;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

public class BaseQuery<Q extends BaseQuery> extends AbstractQuery<Q, MybatisCmdFactory> {

    protected Class returnType;

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
            return super.select(entity, storey);
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
        TenantUtil.addTenantCondition(this, this.$(), entity, storey);
    }

    @Override
public Q from(Class entity, int storey, Consumer<Dataset> consumer) {
        this.addTenantCondition(entity, storey);
        return super.from(entity, storey, consumer);
    }

    @Override
public <T> Q select(Getter<T> column, Function<TableField, Cmd> f) {
        return super.select(column, f);
    }

    @Override
public Q join(JoinMode mode, Class mainTable, int mainTableStorey, Class secondTable, int secondTableStorey, Consumer<On> consumer) {
        this.addTenantCondition(secondTable, secondTableStorey);
        if (Objects.isNull(consumer)) {
            //自动加上外键连接条件
            consumer = ForeignKeyUtil.buildForeignKeyOnConsumer(this.$, mainTable, mainTableStorey, secondTable, secondTableStorey);
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
