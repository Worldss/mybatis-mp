package com.mybatis.mp.core.test.testCase.insert;

import com.mybatis.mp.core.test.DO.DefaultValueTest;
import com.mybatis.mp.core.test.mapper.DefaultValueTestMapper;
import com.mybatis.mp.core.test.testCase.BaseTest;
import junit.framework.Assert;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

public class DefaultValueTestCase extends BaseTest {

    @Test
    public void defaultValueTest() {
        try (SqlSession session = this.sqlSessionFactory.openSession(true)) {
            DefaultValueTestMapper mapper = session.getMapper(DefaultValueTestMapper.class);
            DefaultValueTest defaultValueTest = new DefaultValueTest();

            mapper.save(defaultValueTest);
            //System.out.println(idTest);
            Assert.assertNotNull(defaultValueTest.getId());
            Assert.assertNotNull(defaultValueTest.getValue1());
            Assert.assertNotNull(defaultValueTest.getValue2());
            Assert.assertNotNull(defaultValueTest.getCreateTime());
        }
    }
}
