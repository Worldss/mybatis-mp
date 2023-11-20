package com.mybatis.mp.core.test.mapper;

import cn.mybatis.mp.core.mybatis.mapper.MybatisMapper;
import com.mybatis.mp.core.test.DO.TenantTest;

public interface TenantTestMapper extends MybatisMapper<TenantTest> {

    default Class getType(){
        return getClass();
    }
}
