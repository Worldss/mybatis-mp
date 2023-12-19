package com.mybatis.mp.core.test.testCase.insert;

import com.mybatis.mp.core.test.DO.DefaultValueTest;
import com.mybatis.mp.core.test.mapper.DefaultValueTestMapper;
import com.mybatis.mp.core.test.testCase.BaseTest;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
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

            List<DefaultValueTest> list = Arrays.asList(new DefaultValueTest(), new DefaultValueTest());
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

//    @Test
//    public void testBatch() {
//        int length = 20000;
//        List<DefaultValueTest> list = new ArrayList<>(length);
//        for (int i = 0; i < length; i++) {
//            list.add(new DefaultValueTest());
//        }
//
//        long startTime = 0;
//        startTime = System.currentTimeMillis();
//        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
//            DefaultValueTestMapper mapper = session.getMapper(DefaultValueTestMapper.class);
//            for (DefaultValueTest item : list) {
//                mapper.save(item);
//            }
//        }
//        System.out.println("普通：" + (System.currentTimeMillis() - startTime));
//
//
//        startTime = System.currentTimeMillis();
//        int xx = MybatisBatchUtil.batchSave(this.sqlSessionFactory, DefaultValueTestMapper.class, list);
//        //System.out.println(xx);
//        System.out.println("批量：" + (System.currentTimeMillis() - startTime));
//
//
//        startTime = System.currentTimeMillis();
//        List<DefaultValueTest> saveBatchList = new ArrayList<>(100);
//        int saveTotalCnt = MybatisBatchUtil.batch(this.sqlSessionFactory, (session) -> {
//            DefaultValueTestMapper mapper = session.getMapper(DefaultValueTestMapper.class);
//            int saveCnt = 0;
//            int j=0;
//            for (int i = 0; i < length; i++) {
//                saveBatchList.add(list.get(i));
//                if (i != 0 && i % 100 == 0) {
//                    j++;
//                    mapper.saveBatch(saveBatchList, DefaultValueTest::getValue1, DefaultValueTest::getValue2, DefaultValueTest::getCreateTime);
//                    saveBatchList.clear();
//                }
//                if (i != 0 && j == 5) {
//                    j=0;
//                    saveCnt += MybatisBatchUtil.getEffectCnt(session.flushStatements());
//                }
//            }
//            if (!saveBatchList.isEmpty()) {
//                mapper.saveBatch(saveBatchList, DefaultValueTest::getValue1, DefaultValueTest::getValue2, DefaultValueTest::getCreateTime);
//                saveBatchList.clear();
//                saveCnt += MybatisBatchUtil.getEffectCnt(session.flushStatements());
//            }
//            assertEquals(length, saveCnt);
//            assertEquals(length, QueryChain.of(mapper).count());
//            return saveCnt;
//
//        });
//        //System.out.println(saveTotalCnt);
//
//        System.out.println("批量+原生批量：" + (System.currentTimeMillis() - startTime));
//
//    }
}
