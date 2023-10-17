package org.mybatis.mp.core.mybatis.mapper;


import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.mp.core.db.reflect.TableInfo;
import org.mybatis.mp.core.db.reflect.TableInfos;
import org.mybatis.mp.core.mybatis.mapper.context.EntityInsertContext;
import org.mybatis.mp.core.mybatis.mapper.context.EntityUpdateContext;
import org.mybatis.mp.core.mybatis.mapper.context.Pager;
import org.mybatis.mp.core.mybatis.mapper.context.SQLCmdQueryContext;
import org.mybatis.mp.core.mybatis.provider.MybatisSQLProvider;
import org.mybatis.mp.core.sql.executor.Query;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 数据库 Mapper
 *
 * @param <T>
 */
public interface MybatisMapper<T> {

    /**
     * @param id
     * @return
     * @see MybatisSQLProvider#getById(Serializable, ProviderContext)
     */
    @SelectProvider(type = MybatisSQLProvider.class, method = MybatisSQLProvider.GET_BY_ID_NAME)
    T getById(Serializable id);

    /**
     * 动态查询 返回单个当前实体
     *
     * @param query
     * @return
     */

    default T get(Query query) {
        if (query.getLimit() == null) {
            query.limit(1);
        }
        return this.$get(new SQLCmdQueryContext<>(query));
    }

    default int save(T entity) {
        return this.$save(new EntityInsertContext(entity));
    }

    default int update(T entity) {
        return this.$update(new EntityUpdateContext(entity));
    }

    default int delete(T entity) {
        if (Objects.isNull(entity)) {
            return 0;
        }
        TableInfo tableInfo = TableInfos.get(entity.getClass());
        try {
            Serializable id = (Serializable) tableInfo.getIdInfo().getReadFieldInvoker().invoke(entity, null);
            return this.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param id
     * @return
     * @see MybatisSQLProvider#deleteById(Serializable, ProviderContext)
     */
    @DeleteProvider(type = MybatisSQLProvider.class, method = MybatisSQLProvider.DELETE_BY_ID_NAME)
    int deleteById(Serializable id);

    /**
     * 返回当前实体类查询
     *
     * @param query
     * @return
     */
    default List<T> list(Query query) {
        return this.$list(new SQLCmdQueryContext<T>(query));
    }


    /**
     * 全部
     *
     * @return
     * @see MybatisSQLProvider#all(ProviderContext)
     */
    @SelectProvider(type = MybatisSQLProvider.class, method = MybatisSQLProvider.ALL_NAME)
    List<T> all();

    default <R> R findOne(Query query) {
        return this.$findOne(new SQLCmdQueryContext<R>(query), new RowBounds(0, 1));
    }

    default <R> List<R> find(Query query) {
        return this.$find(new SQLCmdQueryContext<R>(query));
    }

    /**
     * 返回当前实体类查询
     *
     * @param query
     * @return
     */
    default Integer count(Query query) {
        return this.$count(new SQLCmdQueryContext(query));
    }

    /**
     * 分页查询，返回类型是当前实体类
     *
     * @param query
     * @param pager
     * @return
     */
    default Pager<T> paging(Query query, Pager<T> pager) {
        if (pager.isExecuteCount()) {
            Integer count = this.$count(new SQLCmdQueryContext(query));
            pager.setTotal(Optional.of(count).orElse(0));
        }
        query.limit(pager.getOffset(), pager.getSize());
        pager.setResults(this.$list(new SQLCmdQueryContext(query)));
        return pager;
    }

    /**
     * 分页查找 返回非当前实体类
     *
     * @param query
     * @param pager
     * @param <R>
     * @return
     */
    default <R> Pager<R> pagingFind(Query query, Pager<R> pager) {
        if (pager.isExecuteCount()) {
            Integer count = this.$count(new SQLCmdQueryContext<>(query));
            pager.setTotal(Optional.of(count).orElse(0));
        }
        query.limit(pager.getOffset(), pager.getSize());
        pager.setResults(this.$find(new SQLCmdQueryContext<>(query)));
        return pager;
    }


    /**
     * 动态查询 返回单个当前实体
     *
     * @param queryContext
     * @return
     * @see MybatisSQLProvider#cmdQuery(SQLCmdQueryContext, ProviderContext)
     */
    @SelectProvider(type = MybatisSQLProvider.class, method = "cmdQuery")
    T $get(SQLCmdQueryContext<T> queryContext);


    /**
     * @param insertContext
     * @return
     * @see MybatisSQLProvider#save(EntityInsertContext, ProviderContext)
     */
    @InsertProvider(type = MybatisSQLProvider.class, method = MybatisSQLProvider.SAVE_NAME)
    int $save(EntityInsertContext<T> insertContext);


    /**
     * @param updateContext
     * @return
     * @see MybatisSQLProvider#update(EntityUpdateContext, ProviderContext)
     */
    @UpdateProvider(type = MybatisSQLProvider.class, method = MybatisSQLProvider.UPDATE_NAME)
    int $update(EntityUpdateContext<T> updateContext);


    /**
     * 返回当前实体类查询
     *
     * @param queryContext
     * @return
     */
    @SelectProvider(type = MybatisSQLProvider.class, method = "cmdQuery")
    List<T> $list(SQLCmdQueryContext<T> queryContext);

    /**
     * 统计条数
     *
     * @param queryContext
     * @return
     * @see org.mybatis.mp.core.mybatis.provider.MybatisSQLProvider#countCmdQuery(SQLCmdQueryContext, ProviderContext)
     */
    @SelectProvider(type = MybatisSQLProvider.class, method = "countCmdQuery")
    Integer $count(SQLCmdQueryContext queryContext);


    /**
     * @param queryContext
     * @param <R>
     * @return
     * @see org.mybatis.mp.core.mybatis.provider.MybatisSQLProvider#cmdQuery(SQLCmdQueryContext, ProviderContext)
     */
    @SelectProvider(type = MybatisSQLProvider.class, method = "cmdQuery")
    <R> List<R> $find(SQLCmdQueryContext<R> queryContext);


    /**
     * @param queryContext
     * @param <R>
     * @return
     * @see org.mybatis.mp.core.mybatis.provider.MybatisSQLProvider#cmdQuery(SQLCmdQueryContext, ProviderContext)
     */
    @SelectProvider(type = MybatisSQLProvider.class, method = "cmdQuery")
    <R> R $findOne(SQLCmdQueryContext<R> queryContext, RowBounds rowBounds);
}
