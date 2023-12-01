package com.mybatis.mp.core.test.model;

import cn.mybatis.mp.db.Model;

import com.mybatis.mp.core.test.DO.SysUser;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SysUserModel implements Model<SysUser> {

    private Integer id;

    private String userName;

    private String password;

    private Integer role_id;

    private LocalDateTime create_time;
}
