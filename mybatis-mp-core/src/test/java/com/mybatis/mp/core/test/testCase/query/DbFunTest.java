package com.mybatis.mp.core.test.testCase.query;

import cn.mybatis.mp.core.sql.executor.chain.QueryChain;
import com.mybatis.mp.core.test.DO.SysUser;
import com.mybatis.mp.core.test.mapper.SysUserMapper;
import com.mybatis.mp.core.test.testCase.BaseTest;
import db.sql.api.impl.cmd.Methods;
import junit.framework.Assert;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

public class DbFunTest extends BaseTest {

    @Test
    public void md5Test() {
//        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
//            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
//            Integer id = QueryChain.of(sysUserMapper)
//                    .select(SysUser::getId, c -> c.sin())
//                    .from(SysUser.class)
//                    .eq(SysUser::getId, 2)
//                    .and(queryChain -> {
//                        return queryChain.$(SysUser::getCreate_time, c -> c.date().eq("2023-12-10"));
//                    })
//                    .and(queryChain -> {
//                        return Methods.date(queryChain.$(SysUser::getCreate_time)).eq("2023-12-10");
//                    })
//                    .empty(SysUser::getUserName)
//                    .orderBy(SysUser::getId, c -> c.plus(1))
//                    .setReturnType(Integer.TYPE)
//                    .get();
//
//            Assert.assertEquals("eq", null, id);
//        }
    }
}
