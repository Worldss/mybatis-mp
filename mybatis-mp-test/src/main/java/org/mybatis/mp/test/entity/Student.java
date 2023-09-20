package org.mybatis.mp.test.entity;

import lombok.Data;
import org.apache.ibatis.type.LocalDateTimeTypeHandler;
import org.mybatis.mp.db.IdAutoType;
import org.mybatis.mp.db.annotations.Field;
import org.mybatis.mp.db.annotations.Id;
import org.mybatis.mp.db.annotations.Table;
import org.mybatis.mp.test.keyGenerator.JacksonTypeHandler;
import org.mybatis.mp.test.keyGenerator.JacksonTypeHandler2;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Table
public class Student {

    @Id(value = IdAutoType.SQL, sql = "SELECT LAST_INSERT_ID()")
    private Integer id;

    private String name;

    private Boolean excellent;

    private LocalDateTime createTime;


}
