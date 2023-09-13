package org.mybatis.mp.core.mybatis;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.transaction.Transaction;

import java.sql.SQLException;
import java.util.List;

/**
 *
 */
public class MybatisExecutor implements Executor {

    private final Executor delegate;

    public MybatisExecutor(Executor delegate) {
        this.delegate = delegate;
    }

    @Override
    public int update(MappedStatement mappedStatement, Object parameterObject) throws SQLException {
        return delegate.update(mappedStatement, parameterObject);
    }

    @Override
    public <E> List<E> query(MappedStatement mappedStatement, Object parameterObject, RowBounds rowBounds, ResultHandler resultHandler, CacheKey cacheKey, BoundSql boundSql) throws SQLException {
        return delegate.query(mappedStatement, parameterObject, rowBounds, resultHandler, cacheKey, boundSql);
    }

    @Override
    public <E> List<E> query(MappedStatement mappedStatement, Object parameterObject, RowBounds rowBounds, ResultHandler resultHandler) throws SQLException {
        return delegate.query(mappedStatement, parameterObject, rowBounds, resultHandler);
    }

    @Override
    public <E> Cursor<E> queryCursor(MappedStatement mappedStatement, Object parameterObject, RowBounds rowBounds) throws SQLException {
        return delegate.queryCursor(mappedStatement, parameterObject, rowBounds);
    }

    @Override
    public List<BatchResult> flushStatements() throws SQLException {
        return delegate.flushStatements();
    }

    @Override
    public void commit(boolean b) throws SQLException {
        delegate.commit(b);
    }

    @Override
    public void rollback(boolean b) throws SQLException {
        delegate.rollback(b);
    }

    @Override
    public CacheKey createCacheKey(MappedStatement mappedStatement, Object parameterObject, RowBounds rowBounds, BoundSql boundSql) {
        return delegate.createCacheKey(mappedStatement, parameterObject, rowBounds, boundSql);
    }

    @Override
    public boolean isCached(MappedStatement mappedStatement, CacheKey cacheKey) {
        return delegate.isCached(mappedStatement, cacheKey);
    }

    @Override
    public void clearLocalCache() {
        delegate.clearLocalCache();
    }

    @Override
    public void deferLoad(MappedStatement mappedStatement, MetaObject metaObject, String s, CacheKey cacheKey, Class<?> aClass) {
        delegate.deferLoad(mappedStatement, metaObject, s, cacheKey, aClass);
    }

    @Override
    public Transaction getTransaction() {
        return delegate.getTransaction();
    }

    @Override
    public void close(boolean b) {
        delegate.close(b);
    }

    @Override
    public boolean isClosed() {
        return delegate.isClosed();
    }

    @Override
    public void setExecutorWrapper(Executor executor) {
        delegate.setExecutorWrapper(executor);
    }
}
