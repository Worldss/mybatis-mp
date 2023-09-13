package org.mybatis.mp.core.mybatis.mapper;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.mybatis.mp.core.mybatis.provider.TableSQLProvider;

import java.io.Serializable;
import java.util.List;

/**
 * 数据库 Mapper
 *
 * @param <T>
 */
public interface Mapper<T> {

    /**
     * @param entity
     * @return
     * @see org.mybatis.mp.core.mybatis.provider.TableSQLProvider#save(Object, ProviderContext)
     */
    @InsertProvider(type = TableSQLProvider.class, method = TableSQLProvider.SAVE_NAME)
    int save(T entity);

    /**
     * @param update
     * @return
     * @see org.mybatis.mp.core.mybatis.provider.TableSQLProvider#update(Object, ProviderContext)
     */
    @UpdateProvider(type = TableSQLProvider.class, method = TableSQLProvider.UPDATE_NAME)
    int update(T update);

    /**
     * @param id
     * @return
     * @see org.mybatis.mp.core.mybatis.provider.TableSQLProvider#getById(Serializable, ProviderContext)
     */
    @SelectProvider(type = TableSQLProvider.class, method = TableSQLProvider.GET_BY_ID_NAME)
    T getById(Serializable id);

    /**
     * @param id
     * @return
     * @see org.mybatis.mp.core.mybatis.provider.TableSQLProvider#deleteById(Serializable, ProviderContext)
     */
    @SelectProvider(type = TableSQLProvider.class, method = TableSQLProvider.DELETE_BY_ID_NAME)
    T deleteById(Serializable id);

    @SelectProvider(type = TableSQLProvider.class, method = "all")
    List<T> all();

}
