package org.mybatis.mp.test.vo;

import lombok.Data;
import lombok.ToString;
import org.mybatis.mp.db.annotations.ResultField;
import org.mybatis.mp.db.annotations.ResultTable;
import org.mybatis.mp.test.entity.Student;

import java.time.LocalDateTime;

@Data
@ToString(callSuper = true)
@ResultTable(Student.class)
public class StudentVo {

    private Integer id;

    private String name;

    @ResultField(prefix = "st")
    private LocalDateTime stCreateTime;


    @ResultField(property = "createTime")
    private LocalDateTime st2CreateTime;
}
