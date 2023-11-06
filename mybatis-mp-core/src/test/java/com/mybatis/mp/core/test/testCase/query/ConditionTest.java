package com.mybatis.mp.core.test.testCase.query;

import cn.mybatis.mp.core.sql.executor.Query;
import com.mybatis.mp.core.test.mapper.SysUserMapper;
import com.mybatis.mp.core.test.model.SysUser;
import com.mybatis.mp.core.test.testCase.BaseTest;
import db.sql.api.LikeMode;
import junit.framework.Assert;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.util.List;


@SuppressWarnings("unchecked")
public class ConditionTest extends BaseTest {

    @Test
    public void eq() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            Integer id = sysUserMapper.get(new Query() {{
                select(SysUser::getId);
                from(SysUser.class);
                eq(SysUser::getId, 2);
                setReturnType(Integer.TYPE);
            }});
            Assert.assertEquals("eq", Integer.valueOf(2), id);
        }
    }

    @Test
    public void ne() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            Integer id = sysUserMapper.get(new Query() {{
                select(SysUser::getId);
                from(SysUser.class);
                ne(SysUser::getId, 2);
                like(SysUser::getUserName, "test");
                setReturnType(Integer.TYPE);
            }});
            Assert.assertEquals("eq", Integer.valueOf(3), id);
        }
    }

    @Test
    public void gt() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            Integer id = sysUserMapper.get(new Query() {{
                select(SysUser::getId);
                from(SysUser.class);
                gt(SysUser::getId, 1);
                limit(1);
                setReturnType(Integer.TYPE);
            }});
            Assert.assertEquals("gt", Integer.valueOf(2), id);
        }
    }

    @Test
    public void gte() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            Integer id = sysUserMapper.get(new Query() {{
                select(SysUser::getId);
                from(SysUser.class);
                gte(SysUser::getId, 2);
                limit(1);
                setReturnType(Integer.TYPE);
            }});
            Assert.assertEquals("gte", Integer.valueOf(2), id);
        }
    }

    @Test
    public void lt() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            Integer id = sysUserMapper.get(new Query() {{
                select(SysUser::getId);
                from(SysUser.class);
                lt(SysUser::getId, 2);
                limit(1);
                setReturnType(Integer.TYPE);
            }});
            Assert.assertEquals("lt", Integer.valueOf(1), id);
        }
    }

    @Test
    public void lte() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            Integer id = sysUserMapper.get(new Query() {{
                select(SysUser::getId);
                from(SysUser.class);
                lte(SysUser::getId, 1);
                limit(1);
                setReturnType(Integer.TYPE);
            }});
            Assert.assertEquals("lte", Integer.valueOf(1), id);
        }
    }

    @Test
    public void isNotNull() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            SysUser sysUser = sysUserMapper.get(new Query() {{
                select(SysUser::getId, SysUser::getPassword);
                from(SysUser.class);
                isNotNull(SysUser::getPassword);
                eq(SysUser::getId, 3);
                setReturnType(Integer.TYPE);
            }});
            Assert.assertEquals("isNotNull", null, sysUser);
        }
    }

    @Test
    public void isNull() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            SysUser sysUser = sysUserMapper.get(new Query() {{
                select(SysUser::getId, SysUser::getPassword, SysUser::getRole_id);
                from(SysUser.class);
                isNull(SysUser::getPassword);
            }});

            SysUser eqSysUser = new SysUser();
            eqSysUser.setId(3);
            eqSysUser.setPassword(null);
            eqSysUser.setRole_id(1);
            Assert.assertEquals("isNull", eqSysUser, sysUser);
        }
    }

    @Test
    public void like() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            SysUser sysUser = sysUserMapper.get(new Query() {{
                select(SysUser::getId, SysUser::getPassword, SysUser::getRole_id);
                from(SysUser.class);
                like(SysUser::getUserName, "test1");
            }});

            SysUser eqSysUser = new SysUser();
            eqSysUser.setId(2);
            eqSysUser.setPassword("123456");
            eqSysUser.setRole_id(1);
            Assert.assertEquals("like", eqSysUser, sysUser);
        }
    }

    @Test
    public void rightLike() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            Integer count = sysUserMapper.get(new Query().
                    select(SysUser::getId, c -> c.count()).
                    from(SysUser.class).
                    like(SysUser::getUserName, "test", LikeMode.RIGHT).
                    setReturnType(Integer.TYPE)
            );

            Assert.assertEquals("rightLike", Integer.valueOf(2), count);
        }
    }

    @Test
    public void leftLike() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            SysUser sysUser = sysUserMapper.get(new Query().
                    select(SysUser::getId, SysUser::getPassword, SysUser::getRole_id).
                    from(SysUser.class).
                    like(SysUser::getUserName, "test1", LikeMode.LEFT)

            );

            SysUser eqSysUser = new SysUser();
            eqSysUser.setId(2);
            eqSysUser.setPassword("123456");
            eqSysUser.setRole_id(1);
            Assert.assertEquals("leftLike", eqSysUser, sysUser);
        }
    }

    @Test
    public void notLike() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            SysUser sysUser = sysUserMapper.get(new Query() {{
                select(SysUser::getId, SysUser::getPassword, SysUser::getRole_id);
                from(SysUser.class);
                notLike(SysUser::getUserName, "test1");
                like(SysUser::getUserName, "test");
            }});

            SysUser eqSysUser = new SysUser();
            eqSysUser.setId(3);
            eqSysUser.setPassword(null);
            eqSysUser.setRole_id(1);
            Assert.assertEquals("notLike", eqSysUser, sysUser);
        }
    }

    @Test
    public void notRightLike() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            SysUser sysUser = sysUserMapper.get(new Query().
                    select(SysUser::getId, SysUser::getPassword, SysUser::getRole_id).
                    from(SysUser.class).
                    notLike(SysUser::getUserName, "test2", LikeMode.RIGHT).
                    like(SysUser::getUserName, "test")
            );

            SysUser eqSysUser = new SysUser();
            eqSysUser.setId(2);
            eqSysUser.setPassword("123456");
            eqSysUser.setRole_id(1);
            Assert.assertEquals("notRightLike", eqSysUser, sysUser);
        }
    }

    @Test
    public void notLeftLike() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            SysUser sysUser = sysUserMapper.get(new Query().
                    select(SysUser::getId, SysUser::getPassword, SysUser::getRole_id).
                    from(SysUser.class).
                    notLike(SysUser::getUserName, "est1", LikeMode.LEFT).
                    like(SysUser::getUserName, "test")
            );

            SysUser eqSysUser = new SysUser();
            eqSysUser.setId(3);
            eqSysUser.setPassword(null);
            eqSysUser.setRole_id(1);
            Assert.assertEquals("notLeftLike", eqSysUser, sysUser);
        }
    }

    @Test
    public void between() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            List<Integer> list = sysUserMapper.list(new Query().
                    select(SysUser::getId).
                    from(SysUser.class).
                    between(SysUser::getId, 1, 2).
                    setReturnType(Integer.TYPE)
            );


            Assert.assertEquals("notLeftLike", Integer.valueOf(1), list.get(0));
            Assert.assertEquals("notLeftLike", Integer.valueOf(2), list.get(1));
        }
    }

    @Test
    public void notBetween() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            List<Integer> list = sysUserMapper.list(new Query().
                    select(SysUser::getId).
                    from(SysUser.class).
                    between(SysUser::getId, 1, 3).
                    notBetween(SysUser::getId, 1, 2).
                    setReturnType(Integer.TYPE)
            );


            Assert.assertEquals("notLeftLike", 1, list.size());
            Assert.assertEquals("notLeftLike", Integer.valueOf(3), list.get(0));
        }
    }
}