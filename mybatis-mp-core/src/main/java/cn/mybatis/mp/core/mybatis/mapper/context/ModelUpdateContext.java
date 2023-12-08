package cn.mybatis.mp.core.mybatis.mapper.context;


import cn.mybatis.mp.core.db.reflect.ModelFieldInfo;
import cn.mybatis.mp.core.db.reflect.ModelInfo;
import cn.mybatis.mp.core.db.reflect.Models;
import cn.mybatis.mp.core.sql.executor.Update;
import cn.mybatis.mp.core.tenant.TenantContext;
import cn.mybatis.mp.core.tenant.TenantInfo;
import cn.mybatis.mp.db.Model;
import cn.mybatis.mp.db.annotations.TableField;
import db.sql.api.impl.cmd.basic.Table;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public class ModelUpdateContext<T extends Model> extends SQLCmdUpdateContext {

    private final T value;

    public ModelUpdateContext(T t) {
        this(t, Collections.emptySet());
    }

    public ModelUpdateContext(T t, Set<String> forceUpdateFields) {
        super(createCmd(t, forceUpdateFields));
        this.value = t;
    }

    private static Update createCmd(Object t, Set<String> forceUpdateFields) {
        ModelInfo modelInfo = Models.get(t.getClass());
        Update update = new Update() {{
            Table table = $(modelInfo.getEntityType());
            update(table);
            for (int i = 0; i < modelInfo.getFieldSize(); i++) {
                ModelFieldInfo modelFieldInfo = modelInfo.getModelFieldInfos().get(i);
                Object value = modelFieldInfo.getValue(t);
                if (modelFieldInfo.getTableFieldInfo().isTableId()) {
                    if (Objects.isNull(value)) {
                        throw new RuntimeException(modelFieldInfo.getField().getName() + " can't be null");
                    }
                    eq($.field(table, modelFieldInfo.getTableFieldInfo().getColumnName()), $.value(value));
                } else if (modelFieldInfo.getTableFieldInfo().isTenantId()) {
                    //添加租户条件
                    TenantInfo tenantInfo = TenantContext.getTenantInfo();
                    if (tenantInfo != null) {
                        Object tenantId = tenantInfo.getTenantId();
                        if (Objects.nonNull(tenantId)) {
                            eq($.field(table, modelFieldInfo.getTableFieldInfo().getColumnName()), $.value(tenantId));
                        }
                    }
                } else if (modelFieldInfo.getTableFieldInfo().isVersion()) {
                    if (Objects.isNull(value)) {
                        throw new RuntimeException(modelFieldInfo.getField().getName() + " is version filed, can't be null");
                    }
                    Integer version = (Integer) value + 1;
                    //乐观锁设置
                    set($.field(table, modelFieldInfo.getTableFieldInfo().getColumnName()), $.value(version));
                    eq($.field(table, modelFieldInfo.getTableFieldInfo().getColumnName()), $.value(value));
                    try {
                        //乐观锁回写
                        modelFieldInfo.getWriteFieldInvoker().invoke(t, new Object[]{version});
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                } else if (forceUpdateFields.contains(modelFieldInfo.getField().getName())) {
                    set($.field(table, modelFieldInfo.getTableFieldInfo().getColumnName()), Objects.isNull(value) ? $.NULL() : $.value(value));
                } else if (Objects.nonNull(value)) {
                    TableField tableField = modelFieldInfo.getTableFieldInfo().getTableFieldAnnotation();
                    MybatisParameter mybatisParameter = new MybatisParameter(value, tableField.typeHandler(), tableField.jdbcType());
                    set($.field(table, modelFieldInfo.getTableFieldInfo().getColumnName()), $.value(mybatisParameter));
                }
            }
        }};
        if (update.getWhere() == null || !update.getWhere().hasContent()) {
            throw new RuntimeException(MessageFormat.format("model {0} can't found id", t.getClass()));
        }
        return update;
    }


}
