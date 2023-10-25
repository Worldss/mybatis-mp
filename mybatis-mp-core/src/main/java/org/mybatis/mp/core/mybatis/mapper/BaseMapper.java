package org.mybatis.mp.core.mybatis.mapper;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.mp.core.db.reflect.FieldInfo;
import org.mybatis.mp.core.db.reflect.TableInfo;
import org.mybatis.mp.core.db.reflect.TableInfos;
import org.mybatis.mp.core.mybatis.mapper.context.*;
import org.mybatis.mp.core.mybatis.provider.MybatisSQLProvider;
import org.mybatis.mp.core.sql.executor.Delete;
import org.mybatis.mp.core.sql.executor.Query;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface BaseMapper<T> {

    /**
     * 动态查询 返回单个当前实体
     *
     * @param query
     * @return
     */

    default T get(Query query) {
        return this.$get(new SQLCmdQueryContext<>(query), new RowBounds(0, 1));
    }

    /**
     * 实体类新增
     *
     * @param entity
     * @return
     */
    default int save(T entity) {
        return this.$save(new EntityInsertContext(entity));
    }

    /**
     * 实体类修改
     *
     * @param entity
     * @return
     */
    default int update(T entity) {
        return this.$update(new EntityUpdateContext(entity));
    }

    default int delete(T entity) {
        TableInfo tableInfo = TableInfos.get(entity.getClass());
        try {
            FieldInfo idInfo = tableInfo.getIdInfo();
            Delete delete = new Delete().delete(entity.getClass()).from(entity.getClass());
            Serializable id = (Serializable) idInfo.getReadFieldInvoker().invoke(entity, null);
            delete.eq(delete.$().field(entity.getClass(), idInfo.getReflectField().getName(), 1), id);
            return this.$delete(new SQLCmdDeleteContext(delete));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 列表查询
     *
     * @param query
     * @return
     */
    default <R> List<R> list(Query query) {
        return this.$list(new SQLCmdQueryContext<>(query));
    }


    /**
     * 全部
     *
     * @return
     * @see MybatisSQLProvider#all(ProviderContext)
     */
    @SelectProvider(type = MybatisSQLProvider.class, method = MybatisSQLProvider.ALL_NAME)
    List<T> all();

    /**
     * count查询
     *
     * @param query
     * @return
     */
    default Integer count(Query query) {
        return this.$count(new SQLCmdQueryContext(query));
    }

    /**
     * 分页查询
     *
     * @param query
     * @param pager
     * @return
     */
    default Pager<T> paging(Query query, Pager<T> pager) {
        if (pager.isExecuteCount()) {
            Class returnType = query.getReturnType();
            query.setReturnType(Integer.TYPE);
            Integer count = this.$count(new SQLCmdQueryContext(query));
            pager.setTotal(Optional.of(count).orElse(0));
            query.setReturnType(returnType);
        }
        query.limit(pager.getOffset(), pager.getSize());
        pager.setResults(this.$list(new SQLCmdQueryContext(query)));
        return pager;
    }


    /**
     * 动态查询 返回单个
     *
     * @param queryContext
     * @return
     * @see MybatisSQLProvider#cmdQuery(SQLCmdQueryContext, ProviderContext)
     */
    @SelectProvider(type = MybatisSQLProvider.class, method = "cmdQuery")
    <R> R $get(SQLCmdQueryContext<R> queryContext, RowBounds rowBounds);


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
     * @param deleteContext
     * @return
     * @see MybatisSQLProvider#delete(SQLCmdDeleteContext, ProviderContext)
     */
    @UpdateProvider(type = MybatisSQLProvider.class, method = MybatisSQLProvider.DELETE_NAME)
    int $delete(SQLCmdDeleteContext deleteContext);


    /**
     * 列表查询
     *
     * @param queryContext
     * @return
     */
    @SelectProvider(type = MybatisSQLProvider.class, method = "cmdQuery")
    <T> List<T> $list(SQLCmdQueryContext<T> queryContext);

    /**
     * count查询
     *
     * @param queryContext
     * @return
     * @see org.mybatis.mp.core.mybatis.provider.MybatisSQLProvider#countCmdQuery(SQLCmdQueryContext, ProviderContext)
     */
    @SelectProvider(type = MybatisSQLProvider.class, method = "countCmdQuery")
    Integer $count(SQLCmdQueryContext queryContext);
}
