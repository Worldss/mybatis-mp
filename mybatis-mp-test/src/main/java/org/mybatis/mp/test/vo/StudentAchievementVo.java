package org.mybatis.mp.test.vo;

import lombok.Data;
import lombok.ToString;

import org.mybatis.mp.db.annotations.ResultTable;
import org.mybatis.mp.db.annotations.NestedResultTable;
import org.mybatis.mp.test.entity.Achievement;
import org.mybatis.mp.test.entity.Student;

@Data
@ToString(callSuper = true)
@ResultTable(Student.class)
@ResultTable(value = Achievement.class, prefix = "achievement",columnPrefix = "xx_")
public class StudentAchievementVo extends StudentVo {


    private Integer achievementId;

    @NestedResultTable(target =  Achievement.class,columnPrefix = "xx_")
    private Achievement achievement;

}
