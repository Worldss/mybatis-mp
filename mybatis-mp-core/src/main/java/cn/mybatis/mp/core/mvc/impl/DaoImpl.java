package cn.mybatis.mp.core.mvc.impl;

import cn.mybatis.mp.core.mvc.Dao;
import cn.mybatis.mp.core.mybatis.mapper.MybatisMapper;
import cn.mybatis.mp.core.sql.executor.chain.DeleteChain;
import cn.mybatis.mp.core.sql.executor.chain.InsertChain;
import cn.mybatis.mp.core.sql.executor.chain.QueryChain;
import cn.mybatis.mp.core.sql.executor.chain.UpdateChain;
import cn.mybatis.mp.core.util.GenericUtil;
import cn.mybatis.mp.db.Model;
import db.sql.api.Getter;

import java.io.Serializable;
import java.util.List;

public abstract class DaoImpl<T, K> implements Dao<T, K> {

    protected final MybatisMapper<T> mapper;
    protected Class<K> idType;

    public DaoImpl(MybatisMapper<T> mapper) {
        this.mapper = mapper;
    }

    @Override
    public Class<K> getIdType() {
        if (idType == null) {
            idType = (Class<K>) GenericUtil.getGenericInterfaceClass(this.getClass()).get(1);
        }
        return idType;
    }

    protected QueryChain queryChain() {
        return QueryChain.of(mapper);
    }

    protected UpdateChain updateChain() {
        return UpdateChain.of(mapper);
    }

    protected InsertChain insertChain() {
        return InsertChain.of(mapper);
    }

    protected DeleteChain deleteChain() {
        return DeleteChain.of(mapper);
    }

    private void checkIdType() {
        if (getIdType() == Void.class) {
            throw new RuntimeException("Not Supported");
        }
    }

    @Override
    public T getById(K id) {
        this.checkIdType();
        return mapper.getById((Serializable) id);
    }

    @Override
    public int save(T entity) {
        return mapper.save(entity);
    }

    @Override
    public int save(List<T> list) {
        return mapper.save(list);
    }

    @Override
    public int save(Model<T> model) {
        return mapper.save(model);
    }

    @Override
    public int update(T entity) {
        this.checkIdType();
        return mapper.update(entity);
    }

    @Override
    public int update(List<T> list) {
        return mapper.update(list);
    }

    @Override
    public int update(T entity, Getter<T>... forceUpdateFields) {
        this.checkIdType();
        return mapper.update(entity, forceUpdateFields);
    }

    @Override
    public int update(Model<T> model) {
        if (getIdType() == Void.class) {
            throw new RuntimeException("Not Supported");
        }
        return mapper.update(model);
    }

    @Override
    public int update(Model<T> model, Getter<T>... forceUpdateFields) {
        return mapper.update(model, forceUpdateFields);
    }

    @Override
    public int delete(T entity) {
        this.checkIdType();
        return mapper.delete(entity);
    }

    @Override
    public int delete(List<T> list) {
        return mapper.delete(list);
    }

    @Override
    public int deleteById(K id) {
        this.checkIdType();
        return mapper.deleteById((Serializable) id);
    }

    @Override
    public int deleteByIds(K... ids) {
        this.checkIdType();
        return mapper.deleteByIds(ids);
    }

    @Override
    public int deleteByIds(List<K> ids) {
        this.checkIdType();
        return mapper.deleteByIds((List<Serializable>) ids);
    }
}
