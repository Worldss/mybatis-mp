package com.mybatis.mp.core.test.model;

import cn.mybatis.mp.db.Model;

import com.mybatis.mp.core.test.DO.TenantTest;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TenantModel implements Model<TenantTest> {
    private String id;

    private Integer tenantId;

    private String name;

    private LocalDateTime createTime;
}
