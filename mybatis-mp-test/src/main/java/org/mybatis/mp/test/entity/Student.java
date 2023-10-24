package org.mybatis.mp.test.entity;

import lombok.Data;
import org.mybatis.mp.db.IdAutoType;
import org.mybatis.mp.db.annotations.TableId;
import org.mybatis.mp.db.annotations.Table;

import java.time.LocalDateTime;

@Data
@Table
public class Student {

    @TableId(value = IdAutoType.SQL, sql = "SELECT LAST_INSERT_ID()")
    private Integer id;

    private String name;

    private Boolean excellent;

    private LocalDateTime createTime;
}
