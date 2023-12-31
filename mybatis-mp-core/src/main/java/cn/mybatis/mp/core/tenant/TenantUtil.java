package cn.mybatis.mp.core.tenant;

import cn.mybatis.mp.core.db.reflect.ModelInfo;
import cn.mybatis.mp.core.db.reflect.Models;
import cn.mybatis.mp.core.db.reflect.TableInfo;
import cn.mybatis.mp.core.db.reflect.Tables;
import cn.mybatis.mp.db.Model;
import db.sql.api.cmd.executor.method.condition.ICompare;
import db.sql.api.impl.cmd.CmdFactory;

import java.io.Serializable;
import java.util.Objects;

public final class TenantUtil {

    public static Serializable getTenantId() {
        TenantInfo tenantInfo = TenantContext.getTenantInfo();
        if (Objects.isNull(tenantInfo)) {
            return null;
        }
        return tenantInfo.getTenantId();
    }

    /**
     * 设置实体类的租户ID
     *
     * @param model 实体类model
     */
    public static void setTenantId(Model model) {
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
     * 设置实体类的租户ID
     *
     * @param entity
     */
    public static Serializable setTenantId(Object entity) {
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
     * 添加租户条件
     *
     * @param ICompare   比较器
     * @param cmdFactory 命令工厂
     * @param entity     实体类
     * @param storey     实体类表的存储层级
     */
    public static void addTenantCondition(ICompare ICompare, CmdFactory cmdFactory, Class entity, int storey) {
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
        ICompare.eq(cmdFactory.field(entity, tableInfo.getTenantIdFieldInfo().getField().getName(), storey), tenantId);
    }
}
