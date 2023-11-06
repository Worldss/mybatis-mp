package cn.mybatis.mp.spring.boot.demo.DO;

import cn.mybatis.mp.db.annotations.Ignore;
import cn.mybatis.mp.db.annotations.Table;
import cn.mybatis.mp.db.annotations.TableField;
import cn.mybatis.mp.db.annotations.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Table
public class SysRole {

    @TableId
    private Integer id;

    private String name;

    @TableField(update = false)
    private LocalDateTime createTime;

    @Ignore
    private String ignoreField;
}
