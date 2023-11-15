package cn.mybatis.mp.core.mvc;

import cn.mybatis.mp.core.mybatis.mapper.MybatisMapper;
import cn.mybatis.mp.core.util.GenericUtil;
import cn.mybatis.mp.db.Model;
import db.sql.api.Getter;

import java.io.Serializable;

public interface Dao<T, K> {

    MybatisMapper<T> getMapper();

    default Class<K> getIdType() {
        return (Class<K>) GenericUtil.getGenericInterfaceClass(this.getClass()).get(1);
    }

    default T getById(K id) {
        if (id.getClass() == Void.class) {
            throw new RuntimeException("Not Supported");
        }
        return getMapper().getById((Serializable) id);
    }

    default void save(T entity) {
        getMapper().save(entity);
    }

    default void save(Model<T> model) {
        getMapper().save(model);
    }

    default void update(T entity) {
        if (getIdType() == Void.class) {
            throw new RuntimeException("Not Supported");
        }
        getMapper().update(entity);
    }

    default int update(T entity, Getter<T>... forceUpdateFields) {
        if (getIdType() == Void.class) {
            throw new RuntimeException("Not Supported");
        }
        return getMapper().update(entity, forceUpdateFields);
    }

    default int update(Model<T> model) {
        if (getIdType() == Void.class) {
            throw new RuntimeException("Not Supported");
        }
        return getMapper().update(model);
    }

    default int update(Model<T> model, Getter<T>... forceUpdateFields) {
        return getMapper().update(model, forceUpdateFields);
    }

    default int delete(T entity) {
        if (getIdType() == Void.class) {
            throw new RuntimeException("Not Supported");
        }
        return getMapper().delete(entity);
    }

    default int deleteById(K id) {
        if (id.getClass() == Void.class) {
            throw new RuntimeException("Not Supported");
        }
        return getMapper().deleteById((Serializable) id);
    }
}
