package com.mybatis.mp.core.test.testCase.insert;

import com.mybatis.mp.core.test.DO.DefaultValueTest;
import com.mybatis.mp.core.test.mapper.DefaultValueTestMapper;
import com.mybatis.mp.core.test.testCase.BaseTest;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DefaultValueTestCase extends BaseTest {

    @Test
    public void defaultValueTest() {
        try (SqlSession session = this.sqlSessionFactory.openSession(true)) {
            DefaultValueTestMapper mapper = session.getMapper(DefaultValueTestMapper.class);
            DefaultValueTest defaultValueTest = new DefaultValueTest();

            mapper.save(defaultValueTest);
            //System.out.println(idTest);
            assertNotNull(defaultValueTest.getId());
            assertNotNull(defaultValueTest.getValue1());
            assertNotNull(defaultValueTest.getValue2());
            assertNotNull(defaultValueTest.getCreateTime());
        }
    }

    @Test
    public void batchInsert() {
        try (SqlSession session = this.sqlSessionFactory.openSession(true)) {
            DefaultValueTestMapper mapper = session.getMapper(DefaultValueTestMapper.class);

            List<DefaultValueTest> list= Arrays.asList(new DefaultValueTest(),new DefaultValueTest());
            mapper.saveBatch(list, DefaultValueTest::getValue1, DefaultValueTest::getValue2, DefaultValueTest::getCreateTime);
            DefaultValueTest defaultValueTest = mapper.getById(1);
            assertNotNull(defaultValueTest.getId());
            assertNotNull(defaultValueTest.getValue1());
            assertNotNull(defaultValueTest.getValue2());
            assertNotNull(defaultValueTest.getCreateTime());

            DefaultValueTest defaultValueTest2 = mapper.getById(2);
            assertNotNull(defaultValueTest2.getId());
            assertNotNull(defaultValueTest2.getValue1());
            assertNotNull(defaultValueTest2.getValue2());
            assertNotNull(defaultValueTest2.getCreateTime());
        }
    }
}
