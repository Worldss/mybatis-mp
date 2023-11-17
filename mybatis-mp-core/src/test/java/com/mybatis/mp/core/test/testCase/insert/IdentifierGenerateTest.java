package com.mybatis.mp.core.test.testCase.insert;

import com.mybatis.mp.core.test.mapper.IdTestMapper;
import com.mybatis.mp.core.test.model.IdTest;
import com.mybatis.mp.core.test.testCase.BaseTest;
import junit.framework.Assert;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class IdentifierGenerateTest extends BaseTest {

    @Test
    public void insertTest() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            IdTestMapper idTestMapper = session.getMapper(IdTestMapper.class);
            IdTest idTest = new IdTest();
            idTest.setCreateTime(LocalDateTime.now());
            idTestMapper.save(idTest);
            System.out.println(idTest);
            Assert.assertTrue(idTest.getId() != null);

            Assert.assertTrue(idTestMapper.getById(idTest.getId()) != null);
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
            Assert.assertTrue(idTest.getId() == 1L);

            Assert.assertTrue(idTestMapper.getById(idTest.getId()) != null);
        }
    }
}
