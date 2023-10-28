package cn.mybatis.mp.test.vo;

import cn.mybatis.mp.db.annotations.ResultEntity;
import cn.mybatis.mp.db.annotations.ResultEntityField;
import cn.mybatis.mp.test.entity.Student;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString(callSuper = true)
@ResultEntity(value = Student.class)
public class StudentVo {

    private Integer id;

    private String name;

    //private LocalDateTime stCreateTime;

    @ResultEntityField(target = Student.class, property = "createTime")
    private LocalDateTime st2CreateTime;
}
