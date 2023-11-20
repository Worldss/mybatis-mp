package com.mybatis.mp.core.test.testCase.version;

import com.mybatis.mp.core.test.mapper.VersionTestMapper;

import com.mybatis.mp.core.test.DO.VersionTest;
import com.mybatis.mp.core.test.model.VersionModel;
import com.mybatis.mp.core.test.testCase.BaseTest;
import junit.framework.Assert;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class VersionTestCase extends BaseTest {

    @Test
    public void insertTest() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            VersionTestMapper versionTestMapper = session.getMapper(VersionTestMapper.class);
            VersionTest versionTest = new VersionTest();
            versionTest.setName("我是1");
            versionTest.setCreateTime(LocalDateTime.now());
            versionTestMapper.save(versionTest);
            System.out.println(versionTest);
            Assert.assertTrue(versionTest.getId() != null);
            Assert.assertTrue(versionTestMapper.getById(versionTest.getId()).getVersion() == 1);
        }
    }

    @Test
    public void updateTest() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            VersionTestMapper versionTestMapper = session.getMapper(VersionTestMapper.class);
            VersionTest versionTest = new VersionTest();
            versionTest.setName("我是1");
            versionTest.setCreateTime(LocalDateTime.now());
            versionTestMapper.save(versionTest);

            versionTest.setName("我是2");
            versionTestMapper.update(versionTest);
            System.out.println(versionTest);
            Assert.assertTrue(versionTest.getVersion() == 2);
            Assert.assertTrue(versionTestMapper.getById(versionTest.getId()).getVersion() == 2);
        }
    }

    @Test
    public void insertWithModelTest() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            VersionTestMapper versionTestMapper = session.getMapper(VersionTestMapper.class);
            VersionModel versionTest = new VersionModel();
            versionTest.setName("我是1");
            versionTest.setCreateTime(LocalDateTime.now());
            versionTestMapper.save(versionTest);
            System.out.println(versionTest);
            Assert.assertTrue(versionTest.getId() != null);
            Assert.assertTrue(versionTestMapper.getById(versionTest.getId()).getVersion() == 1);
        }
    }

    @Test
    public void updateWithModelTest() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            VersionTestMapper versionTestMapper = session.getMapper(VersionTestMapper.class);
            VersionModel versionTest = new VersionModel();
            versionTest.setName("我是1");
            versionTest.setCreateTime(LocalDateTime.now());
            versionTestMapper.save(versionTest);

            versionTest.setName("我是2");
            versionTestMapper.update(versionTest);
            System.out.println(versionTest);
            Assert.assertTrue(versionTest.getVersion() == 2);
            Assert.assertTrue(versionTestMapper.getById(versionTest.getId()).getVersion() == 2);
        }
    }
}
