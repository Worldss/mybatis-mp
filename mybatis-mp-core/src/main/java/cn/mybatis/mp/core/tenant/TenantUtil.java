package cn.mybatis.mp.core.tenant;

import cn.mybatis.mp.core.db.reflect.ModelInfo;
import cn.mybatis.mp.core.db.reflect.Models;
import cn.mybatis.mp.core.db.reflect.TableInfo;
import cn.mybatis.mp.core.db.reflect.Tables;
import cn.mybatis.mp.core.sql.executor.MybatisCmdFactory;
import cn.mybatis.mp.db.Model;
import db.sql.api.cmd.executor.method.compare.Compare;

import java.io.Serializable;
import java.util.Objects;

public class TenantUtil {

    public static final Serializable getTenantId() {
        TenantInfo tenantInfo = TenantContext.getTenantInfo();
        if (Objects.isNull(tenantInfo)) {
            return null;
        }
        return tenantInfo.getTenantId();
    }

    /**
     * 设置实体类的租户ID
     *
     * @param entity
     */
    public static final void setTenantId(Object entity) {
        TableInfo tableInfo = Tables.get(entity.getClass());
        if (Objects.isNull(tableInfo.getTenantIdFieldInfo())) {
            return;
        }

        Serializable tenantId = getTenantId();
        if (Objects.isNull(tenantId)) {
            return;
        }

        try {
            tableInfo.getTenantIdFieldInfo().getWriteFieldInvoker().invoke(entity, new Object[]{
                    tenantId
            });
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 设置实体类的租户ID
     *
     * @param model
     */
    public static final void setTenantId(Model model) {
        ModelInfo modelInfo = Models.get(model.getClass());
        if (Objects.isNull(modelInfo.getTenantIdFieldInfo())) {
            return;
        }

        Serializable tenantId = getTenantId();
        if (Objects.isNull(tenantId)) {
            return;
        }

        try {
            modelInfo.getTenantIdFieldInfo().getWriteFieldInvoker().invoke(model, new Object[]{
                    tenantId
            });
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 添加租户条件
     *
     * @param compare
     * @param mybatisCmdFactory
     * @param entity
     * @param storey
     */
    public static final void addTenantCondition(Compare compare, MybatisCmdFactory mybatisCmdFactory, Class entity, int storey) {
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
        compare.eq(mybatisCmdFactory.field(entity, tableInfo.getTenantIdFieldInfo().getField().getName(), storey), tenantId);
    }
}
