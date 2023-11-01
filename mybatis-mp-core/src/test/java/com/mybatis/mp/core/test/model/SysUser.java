package com.mybatis.mp.core.test.model;

import cn.mybatis.mp.db.annotations.ForeignKey;
import cn.mybatis.mp.db.annotations.Table;
import cn.mybatis.mp.db.annotations.TableField;
import cn.mybatis.mp.db.annotations.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Table("t_sys_user")
public class SysUser {

    @TableId
    @TableField("_id")
    private Integer id;

    private String userName;

    private String password;

    @ForeignKey(SysRole.class)
    private Integer role_id;

    private LocalDateTime create_time;
}
