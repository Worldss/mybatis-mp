package com.mybatis.mp.core.test.model;

import cn.mybatis.mp.db.Model;
import com.mybatis.mp.core.test.DO.VersionTest;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VersionModel implements Model<VersionTest> {

    private String id;

    private Integer version;

    private String name;

    private LocalDateTime createTime;

    public static void main(String[] args) {
        System.out.println(Model.class.isAssignableFrom(VersionModel.class));
        System.out.println(VersionTest.class.isAssignableFrom(Model.class));
    }
}
