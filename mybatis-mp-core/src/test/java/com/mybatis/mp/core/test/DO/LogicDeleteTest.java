package com.mybatis.mp.core.test.DO;

import cn.mybatis.mp.db.annotations.LogicDelete;
import cn.mybatis.mp.db.annotations.Table;
import cn.mybatis.mp.db.annotations.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Table
public class LogicDeleteTest {

    @TableId
    private Long id;

    private String name;

    private LocalDateTime deleteTime;

    @LogicDelete(beforeValue = "0", afterValue = "1", deleteTimeField = "deleteTime")
    private Byte deleted;

//    @LogicDelete(  afterValue = "{NOW}" )
//    private LocalDateTime deleteTime;
}
