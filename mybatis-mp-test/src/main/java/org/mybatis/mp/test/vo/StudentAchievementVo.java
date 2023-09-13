package org.mybatis.mp.test.vo;

import lombok.Data;
import lombok.ToString;
import org.mybatis.mp.db.annotations.ResultField;
import org.mybatis.mp.db.annotations.ResultTable;
import org.mybatis.mp.test.entity.Achievement;
import org.mybatis.mp.test.entity.Student;

import java.math.BigDecimal;

@Data
@ToString(callSuper = true)
@ResultTable(Student.class)
public class StudentAchievementVo extends StudentVo {

    @ResultField(target = Achievement.class)
    private Integer student_id;

    @ResultField(target = Achievement.class)
    private BigDecimal score;

}
