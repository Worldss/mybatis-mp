package com.mybatis.mp.core.test.testCase.query;

import cn.mybatis.mp.core.sql.executor.chain.QueryChain;
import com.mybatis.mp.core.test.DO.SysRole;
import com.mybatis.mp.core.test.DO.SysUser;
import com.mybatis.mp.core.test.mapper.SysUserMapper;
import com.mybatis.mp.core.test.testCase.BaseTest;
import com.mybatis.mp.core.test.vo.NestedSysRoleVo;
import com.mybatis.mp.core.test.vo.SysUserRoleVo;
import com.mybatis.mp.core.test.vo.SysUserVo;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
            assertEquals(eqSysUser, sysUser, "@ResultEntity注解测试");
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
            assertEquals(eqSysUser, sysUser, "@ResultEntityField注解测试");
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
            assertEquals(eqSysUser, sysUser, "@ResultEntity 之 @ResultField注解 ，返回Vo测试");
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

            assertEquals(eqSysUser, sysUser, "@NestedResultEntity注解，返回实体类测试");

            assertEquals(eqSysUser, queryChain.list().get(0), "@NestedResultEntity注解，返回实体类测试");
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
            assertEquals(eqSysUser, sysUser, "@NestedResultEntity注解，返回Vo测试");
            assertEquals(eqSysUser, queryChain.list().get(0), "@NestedResultEntity注解，返回Vo测试");
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
            assertEquals(eqSysUser, sysUser, "@NestedResultEntity 之 @ResultField注解 ，返回Vo测试");
        }
    }
}
