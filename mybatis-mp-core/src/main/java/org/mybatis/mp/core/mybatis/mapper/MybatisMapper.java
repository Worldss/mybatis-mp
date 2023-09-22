package org.mybatis.mp.core.mybatis.mapper;


import db.sql.core.cmd.Limit;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.session.RowBounds;
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
     * @param insertContext
     * @return
     * @see MybatisSQLProvider#save(EntityInsertContext, ProviderContext)
     */
    @InsertProvider(type = MybatisSQLProvider.class, method = MybatisSQLProvider.SAVE_NAME)
    int save(EntityInsertContext insertContext);


    default int update(T entity) {
        return this.update(new EntityUpdateContext(entity));
    }


    /**
     * @param id
     * @return
     * @see MybatisSQLProvider#deleteById(Serializable, ProviderContext)
     */
    @SelectProvider(type = MybatisSQLProvider.class, method = MybatisSQLProvider.DELETE_BY_ID_NAME)
    T deleteById(Serializable id);

    default int save(T entity) {
        return this.save(new EntityInsertContext(entity));
    }

    /**
     * @param updateContext
     * @return
     * @see MybatisSQLProvider#update(EntityUpdateContext, ProviderContext)
     */
    @UpdateProvider(type = MybatisSQLProvider.class, method = MybatisSQLProvider.UPDATE_NAME)
    int update(EntityUpdateContext updateContext);

    /**
     * 全部
     *
     * @return
     * @see MybatisSQLProvider#all(ProviderContext)
     */
    @SelectProvider(type = MybatisSQLProvider.class, method = MybatisSQLProvider.ALL_NAME)
    List<T> all();


    /**
     * 返回当前实体类查询
     *
     * @param query
     * @return
     */
    default List<T> list(Query query) {
        return this.list(new SQLCmdQueryContext(query));
    }

    /**
     * 返回当前实体类查询
     *
     * @param query
     * @return
     */
    default Integer count(Query query) {
        return this.count(new SQLCmdQueryContext(query));
    }

    default Pager<T> paging(Query query, Pager pager) {
        if (pager.isExecuteCount()) {
            Integer count = this.count(query);
            pager.setTotal(Optional.of(count).orElse(0));
        }
        query.limit(pager.getOffset(), pager.getSize());
        pager.setResults(this.list(query));
        return pager;
    }


    /**
     * 返回当前实体类查询
     *
     * @param queryContext
     * @return
     */
    @SelectProvider(type = MybatisSQLProvider.class, method = "cmdQuery")
    List<T> list(SQLCmdQueryContext queryContext);

    /**
     * 统计条数
     *
     * @param queryContext
     * @return
     * @see org.mybatis.mp.core.mybatis.provider.MybatisSQLProvider#countCmdQuery(SQLCmdQueryContext, ProviderContext)
     */
    @SelectProvider(type = MybatisSQLProvider.class, method = "countCmdQuery")
    Integer count(SQLCmdQueryContext queryContext);


    default <R> List<R> selectWithCmdQuery(Query query) {
        return this.selectWithCmdQuery(new SQLCmdQueryContext(query));
    }

    default <R> List<R> selectWithCmdQuery(SQLCmdQueryContext<R> queryContext) {
        RowBounds rowBounds = new RowBounds();
//        Limit limit = queryContext.getExecution().getLimit();
//        if (Objects.nonNull(limit)) {
//            rowBounds = new RowBounds(limit.getOffset(), limit.getLimit());
//            //queryContext.getExecution().getCmdList().remove(limit);
//            //queryContext.getExecution().limit(null);
//            //rowBounds = new RowBounds();
//        } else {
//            rowBounds = new RowBounds();
//        }
        return this.selectWithCmdQuery(queryContext, rowBounds);
    }

    /**
     * @param queryContext
     * @param <R>
     * @return
     * @see org.mybatis.mp.core.mybatis.provider.MybatisSQLProvider#cmdQuery(SQLCmdQueryContext, ProviderContext)
     */
    @SelectProvider(type = MybatisSQLProvider.class, method = "cmdQuery")
    <R> List<R> selectWithCmdQuery(SQLCmdQueryContext<R> queryContext, RowBounds rowBounds);

    default <R> R getOneWithCmdQuery(Query query) {
        return (R) this.getOneWithCmdQuery(new SQLCmdQueryContext(query), new RowBounds(0, 1));
    }

    /**
     * @param queryContext
     * @param <R>
     * @return
     * @see org.mybatis.mp.core.mybatis.provider.MybatisSQLProvider#cmdQuery(SQLCmdQueryContext, ProviderContext)
     */
    @SelectProvider(type = MybatisSQLProvider.class, method = "cmdQuery")
    <R> R getOneWithCmdQuery(SQLCmdQueryContext<R> queryContext, RowBounds rowBounds);
}
