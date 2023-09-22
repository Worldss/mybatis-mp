package org.mybatis.mp.test.mapper;

import org.apache.ibatis.annotations.Select;
import org.mybatis.mp.core.mybatis.mapper.MybatisMapper;
import org.mybatis.mp.test.entity.Student;
import org.mybatis.mp.test.vo.StudentVo;

import java.util.List;

public interface StudentMybatisMapper extends MybatisMapper<Student> {

    @Select("select * from student where id=#{value}")
    StudentVo getById2(Integer id);


}
