package org.mybatis.mp.test.vo;

import lombok.Data;
import lombok.ToString;
import org.mybatis.mp.db.annotations.ResultField;
import org.mybatis.mp.db.annotations.ResultTable;
import org.mybatis.mp.test.entity.Student;

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
