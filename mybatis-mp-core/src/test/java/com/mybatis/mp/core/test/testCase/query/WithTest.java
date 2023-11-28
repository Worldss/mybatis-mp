package com.mybatis.mp.core.test.testCase.query;

import cn.mybatis.mp.core.sql.executor.SubQuery;
import cn.mybatis.mp.core.sql.executor.chain.QueryChain;
import com.mybatis.mp.core.test.DO.SysRole;
import com.mybatis.mp.core.test.DO.SysUser;
import com.mybatis.mp.core.test.mapper.SysUserMapper;
import com.mybatis.mp.core.test.testCase.BaseTest;
import db.sql.api.cmd.JoinMode;
import junit.framework.Assert;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.util.List;

public class WithTest extends BaseTest {

    @Test
    public void withQuery() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);

            SubQuery subQuery = SubQuery.create("sub")
                    .select(SysRole.class)
                    .from(SysRole.class)
                    .eq(SysRole::getId, 1);

            List<SysUser> list = QueryChain.of(sysUserMapper)
                    .with(subQuery)
                    .select(subQuery, SysRole::getId, c -> c.as("xx"))
                    .select(subQuery, "id")
                    .select(SysUser.class)
                    .from(SysUser.class)
                    .from(subQuery)
                    .eq(SysUser::getRole_id, subQuery.$(subQuery, SysRole::getId))
                    .orderBy(subQuery, SysRole::getId)
                    .list();
            Assert.assertEquals("withQuery", 2, list.size());
        }
    }
}
