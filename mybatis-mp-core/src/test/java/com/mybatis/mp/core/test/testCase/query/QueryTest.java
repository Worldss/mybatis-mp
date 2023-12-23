package com.mybatis.mp.core.test.testCase.query;

import cn.mybatis.mp.core.mybatis.mapper.context.Pager;
import cn.mybatis.mp.core.sql.executor.Query;
import cn.mybatis.mp.core.sql.executor.SubQuery;
import cn.mybatis.mp.core.sql.executor.chain.QueryChain;
import com.mybatis.mp.core.test.DO.SysRole;
import com.mybatis.mp.core.test.DO.SysUser;
import com.mybatis.mp.core.test.mapper.SysUserMapper;
import com.mybatis.mp.core.test.testCase.BaseTest;
import db.sql.api.impl.cmd.basic.OrderByDirection;
import db.sql.api.impl.cmd.dbFun.FunctionInterface;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class QueryTest extends BaseTest {

    @Test
    public void simpleSelect() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);

            SysUser sysUser = QueryChain.of(sysUserMapper)
                    .select(SysUser::getId, SysUser::getUserName, SysUser::getRole_id)
                    .from(SysUser.class)
                    .eq(SysUser::getId, 1).and()
                    .get();
            SysUser eqSysUser = new SysUser();
            eqSysUser.setId(1);
            eqSysUser.setUserName("admin");
            eqSysUser.setRole_id(0);
            assertEquals(eqSysUser, sysUser, "单表部分select检测");
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
            assertEquals(eqSysUser, sysUser, "getById检测");
        }
    }

    @Test
    public void getWithWhere() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            SysUser sysUser = sysUserMapper.get(where -> {
                where.eq(SysUser::getId, 2);
            });
            SysUser eqSysUser = new SysUser();
            eqSysUser.setId(2);
            eqSysUser.setUserName("test1");
            eqSysUser.setRole_id(1);
            eqSysUser.setPassword("123456");
            eqSysUser.setCreate_time(LocalDateTime.parse("2023-10-11T10:10:10"));
            assertEquals(eqSysUser, sysUser, "getWithWhere检测");
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
            assertEquals(eqSysUser, sysUser, "返回单表，innerJoin检测");
        }
    }

    @Test
    public void innerJoinCursorTest() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            try (Cursor<SysUser> sysUserCursor = QueryChain.of(sysUserMapper)
                    .select(SysUser::getId, SysUser::getUserName, SysUser::getRole_id)
                    .from(SysUser.class)
                    .join(SysUser.class, SysRole.class)
                    .eq(SysUser::getId, 2)
                    .cursor()) {
                assertTrue(sysUserCursor instanceof Cursor);
                SysUser sysUser = null;
                for (SysUser entity : sysUserCursor) {
                    assertNull(sysUser);
                    sysUser = entity;
                }
                SysUser eqSysUser = new SysUser();
                eqSysUser.setId(2);
                eqSysUser.setUserName("test1");
                eqSysUser.setRole_id(1);
                assertEquals(eqSysUser, sysUser);
            } catch (IOException e) {

            }
        }
    }

    @Test
    public void groupByTest() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            List<Integer> counts = QueryChain.of(sysUserMapper)
                    .selectWithFun(SysUser::getId, c -> c.count())
                    .from(SysUser.class)
                    .groupBy(SysUser::getRole_id)
                    .setReturnType(Integer.TYPE)
                    .list();

            assertEquals(counts.get(0), Integer.valueOf(1), "groupBy");
            assertEquals(counts.get(1), Integer.valueOf(2), "groupBy");
        }
    }

    @Test
    public void orderbyTest() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            SysUser sysUser = QueryChain.of(sysUserMapper)
                    .select(SysUser::getId, SysUser::getUserName, SysUser::getRole_id)
                    .from(SysUser.class)
                    .orderBy(OrderByDirection.DESC, SysUser::getRole_id, SysUser::getId)
                    .limit(1)
                    .get();
            SysUser eqSysUser = new SysUser();
            eqSysUser.setId(3);
            eqSysUser.setUserName("test2");
            eqSysUser.setRole_id(1);
            assertEquals(sysUser, eqSysUser, "orderby");
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

            assertEquals(count, Integer.valueOf(2), "having");
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
            assertEquals(count, Integer.valueOf(3), "count1");
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
            assertEquals(count, Integer.valueOf(3), "countAll");
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

            assertEquals(pager.getTotal(), Integer.valueOf(3), "paging Total");
            assertEquals(pager.getResults().size(), 2, "paging Results size");
            assertEquals(pager.getTotalPage(), Integer.valueOf(2), "paging TotalPage");
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


            assertEquals(list.size(), 2, "exists size");

            SysUser eqSysUser = new SysUser();
            eqSysUser.setId(2);
            eqSysUser.setUserName("test1");
            eqSysUser.setRole_id(1);
            assertEquals(list.get(1), eqSysUser, "exists");
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


            assertEquals(list.size(), 2, "inSubQuery size");

            SysUser eqSysUser = new SysUser();
            eqSysUser.setId(2);
            eqSysUser.setUserName("test1");
            eqSysUser.setRole_id(1);
            assertEquals(list.get(1), eqSysUser, "inSubQuery");
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
            assertEquals(roleIds.size(), 2, "selectDistinct");
            assertEquals(roleIds.get(0), Integer.valueOf(0), "selectDistinct");
            assertEquals(roleIds.get(1), Integer.valueOf(1), "selectDistinct");
        }
    }

    @Test
    public void selectDistinctMutiTest() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            List<SysUser> list = QueryChain.of(sysUserMapper)
                    .selectDistinct()
                    .selectWithFun(SysUser::getRole_id, c -> c.as("role_id"))
                    .selectWithFun(SysUser::getId, c -> c.as("id"))
                    .from(SysUser.class)
                    .list();
            assertEquals(list.size(), 3, "selectDistinctMuti");
            {
                SysUser eqSysUser = new SysUser();
                eqSysUser.setId(1);
                eqSysUser.setRole_id(0);
                assertEquals(list.get(0), eqSysUser, "selectDistinctMuti");
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
            assertEquals(list.size(), 2, "union");
            {
                SysUser eqSysUser = new SysUser();
                eqSysUser.setId(1);
                eqSysUser.setRole_id(0);
                assertEquals(list.get(0), eqSysUser, "union");
            }

            {
                SysUser eqSysUser = new SysUser();
                eqSysUser.setId(2);
                eqSysUser.setRole_id(1);
                assertEquals(list.get(1), eqSysUser, "union");
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

            assertEquals(list.size(), 3, "unionAll");
            {
                SysUser eqSysUser = new SysUser();
                eqSysUser.setId(1);
                eqSysUser.setRole_id(0);
                assertEquals(list.get(0), eqSysUser, "unionAll");
                assertEquals(list.get(1), eqSysUser, "unionAll");
            }

            {
                SysUser eqSysUser = new SysUser();
                eqSysUser.setId(2);
                eqSysUser.setRole_id(1);
                assertEquals(list.get(2), eqSysUser, "unionAll");
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
            assertTrue(exists, "existsTest检测");
        }
    }

    @Test
    public void selectSubQueryTest() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);

            SubQuery subQuery = SubQuery.create("xx")
                    .select(SysUser::getId)
                    .from(SysUser.class)
                    .eq(SysUser::getId, 2);


            List<Integer> list = QueryChain.of(sysUserMapper)
                    .select(subQuery)
                    .from(SysUser.class)
                    .limit(1)
                    .setReturnType(Integer.TYPE)
                    .list();

            assertEquals(2, (int) list.get(0), "selectSubQueryTest");
        }
    }
}
