package org.mybatis.mp.test.mapper;

import org.mybatis.mp.core.mybatis.mapper.MybatisMapper;
import org.mybatis.mp.test.entity.Student;

public interface StudentMapper extends MybatisMapper<Student> {

//    @Select("select * from student where id=#{value}")
//    StudentVo getById2(Integer id);


}
