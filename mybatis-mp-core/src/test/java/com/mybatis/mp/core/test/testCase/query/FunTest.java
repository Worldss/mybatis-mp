package com.mybatis.mp.core.test.testCase.query;

import cn.mybatis.mp.core.sql.executor.Query;
import com.mybatis.mp.core.test.mapper.SysUserMapper;
import com.mybatis.mp.core.test.mapper.SysUserScoreMapper;
import com.mybatis.mp.core.test.model.SysUser;
import com.mybatis.mp.core.test.model.SysUserScore;
import com.mybatis.mp.core.test.testCase.BaseTest;
import db.sql.api.LikeMode;
import db.sql.core.api.DatabaseId;
import db.sql.core.api.cmd.condition.Eq;
import db.sql.core.api.tookit.SQLPrinter;
import junit.framework.Assert;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class FunTest extends BaseTest {

    @Test
    public void count() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            Integer count = sysUserMapper.get(new Query().
                    select(SysUser::getId, c -> c.count()).
                    from(SysUser.class).
                    like(SysUser::getUserName, "test", LikeMode.RIGHT).
                    setReturnType(Integer.TYPE)
            );

            Assert.assertEquals("count", Integer.valueOf(2), count);
        }
    }

    @Test
    public void min() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            Integer count = sysUserMapper.get(new Query().
                    select(SysUser::getId, c -> c.min()).
                    from(SysUser.class).
                    like(SysUser::getUserName, "test", LikeMode.RIGHT).
                    setReturnType(Integer.TYPE)
            );

            Assert.assertEquals("min", Integer.valueOf(2), count);
        }
    }

    @Test
    public void max() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            Integer count = sysUserMapper.get(new Query().
                    select(SysUser::getId, c -> c.max()).
                    from(SysUser.class).
                    like(SysUser::getUserName, "test", LikeMode.RIGHT).
                    setReturnType(Integer.TYPE)
            );

            Assert.assertEquals("max", Integer.valueOf(3), count);
        }
    }

    @Test
    public void avg() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            BigDecimal avg = sysUserMapper.get(new Query().
                    select(SysUser::getId, c -> c.avg()).
                    from(SysUser.class).
                    eq(SysUser::getId, 1).or().eq(SysUser::getId, 3).
                    setReturnType(BigDecimal.class)
            );

            Assert.assertEquals("avg", new BigDecimal("2"), avg);
        }
    }

    @Test
    public void multiply() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            BigDecimal multiply = sysUserMapper.get(new Query().
                    select(SysUser::getId, c -> c.multiply(-1)).
                    from(SysUser.class).
                    eq(SysUser::getId, 1).
                    setReturnType(BigDecimal.class)
            );

            Assert.assertEquals("multiply", new BigDecimal("-1"), multiply);
        }
    }

    @Test
    public void divide() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            BigDecimal divide = sysUserMapper.get(new Query().
                    select(SysUser::getId, c -> c.divide(-2)).
                    from(SysUser.class).
                    eq(SysUser::getId, 2).
                    setReturnType(BigDecimal.class)
            );

            Assert.assertEquals("divide", new BigDecimal("-1"), divide);
        }
    }

    @Test
    public void plus() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            BigDecimal divide = sysUserMapper.get(new Query().
                    select(SysUser::getId, c -> c.plus(1)).
                    from(SysUser.class).
                    eq(SysUser::getId, 2).
                    setReturnType(BigDecimal.class)
            );

            Assert.assertEquals("plus", new BigDecimal("3"), divide);
        }
    }

    @Test
    public void subtract() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            BigDecimal divide = sysUserMapper.get(new Query().
                    select(SysUser::getId, c -> c.subtract(1)).
                    from(SysUser.class).
                    eq(SysUser::getId, 3).
                    setReturnType(BigDecimal.class)
            );

            Assert.assertEquals("subtract", new BigDecimal("2"), divide);
        }
    }

    @Test
    public void abs() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            BigDecimal divide = sysUserMapper.get(new Query().
                    select(SysUser::getId, c -> c.multiply(-2).abs()).
                    from(SysUser.class).
                    eq(SysUser::getId, 3).
                    setReturnType(BigDecimal.class)
            );
            Assert.assertEquals("abs", new BigDecimal("6"), divide);
        }
    }

    @Test
    public void pow() {
        Query query = new Query().
                select(SysUser::getId, c -> c.pow(2)).
                from(SysUser.class).
                eq(SysUser::getId, 3).
                setReturnType(BigDecimal.class);
        check("if_", "SELECT  POW( t._id , 2) FROM t_sys_user t WHERE  t._id = 3", query);
    }


    @Test
    public void concat() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            String str = sysUserMapper.get(new Query().
                    select(SysUser::getId, c -> c.concat("2")).
                    from(SysUser.class).
                    eq(SysUser::getId, 3).
                    setReturnType(String.class)
            );
            Assert.assertEquals("concat", "32", str);
        }
    }

    @Test
    public void concatAs() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            String str = sysUserMapper.get(new Query().
                    select(SysUser::getId, c -> c.concatAs("a", "2", "3")).
                    from(SysUser.class).
                    eq(SysUser::getId, 3).
                    setReturnType(String.class)
            );
            Assert.assertEquals("concatAs", "3a2a3", str);
        }
    }

    @Test
    public void round() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserScoreMapper sysUserScoreMapper = session.getMapper(SysUserScoreMapper.class);
            {
                BigDecimal divide = sysUserScoreMapper.get(new Query().
                        select(SysUserScore::getScore, c -> c.multiply(2.12).round(1)).
                        from(SysUserScore.class).
                        eq(SysUserScore::getUserId, 3).
                        setReturnType(BigDecimal.class)
                );
                Assert.assertEquals("round", new BigDecimal("5.5"), divide);
            }
            {
                BigDecimal divide = sysUserScoreMapper.get(new Query().
                        select(SysUserScore::getScore, c -> c.multiply(2.3).round(1)).
                        from(SysUserScore.class).
                        eq(SysUserScore::getUserId, 3).
                        setReturnType(BigDecimal.class)
                );
                Assert.assertEquals("round", new BigDecimal("6.0"), divide);
            }
        }
    }

    @Test
    public void if_() {
        Query query = (new Query().
                select(SysUser::getId, c -> c.eq(3).if_("abc", "")).
                from(SysUser.class).
                eq(SysUser::getId, 3).
                setReturnType(String.class)
        );
        check("if_", "SELECT  IF( t._id = 3 , 'abc' , '') FROM t_sys_user t WHERE  t._id = 3", query);
    }

    @Test
    public void caseWhenThen() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            String str = sysUserMapper.get(new Query() {{
                select(SysUser::getId, c -> c.eq(1).caseThen(1).when($(SysUser::getId, c2 -> c2.eq(1)), 3).else_(4));
                from(SysUser.class);
                eq(SysUser::getId, 1);
                setReturnType(String.class);
            }});
            Assert.assertEquals("caseWhenThen", "1", str);
        }
    }

    @Test
    public void caseWhenElse() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            String str = sysUserMapper.get(new Query() {{
                select(SysUser::getId, c -> c.eq(1).caseThen(1).when($.eq($(SysUser::getId), 2), 3).else_(4));
                from(SysUser.class);
                eq(SysUser::getId, 3);
                setReturnType(String.class);
            }});
            Assert.assertEquals("caseWhenElse", "4", str);
        }
    }
}
