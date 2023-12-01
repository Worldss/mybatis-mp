package com.mybatis.mp.core.test.testCase.update;

import cn.mybatis.mp.core.sql.executor.Wheres;
import cn.mybatis.mp.core.sql.executor.chain.QueryChain;
import com.mybatis.mp.core.test.DO.SysUser;
import com.mybatis.mp.core.test.mapper.SysUserMapper;
import com.mybatis.mp.core.test.model.SysUserModel;
import com.mybatis.mp.core.test.testCase.BaseTest;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UpdateTest extends BaseTest {

    @Test
    public void updateEntityTest() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);


            SysUser updateSysUser = new SysUser();
            updateSysUser.setId(1);
            updateSysUser.setUserName("adminxx");
            sysUserMapper.update(updateSysUser);
            SysUser eqSysUser = new SysUser();
            eqSysUser.setId(1);
            eqSysUser.setUserName("adminxx");
            eqSysUser.setPassword("123");
            eqSysUser.setRole_id(0);
            eqSysUser.setCreate_time(LocalDateTime.parse("2023-10-10T10:10:10"));

            List<SysUser> list=QueryChain.of(sysUserMapper).eq(SysUser::getUserName,"adminxx").list();
            assertEquals(list.size(),1);
            assertEquals(list.get(0), eqSysUser, "实体修改");
        }
    }

    @Test
    public void updateEntityTestForceUpdate() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);


            SysUser updateSysUser = new SysUser();
            updateSysUser.setId(1);
            updateSysUser.setUserName("adminxx");
            sysUserMapper.update(updateSysUser, SysUser::getPassword);
            SysUser eqSysUser = new SysUser();
            eqSysUser.setId(1);
            eqSysUser.setUserName("adminxx");
            eqSysUser.setPassword(null);
            eqSysUser.setRole_id(0);
            eqSysUser.setCreate_time(LocalDateTime.parse("2023-10-10T10:10:10"));

            List<SysUser> list=QueryChain.of(sysUserMapper).eq(SysUser::getUserName,"adminxx").list();
            assertEquals(list.size(),1);
            assertEquals(list.get(0), eqSysUser, "实体强制修改");
        }
    }


    @Test
    public void updateModelTest() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);


            SysUserModel updateSysUser = new SysUserModel();
            updateSysUser.setId(1);
            updateSysUser.setUserName("adminxx");
            sysUserMapper.update(updateSysUser);
            SysUser eqSysUser = new SysUser();
            eqSysUser.setId(1);
            eqSysUser.setUserName("adminxx");
            eqSysUser.setPassword("123");
            eqSysUser.setRole_id(0);
            eqSysUser.setCreate_time(LocalDateTime.parse("2023-10-10T10:10:10"));


            List<SysUser> list=QueryChain.of(sysUserMapper).eq(SysUser::getUserName,"adminxx").list();
            assertEquals(list.size(),1);
            assertEquals(list.get(0), eqSysUser, "实体model修改");
        }
    }

    @Test
    public void updateModelTestForceUpdate() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);


            SysUserModel updateSysUser = new SysUserModel();
            updateSysUser.setId(1);
            updateSysUser.setUserName("adminxx");
            sysUserMapper.update(updateSysUser, SysUser::getPassword);
            SysUser eqSysUser = new SysUser();
            eqSysUser.setId(1);
            eqSysUser.setUserName("adminxx");
            eqSysUser.setPassword(null);
            eqSysUser.setRole_id(0);
            eqSysUser.setCreate_time(LocalDateTime.parse("2023-10-10T10:10:10"));

            List<SysUser> list=QueryChain.of(sysUserMapper).eq(SysUser::getUserName,"adminxx").list();
            assertEquals(list.size(),1);
            assertEquals(list.get(0), eqSysUser, "实体model强制修改");
        }
    }




    @Test
    public void updateEntityWithWhereTest() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);

            SysUser updateSysUser = new SysUser();
            updateSysUser.setUserName("adminxx");
            sysUserMapper.update(updateSysUser, Wheres.create().eq(SysUser::getId,1));

            SysUser eqSysUser = new SysUser();
            eqSysUser.setId(1);
            eqSysUser.setUserName("adminxx");
            eqSysUser.setPassword("123");
            eqSysUser.setRole_id(0);
            eqSysUser.setCreate_time(LocalDateTime.parse("2023-10-10T10:10:10"));


            List<SysUser> list=QueryChain.of(sysUserMapper).eq(SysUser::getUserName,"adminxx").list();
            assertEquals(list.size(),1);
            assertEquals(list.get(0), eqSysUser, "实体with where修改");
        }
    }

    @Test
    public void updateEntityTestForceUpdateWithWhere() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);

            SysUser updateSysUser = new SysUser();
            updateSysUser.setUserName("adminxx");
            sysUserMapper.update(updateSysUser, Wheres.create().eq(SysUser::getId,1), SysUser::getPassword);


            SysUser eqSysUser = new SysUser();
            eqSysUser.setId(1);
            eqSysUser.setUserName("adminxx");
            eqSysUser.setPassword(null);
            eqSysUser.setRole_id(0);
            eqSysUser.setCreate_time(LocalDateTime.parse("2023-10-10T10:10:10"));


            List<SysUser> list=QueryChain.of(sysUserMapper).eq(SysUser::getUserName,"adminxx").list();
            assertEquals(list.size(),1);
            assertEquals(list.get(0), eqSysUser, "实体with where 强制修改");
        }
    }



    @Test
    public void updateModelWithWhereTest() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);

            SysUserModel updateSysUser = new SysUserModel();
            updateSysUser.setUserName("adminxx");
            sysUserMapper.update(updateSysUser, where -> where.eq(SysUser::getId, 1));

            SysUser eqSysUser = new SysUser();
            eqSysUser.setId(1);
            eqSysUser.setUserName("adminxx");
            eqSysUser.setPassword("123");
            eqSysUser.setRole_id(0);
            eqSysUser.setCreate_time(LocalDateTime.parse("2023-10-10T10:10:10"));


            List<SysUser> list=QueryChain.of(sysUserMapper).eq(SysUser::getUserName,"adminxx").list();
            assertEquals(list.size(),1);
            assertEquals(list.get(0), eqSysUser, "实体with where修改");
        }
    }

    @Test
    public void updateModelTestForceUpdateWithWhere() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);

            SysUserModel updateSysUser = new SysUserModel();
            updateSysUser.setUserName("adminxx");
            sysUserMapper.update(updateSysUser, Wheres.create().eq(SysUser::getId,1), SysUser::getPassword);


            SysUser eqSysUser = new SysUser();
            eqSysUser.setId(1);
            eqSysUser.setUserName("adminxx");
            eqSysUser.setPassword(null);
            eqSysUser.setRole_id(0);
            eqSysUser.setCreate_time(LocalDateTime.parse("2023-10-10T10:10:10"));


            List<SysUser> list=QueryChain.of(sysUserMapper).eq(SysUser::getUserName,"adminxx").list();
            assertEquals(list.size(),1);
            assertEquals(list.get(0), eqSysUser, "实体with where 强制修改");
        }
    }
}
