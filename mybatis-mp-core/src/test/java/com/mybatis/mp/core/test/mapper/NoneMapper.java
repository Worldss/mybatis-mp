package com.mybatis.mp.core.test.mapper;

import org.apache.ibatis.annotations.Select;

public interface NoneMapper {

    @Select("select test")
    String test();
}
