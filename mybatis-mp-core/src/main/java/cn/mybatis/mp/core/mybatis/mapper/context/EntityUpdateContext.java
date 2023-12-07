package cn.mybatis.mp.core.mybatis.mapper.context;


import cn.mybatis.mp.core.db.reflect.TableFieldInfo;
import cn.mybatis.mp.core.db.reflect.TableInfo;
import cn.mybatis.mp.core.db.reflect.Tables;
import cn.mybatis.mp.core.sql.executor.Update;
import cn.mybatis.mp.core.tenant.TenantContext;
import cn.mybatis.mp.core.tenant.TenantInfo;
import cn.mybatis.mp.db.annotations.TableField;
import db.sql.api.impl.cmd.basic.Table;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public class EntityUpdateContext<T> extends SQLCmdUpdateContext {

    private final T value;

    public EntityUpdateContext(T t) {
        this(t, Collections.emptySet());
    }

    public EntityUpdateContext(T t, Set<String> forceUpdateFields) {
        super(createCmd(t, forceUpdateFields));
        this.value = t;
    }

    private static Update createCmd(Object t, Set<String> forceUpdateFields) {
        TableInfo tableInfo = Tables.get(t.getClass());
        Update update = new Update() {{
            Table table = $(t.getClass());
            update(table);
            for (int i = 0; i < tableInfo.getFieldSize(); i++) {
                TableFieldInfo tableFieldInfo = tableInfo.getTableFieldInfos().get(i);
                Object value = tableFieldInfo.getValue(t);
                if (tableFieldInfo.isTableId()) {
                    if (Objects.isNull(value)) {
                        throw new RuntimeException(tableFieldInfo.getField().getName() + " can't be null");
                    }
                    eq($.field(table, tableFieldInfo.getColumnName()), $.value(value));
                } else if (tableFieldInfo.isTenantId()) {
                    //添加租户条件
                    TenantInfo tenantInfo = TenantContext.getTenantInfo();
                    if (tenantInfo != null) {
                        Object tenantId = tenantInfo.getTenantId();
                        if (Objects.nonNull(tenantId)) {
                            eq($.field(table, tableFieldInfo.getColumnName()), $.value(tenantId));
                        }
                    }
                } else if (tableFieldInfo.isVersion()) {
                    if (Objects.isNull(value)) {
                        throw new RuntimeException(tableFieldInfo.getField().getName() + " is version filed, can't be null");
                    }
                    Integer version = (Integer) value + 1;
                    //乐观锁设置
                    set($.field(table, tableFieldInfo.getColumnName()), $.value(version));
                    eq($.field(table, tableFieldInfo.getColumnName()), $.value(value));
                    try {
                        //乐观锁回写
                        tableFieldInfo.getWriteFieldInvoker().invoke(t, new Object[]{version});
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                } else if (forceUpdateFields.contains(tableFieldInfo.getField().getName())) {
                    set($.field(table, tableFieldInfo.getColumnName()), Objects.isNull(value) ? $.NULL() : $.value(value));
                } else if (!tableFieldInfo.getTableFieldAnnotation().update()) {
                } else if (Objects.nonNull(value)) {
                    TableField tableField = tableFieldInfo.getTableFieldAnnotation();
                    MybatisParameter mybatisParameter = new MybatisParameter(value, tableField.typeHandler(), tableField.jdbcType());
                    set($.field(table, tableFieldInfo.getColumnName()), $.value(mybatisParameter));
                }
            }
        }};

        if (update.getWhere() == null || !update.getWhere().hasContent()) {
            throw new RuntimeException(MessageFormat.format("entity {0} can't found id", t.getClass()));
        }
        return update;
    }
}
