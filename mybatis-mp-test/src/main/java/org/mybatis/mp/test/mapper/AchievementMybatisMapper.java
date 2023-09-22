package org.mybatis.mp.test.mapper;

import org.apache.ibatis.annotations.Select;
import org.mybatis.mp.core.mybatis.mapper.MybatisMapper;
import org.mybatis.mp.test.entity.Achievement;
import org.mybatis.mp.test.vo.StudentAchievementVo;

import java.util.List;

public interface AchievementMybatisMapper extends MybatisMapper<Achievement> {


    @Select("select t.*,t2.student_id,t2.score,t2.score xx_score,t2.id xx_id,123 haha  from Achievement t2 left join  student  t  on t.id=t2.student_id")
    List<StudentAchievementVo> list( );

}
