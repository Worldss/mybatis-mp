package cn.mybatis.mp.test.entity.DTO;

import cn.mybatis.mp.db.Model;
import cn.mybatis.mp.test.entity.Student;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StudentDTO implements Model<Student> {

    private Integer id;

    private String name;

    private Boolean excellent;

    private LocalDateTime createTime;
}
