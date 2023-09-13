package org.mybatis.mp.test.entity;

import lombok.Data;
import org.mybatis.mp.db.IdAutoType;
import org.mybatis.mp.db.annotations.Id;
import org.mybatis.mp.db.annotations.Table;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Table
public class Achievement {

    @Id(IdAutoType.AUTO)
    private Integer id;

    private Integer student_id;

    private BigDecimal score;

    private Date createTime;
}
