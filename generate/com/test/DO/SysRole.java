package com.test.DO;

import java.time.LocalTime;
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
@Table(value="sys_role")
public class SysRole {

    @TableId(IdAutoType.AUTO)
    private Integer id;

    @TableField(defaultValue = "{BLANK}")
    private String name;

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

    public void setCreateTime (LocalTime createTime) {
        this.createTime = createTime;
    }

    public LocalTime getCreateTime () {
        return this.createTime;
    }

    @Override
    public String toString() {
        return "SysRole{" +
            "id = " + id +
            ", name = " + name +
            ", createTime = " + createTime +
        "}";
    }
}
