package cn.mybatis.mp.core.mybatis;

import cn.mybatis.mp.core.mybatis.mapper.MybatisMapper;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;
import java.util.Objects;

/**
 * mybatis 批量工具
 */
public final class MybatisBatchUtil {

    public final static int DEFAULT_BATCH_SIZE = 1000;


    /**
     * 批量插入（表自增的，无法获取主键ID）
     *
     * @param sqlSessionFactory mybatis SqlSessionFactory 通过spring 注解注入获取
     * @param mapperType        MybatisMapper 的 class
     * @param list              数据列表
     * @param <M>               MybatisMapper
     * @param <T>               数据的类型
     * @return 影响的条数
     */
    public static <M extends MybatisMapper, T> int batchSave(SqlSessionFactory sqlSessionFactory, Class<M> mapperType, List<T> list) {
        return batchSave(sqlSessionFactory, mapperType, list, DEFAULT_BATCH_SIZE);
    }

    /**
     * 批量插入（batchSize！=1时，无法获取主键）
     *
     * @param sqlSessionFactory mybatis SqlSessionFactory 通过spring 注解注入获取
     * @param mapperType        MybatisMapper 的 class
     * @param list              数据列表
     * @param batchSize         一次批量处理的条数(如需获取主键，请设置为1)
     * @param <M>               MybatisMapper
     * @param <T>               数据的类型
     * @return 影响的条数
     */
    public static <M extends MybatisMapper, T> int batchSave(SqlSessionFactory sqlSessionFactory, Class<M> mapperType, List<T> list, int batchSize) {
        return batch(sqlSessionFactory, mapperType, list, batchSize, (session, mapper, data) -> {
            mapper.save(data);
        });
    }


    /**
     * 批量更新
     *
     * @param sqlSessionFactory mybatis SqlSessionFactory 通过spring 注解注入获取
     * @param mapperType        MybatisMapper 的 class
     * @param list              数据列表
     * @param <M>               MybatisMapper
     * @param <T>               数据的类型
     * @return 影响的条数
     */
    public static <M extends MybatisMapper, T> int batchUpdate(SqlSessionFactory sqlSessionFactory, Class<M> mapperType, List<T> list) {
        return batchUpdate(sqlSessionFactory, mapperType, list, DEFAULT_BATCH_SIZE);
    }

    /**
     * 批量更新
     *
     * @param sqlSessionFactory mybatis SqlSessionFactory 通过spring 注解注入获取
     * @param mapperType        MybatisMapper 的 class
     * @param list              数据列表
     * @param batchSize         一次批量处理的条数
     * @param <M>               MybatisMapper
     * @param <T>               数据的类型
     * @return 影响的条数
     */
    public static <M extends MybatisMapper, T> int batchUpdate(SqlSessionFactory sqlSessionFactory, Class<M> mapperType, List<T> list, int batchSize) {
        return batch(sqlSessionFactory, mapperType, list, batchSize, ((session, mapper, data) -> {
            mapper.update(data);
        }));
    }

    /**
     * 批量操作
     *
     * @param sqlSessionFactory mybatis SqlSessionFactory 通过spring 注解注入获取
     * @param mapperType        MybatisMapper 的 class
     * @param list              数据列表
     * @param batchSize         一次批量处理的条数
     * @param batchFunction     操作方法
     * @param <M>               MybatisMapper
     * @param <T>               数据的类型
     * @return 影响的条数
     */
    public static <M extends MybatisMapper, T> int batch(SqlSessionFactory sqlSessionFactory, Class<M> mapperType, List<T> list, int batchSize, MybatisBatchBiConsumer<SqlSession, M, T> batchFunction) {
        if (list == null || list.isEmpty()) {
            return 0;
        }
        try (SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH)) {
            M mapper = session.getMapper(mapperType);
            int updateCnt = 0;
            int optTimes = 0;
            for (T entity : list) {
                optTimes++;
                batchFunction.accept(session, mapper, entity);
                if (optTimes == batchSize) {
                    updateCnt += getUpdateCnt(session.flushStatements());
                    optTimes = 0;
                }
            }
            if (optTimes != 0) {
                updateCnt += getUpdateCnt(session.flushStatements());
            }
            return updateCnt;
        }
    }

    private static int getUpdateCnt(List<BatchResult> batchResultList) {
        int updateCnt = 0;
        for (BatchResult batchResult : batchResultList) {
            for (int i : batchResult.getUpdateCounts()) {
                updateCnt += i;
            }
        }
        return updateCnt;
    }

    public interface MybatisBatchBiConsumer<S extends SqlSession, M extends MybatisMapper, T> {
        void accept(S session, M mapper, T data);

        default MybatisBatchBiConsumer<S, M, T> andThen(MybatisBatchBiConsumer<? super S, ? super M, ? super T> after) {
            Objects.requireNonNull(after);
            return (l, r, t) -> {
                accept(l, r, t);
                after.accept(l, r, t);
            };
        }
    }

}
