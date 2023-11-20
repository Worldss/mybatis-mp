package cn.mybatis.mp.core.tenant;

import java.io.Serializable;

public class TenantInfo {

    public TenantInfo(Serializable tenantId) {
        this(tenantId,false);
    }
    public TenantInfo(Serializable tenantId, boolean ignore) {
        this.tenantId = tenantId;
        this.ignore = ignore;
    }


    private final Serializable tenantId;

    private final boolean ignore;

    public Serializable getTenantId() {
        return tenantId;
    }

    public boolean isIgnore() {
        return ignore;
    }
}
