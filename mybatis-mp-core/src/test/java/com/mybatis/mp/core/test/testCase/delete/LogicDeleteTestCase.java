package com.mybatis.mp.core.test.testCase.delete;

import cn.mybatis.mp.core.MybatisMpConfig;
import cn.mybatis.mp.core.logicDelete.LogicDeleteUtil;
import cn.mybatis.mp.core.sql.executor.chain.QueryChain;
import com.mybatis.mp.core.test.DO.LogicDeleteTest;
import com.mybatis.mp.core.test.mapper.LogicDeleteTestMapper;
import com.mybatis.mp.core.test.testCase.BaseTest;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LogicDeleteTestCase extends BaseTest {

    public LogicDeleteTestCase() {
        MybatisMpConfig.setLogicDeleteSwitch(true);
    }

    @Test
    public void deleteIdTest() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            LogicDeleteTestMapper logicDeleteTestMapper = session.getMapper(LogicDeleteTestMapper.class);
            logicDeleteTestMapper.deleteById(1);
            List<LogicDeleteTest> list = QueryChain.of(logicDeleteTestMapper).list();
            assertEquals(list.size(), 2);
        }
    }


    @Test
    public void deleteIdTest2() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            LogicDeleteTestMapper logicDeleteTestMapper = session.getMapper(LogicDeleteTestMapper.class);
            logicDeleteTestMapper.deleteById(1);

            LogicDeleteTest logicDeleteTest = LogicDeleteUtil.execute(false, () -> {
                return logicDeleteTestMapper.getById(1);
            });
            assertEquals(logicDeleteTest.getDeleted(), Byte.valueOf("1"));
        }
    }

    @Test
    public void deleteEntityTest() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            LogicDeleteTestMapper logicDeleteTestMapper = session.getMapper(LogicDeleteTestMapper.class);
            logicDeleteTestMapper.delete(logicDeleteTestMapper.getById(1));
            List<LogicDeleteTest> list = QueryChain.of(logicDeleteTestMapper).list();
            assertEquals(list.size(), 2);
        }
    }


    @Test
    public void deleteWithWhereTest() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            LogicDeleteTestMapper logicDeleteTestMapper = session.getMapper(LogicDeleteTestMapper.class);
            logicDeleteTestMapper.delete(where -> where.eq(LogicDeleteTest::getId, 1));
            List<LogicDeleteTest> list = QueryChain.of(logicDeleteTestMapper).list();
            assertEquals(list.size(), 2);
        }
    }

}
