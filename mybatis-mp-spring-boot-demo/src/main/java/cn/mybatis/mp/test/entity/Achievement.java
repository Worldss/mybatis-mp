package cn.mybatis.mp.test.entity;

import lombok.Data;
import cn.mybatis.mp.db.IdAutoType;
import cn.mybatis.mp.db.annotations.ForeignKey;
import cn.mybatis.mp.db.annotations.Table;
import cn.mybatis.mp.db.annotations.TableId;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Table
public class Achievement {

    @TableId(IdAutoType.AUTO)
    private Integer id;

    @ForeignKey(Student.class)
    private Integer studentId;

    private BigDecimal score;

    private Date createTime;
}
