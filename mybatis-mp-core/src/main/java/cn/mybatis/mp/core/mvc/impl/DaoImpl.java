package cn.mybatis.mp.core.mvc.impl;

import cn.mybatis.mp.core.mvc.Dao;
import cn.mybatis.mp.core.mybatis.mapper.MybatisMapper;
import cn.mybatis.mp.core.sql.executor.chain.QueryChain;
import cn.mybatis.mp.core.sql.executor.chain.UpdateChain;

import java.io.Serializable;

public abstract class DaoImpl<T, K extends Serializable> implements Dao<T, K> {

    private final MybatisMapper<T> mapper;

    public DaoImpl(MybatisMapper<T> mapper) {
        this.mapper = mapper;
    }

    private Class<K> idType;

    @Override
    public Class<K> getIdType() {
        if (idType == null) {
            idType = Dao.super.getIdType();
        }
        return idType;
    }

    @Override
    public MybatisMapper<T> getMapper() {
        return this.mapper;
    }

    protected QueryChain queryChain() {
        return new QueryChain(getMapper());
    }

    protected UpdateChain updateChain() {
        return new UpdateChain(getMapper());
    }
}
