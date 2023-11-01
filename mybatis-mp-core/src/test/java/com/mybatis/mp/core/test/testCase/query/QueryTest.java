package com.mybatis.mp.core.test.testCase.query;

import cn.mybatis.mp.core.sql.executor.Query;
import com.mybatis.mp.core.test.mapper.SysUserMapper;
import com.mybatis.mp.core.test.model.SysRole;
import com.mybatis.mp.core.test.model.SysUser;
import com.mybatis.mp.core.test.testCase.BaseTest;

import db.sql.api.LikeMode;
import junit.framework.Assert;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

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
    public void eq() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            Integer id = sysUserMapper.get(new Query() {{
                select(SysUser::getId);
                from(SysUser.class);
                eq(SysUser::getId, 2);
                setReturnType(Integer.TYPE);
            }});
            Assert.assertEquals("eq", 2, id.intValue());
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
            Assert.assertEquals("gt", 2, id.intValue());
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
            Assert.assertEquals("gte", 2, id.intValue());
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
            Assert.assertEquals("lt", 1, id.intValue());
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
            Assert.assertEquals("lte", 1, id.intValue());
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
    public void leftLike() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            Integer count = sysUserMapper.get(new Query().
                    select(SysUser::getId, c -> c.count()).
                    from(SysUser.class).
                    like(SysUser::getUserName, "test", LikeMode.LEFT).
                    setReturnType(Integer.TYPE)
            );

            Assert.assertEquals("leftLike", 2, count.intValue());
        }
    }

    @Test
    public void rightLike() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            SysUser sysUser = sysUserMapper.get(new Query().
                    select(SysUser::getId, SysUser::getPassword, SysUser::getRole_id).
                    from(SysUser.class).
                    like(SysUser::getUserName, "test1", LikeMode.RIGHT)

            );

            SysUser eqSysUser = new SysUser();
            eqSysUser.setId(2);
            eqSysUser.setPassword("123456");
            eqSysUser.setRole_id(1);
            Assert.assertEquals("rightLike", eqSysUser, sysUser);
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
    public void notLeftLike() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            SysUser sysUser = sysUserMapper.get(new Query().
                    select(SysUser::getId, SysUser::getPassword, SysUser::getRole_id).
                    from(SysUser.class).
                    notLike(SysUser::getUserName, "test2", LikeMode.LEFT).
                    like(SysUser::getUserName, "test")
            );

            SysUser eqSysUser = new SysUser();
            eqSysUser.setId(2);
            eqSysUser.setPassword("123456");
            eqSysUser.setRole_id(1);
            Assert.assertEquals("notLeftLike", eqSysUser, sysUser);
        }
    }

    @Test
    public void notRightLike() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            SysUser sysUser = sysUserMapper.get(new Query().
                    select(SysUser::getId, SysUser::getPassword, SysUser::getRole_id).
                    from(SysUser.class).
                    notLike(SysUser::getUserName, "est1", LikeMode.RIGHT).
                    like(SysUser::getUserName, "test")
            );

            SysUser eqSysUser = new SysUser();
            eqSysUser.setId(3);
            eqSysUser.setPassword(null);
            eqSysUser.setRole_id(1);
            Assert.assertEquals("notRightLike", eqSysUser, sysUser);
        }
    }
}
