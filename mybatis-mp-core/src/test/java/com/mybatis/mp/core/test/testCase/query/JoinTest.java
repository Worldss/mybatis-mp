package com.mybatis.mp.core.test.testCase.query;

import cn.mybatis.mp.core.sql.executor.Query;
import cn.mybatis.mp.core.sql.executor.SubQuery;
import cn.mybatis.mp.core.sql.executor.chain.QueryChain;
import com.mybatis.mp.core.test.DO.SysRole;
import com.mybatis.mp.core.test.DO.SysUser;
import com.mybatis.mp.core.test.mapper.SysUserMapper;
import com.mybatis.mp.core.test.testCase.BaseTest;
import db.sql.api.cmd.JoinMode;
import db.sql.api.impl.cmd.dbFun.FunctionInterface;
import junit.framework.Assert;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.util.List;


public class JoinTest extends BaseTest {

    @Test
    public void defaultAddOn() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            Integer count = QueryChain.of(sysUserMapper)
                    .select(SysUser::getId, FunctionInterface::count)
                    .from(SysUser.class)
                    .join(SysUser.class, SysRole.class)
                    .setReturnType(Integer.TYPE)
                    .get();


            Assert.assertEquals("defaultAddOn", Integer.valueOf(2), count);
        }
    }

    @Test
    public void customAddOn() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            Integer count = QueryChain.of(sysUserMapper)
                    .select(SysUser::getId, FunctionInterface::count)
                    .from(SysUser.class)
                    .join(SysUser.class, SysRole.class, on -> on.eq(SysUser::getRole_id, SysRole::getId).like(SysUser::getUserName, "test1"))
                    .setReturnType(Integer.TYPE)
                    .get();


            Assert.assertEquals("customAddOn", Integer.valueOf(1), count);
        }
    }

    @Test
    public void innerJoin() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            Integer count = QueryChain.of(sysUserMapper)
                    .select(SysUser::getId, FunctionInterface::count)
                    .from(SysUser.class)
                    .join(SysUser.class, SysRole.class)
                    .setReturnType(Integer.TYPE)
                    .get();
            Assert.assertEquals("innerJoin", Integer.valueOf(2), count);
        }
    }

    @Test
    public void leftJoin() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            Integer count = QueryChain.of(sysUserMapper)
                    .select(SysUser::getId, FunctionInterface::count)
                    .from(SysUser.class)
                    .join(JoinMode.LEFT, SysUser.class, SysRole.class)
                    .setReturnType(Integer.TYPE)
                    .get();
            Assert.assertEquals("leftJoin", Integer.valueOf(3), count);
        }
    }

    @Test
    public void rightJoin() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            Integer count = QueryChain.of(sysUserMapper)
                    .select(SysUser::getId, FunctionInterface::count)
                    .from(SysUser.class)
                    .join(JoinMode.RIGHT, SysUser.class, SysRole.class)
                    .setReturnType(Integer.TYPE)
                    .get();
            Assert.assertEquals("rightJoin", Integer.valueOf(2), count);
        }
    }

    @Test
    public void fullJoin() {

        Query query = new Query()
                .select(SysUser::getId, FunctionInterface::count)
                .from(SysUser.class)
                .join(JoinMode.FULL, SysUser.class, SysRole.class)
                .setReturnType(Integer.TYPE);
        check("fullJoin", "SELECT  COUNT( t.id) FROM t_sys_user t  FULL OUTER JOIN sys_role t2 ON  t2.id =  t.role_id", query);

    }

    @Test
    public void joinSelf() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            Integer count = QueryChain.of(sysUserMapper)
                    .select(SysUser::getId, FunctionInterface::count)
                    .from(SysUser.class)
                    .join(JoinMode.INNER, SysUser.class, 1, SysUser.class, 2, on -> on.eq(SysUser::getId, 1, SysUser::getRole_id, 2))
                    .setReturnType(Integer.TYPE)
                    .get();
            Assert.assertEquals("joinSelf", Integer.valueOf(2), count);
        }
    }

    @Test
    public void joinSubQuery() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);

            SubQuery subQuery = SubQuery.create("sub")
                    .select(SysRole.class)
                    .from(SysRole.class)
                    .eq(SysRole::getId, 1);

            List<SysUser> list = QueryChain.of(sysUserMapper)
                    .select(subQuery,SysRole::getId,c->c.as("xx"))
                    .select(SysUser.class)
                    .from(SysUser.class)
                    .join(JoinMode.INNER, SysUser.class, subQuery, on -> on.eq(SysUser::getRole_id, subQuery.$(subQuery, SysRole::getId)))
                    .list();
            Assert.assertEquals("joinSelf", 2, list.size());
        }
    }
}
