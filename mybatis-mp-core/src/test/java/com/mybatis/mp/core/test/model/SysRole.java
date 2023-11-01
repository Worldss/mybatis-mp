package com.mybatis.mp.core.test.model;

import cn.mybatis.mp.db.annotations.Table;
import cn.mybatis.mp.db.annotations.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Table
public class SysRole {

    @TableId
    private Integer id;

    private String name;

    private LocalDateTime createTime;
}
