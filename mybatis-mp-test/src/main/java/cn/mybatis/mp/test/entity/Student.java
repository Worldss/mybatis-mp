package cn.mybatis.mp.test.entity;

import lombok.Data;
import org.apache.ibatis.type.LocalDateTimeTypeHandler;
import cn.mybatis.mp.db.IdAutoType;
import cn.mybatis.mp.db.annotations.Ignore;
import cn.mybatis.mp.db.annotations.Table;
import cn.mybatis.mp.db.annotations.TableField;
import cn.mybatis.mp.db.annotations.TableId;

import java.time.LocalDateTime;

@Data
@Table
public class Student {

    @TableId(value = IdAutoType.SQL, sql = "SELECT LAST_INSERT_ID()")
    private Integer id;

    private String name;

    @Ignore
    private Boolean excellent;

    @TableField(typeHandler = LocalDateTimeTypeHandler.class)
    private LocalDateTime createTime;
}
