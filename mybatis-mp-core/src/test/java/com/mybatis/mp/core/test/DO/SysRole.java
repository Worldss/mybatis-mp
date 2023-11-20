package com.mybatis.mp.core.test.DO;

import cn.mybatis.mp.db.IdAutoType;
import cn.mybatis.mp.db.annotations.Table;
import cn.mybatis.mp.db.annotations.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Table
public class SysRole {

    @TableId(IdAutoType.AUTO)
    private Integer id;

    private String name;

    private LocalDateTime createTime;
}
