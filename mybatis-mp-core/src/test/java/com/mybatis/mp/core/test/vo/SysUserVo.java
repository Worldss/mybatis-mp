package com.mybatis.mp.core.test.vo;

import cn.mybatis.mp.db.annotations.NestedResultEntity;
import cn.mybatis.mp.db.annotations.ResultEntity;
import cn.mybatis.mp.db.annotations.ResultEntityField;
import cn.mybatis.mp.db.annotations.ResultField;
import com.mybatis.mp.core.test.DO.SysRole;
import com.mybatis.mp.core.test.DO.SysUser;
import lombok.Data;

@Data
@ResultEntity(SysUser.class)
public class SysUserVo {

    private Integer id;

    private String userName;

    @ResultEntityField(target = SysUser.class, property = "password")
    private String pwd;

    @ResultField("kk")
    private String kkName;

    @NestedResultEntity(target = SysRole.class)
    private SysRole role;
}
