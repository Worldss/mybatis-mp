package cn.mybatis.mp.test.vo;

import lombok.Data;
import lombok.ToString;
import cn.mybatis.mp.db.annotations.ResultField;
import cn.mybatis.mp.db.annotations.ResultTable;
import cn.mybatis.mp.test.entity.Student;

import java.time.LocalDateTime;

@Data
@ToString(callSuper = true)
@ResultTable(value = Student.class, columnPrefix = "$")
public class StudentVo {

    private Integer id;

    private String name;

    //private LocalDateTime stCreateTime;

    @ResultField(target = Student.class, property = "createTime")
    private LocalDateTime st2CreateTime;
}
