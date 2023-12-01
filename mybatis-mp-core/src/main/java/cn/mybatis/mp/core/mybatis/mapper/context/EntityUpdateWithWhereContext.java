package cn.mybatis.mp.core.mybatis.mapper.context;


import cn.mybatis.mp.core.db.reflect.TableInfo;
import cn.mybatis.mp.core.db.reflect.Tables;
import cn.mybatis.mp.core.sql.executor.Update;
import cn.mybatis.mp.core.tenant.TenantContext;
import cn.mybatis.mp.core.tenant.TenantInfo;
import cn.mybatis.mp.db.annotations.TableField;
import db.sql.api.impl.cmd.basic.Table;
import db.sql.api.impl.cmd.struct.Where;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public class EntityUpdateWithWhereContext<T> extends SQLCmdUpdateContext {

    private final T value;


    public EntityUpdateWithWhereContext(T t, Where where) {
        this(t, where, Collections.emptySet());
    }

    public EntityUpdateWithWhereContext(T t, Where where, Set<String> forceUpdateFields) {
        super(createCmd(t, where, forceUpdateFields));
        this.value = t;

    }

    private static Update createCmd(Object t, Where where, Set<String> forceUpdateFields) {
        TableInfo tableInfo = Tables.get(t.getClass());
        Update update = new Update(where) {{
            Table table = $(t.getClass());
            update(table);
            tableInfo.getTableFieldInfos().stream().forEach(item -> {
                Object value = item.getValue(t);
                if (item.isTenantId()) {
                    //添加租户条件
                    TenantInfo tenantInfo = TenantContext.getTenantInfo();
                    if (tenantInfo != null) {
                        Object tenantId = tenantInfo.getTenantId();
                        if (Objects.nonNull(tenantId)) {
                            eq($.field(table, item.getColumnName()), $.value(tenantId));
                        }
                    }
                }
                if (forceUpdateFields.contains(item.getField().getName())) {
                    set($.field(table, item.getColumnName()), Objects.isNull(value) ? $.NULL() : $.value(value));
                } else if (!item.getTableFieldAnnotation().update()) {

                } else if (Objects.nonNull(value)) {
                    TableField tableField = item.getTableFieldAnnotation();
                    MybatisParameter mybatisParameter = new MybatisParameter(value, tableField.typeHandler(), tableField.jdbcType());
                    set($.field(table, item.getColumnName()), $.value(mybatisParameter));
                }
            });
        }};
        return update;
    }
}
