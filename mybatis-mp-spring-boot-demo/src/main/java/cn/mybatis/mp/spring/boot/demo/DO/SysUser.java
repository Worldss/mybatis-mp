package cn.mybatis.mp.spring.boot.demo.DO;

import cn.mybatis.mp.db.annotations.ForeignKey;
import cn.mybatis.mp.db.annotations.Table;
import cn.mybatis.mp.db.annotations.TableField;
import cn.mybatis.mp.db.annotations.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Table
public class SysUser {

    @TableId
    private Integer id;

    private String name;

    @ForeignKey(SysRole.class)
    private Integer roleId;

    @TableField(update = false)
    private LocalDateTime createTime;
}
