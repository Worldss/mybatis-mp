package cn.mybatis.mp.test.vo;

import lombok.Data;
import lombok.ToString;
import cn.mybatis.mp.db.annotations.NestedResultTable;
import cn.mybatis.mp.db.annotations.ResultTable;
import cn.mybatis.mp.test.entity.Achievement;
import cn.mybatis.mp.test.entity.Student;

@Data
@ToString(callSuper = true)
@ResultTable(Student.class)
public class StudentAchievementVo  extends Student {

    @NestedResultTable(target =  Achievement.class,columnPrefix = "xx_")
    private Achievement achievement;

}
