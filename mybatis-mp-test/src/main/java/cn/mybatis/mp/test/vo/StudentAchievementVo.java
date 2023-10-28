package cn.mybatis.mp.test.vo;

import cn.mybatis.mp.db.annotations.NestedResultEntity;
import cn.mybatis.mp.db.annotations.ResultEntity;
import cn.mybatis.mp.test.entity.Achievement;
import cn.mybatis.mp.test.entity.Student;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@ResultEntity(Student.class)
public class StudentAchievementVo extends Student {

    @NestedResultEntity(target = Achievement.class)
    private Achievement achievement;

}
