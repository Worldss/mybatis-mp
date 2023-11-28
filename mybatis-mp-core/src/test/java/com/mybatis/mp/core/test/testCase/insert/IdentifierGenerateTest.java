package com.mybatis.mp.core.test.testCase.insert;

import cn.mybatis.mp.core.mybatis.MybatisBatchUtil;
import com.mybatis.mp.core.test.DO.IdTest;
import com.mybatis.mp.core.test.mapper.IdTestMapper;
import com.mybatis.mp.core.test.testCase.BaseTest;
import junit.framework.Assert;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class IdentifierGenerateTest extends BaseTest {

    @Test
    public void insertTest() {
        try (SqlSession session = this.sqlSessionFactory.openSession(true)) {
            long startTime = System.currentTimeMillis();
            IdTestMapper idTestMapper = session.getMapper(IdTestMapper.class);
            for (int i = 0; i < 10000; i++) {

                IdTest idTest = new IdTest();
                idTest.setCreateTime(LocalDateTime.now());
                idTestMapper.save(idTest);
                //System.out.println(idTest);
                Assert.assertNotNull(idTest.getId());
            }
            System.out.println(System.currentTimeMillis() - startTime);
        }
    }

    @Test
    public void insertWithIdTest() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            IdTestMapper idTestMapper = session.getMapper(IdTestMapper.class);
            IdTest idTest = new IdTest();
            idTest.setId(1L);
            idTest.setCreateTime(LocalDateTime.now());
            idTestMapper.save(idTest);
            System.out.println(idTest);
            Assert.assertEquals(1L, (long) idTest.getId());

            Assert.assertNotNull(idTestMapper.getById(idTest.getId()));
        }
    }
}
