package cn.mybatis.mp.core.mybatis.mapper.context;


import cn.mybatis.mp.core.db.reflect.ModelInfo;
import cn.mybatis.mp.core.db.reflect.Models;
import cn.mybatis.mp.core.sql.executor.Update;
import cn.mybatis.mp.core.tenant.TenantContext;
import cn.mybatis.mp.core.tenant.TenantInfo;
import cn.mybatis.mp.db.Model;
import cn.mybatis.mp.db.annotations.TableField;
import db.sql.api.impl.cmd.basic.Table;
import db.sql.api.impl.cmd.struct.Where;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public class ModelUpdateWithWhereContext<T extends Model> extends SQLCmdUpdateContext {

    private final T value;

    public ModelUpdateWithWhereContext(T t, Where where) {
        this(t, where, Collections.emptySet());
    }

    public ModelUpdateWithWhereContext(T t, Where where, Set<String> forceUpdateFields) {
        super(createCmd(t, where, forceUpdateFields));
        this.value = t;
    }

    private static Update createCmd(Object t, Where where, Set<String> forceUpdateFields) {
        ModelInfo modelInfo = Models.get(t.getClass());
        Update update = new Update(where) {{
            Table table = $.table(modelInfo.getTableInfo().getSchemaAndTableName());
            update(table);
            modelInfo.getModelFieldInfos().stream().forEach(item -> {
                Object value = item.getValue(t);
                if (item.getTableFieldInfo().isTenantId()) {
                    //添加租户条件
                    TenantInfo tenantInfo = TenantContext.getTenantInfo();
                    if (tenantInfo != null) {
                        Object tenantId = tenantInfo.getTenantId();
                        if (Objects.nonNull(tenantId)) {
                            eq($.field(table, item.getTableFieldInfo().getColumnName()), $.value(tenantId));
                        }
                    }
                } else if (forceUpdateFields.contains(item.getField().getName())) {
                    set($.field(table, item.getTableFieldInfo().getColumnName()), Objects.isNull(value) ? $.NULL() : $.value(value));
                } else if (!item.getTableFieldInfo().getTableFieldAnnotation().update()) {

                } else if (Objects.nonNull(value)) {
                    TableField tableField = item.getTableFieldInfo().getTableFieldAnnotation();
                    MybatisParameter mybatisParameter = new MybatisParameter(value, tableField.typeHandler(), tableField.jdbcType());
                    set($.field(table, item.getTableFieldInfo().getColumnName()), $.value(mybatisParameter));
                }
            });
        }};
        if (update.getWhere() == null || !update.getWhere().conditionChain().hasContent()) {
            throw new RuntimeException(MessageFormat.format("model {0} can't found id", t.getClass()));
        }
        return update;
    }


}
