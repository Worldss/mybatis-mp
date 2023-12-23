package com.mybatis.mp.core.test.testCase.cmdTemplete;

import cn.mybatis.mp.core.sql.executor.chain.QueryChain;
import com.mybatis.mp.core.test.DO.SysUser;
import com.mybatis.mp.core.test.mapper.SysUserMapper;
import com.mybatis.mp.core.test.testCase.BaseTest;
import db.sql.api.impl.cmd.basic.ConditionTemplate;
import db.sql.api.impl.cmd.basic.CmdTemplate;
import db.sql.api.impl.cmd.basic.FunTemplate;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CmdTemplateTestCase extends BaseTest {

    @Test
    public void templateTest() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            QueryChain queryChain = QueryChain.of(sysUserMapper);
            queryChain.selectWithFun(SysUser::getRole_id, c -> new CmdTemplate("count({0})+{1}", c, "1"));
            queryChain.from(SysUser.class);
            queryChain.and(cs -> new ConditionTemplate("{0}+{1}={2}", cs[0], cs[1], 2), SysUser::getId, SysUser::getId);
            queryChain.setReturnType(String.class);
            String str = queryChain.get();

            assertEquals(str, "2");
        }
    }


    @Test
    public void templateTest2() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            QueryChain queryChain = QueryChain.of(sysUserMapper);
            queryChain.selectWithFun(SysUser::getRole_id, c -> new FunTemplate("count({0})",c).plus(1).concat(1,"2",3).length());
            queryChain.from(SysUser.class);
            queryChain.and(cs -> new ConditionTemplate("{0}+{1}={2}", cs[0], cs[1], 2), SysUser::getId, SysUser::getId);
            queryChain.setReturnType(String.class);
            String str = queryChain.get();

            assertEquals(str, "4");
        }
    }

}
