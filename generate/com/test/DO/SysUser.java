package com.test.DO;

import java.time.LocalTime;
import java.time.LocalDateTime;
import cn.mybatis.mp.db.annotations.TableId;
import cn.mybatis.mp.db.annotations.TableField;
import cn.mybatis.mp.db.annotations.Table;
import cn.mybatis.mp.db.IdAutoType;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2023-12-04
 */
@Table(value="sys_user")
public class SysUser {

    @TableId(IdAutoType.AUTO)
    private Integer id;

    private String name;

    @TableField(defaultValue = "1")
    private Integer roleId;

    @TableField(defaultValue = "{NOW}")
    private LocalDateTime createDate;

    @TableField(update = false,defaultValue = "{NOW}")
    private LocalTime createTime;

    public void setId (Integer id) {
        this.id = id;
    }

    public Integer getId () {
        return this.id;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getName () {
        return this.name;
    }

    public void setRoleId (Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getRoleId () {
        return this.roleId;
    }

    public void setCreateDate (LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getCreateDate () {
        return this.createDate;
    }

    public void setCreateTime (LocalTime createTime) {
        this.createTime = createTime;
    }

    public LocalTime getCreateTime () {
        return this.createTime;
    }

    @Override
    public String toString() {
        return "SysUser{" +
            "id = " + id +
            ", name = " + name +
            ", roleId = " + roleId +
            ", createDate = " + createDate +
            ", createTime = " + createTime +
        "}";
    }
}
