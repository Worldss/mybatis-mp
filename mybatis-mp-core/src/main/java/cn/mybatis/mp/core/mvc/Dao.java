package cn.mybatis.mp.core.mvc;

import cn.mybatis.mp.db.Model;
import db.sql.api.Getter;

public interface Dao<T, K> {

    Class<K> getIdType();

    T getById(K id);

    void save(T entity);

    void save(Model<T> model);

    void update(T entity);

    int update(T entity, Getter<T>... forceUpdateFields);

    int update(Model<T> model);

    int update(Model<T> model, Getter<T>... forceUpdateFields);

    int delete(T entity);

    int deleteById(K id);
}
