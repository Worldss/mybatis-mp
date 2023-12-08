package com.mybatis.mp.core.test.testCase.delete;

import cn.mybatis.mp.core.sql.executor.Wheres;
import cn.mybatis.mp.core.sql.executor.chain.QueryChain;
import com.mybatis.mp.core.test.DO.SysUser;
import com.mybatis.mp.core.test.mapper.SysUserMapper;
import com.mybatis.mp.core.test.model.SysUserModel;
import com.mybatis.mp.core.test.testCase.BaseTest;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeleteTest extends BaseTest {


    @Test
    public void deleteIdTest() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            sysUserMapper.deleteById(1);
            List<SysUser> list = QueryChain.of(sysUserMapper).list();
            assertEquals(list.size(), 2);
        }
    }


    @Test
    public void deleteIdsTest() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            sysUserMapper.deleteByIds(1,2);
            List<SysUser> list = QueryChain.of(sysUserMapper).list();
            assertEquals(list.size(), 1);
        }

        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            sysUserMapper.deleteByIds(Arrays.asList(1,2));
            List<SysUser> list = QueryChain.of(sysUserMapper).list();
            assertEquals(list.size(), 1);
        }
    }

    @Test
    public void deleteEntityTest() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            sysUserMapper.delete(sysUserMapper.getById(1));
            List<SysUser> list = QueryChain.of(sysUserMapper).list();
            assertEquals(list.size(), 2);
        }
    }


    @Test
    public void deleteWithWhereTest() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            sysUserMapper.delete(where -> where.eq(SysUser::getId, 1));
            List<SysUser> list = QueryChain.of(sysUserMapper).list();
            assertEquals(list.size(), 2);
        }
    }
}
