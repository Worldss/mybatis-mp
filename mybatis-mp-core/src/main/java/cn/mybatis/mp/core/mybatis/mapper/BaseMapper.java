package cn.mybatis.mp.core.mybatis.mapper;

import cn.mybatis.mp.core.mybatis.mapper.context.*;
import cn.mybatis.mp.core.mybatis.provider.MybatisSQLProvider;
import cn.mybatis.mp.core.sql.executor.*;
import cn.mybatis.mp.core.sql.executor.chain.DeleteChain;
import cn.mybatis.mp.core.sql.executor.chain.QueryChain;
import cn.mybatis.mp.db.Model;
import db.sql.api.Getter;
import db.sql.api.impl.cmd.struct.Where;
import db.sql.api.impl.tookit.LambdaUtil;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.session.RowBounds;

import java.util.*;
import java.util.function.Consumer;


public interface BaseMapper<T> {

    /**
     * 获取实体类的type
     *
     * @return
     */
    Class<T> getEntityType();

    /**
     * 获取当前mapper类的type
     *
     * @return
     */
    Class<? extends BaseMapper<T>> getMapperType();

    default T get(Consumer<Where> consumer) {
        Where where = Wheres.create();
        consumer.accept(where);
        return QueryChain.of(this, where).get(false);
    }

    /**
     * 是否存在
     *
     * @param consumer
     * @return
     */
    default boolean exists(Consumer<Where> consumer) {
        Where where = Wheres.create();
        consumer.accept(where);
        return QueryChain.of(this, where).exists(false);
    }

    /**
     * 动态查询 返回单个当前实体
     *
     * @param query
     * @return
     */

    default <R> R get(BaseQuery query) {
        return this.get(query, true);
    }

    /**
     * 动态查询 返回单个当前实体
     *
     * @param query
     * @param optimize 是否优化
     * @param <R>
     * @return
     */
    default <R> R get(BaseQuery query, boolean optimize) {
        query.limit(1);
        return this.$get(new SQLCmdQueryContext(query, optimize), new RowBounds(0, 1));
    }

    /**
     * 是否存在
     *
     * @param query
     * @return
     */
    default boolean exists(BaseQuery query) {
        return this.exists(query, true);
    }

    /**
     * 是否存在
     *
     * @param query
     * @param optimize 是否优化
     * @return
     */
    default boolean exists(BaseQuery query, boolean optimize) {
        query.limit(1);
        query.setReturnType(Integer.TYPE);
        Integer obj = this.get(query, optimize);
        return obj != null && obj >= 1;
    }


    /**
     * 实体类新增
     *
     * @param entity
     * @return
     */
    default int save(T entity) {
        return this.$saveEntity(new EntityInsertContext(entity));
    }

    /**
     * model插入 部分字段插入
     *
     * @param model
     * @return
     */
    default int save(Model<T> model) {
        return this.$saveModel(new ModelInsertContext<>(model));
    }

    /**
     * 动态插入
     *
     * @param insert
     * @return
     */
    default int save(BaseInsert insert) {
        return this.$save(new SQLCmdInsertContext<>(insert));
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


    /**
     * 实体类修改
     *
     * @param entity
     * @param forceUpdateFields 强制更新指定，解决需要修改为null的需求
     * @return
     */
    default int update(T entity, Getter<T>... forceUpdateFields) {
        Set<String> forceUpdateFieldsSet = new HashSet<>();
        for (Getter<T> column : forceUpdateFields) {
            forceUpdateFieldsSet.add(LambdaUtil.getName(column));
        }
        return this.$update(new EntityUpdateContext(entity, forceUpdateFieldsSet));
    }


    default int update(T entity, Consumer<Where> consumer) {
        Where where = Wheres.create();
        consumer.accept(where);
        return this.$update(new EntityUpdateWithWhereContext(entity, where));
    }

    default int update(T entity, Where where, Getter<T>... forceUpdateFields) {
        Set<String> forceUpdateFieldsSet = new HashSet<>();
        for (Getter getter : forceUpdateFields) {
            forceUpdateFieldsSet.add(LambdaUtil.getName(getter));
        }
        return this.$update(new EntityUpdateWithWhereContext(entity, where, forceUpdateFieldsSet));
    }

    /**
     * model插入 部分字段修改
     *
     * @param model
     * @return
     */
    default int update(Model<T> model) {
        return this.$update(new ModelUpdateContext<>(model));
    }

    /**
     * model插入 部分字段修改
     *
     * @param model
     * @param forceUpdateFields 强制更新指定，解决需要修改为null的需求
     * @return
     */
    default int update(Model<T> model, Getter<T>... forceUpdateFields) {
        Set<String> forceUpdateFieldsSet = new HashSet<>();
        for (Getter getter : forceUpdateFields) {
            forceUpdateFieldsSet.add(LambdaUtil.getName(getter));
        }
        return this.$update(new ModelUpdateContext<>(model, forceUpdateFieldsSet));
    }

    /**
     * 动态修改
     *
     * @param update
     * @return
     */
    default int update(BaseUpdate update) {
        return this.$update(new SQLCmdUpdateContext(update));
    }


    /**
     * 动态批量删除
     *
     * @param consumer
     * @return
     */
    default int delete(Consumer<Where> consumer) {
        Where where = Wheres.create();
        consumer.accept(where);
        return DeleteChain.of(this, where).execute();
    }


    /**
     * 动态删除
     *
     * @param delete
     * @return
     */
    default int delete(BaseDelete delete) {
        return this.$delete(new SQLCmdDeleteContext(delete));
    }

    /**
     * 列表查询,返回类型，当前实体类
     *
     * @param consumer
     * @return
     */
    default <T> List<T> list(Consumer<Where> consumer) {
        Where where = Wheres.create();
        consumer.accept(where);
        return QueryChain.of(this, where).list(false);
    }


    /**
     * 列表查询
     *
     * @param query
     * @return
     */
    default <R> List<R> list(BaseQuery query) {
        return this.list(query, true);
    }


    /**
     * 列表查询
     *
     * @param query
     * @param optimize 是否优化
     * @return
     */
    default <R> List<R> list(BaseQuery query, boolean optimize) {
        return this.$list(new SQLCmdQueryContext(query, optimize));
    }


    /**
     * 游标查询,返回类型，当前实体类
     *
     * @param consumer
     * @return
     */
    default <T> Cursor<T> cursor(Consumer<Where> consumer) {
        Where where = Wheres.create();
        consumer.accept(where);
        return QueryChain.of(this, where).cursor(false);
    }


    /**
     * 游标查询
     *
     * @param query
     * @return
     */
    default <R> Cursor<R> cursor(BaseQuery query) {
        return this.cursor(query, true);
    }


    /**
     * 游标查询
     *
     * @param query
     * @param optimize 是否优化
     * @return
     */
    default <R> Cursor<R> cursor(BaseQuery query, boolean optimize) {
        return this.$cursor(new SQLCmdQueryContext(query, optimize));
    }


    /**
     * count查询
     *
     * @param consumer
     * @return
     */
    default Integer count(Consumer<Where> consumer) {
        Where where = Wheres.create();
        consumer.accept(where);
        return QueryChain.of(this, where).count();
    }

    /**
     * count查询
     *
     * @param query
     * @return
     */
    default Integer count(BaseQuery query) {
        query.setReturnType(Integer.TYPE);
        return this.$count(new SQLCmdCountQueryContext(query, false));
    }


    /**
     * 分页查询
     *
     * @param consumer
     * @param pager
     * @return
     */
    default Pager<T> paging(Consumer<Where> consumer, Pager<T> pager) {
        Where where = Wheres.create();
        consumer.accept(where);
        return QueryChain.of(this, where).paging(pager);
    }

    /**
     * 分页查询
     *
     * @param query
     * @param pager
     * @return
     */
    default <R> Pager<R> paging(BaseQuery query, Pager<R> pager) {
        if (pager.isExecuteCount()) {
            Class returnType = query.getReturnType();
            query.setReturnType(Integer.TYPE);
            Integer count = this.$countFromQuery(new SQLCmdCountFromQueryContext(query, pager.isOptimize()));
            query.setReturnType(returnType);

            pager.setTotal(Optional.of(count).orElse(0));
            if (pager.getTotal() < 1) {
                pager.setResults(Collections.emptyList());
                return pager;
            }
        }
        query.limit(pager.getOffset(), pager.getSize());
        pager.setResults(this.list(query, pager.isOptimize()));
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
    <R> R $get(SQLCmdQueryContext queryContext, RowBounds rowBounds);

    /**
     * @param insertContext
     * @return
     * @see MybatisSQLProvider#save(SQLCmdInsertContext, ProviderContext)
     */
    @InsertProvider(type = MybatisSQLProvider.class, method = MybatisSQLProvider.SAVE_NAME)
    int $save(SQLCmdInsertContext insertContext);


    /**
     * @param insertContext
     * @return
     * @see MybatisSQLProvider#save(SQLCmdInsertContext, ProviderContext)
     */
    @InsertProvider(type = MybatisSQLProvider.class, method = MybatisSQLProvider.SAVE_NAME)
    int $saveEntity(EntityInsertContext insertContext);


    /**
     * @param insertContext
     * @return
     * @see MybatisSQLProvider#save(SQLCmdInsertContext, ProviderContext)
     */
    @InsertProvider(type = MybatisSQLProvider.class, method = MybatisSQLProvider.SAVE_NAME)
    int $saveModel(ModelInsertContext insertContext);

    /**
     * @param updateContext
     * @return
     * @see MybatisSQLProvider#update(SQLCmdUpdateContext, ProviderContext)
     */
    @UpdateProvider(type = MybatisSQLProvider.class, method = MybatisSQLProvider.UPDATE_NAME)
    int $update(SQLCmdUpdateContext updateContext);


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
     * @see MybatisSQLProvider#cmdQuery(SQLCmdQueryContext, ProviderContext)
     */
    @SelectProvider(type = MybatisSQLProvider.class, method = MybatisSQLProvider.QUERY_NAME)
    <T> List<T> $list(SQLCmdQueryContext queryContext);

    /**
     * 游标查询
     *
     * @param queryContext
     * @return
     * @see MybatisSQLProvider#cmdQuery(SQLCmdQueryContext, ProviderContext)
     */
    @SelectProvider(type = MybatisSQLProvider.class, method = MybatisSQLProvider.QUERY_NAME)
    <T> Cursor<T> $cursor(SQLCmdQueryContext queryContext);

    /**
     * count查询
     *
     * @param queryContext
     * @return
     * @see MybatisSQLProvider#cmdCount(SQLCmdCountQueryContext, ProviderContext)
     */
    @SelectProvider(type = MybatisSQLProvider.class, method = MybatisSQLProvider.COUNT_NAME)
    Integer $count(SQLCmdCountQueryContext queryContext);

    /**
     * count查询 - 从query中
     *
     * @param queryContext
     * @return
     * @see MybatisSQLProvider#countFromQuery(SQLCmdCountFromQueryContext, ProviderContext)
     */
    @SelectProvider(type = MybatisSQLProvider.class, method = MybatisSQLProvider.QUERY_COUNT_NAME)
    Integer $countFromQuery(SQLCmdCountFromQueryContext queryContext);
}
