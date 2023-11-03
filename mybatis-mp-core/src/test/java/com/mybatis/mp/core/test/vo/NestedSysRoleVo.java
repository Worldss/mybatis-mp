package com.mybatis.mp.core.test.vo;

import cn.mybatis.mp.db.annotations.NestedResultEntityField;
import cn.mybatis.mp.db.annotations.ResultField;
import lombok.Data;

@Data
public class NestedSysRoleVo {

    private Integer id;

    @NestedResultEntityField("name")
    private String xxName;

    @ResultField("cc")
    private String cc;
}
