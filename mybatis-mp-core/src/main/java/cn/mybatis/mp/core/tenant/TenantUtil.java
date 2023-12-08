package cn.mybatis.mp.core.tenant;

import cn.mybatis.mp.core.db.reflect.ModelInfo;
import cn.mybatis.mp.core.db.reflect.Models;
import cn.mybatis.mp.core.db.reflect.TableInfo;
import cn.mybatis.mp.core.db.reflect.Tables;
import cn.mybatis.mp.db.Model;
import db.sql.api.cmd.executor.method.condition.compare.Compare;
import db.sql.api.impl.cmd.CmdFactory;

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
    public static final Serializable setTenantId(Object entity) {
        TableInfo tableInfo = Tables.get(entity.getClass());
        if (Objects.isNull(tableInfo.getTenantIdFieldInfo())) {
            return null;
        }

        Serializable tenantId = getTenantId();
        if (Objects.isNull(tenantId)) {
            return null;
        }

        try {
            tableInfo.getTenantIdFieldInfo().getWriteFieldInvoker().invoke(entity, new Object[]{
                    tenantId
            });
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return tenantId;
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
     * @param compare    比较器
     * @param cmdFactory 命令工厂
     * @param entity     实体类
     * @param storey     实体类表的存储层级
     */
    public static final void addTenantCondition(Compare compare, CmdFactory cmdFactory, Class entity, int storey) {
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
        compare.eq(cmdFactory.field(entity, tableInfo.getTenantIdFieldInfo().getField().getName(), storey), tenantId);
    }
}
