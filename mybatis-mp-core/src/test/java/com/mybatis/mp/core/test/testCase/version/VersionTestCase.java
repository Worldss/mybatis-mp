package com.mybatis.mp.core.test.testCase.version;

import com.mybatis.mp.core.test.DO.VersionTest;
import com.mybatis.mp.core.test.mapper.VersionTestMapper;
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
            Assert.assertNotNull(versionTest.getId());
            Assert.assertEquals(1, (int) versionTestMapper.getById(versionTest.getId()).getVersion());
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
            Assert.assertEquals(2, (int) versionTest.getVersion());
            Assert.assertEquals(2, (int) versionTestMapper.getById(versionTest.getId()).getVersion());
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
            Assert.assertNotNull(versionTest.getId());
            Assert.assertEquals(1, (int) versionTestMapper.getById(versionTest.getId()).getVersion());
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
            Assert.assertEquals(2, (int) versionTest.getVersion());
            Assert.assertEquals(2, (int) versionTestMapper.getById(versionTest.getId()).getVersion());
        }
    }
}
