package org.mybatis.mp.test.entity;

import lombok.Data;
import org.mybatis.mp.db.IdAutoType;
import org.mybatis.mp.db.annotations.ForeignKey;
import org.mybatis.mp.db.annotations.TableId;
import org.mybatis.mp.db.annotations.Table;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Table
public class Achievement {

    @TableId(IdAutoType.AUTO)
    private Integer id;

    @ForeignKey(Student.class)
    private Integer student_id;

    private BigDecimal score;

    private Date createTime;
}
