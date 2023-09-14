package org.mybatis.mp.test.mapper;

import org.apache.ibatis.annotations.Select;
import org.mybatis.mp.core.mybatis.mapper.Mapper;
import org.mybatis.mp.test.entity.Achievement;
import org.mybatis.mp.test.vo.StudentAchievementVo;

import java.util.List;

public interface AchievementMapper extends Mapper<Achievement> {


    @Select("select t.*,t2.student_id,t2.score,t2.score xx_score,t2.id xx_id  from student  t left join Achievement t2  on t.id=t2.student_id")
    List<StudentAchievementVo> list( );

}
