package com.mybatis.mp.core.test.testCase.query;

import cn.mybatis.mp.core.sql.MybatisCmdFactory;
import cn.mybatis.mp.core.sql.executor.Query;
import cn.mybatis.mp.core.sql.executor.chain.QueryChain;
import com.mybatis.mp.core.test.DO.SysUser;
import com.mybatis.mp.core.test.DO.SysUserScore;
import com.mybatis.mp.core.test.mapper.SysUserMapper;
import com.mybatis.mp.core.test.mapper.SysUserScoreMapper;
import com.mybatis.mp.core.test.testCase.BaseTest;
import db.sql.api.cmd.LikeMode;
import db.sql.api.impl.cmd.Methods;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FunTest extends BaseTest {

    @Test
    public void count() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            Integer count = QueryChain.of(sysUserMapper)
                    .selectWithFun(SysUser::getId, c -> c.count())
                    .from(SysUser.class)
                    .like(SysUser::getUserName, "test", LikeMode.RIGHT)
                    .setReturnType(Integer.TYPE)
                    .count();

            assertEquals(Integer.valueOf(2), count, "count");
        }
    }

    @Test
    public void min() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            Integer count = QueryChain.of(sysUserMapper)
                    .selectWithFun(SysUser::getId, c -> c.min())
                    .from(SysUser.class)
                    .like(SysUser::getUserName, "test", LikeMode.RIGHT)
                    .setReturnType(Integer.TYPE)
                    .get();

            assertEquals(Integer.valueOf(2), count, "min");
        }
    }

    @Test
    public void max() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            Integer count = QueryChain.of(sysUserMapper)
                    .selectWithFun(SysUser::getId, c -> c.max())
                    .from(SysUser.class)
                    .like(SysUser::getUserName, "test", LikeMode.RIGHT)
                    .setReturnType(Integer.TYPE)
                    .get();

            assertEquals(Integer.valueOf(3), count, "max");
        }
    }

    @Test
    public void avg() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            BigDecimal avg = QueryChain.of(sysUserMapper)
                    .selectWithFun(SysUser::getId, c -> c.avg())
                    .from(SysUser.class)
                    .eq(SysUser::getId, 1).or().eq(SysUser::getId, 3)
                    .setReturnType(BigDecimal.class)
                    .get();
            assertEquals(0, new BigDecimal("2").compareTo(avg), "avg");
        }
    }

    @Test
    public void multiply() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            BigDecimal multiply = QueryChain.of(sysUserMapper)
                    .selectWithFun(SysUser::getId, c -> c.multiply(-1))
                    .from(SysUser.class)
                    .eq(SysUser::getId, 1)
                    .setReturnType(BigDecimal.class)
                    .get();

            assertEquals(new BigDecimal("-1"), multiply, "multiply");
        }
    }

    @Test
    public void divide() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            BigDecimal divide = QueryChain.of(sysUserMapper)
                    .selectWithFun(SysUser::getId, c -> c.divide(-2))
                    .from(SysUser.class)
                    .eq(SysUser::getId, 2)
                    .setReturnType(BigDecimal.class)
                    .get();


            assertEquals(new BigDecimal("-1"), divide, "divide");
        }
    }

    @Test
    public void plus() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            BigDecimal divide = QueryChain.of(sysUserMapper)
                    .selectWithFun(SysUser::getId, c -> c.plus(1))
                    .eq(SysUser::getId, 2)
                    .setReturnType(BigDecimal.class)
                    .get();

            assertEquals(new BigDecimal("3"), divide, "plus");
        }
    }

    @Test
    public void subtract() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            BigDecimal divide = QueryChain.of(sysUserMapper)
                    .selectWithFun(SysUser::getId, c -> c.subtract(1))
                    .from(SysUser.class)
                    .eq(SysUser::getId, 3)
                    .setReturnType(BigDecimal.class)
                    .get();

            assertEquals(new BigDecimal("2"), divide, "subtract");
        }
    }

    @Test
    public void abs() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            BigDecimal divide = QueryChain.of(sysUserMapper)
                    .selectWithFun(SysUser::getId, c -> c.multiply(-2).abs())
                    .from(SysUser.class)
                    .eq(SysUser::getId, 3)
                    .setReturnType(BigDecimal.class)
                    .get();
            assertEquals(new BigDecimal("6"), divide, "abs");
        }
    }

    @Test
    public void pow() {
        Query query = new Query().
                selectWithFun(SysUser::getId, c -> c.pow(2)).
                from(SysUser.class).
                eq(SysUser::getId, 3).
                setReturnType(BigDecimal.class);
        check("if_", "SELECT  POW( t.id , 2) FROM t_sys_user t WHERE  t.id = 3", query);
    }


    @Test
    public void concat() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            String str = QueryChain.of(sysUserMapper)
                    .selectWithFun(SysUser::getId, c -> c.concat("2"))
                    .from(SysUser.class)
                    .eq(SysUser::getId, 3)
                    .setReturnType(String.class)
                    .get();

            assertEquals("32", str, "concat");
        }
    }

    @Test
    public void concatAs() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            String str = QueryChain.of(sysUserMapper)
                    .selectWithFun(SysUser::getId, c -> c.concatAs("a", "2", "3"))
                    .from(SysUser.class)
                    .eq(SysUser::getId, 3)
                    .setReturnType(String.class)
                    .get();
            assertEquals("3a2a3", str, "concatAs");
        }
    }

    @Test
    public void round() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserScoreMapper sysUserScoreMapper = session.getMapper(SysUserScoreMapper.class);
            {
                BigDecimal divide = QueryChain.of(sysUserScoreMapper)
                        .selectWithFun(SysUserScore::getScore, c -> c.multiply(2.12).round(1))
                        .from(SysUserScore.class)
                        .eq(SysUserScore::getUserId, 3)
                        .setReturnType(BigDecimal.class)
                        .get();
                assertEquals(new BigDecimal("5.5"), divide, "round");
            }
            {
                BigDecimal divide = QueryChain.of(sysUserScoreMapper)
                        .selectWithFun(SysUserScore::getScore, c -> c.multiply(2.3).round(1))
                        .from(SysUserScore.class)
                        .eq(SysUserScore::getUserId, 3)
                        .setReturnType(BigDecimal.class)
                        .get();

                assertEquals(new BigDecimal("6.0"), divide, "round");
            }
        }
    }

    @Test
    public void if_() {
        Query query = (new Query().
                selectWithFun(SysUser::getId, c -> c.eq(3).if_("abc", "")).
                from(SysUser.class).
                eq(SysUser::getId, 3).
                setReturnType(String.class)
        );
        check("if_", "SELECT  IF( t.id = 3 , 'abc' , '') FROM t_sys_user t WHERE  t.id = 3", query);
    }

    @Test
    public void caseWhenThen() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            String str = QueryChain.of(sysUserMapper)
                    .connect(self -> {
                        MybatisCmdFactory $ = self.$();
                        self.selectWithFun(SysUser::getId, c -> {
                            return c.eq(1).caseThen(1)
                                    .when(self.$(SysUser::getId, c2 -> c2.eq(1)), 3)
                                    .else_(4);
                        });
                    })
                    .from(SysUser.class)
                    .eq(SysUser::getId, 1)
                    .setReturnType(String.class)
                    .get();
            assertEquals("1", str, "caseWhenThen");
        }
    }

    @Test
    public void caseWhenElse() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            String str = QueryChain.of(sysUserMapper)
                    .connect(self -> {
                        MybatisCmdFactory $ = self.$();
                        self.selectWithFun(SysUser::getId, c -> {
                            return c.eq(1)
                                    .caseThen(1)
                                    .when(Methods.eq($.field(SysUser::getId), 2), 3)
                                    .else_(4);
                        });
                    })
                    .from(SysUser.class)
                    .eq(SysUser::getId, 3)
                    .setReturnType(String.class)
                    .get();
            assertEquals("4", str, "caseWhenElse");
        }
    }
}
