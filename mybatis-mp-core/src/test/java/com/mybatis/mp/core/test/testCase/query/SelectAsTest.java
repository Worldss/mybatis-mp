package com.mybatis.mp.core.test.testCase.query;

import cn.mybatis.mp.core.sql.executor.chain.QueryChain;
import com.mybatis.mp.core.test.DO.SysRole;
import com.mybatis.mp.core.test.DO.SysUser;
import com.mybatis.mp.core.test.mapper.SysUserMapper;
import com.mybatis.mp.core.test.testCase.BaseTest;
import com.mybatis.mp.core.test.vo.NestedSysRoleVo;
import com.mybatis.mp.core.test.vo.SysUserRoleVo;
import com.mybatis.mp.core.test.vo.SysUserVo;
import junit.framework.Assert;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class SelectAsTest extends BaseTest {

    @Test
    public void simpleReturnAs() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            SysUserVo sysUser = QueryChain.of(sysUserMapper)
                    .select(SysUser::getId, SysUser::getUserName)
                    .from(SysUser.class)
                    .eq(SysUser::getId, 1)
                    .setReturnType(SysUserVo.class)
                    .get();
            SysUserVo eqSysUser = new SysUserVo();
            eqSysUser.setId(1);
            eqSysUser.setUserName("admin");
            Assert.assertEquals("@ResultEntity注解测试", eqSysUser, sysUser);
        }
    }


    @Test
    public void simpleReturnResultEntityFieldAs() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            SysUserVo sysUser = QueryChain.of(sysUserMapper)
                    .select(SysUser::getId, SysUser::getUserName, SysUser::getPassword)
                    .from(SysUser.class)
                    .eq(SysUser::getId, 1)
                    .setReturnType(SysUserVo.class)
                    .get();
            SysUserVo eqSysUser = new SysUserVo();
            eqSysUser.setId(1);
            eqSysUser.setUserName("admin");
            eqSysUser.setPwd("123");
            Assert.assertEquals("@ResultEntityField注解测试", eqSysUser, sysUser);
        }
    }

    @Test
    public void simpleReturnResultFieldAs() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            SysUserVo sysUser = QueryChain.of(sysUserMapper)
                    .select(SysUser::getId, SysUser::getUserName, SysUser::getPassword)
                    .select(SysUser::getId, c -> c.concat("kk").as("kk"))
                    .from(SysUser.class)
                    .eq(SysUser::getId, 1)
                    .setReturnType(SysUserVo.class)
                    .get();
            SysUserVo eqSysUser = new SysUserVo();
            eqSysUser.setId(1);
            eqSysUser.setUserName("admin");
            eqSysUser.setPwd("123");
            eqSysUser.setKkName("1kk");
            Assert.assertEquals("@ResultEntity 之 @ResultField注解 ，返回Vo测试", eqSysUser, sysUser);
        }
    }

    @Test
    public void nestedResultEntity() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);

            QueryChain queryChain = QueryChain.of(sysUserMapper)
                    .select(SysUser::getId, SysUser::getUserName)
                    .select(SysRole.class)
                    .from(SysUser.class)
                    .join(SysUser.class, SysRole.class)
                    .eq(SysUser::getId, 2)
                    .setReturnType(SysUserVo.class);

            SysUserVo sysUser = queryChain
                    .get();

            SysUserVo eqSysUser = new SysUserVo();
            eqSysUser.setId(2);
            eqSysUser.setUserName("test1");
            SysRole sysRole = new SysRole();
            sysRole.setId(1);
            sysRole.setName("测试");
            sysRole.setCreateTime(LocalDateTime.parse("2022-10-10T00:00"));
            eqSysUser.setRole(sysRole);

            Assert.assertEquals("@NestedResultEntity注解，返回实体类测试", eqSysUser, sysUser);

            Assert.assertEquals("@NestedResultEntity注解，返回实体类测试", eqSysUser, queryChain.list().get(0));
        }
    }

    @Test
    public void nestedResultEntity2() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            QueryChain queryChain = QueryChain.of(sysUserMapper)
                    .select(SysUser::getId, SysUser::getUserName)
                    .select(SysRole.class)
                    .from(SysUser.class)
                    .join(SysUser.class, SysRole.class)
                    .eq(SysUser::getId, 2)
                    .setReturnType(SysUserRoleVo.class);
            SysUserRoleVo sysUser = queryChain.get();
            SysUserRoleVo eqSysUser = new SysUserRoleVo();
            eqSysUser.setId(2);
            eqSysUser.setUserName("test1");

            NestedSysRoleVo sysRole = new NestedSysRoleVo();
            sysRole.setId(1);
            sysRole.setXxName("测试");
            eqSysUser.setRole(sysRole);
            Assert.assertEquals("@NestedResultEntity注解，返回Vo测试", eqSysUser, sysUser);
            Assert.assertEquals("@NestedResultEntity注解，返回Vo测试", eqSysUser, queryChain.list().get(0));
        }
    }

    @Test
    public void nestedResultEntity3() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            SysUserRoleVo sysUser = QueryChain.of(sysUserMapper)
                    .select(SysUser::getId, SysUser::getUserName)
                    .select(SysUser::getUserName, c -> c.concat("aa").as("cc"))
                    .select(SysRole.class)
                    .from(SysUser.class)
                    .join(SysUser.class, SysRole.class)
                    .eq(SysUser::getId, 2)
                    .setReturnType(SysUserRoleVo.class)
                    .get();
            SysUserRoleVo eqSysUser = new SysUserRoleVo();
            eqSysUser.setId(2);
            eqSysUser.setUserName("test1");

            NestedSysRoleVo sysRole = new NestedSysRoleVo();
            sysRole.setId(1);
            sysRole.setXxName("测试");
            sysRole.setCc("test1aa");
            eqSysUser.setRole(sysRole);
            Assert.assertEquals("@NestedResultEntity 之 @ResultField注解 ，返回Vo测试", eqSysUser, sysUser);
        }
    }
}
