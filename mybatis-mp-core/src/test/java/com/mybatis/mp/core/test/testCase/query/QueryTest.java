package com.mybatis.mp.core.test.testCase.query;

import cn.mybatis.mp.core.mybatis.mapper.context.Pager;
import cn.mybatis.mp.core.sql.executor.Query;
import cn.mybatis.mp.core.sql.executor.SubQuery;
import cn.mybatis.mp.core.sql.executor.chain.QueryChain;
import com.mybatis.mp.core.test.mapper.SysUserMapper;
import com.mybatis.mp.core.test.DO.SysRole;
import com.mybatis.mp.core.test.DO.SysUser;
import com.mybatis.mp.core.test.testCase.BaseTest;
import db.sql.core.api.cmd.fun.FunctionInterface;
import junit.framework.Assert;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;


public class QueryTest extends BaseTest {

    @Test
    public void simpleSelect() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);

            SysUser sysUser = QueryChain.of(sysUserMapper)
                    .select(SysUser::getId, SysUser::getUserName, SysUser::getRole_id)
                    .from(SysUser.class)
                    .eq(SysUser::getId, 1)
                    .get();
            SysUser eqSysUser = new SysUser();
            eqSysUser.setId(1);
            eqSysUser.setUserName("admin");
            eqSysUser.setRole_id(0);
            Assert.assertEquals("单表部分select检测", eqSysUser, sysUser);
        }
    }

    @Test
    public void getByIdTest() {
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
    public void innerJoinTest() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            SysUser sysUser = QueryChain.of(sysUserMapper)
                    .select(SysUser::getId, SysUser::getUserName, SysUser::getRole_id)
                    .from(SysUser.class)
                    .join(SysUser.class, SysRole.class)
                    .eq(SysUser::getId, 2)
                    .get();

            SysUser eqSysUser = new SysUser();
            eqSysUser.setId(2);
            eqSysUser.setUserName("test1");
            eqSysUser.setRole_id(1);
            Assert.assertEquals("返回单表，innerJoin检测", eqSysUser, sysUser);
        }
    }

    @Test
    public void groupByTest() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            List<Integer> counts = QueryChain.of(sysUserMapper)
                    .select(SysUser::getId, c -> c.count())
                    .from(SysUser.class)
                    .groupBy(SysUser::getRole_id)
                    .setReturnType(Integer.TYPE)
                    .list();

            Assert.assertEquals("groupBy", counts.get(0), Integer.valueOf(1));
            Assert.assertEquals("groupBy", counts.get(1), Integer.valueOf(2));
        }
    }

    @Test
    public void orderbyTest() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            SysUser sysUser = QueryChain.of(sysUserMapper)
                    .select(SysUser::getId, SysUser::getUserName, SysUser::getRole_id)
                    .from(SysUser.class)
                    .orderBy(false, SysUser::getRole_id, SysUser::getId)
                    .limit(1)
                    .get();
            SysUser eqSysUser = new SysUser();
            eqSysUser.setId(3);
            eqSysUser.setUserName("test2");
            eqSysUser.setRole_id(1);
            Assert.assertEquals("orderby", sysUser, eqSysUser);
        }
    }

    @Test
    public void havingTest() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            Integer count = QueryChain.of(sysUserMapper)
                    .select(SysUser::getRole_id, FunctionInterface::count)
                    .from(SysUser.class)
                    .groupBy(SysUser::getRole_id)
                    .having(SysUser::getRole_id, c -> c.gt(0))
                    .setReturnType(Integer.TYPE)
                    .get();

            Assert.assertEquals("having", count, Integer.valueOf(2));
        }
    }

    @Test
    public void count1Test() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            Integer count = QueryChain.of(sysUserMapper)
                    .selectCount1()
                    .from(SysUser.class)
                    .count();
            Assert.assertEquals("count1", count, Integer.valueOf(3));
        }
    }

    @Test
    public void countAllTest() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            Integer count = QueryChain.of(sysUserMapper)
                    .selectCountAll()
                    .from(SysUser.class)
                    .count();
            Assert.assertEquals("countAll", count, Integer.valueOf(3));
        }
    }

    @Test
    public void pagingTestTest() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            Pager<SysUser> pager = QueryChain.of(sysUserMapper)
                    .select(SysUser.class)
                    .from(SysUser.class)
                    .paging(new Pager<>(2));

            Assert.assertEquals("paging Total", pager.getTotal(), Integer.valueOf(3));
            Assert.assertEquals("paging Results size", pager.getResults().size(), 2);
            Assert.assertEquals("paging TotalPage", pager.getTotalPage(), Integer.valueOf(2));
        }
    }

    @Test
    public void existsMethodTest() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            List<SysUser> list = QueryChain.of(sysUserMapper)
                    .select(SysUser::getId, SysUser::getUserName, SysUser::getRole_id)
                    .from(SysUser.class)
                    .connect(query -> {
                        query.exists(SubQuery.create()
                                .select1()
                                .from(SysUser.class)
                                .eq(SysUser::getId, query.$(SysUser::getId))
                                .isNotNull(SysUser::getPassword)
                                .limit(1)
                        );
                    })
                    .list();


            Assert.assertEquals("exists size", list.size(), 2);

            SysUser eqSysUser = new SysUser();
            eqSysUser.setId(2);
            eqSysUser.setUserName("test1");
            eqSysUser.setRole_id(1);
            Assert.assertEquals("exists", list.get(1), eqSysUser);
        }
    }

    @Test
    public void inSubQueryTest() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            List<SysUser> list = QueryChain.of(sysUserMapper)
                    .select(SysUser::getId, SysUser::getUserName, SysUser::getRole_id)
                    .from(SysUser.class)
                    .connect(queryChain -> {
                        queryChain.in(SysUser::getId, new SubQuery()
                                .select(SysUser::getId)
                                .from(SysUser.class)
                                .connect(subQuery -> {
                                    subQuery.eq(SysUser::getId, queryChain.$(SysUser::getId));
                                })
                                .isNotNull(SysUser::getPassword)
                                .limit(1)
                        );
                    })
                    .list();


            Assert.assertEquals("inSubQuery size", list.size(), 2);

            SysUser eqSysUser = new SysUser();
            eqSysUser.setId(2);
            eqSysUser.setUserName("test1");
            eqSysUser.setRole_id(1);
            Assert.assertEquals("inSubQuery", list.get(1), eqSysUser);
        }
    }


    @Test
    public void selectDistinctTest() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            List<Integer> roleIds = QueryChain.of(sysUserMapper)
                    .selectDistinct()
                    .select(SysUser::getRole_id)
                    .from(SysUser.class)
                    .setReturnType(Integer.TYPE)
                    .list();
            Assert.assertEquals("selectDistinct", roleIds.size(), 2);
            Assert.assertEquals("selectDistinct", roleIds.get(0), Integer.valueOf(0));
            Assert.assertEquals("selectDistinct", roleIds.get(1), Integer.valueOf(1));
        }
    }

    @Test
    public void selectDistinctMutiTest() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            List<SysUser> list = QueryChain.of(sysUserMapper)
                    .selectDistinct()
                    .select(SysUser::getRole_id, c -> c.as("role_id"))
                    .select(SysUser::getId, c -> c.as("id"))
                    .from(SysUser.class)
                    .list();
            Assert.assertEquals("selectDistinctMuti", list.size(), 3);
            {
                SysUser eqSysUser = new SysUser();
                eqSysUser.setId(1);
                eqSysUser.setRole_id(0);
                Assert.assertEquals("selectDistinctMuti", list.get(0), eqSysUser);
            }
        }
    }

    @Test
    public void unionTest() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            List<SysUser> list = QueryChain.of(sysUserMapper)
                    .select(SysUser::getRole_id, SysUser::getId)
                    .from(SysUser.class)
                    .eq(SysUser::getId, 1)
                    .union(Query.create()
                            .select(SysUser::getRole_id, SysUser::getId)
                            .from(SysUser.class)
                            .lt(SysUser::getId, 3)
                    )
                    .list();
            Assert.assertEquals("union", list.size(), 2);
            {
                SysUser eqSysUser = new SysUser();
                eqSysUser.setId(1);
                eqSysUser.setRole_id(0);
                Assert.assertEquals("union", list.get(0), eqSysUser);
            }

            {
                SysUser eqSysUser = new SysUser();
                eqSysUser.setId(2);
                eqSysUser.setRole_id(1);
                Assert.assertEquals("union", list.get(1), eqSysUser);
            }
        }
    }

    @Test
    public void unionAllTest() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            List<SysUser> list = QueryChain.of(sysUserMapper)
                    .select(SysUser::getRole_id, SysUser::getId)
                    .from(SysUser.class)
                    .eq(SysUser::getId, 1)
                    .unionAll(Query.create()
                            .select(SysUser::getRole_id, SysUser::getId)
                            .from(SysUser.class)
                            .lt(SysUser::getId, 3)
                    )
                    .list();

            Assert.assertEquals("unionAll", list.size(), 3);
            {
                SysUser eqSysUser = new SysUser();
                eqSysUser.setId(1);
                eqSysUser.setRole_id(0);
                Assert.assertEquals("unionAll", list.get(0), eqSysUser);
                Assert.assertEquals("unionAll", list.get(1), eqSysUser);
            }

            {
                SysUser eqSysUser = new SysUser();
                eqSysUser.setId(2);
                eqSysUser.setRole_id(1);
                Assert.assertEquals("unionAll", list.get(2), eqSysUser);
            }
        }
    }

    @Test
    public void existsTest() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            boolean exists = QueryChain.of(sysUserMapper)
                    .select(SysUser::getId, SysUser::getUserName, SysUser::getRole_id)
                    .from(SysUser.class)
                    .join(SysUser.class, SysRole.class)
                    .like(SysUser::getUserName, "test")
                    .exists();
            Assert.assertEquals("existsTest检测", true, exists);
        }
    }
}
