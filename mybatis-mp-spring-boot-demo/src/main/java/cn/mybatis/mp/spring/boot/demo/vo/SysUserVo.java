package cn.mybatis.mp.spring.boot.demo.vo;

import cn.mybatis.mp.db.annotations.NestedResultEntity;
import cn.mybatis.mp.db.annotations.ResultEntity;
import cn.mybatis.mp.db.annotations.ResultEntityField;
import cn.mybatis.mp.db.annotations.ResultField;
import cn.mybatis.mp.spring.boot.demo.DO.SysRole;
import cn.mybatis.mp.spring.boot.demo.DO.SysUser;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@ResultEntity(SysUser.class)
public class SysUserVo {

    private Integer id;

    private String name;

    @ResultEntityField(target = SysUser.class, property = "createTime")
    private LocalDateTime create_time;

    @ResultField("copy_name")
    private String copyName;

    @NestedResultEntity(target = SysRole.class)
    private SysRole sysRole;
}
