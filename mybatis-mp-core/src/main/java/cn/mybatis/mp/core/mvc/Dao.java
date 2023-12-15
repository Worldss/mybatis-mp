package cn.mybatis.mp.core.mvc;

import cn.mybatis.mp.db.Model;
import db.sql.api.Getter;

import java.io.Serializable;
import java.util.List;

public interface Dao<T, K> {

    Class<K> getIdType();

    T getById(K id);

    T getById(K id, Getter<T>... selectFields);

    int save(T entity);

    int save(List<T> list);

    int save(Model<T> model);

    int update(T entity);

    int update(List<T> list);

    int update(T entity, Getter<T>... forceUpdateFields);

    int update(Model<T> model);

    int update(Model<T> model, Getter<T>... forceUpdateFields);

    int delete(T entity);

    int delete(List<T> list);

    int deleteById(K id);

    int deleteByIds(K... ids);

    int deleteByIds(List<K> ids);
}
