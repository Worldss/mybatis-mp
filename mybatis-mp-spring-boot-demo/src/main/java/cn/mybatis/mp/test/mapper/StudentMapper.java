package cn.mybatis.mp.test.mapper;

import cn.mybatis.mp.core.mybatis.mapper.MybatisMapper;
import cn.mybatis.mp.test.entity.Student;

public interface StudentMapper extends MybatisMapper<Student> {

//    @Select("select * from student where id=#{value}")
//    StudentVo getById2(Integer id);


}
