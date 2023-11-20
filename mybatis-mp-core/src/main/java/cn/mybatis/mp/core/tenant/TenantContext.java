package cn.mybatis.mp.core.tenant;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * 多租户上下文
 */
public class TenantContext {

    private TenantContext() {
    }

    private static Supplier<TenantInfo> tenantInfoGetter;

    /**
     * 注册多租户获取器
     *
     * @param tenantInfoGetter
     */
    public static final void registerTenantGetter(Supplier<TenantInfo> tenantInfoGetter) {
        TenantContext.tenantInfoGetter = tenantInfoGetter;
    }

    /**
     * 获取租户信息
     *
     * @return
     */
    public static TenantInfo getTenantInfo() {
        if (Objects.isNull(tenantInfoGetter)) {
            return null;
        }
        return tenantInfoGetter.get();
    }
}
