package com.mybatis.mp.core.test.DO;

import cn.mybatis.mp.db.annotations.LogicDelete;

import java.time.LocalDateTime;
import java.util.Date;

public class LogicDeleteTest {

    @LogicDelete(beforeValue = "0",afterValue = "1")
    private Integer deleted;

    @LogicDelete(beforeValue = "0",afterValue = "1")
    private Byte deleted5;

    @LogicDelete(beforeValue = "",afterValue = "{NOW}")
    private LocalDateTime deletedTime;

    @LogicDelete(beforeValue = "false",afterValue = "true")
    private Boolean deleted2;

    @LogicDelete(beforeValue = "0",afterValue = "{TIMESTAMP}")
    private Long deleted3;

    @LogicDelete(beforeValue = "",afterValue = "{DATE_NOW}")
    private Date deleted4;
}
