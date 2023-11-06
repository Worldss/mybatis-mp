package com.mybatis.mp.core.test.testCase.query;

import cn.mybatis.mp.core.sql.executor.Query;
import com.mybatis.mp.core.test.mapper.SysUserMapper;
import com.mybatis.mp.core.test.model.SysRole;
import com.mybatis.mp.core.test.model.SysUser;
import com.mybatis.mp.core.test.testCase.BaseTest;
import db.sql.core.api.cmd.fun.FunctionInterface;
import junit.framework.Assert;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

@SuppressWarnings("unchecked")
public class QueryTest extends BaseTest {

    @Test
    public void simpleSelect() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);

            SysUser sysUser = sysUserMapper.get(new Query()
                    .select(SysUser::getId, SysUser::getUserName, SysUser::getRole_id)
                    .from(SysUser.class)
                    .eq(SysUser::getId, 1)
            );
            SysUser eqSysUser = new SysUser();
            eqSysUser.setId(1);
            eqSysUser.setUserName("admin");
            eqSysUser.setRole_id(0);
            Assert.assertEquals("单表部分select检测", eqSysUser, sysUser);
        }
    }

    @Test
    public void getById() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            SysUser sysUser = sysUserMapper.getById(1);
            SysUser eqSysUser = new SysUser();
            eqSysUser.setId(1);
            eqSysUser.setUserName("admin");
            eqSysUser.setRole_id(0);
            eqSysUser.setPassword("123");
            eqSysUser.setCreate_time(LocalDateTime.parse("2023-10-10T10:10:10"));
            Assert.assertEquals("getById检测", eqSysUser, sysUser);
        }
    }

    @Test
    public void innerJoin() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            SysUser sysUser = sysUserMapper.get(new Query() {{
                select(SysUser::getId, SysUser::getUserName, SysUser::getRole_id);
                from(SysUser.class);
                join(SysUser.class, SysRole.class);
                eq(SysUser::getId, 2);
            }});

            SysUser eqSysUser = new SysUser();
            eqSysUser.setId(2);
            eqSysUser.setUserName("test1");
            eqSysUser.setRole_id(1);
            Assert.assertEquals("返回单表，innerJoin检测", eqSysUser, sysUser);
        }
    }

    @Test
    public void groupBy() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            List<Integer> counts = sysUserMapper.list(new Query()
                    .select(SysUser::getId, c -> c.count())
                    .from(SysUser.class)
                    .groupBy(SysUser::getRole_id)
                    .setReturnType(Integer.TYPE)
            );

            Assert.assertEquals("groupBy", counts.get(0), Integer.valueOf(1));
            Assert.assertEquals("groupBy", counts.get(1), Integer.valueOf(2));
        }
    }

    @Test
    public void orderby() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            SysUser sysUser = sysUserMapper.get(new Query()
                    .select(SysUser::getId, SysUser::getUserName, SysUser::getRole_id)
                    .from(SysUser.class)
                    .orderBy(false, SysUser::getRole_id, SysUser::getId)
                    .limit(1)
            );
            SysUser eqSysUser = new SysUser();
            eqSysUser.setId(3);
            eqSysUser.setUserName("test2");
            eqSysUser.setRole_id(1);
            Assert.assertEquals("orderby", sysUser, eqSysUser);
        }
    }

    @Test
    public void having() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            Integer count = sysUserMapper.get(new Query()
                    .select(SysUser::getRole_id, FunctionInterface::count)
                    .from(SysUser.class)
                    .groupBy(SysUser::getRole_id)
                    .having(SysUser::getRole_id, c -> c.gt(0))
                    .setReturnType(Integer.TYPE)
            );

            Assert.assertEquals("having", count, Integer.valueOf(2));
            new Query() {{
                select(SysUser::getRole_id)
                        .from(SysUser.class)
                        .eq($().field(SysUser::getId), 1)
                        .gt($().table(SysUser.class).$("role_id"), 2);
            }};

            new Query() {{
                select($(SysUser::getId))
                        .from($(SysUser.class))
                        .eq($(SysUser::getId), 1);
            }};
        }
    }
}