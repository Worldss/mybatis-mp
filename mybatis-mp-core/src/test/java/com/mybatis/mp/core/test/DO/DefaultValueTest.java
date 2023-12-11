package com.mybatis.mp.core.test.DO;

import cn.mybatis.mp.db.annotations.Table;
import cn.mybatis.mp.db.annotations.TableField;
import cn.mybatis.mp.db.annotations.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Table
@Data
public class DefaultValueTest {

    @TableId
    private Integer id;

    @TableField(defaultValue = "{BLANK}")
    private String value1;

    @TableField(defaultValue = "1")
    private Integer value2;

    @TableField(defaultValue = "{NOW}")
    private LocalDateTime createTime;
}
