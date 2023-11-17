package com.mybatis.mp.core.test.model;

import cn.mybatis.mp.core.incrementer.IdentifierGeneratorType;
import cn.mybatis.mp.db.IdAutoType;
import cn.mybatis.mp.db.annotations.Table;
import cn.mybatis.mp.db.annotations.TableField;
import cn.mybatis.mp.db.annotations.TableId;
import cn.mybatis.mp.db.annotations.Version;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Table
public class VersionTest {

    @TableId(value = IdAutoType.GENERATOR, generatorName = IdentifierGeneratorType.UUID)
    private String id;

    @Version
    private Integer version;

    private String name;

    @TableField(update = false)
    private LocalDateTime createTime;
}
