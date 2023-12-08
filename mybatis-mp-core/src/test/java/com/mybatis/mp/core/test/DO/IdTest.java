package com.mybatis.mp.core.test.DO;

import cn.mybatis.mp.db.annotations.Table;
import cn.mybatis.mp.db.annotations.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Table
public class IdTest {

    //@TableId(value = IdAutoType.GENERATOR, generatorName = IdentifierGeneratorType.DEFAULT)
    @TableId
    private Long id;

    private LocalDateTime createTime;

}
