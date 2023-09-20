package org.mybatis.mp.core.mybatis.mapper;


import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.mybatis.mp.core.mybatis.mapper.context.EntityInsertContext;
import org.mybatis.mp.core.mybatis.mapper.context.SQLCmdQueryContext;
import org.mybatis.mp.core.mybatis.provider.MybatisSQLProvider;
import org.mybatis.mp.core.sql.executor.Query;

import java.io.Serializable;
import java.util.List;

/**
 * 数据库 Mapper
 *
 * @param <T>
 */
public interface Mapper<T> {

    default int save(T entity) {
        return this.save(new EntityInsertContext(entity));
    }

    /**
     * @param insertContext
     * @return
     * @see MybatisSQLProvider#cmdSave(EntityInsertContext, ProviderContext)
     */
    @InsertProvider(type = MybatisSQLProvider.class, method = MybatisSQLProvider.SAVE_NAME)
    int save(EntityInsertContext insertContext);

    /**
     * @param update
     * @return
     * @see MybatisSQLProvider#update(Object, ProviderContext)
     */
    @UpdateProvider(type = MybatisSQLProvider.class, method = MybatisSQLProvider.UPDATE_NAME)
    int update(T update);

    /**
     * @param id
     * @return
     * @see MybatisSQLProvider#getById(Serializable, ProviderContext)
     */
    @SelectProvider(type = MybatisSQLProvider.class, method = MybatisSQLProvider.GET_BY_ID_NAME)
    T getById(Serializable id);

    /**
     * @param id
     * @return
     * @see MybatisSQLProvider#deleteById(Serializable, ProviderContext)
     */
    @SelectProvider(type = MybatisSQLProvider.class, method = MybatisSQLProvider.DELETE_BY_ID_NAME)
    T deleteById(Serializable id);

    @SelectProvider(type = MybatisSQLProvider.class, method = "all")
    List<T> all();


    default <R> List<R> selectWithCmdQuery(Query query) {
        return this.selectWithCmdQuery(new SQLCmdQueryContext(query));
    }

    /**
     * @param queryContext
     * @param <R>
     * @return
     * @see org.mybatis.mp.core.mybatis.provider.MybatisSQLProvider#cmdQuery(SQLCmdQueryContext, ProviderContext)
     */
    @SelectProvider(type = MybatisSQLProvider.class, method = "cmdQuery")
    <R> List<R> selectWithCmdQuery(SQLCmdQueryContext<R> queryContext);

    default <R> R getOneWithCmdQuery(Query query) {
        return (R) this.getOneWithCmdQuery(new SQLCmdQueryContext(query));
    }

    /**
     * @param queryContext
     * @param <R>
     * @return
     * @see org.mybatis.mp.core.mybatis.provider.MybatisSQLProvider#cmdQuery(SQLCmdQueryContext, ProviderContext)
     */
    @SelectProvider(type = MybatisSQLProvider.class, method = "cmdQuery")
    <R> R getOneWithCmdQuery(SQLCmdQueryContext<R> queryContext);
}
