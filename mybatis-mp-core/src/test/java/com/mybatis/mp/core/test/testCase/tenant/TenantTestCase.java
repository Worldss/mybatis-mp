package com.mybatis.mp.core.test.testCase.tenant;

import cn.mybatis.mp.core.sql.executor.chain.DeleteChain;
import cn.mybatis.mp.core.tenant.TenantContext;
import cn.mybatis.mp.core.tenant.TenantInfo;
import com.mybatis.mp.core.test.mapper.TenantTestMapper;
import com.mybatis.mp.core.test.DO.TenantTest;
import com.mybatis.mp.core.test.model.TenantModel;
import com.mybatis.mp.core.test.testCase.BaseTest;
import junit.framework.Assert;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class TenantTestCase extends BaseTest {

    @BeforeEach
    public void before() {
        TenantContext.registerTenantGetter(() -> {
            return new TenantInfo(1);
        });
    }

    @Test
    public void insertTest() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            TenantTestMapper tenantTestMapper = session.getMapper(TenantTestMapper.class);
            TenantTest tenantTest = new TenantTest();
            tenantTest.setName("我是1");
            tenantTest.setCreateTime(LocalDateTime.now());
            tenantTestMapper.save(tenantTest);
            System.out.println(tenantTest);
            Assert.assertTrue(tenantTest.getId() != null);
            Assert.assertTrue(tenantTestMapper.getById(tenantTest.getId()).getTenantId() == 1);
        }
    }

    @Test
    public void updateTest() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            TenantTestMapper tenantTestMapper = session.getMapper(TenantTestMapper.class);
            TenantTest tenantTest = new TenantTest();
            tenantTest.setName("我是1");
            tenantTest.setCreateTime(LocalDateTime.now());
            tenantTestMapper.save(tenantTest);

            tenantTest.setName("我是2");
            tenantTestMapper.update(tenantTest);
            System.out.println(tenantTest);
            Assert.assertTrue(tenantTest.getTenantId() == 1);


            TenantContext.registerTenantGetter(() -> {
                return new TenantInfo(2);
            });
            tenantTest.setName("我是3");
            int updateCnt = tenantTestMapper.update(tenantTest);
            Assert.assertEquals(updateCnt, 0);
        }
    }



    @Test
    public void insertWithModelTest() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            TenantTestMapper tenantTestMapper = session.getMapper(TenantTestMapper.class);
            TenantModel tenantTest = new TenantModel();
            tenantTest.setName("我是1");
            tenantTest.setCreateTime(LocalDateTime.now());
            tenantTestMapper.save(tenantTest);
            System.out.println(tenantTest);
            Assert.assertTrue(tenantTest.getId() != null);
            Assert.assertTrue(tenantTestMapper.getById(tenantTest.getId()).getTenantId() == 1);
        }
    }

    @Test
    public void updateWithModelTest() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            TenantTestMapper tenantTestMapper = session.getMapper(TenantTestMapper.class);
            TenantModel tenantTest = new TenantModel();
            tenantTest.setName("我是1");
            tenantTest.setCreateTime(LocalDateTime.now());
            tenantTestMapper.save(tenantTest);

            tenantTest.setName("我是2");
            tenantTestMapper.update(tenantTest);
            System.out.println(tenantTest);
            Assert.assertTrue(tenantTest.getTenantId() == 1);


            TenantContext.registerTenantGetter(() -> {
                return new TenantInfo(2);
            });
            tenantTest.setName("我是3");
            int updateCnt = tenantTestMapper.update(tenantTest);
            Assert.assertEquals(updateCnt, 0);
        }
    }


    @Test
    public void deleteTest() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            TenantTestMapper tenantTestMapper = session.getMapper(TenantTestMapper.class);
            TenantTest tenantTest = new TenantTest();
            tenantTest.setName("我是1");
            tenantTest.setCreateTime(LocalDateTime.now());
            tenantTestMapper.save(tenantTest);


            check("","delete t FROM tenant_test t WHERE  t.tenant_id = 1 AND  t.id = '"+tenantTest.getId()+"'",DeleteChain.of(tenantTestMapper).delete(TenantTest.class).from(TenantTest.class).eq(TenantTest::getId,tenantTest.getId()));

            TenantContext.registerTenantGetter(() -> {
                return new TenantInfo(2);
            });
            check("","delete t FROM tenant_test t WHERE  t.tenant_id = 2 AND  t.id = '"+tenantTest.getId()+"'",DeleteChain.of(tenantTestMapper).delete(TenantTest.class).from(TenantTest.class).eq(TenantTest::getId,tenantTest.getId()));

        }
    }

    @Test
    public void deleteTest2() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            TenantTestMapper tenantTestMapper = session.getMapper(TenantTestMapper.class);
            System.out.println(tenantTestMapper.getEntityType().getName());
            System.out.println(tenantTestMapper.getMapperType());
        }
    }
}
