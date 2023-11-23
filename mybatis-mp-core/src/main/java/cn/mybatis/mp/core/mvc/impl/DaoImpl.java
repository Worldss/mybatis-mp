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

public abstract class DaoImpl<T, K> implements Dao<T, K> {

    private final MybatisMapper<T> mapper;
    private Class<K> idType;

    public DaoImpl(MybatisMapper<T> mapper) {
        this.mapper = mapper;
    }

    @Override    public Class<K> getIdType() {
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

    @Override    public T getById(K id) {
        if (id.getClass() == Void.class) {
            throw new RuntimeException("Not Supported");
        }
        return mapper.getById((Serializable) id);
    }

    @Override    public void save(T entity) {
        mapper.save(entity);
    }

    @Override    public void save(Model<T> model) {
        mapper.save(model);
    }

    @Override    public void update(T entity) {
        if (getIdType() == Void.class) {
            throw new RuntimeException("Not Supported");
        }
        mapper.update(entity);
    }

    @Override    public int update(T entity, Getter<T>... forceUpdateFields) {
        if (getIdType() == Void.class) {
            throw new RuntimeException("Not Supported");
        }
        return mapper.update(entity, forceUpdateFields);
    }

    @Override    public int update(Model<T> model) {
        if (getIdType() == Void.class) {
            throw new RuntimeException("Not Supported");
        }
        return mapper.update(model);
    }

    @Override    public int update(Model<T> model, Getter<T>... forceUpdateFields) {
        return mapper.update(model, forceUpdateFields);
    }

    @Override    public int delete(T entity) {
        if (getIdType() == Void.class) {
            throw new RuntimeException("Not Supported");
        }
        return mapper.delete(entity);
    }

    @Override    public int deleteById(K id) {
        if (id.getClass() == Void.class) {
            throw new RuntimeException("Not Supported");
        }
        return mapper.deleteById((Serializable) id);
    }
}
