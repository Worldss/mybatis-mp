package cn.mybatis.mp.core.sql.executor;

import cn.mybatis.mp.core.db.reflect.TableFieldInfo;
import cn.mybatis.mp.core.db.reflect.TableInfo;
import cn.mybatis.mp.core.db.reflect.Tables;
import cn.mybatis.mp.core.logicDelete.LogicDeleteUtil;
import cn.mybatis.mp.core.sql.MybatisCmdFactory;
import cn.mybatis.mp.core.tenant.TenantUtil;
import cn.mybatis.mp.core.util.ForeignKeyUtil;
import db.sql.api.Cmd;
import db.sql.api.impl.cmd.executor.AbstractQuery;
import db.sql.api.impl.cmd.struct.OnDataset;
import db.sql.api.impl.cmd.struct.Where;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public abstract class BaseQuery<Q extends BaseQuery> extends AbstractQuery<Q, MybatisCmdFactory> {

    protected Class returnType;

    public BaseQuery() {
        this(new MybatisCmdFactory());
    }

    public BaseQuery(MybatisCmdFactory mybatisCmdFactory) {
        super(mybatisCmdFactory);
    }

    public BaseQuery(Where where) {
        super(where);
    }

    @Override
    public Q select(Class entity, int storey) {
        TableInfo tableInfo = Tables.get(entity);

        List<Cmd> list = new ArrayList<>(tableInfo.getFieldSize());
        for (int i = 0; i < tableInfo.getFieldSize(); i++) {
            TableFieldInfo tableFieldInfo = tableInfo.getTableFieldInfos().get(i);
            if (tableFieldInfo.getTableFieldAnnotation().select()) {
                list.add($.field(entity, tableFieldInfo.getField().getName(), storey));
            }
        }
        this.select(list);

        return (Q) this;
    }

    @Override
    public Q select(int storey, Class... entities) {
        List<Cmd> list = new ArrayList<>(entities.length * 10);
        for (Class entity : entities) {
            TableInfo tableInfo = Tables.get(entity);
            for (int i = 0; i < tableInfo.getFieldSize(); i++) {
                TableFieldInfo tableFieldInfo = tableInfo.getTableFieldInfos().get(i);
                if (tableFieldInfo.getTableFieldAnnotation().select()) {
                    list.add($.field(entity, tableFieldInfo.getField().getName(), storey));
                }
            }
        }
        this.select(list);
        return (Q) this;
    }

    protected void addTenantCondition(Class entity, int storey) {
        TenantUtil.addTenantCondition(this, $, entity, storey);
    }

    protected void addLogicDeleteCondition(Class entity, int storey) {
        LogicDeleteUtil.addLogicDeleteCondition(this, $, entity, storey);
    }


    @Override
    public void fromEntityIntercept(Class entity, int storey) {
        this.addTenantCondition(entity, storey);
        this.addLogicDeleteCondition(entity, storey);
    }

    @Override
    public Consumer<OnDataset> joinEntityIntercept(Class mainTable, int mainTableStorey, Class secondTable, int secondTableStorey, Consumer<OnDataset> consumer) {
        this.addTenantCondition(secondTable, secondTableStorey);
        this.addLogicDeleteCondition(secondTable, secondTableStorey);
        if (Objects.isNull(consumer)) {
            //自动加上外键连接条件
            consumer = ForeignKeyUtil.buildForeignKeyOnConsumer($, mainTable, mainTableStorey, secondTable, secondTableStorey);
        }
        return consumer;
    }

    public Class getReturnType() {
        return returnType;
    }

    public Q setReturnType(Class returnType) {
        this.returnType = returnType;
        return (Q) this;
    }
}
