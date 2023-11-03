package com.mybatis.mp.core.test.model;

import cn.mybatis.mp.db.IdAutoType;
import cn.mybatis.mp.db.annotations.Table;
import cn.mybatis.mp.db.annotations.TableId;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Table
public class SysUserScore {

    @TableId(IdAutoType.NONE)
    private Integer userId;

    private BigDecimal score;
}
