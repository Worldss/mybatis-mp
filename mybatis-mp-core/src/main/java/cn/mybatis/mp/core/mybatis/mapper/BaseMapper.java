package cn.mybatis.mp.core.mybatis.mapper;

import cn.mybatis.mp.core.db.reflect.TableInfo;
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

    default Where where(Consumer<Where> consumer) {
        Where where = Wheres.create();
        consumer.accept(where);
        return where;
    }

    /**
     * 获取实体类的type
     *
     * @return 当前实体类
     */
    Class<T> getEntityType();

    /**
     * 获取当前mapper类的type
     *
     * @return 当前mapper的class
     */
    Class<? extends BaseMapper<T>> getMapperType();

    /**
     * 获取表信息
     *
     * @return
     */
    TableInfo getTableInfo();

    /**
     * 单个查询
     *
     * @param consumer where consumer
     * @return 当个当前实体
     */
    default T get(Consumer<Where> consumer) {
        return QueryChain.of(this, where(consumer)).get(false);
    }

    /**
     * 是否存在
     *
     * @param consumer where consumer
     * @return 是否存在
     */
    default boolean exists(Consumer<Where> consumer) {
        return QueryChain.of(this, where(consumer)).exists(false);
    }

    /**
     * 动态查询 返回单个当前实体
     *
     * @param query
     * @return 单个当前实体
     */

    default <R> R get(BaseQuery query) {
        return this.get(query, true);
    }

    /**
     * 动态查询
     *
     * @param query
     * @param optimize 是否优化
     * @param <R>
     * @return 返回单个当前实体
     */
    default <R> R get(BaseQuery query, boolean optimize) {
        query.limit(1);
        return this.$get(new SQLCmdQueryContext(query, optimize), new RowBounds(0, 1));
    }

    /**
     * 是否存在
     *
     * @param query
     * @return 是否存在
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
     * @return 影响条数
     */
    default int save(T entity) {
        return this.$saveEntity(new EntityInsertContext(entity));
    }

    /**
     * 多个保存，非批量行为
     *
     * @param list
     * @return 插入条数
     */
    default int save(List<T> list) {
        int cnt = 0;
        for (T entity : list) {
            cnt += this.save(entity);
        }
        return cnt;
    }

    /**
     * 使用数据库原生方式批量插入
     * 一次最好在100条内
     * <p>
     * 会自动加入 主键 租户ID 逻辑删除列 乐观锁
     * 自动设置 默认值,不会忽略NULL值字段
     *
     * @param list
     * @param saveFields 指定那些列插入
     * @return 插入的条数
     */
    default int saveBatch(List<T> list, Getter<T>... saveFields) {
        Set<String> saveFieldSet = new HashSet<>();
        for (Getter<T> column : saveFields) {
            saveFieldSet.add(LambdaUtil.getName(column));
        }
        return this.$save(new EntityBatchInsertContext(list, saveFieldSet));
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
     * @return 影响条数
     */
    default int save(BaseInsert insert) {
        return this.$save(new SQLCmdInsertContext<>(insert));
    }

    /**
     * 实体类修改
     *
     * @param entity
     * @return 影响条数
     */
    default int update(T entity) {
        return this.$update(new EntityUpdateContext(entity));
    }

    /**
     * 多个修改，非批量行为
     *
     * @param list
     * @return 影响条数
     */
    default int update(List<T> list) {
        int cnt = 0;
        for (T entity : list) {
            cnt += this.update(entity);
        }
        return cnt;
    }

    /**
     * 多个修改，非批量行为
     *
     * @param list
     * @return 修改条数
     */
    default int update(List<T> list, Getter<T>... forceUpdateFields) {
        Set<String> forceUpdateFieldsSet = new HashSet<>();
        for (Getter<T> column : forceUpdateFields) {
            forceUpdateFieldsSet.add(LambdaUtil.getName(column));
        }
        int cnt = 0;
        for (T entity : list) {
            cnt += this.$update(new EntityUpdateContext(entity, forceUpdateFieldsSet));
        }
        return cnt;
    }

    /**
     * 实体类修改
     *
     * @param entity
     * @param forceUpdateFields 强制更新指定，解决需要修改为null的需求
     * @return 返回修改条数
     */
    default int update(T entity, Getter<T>... forceUpdateFields) {
        Set<String> forceUpdateFieldsSet = new HashSet<>();
        for (Getter<T> column : forceUpdateFields) {
            forceUpdateFieldsSet.add(LambdaUtil.getName(column));
        }
        return this.$update(new EntityUpdateContext(entity, forceUpdateFieldsSet));
    }


    default int update(T entity, Consumer<Where> consumer) {
        return this.$update(new EntityUpdateWithWhereContext(entity, where(consumer)));
    }

    default int update(T entity, Where where, Getter<T>... forceUpdateFields) {
        Set<String> forceUpdateFieldsSet = new HashSet<>();
        for (Getter getter : forceUpdateFields) {
            forceUpdateFieldsSet.add(LambdaUtil.getName(getter));
        }
        return this.$update(new EntityUpdateWithWhereContext(entity, where, forceUpdateFieldsSet));
    }

    /**
     * model修改 部分字段修改
     *
     * @param model 实体类model
     * @return 修改的条数
     */
    default int update(Model<T> model) {
        return this.$update(new ModelUpdateContext<>(model));
    }

    /**
     * model修改 部分字段修改
     *
     * @param model             实体类model
     * @param forceUpdateFields 强制更新指定，解决需要修改为null的需求
     * @return 修改的条数
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
     * @param update 修改update
     * @return 修改的条数
     */
    default int update(BaseUpdate update) {
        return this.$update(new SQLCmdUpdateContext(update));
    }


    /**
     * 动态批量删除
     *
     * @param consumer where consumer
     * @return 删除的条数
     */
    default int delete(Consumer<Where> consumer) {
        return DeleteChain.of(this, where(consumer)).execute();
    }


    /**
     * 动态删除
     *
     * @param delete 上下文
     * @return 删除条数
     */
    default int delete(BaseDelete delete) {
        return this.$delete(new SQLCmdDeleteContext(delete));
    }

    /**
     * 列表查询,返回类型，当前实体类
     *
     * @param consumer where consumer
     * @return 返回结果列表
     */
    default List<T> list(Consumer<Where> consumer) {
        return this.list(consumer, null);
    }

    default List<T> list(Consumer<Where> consumer, Getter<T>... selectFields) {
        QueryChain queryChain = QueryChain.of(this, where(consumer));
        if (Objects.isNull(selectFields) || selectFields.length < 1) {
            queryChain.select(getEntityType());
        } else {
            queryChain.select(selectFields);
        }
        return queryChain.list(false);
    }

    /**
     * 列表查询
     *
     * @param query 查询query
     * @return 返回结果列表
     */
    default <R> List<R> list(BaseQuery query) {
        return this.list(query, true);
    }


    /**
     * 列表查询
     *
     * @param query    查询query
     * @param optimize 是否优化
     * @return 返回查询列表
     */
    default <R> List<R> list(BaseQuery query, boolean optimize) {
        return this.$list(new SQLCmdQueryContext(query, optimize));
    }


    /**
     * 游标查询,返回类型，当前实体类
     *
     * @param consumer where consumer
     * @return 返回游标
     */
    default Cursor<T> cursor(Consumer<Where> consumer) {
        return QueryChain.of(this, where(consumer)).cursor(false);
    }


    /**
     * 游标查询
     *
     * @param query 查询query
     * @return 游标
     */
    default <R> Cursor<R> cursor(BaseQuery query) {
        return this.cursor(query, true);
    }


    /**
     * 游标查询
     *
     * @param query    查询query
     * @param optimize 是否优化
     * @return 返回游标
     */
    default <R> Cursor<R> cursor(BaseQuery query, boolean optimize) {
        return this.$cursor(new SQLCmdQueryContext(query, optimize));
    }


    /**
     * count查询
     *
     * @param consumer where consumer
     * @return 返回count数
     */
    default Integer count(Consumer<Where> consumer) {
        return QueryChain.of(this, where(consumer)).count();
    }

    /**
     * count查询
     *
     * @param query 上下文
     * @return 返回count 数
     */
    default Integer count(BaseQuery query) {
        query.setReturnType(Integer.TYPE);
        return this.$count(new SQLCmdCountQueryContext(query, false));
    }


    /**
     * 分页查询
     *
     * @param consumer where consumer
     * @param pager    分页参数
     * @return 分页结果
     */
    default Pager<T> paging(Consumer<Where> consumer, Pager<T> pager) {
        return this.paging(consumer, pager, null);
    }


    default Pager<T> paging(Consumer<Where> consumer, Pager<T> pager, Getter<T>... selectFields) {
        QueryChain queryChain = QueryChain.of(this, where(consumer));
        if (Objects.isNull(selectFields) || selectFields.length < 1) {
            queryChain.select(getEntityType());
        } else {
            queryChain.select(selectFields);
        }
        return queryChain.paging(pager);
    }

    /**
     * 分页查询
     *
     * @param query 查询query
     * @param pager 分页参数
     * @return 分页结果
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
     * @param queryContext 上下文
     * @return 返回单个查询
     * @see MybatisSQLProvider#cmdQuery(SQLCmdQueryContext, ProviderContext)
     */
    @SelectProvider(type = MybatisSQLProvider.class, method = "cmdQuery")
    <R> R $get(SQLCmdQueryContext queryContext, RowBounds rowBounds);

    /**
     * @param insertContext 上下文
     * @return 插入的条数
     * @see MybatisSQLProvider#save(SQLCmdInsertContext, ProviderContext)
     */
    @InsertProvider(type = MybatisSQLProvider.class, method = MybatisSQLProvider.SAVE_NAME)
    int $save(SQLCmdInsertContext insertContext);


    /**
     * @param insertContext 上下文
     * @return 返回插入的条数
     * @see MybatisSQLProvider#save(SQLCmdInsertContext, ProviderContext)
     */
    @InsertProvider(type = MybatisSQLProvider.class, method = MybatisSQLProvider.SAVE_NAME)
    int $saveEntity(EntityInsertContext insertContext);


    /**
     * @param insertContext 上下文
     * @return 插入的条数
     * @see MybatisSQLProvider#save(SQLCmdInsertContext, ProviderContext)
     */
    @InsertProvider(type = MybatisSQLProvider.class, method = MybatisSQLProvider.SAVE_NAME)
    int $saveModel(ModelInsertContext insertContext);

    /**
     * @param updateContext 上下文
     * @return 修改的条数
     * @see MybatisSQLProvider#update(SQLCmdUpdateContext, ProviderContext)
     */
    @UpdateProvider(type = MybatisSQLProvider.class, method = MybatisSQLProvider.UPDATE_NAME)
    int $update(SQLCmdUpdateContext updateContext);


    /**
     * @param deleteContext 上下文
     * @return 删除的条数
     * @see MybatisSQLProvider#delete(SQLCmdDeleteContext, ProviderContext)
     */
    @UpdateProvider(type = MybatisSQLProvider.class, method = MybatisSQLProvider.DELETE_NAME)
    int $delete(SQLCmdDeleteContext deleteContext);


    /**
     * 列表查询
     *
     * @param queryContext 上下文
     * @return 返回查询的结果
     * @see MybatisSQLProvider#cmdQuery(SQLCmdQueryContext, ProviderContext)
     */
    @SelectProvider(type = MybatisSQLProvider.class, method = MybatisSQLProvider.QUERY_NAME)
    <R> List<R> $list(SQLCmdQueryContext queryContext);

    /**
     * 游标查询
     *
     * @param queryContext 上下文
     * @return 返回游标
     * @see MybatisSQLProvider#cmdQuery(SQLCmdQueryContext, ProviderContext)
     */
    @SelectProvider(type = MybatisSQLProvider.class, method = MybatisSQLProvider.QUERY_NAME)
    <R> Cursor<R> $cursor(SQLCmdQueryContext queryContext);

    /**
     * count查询
     *
     * @param queryContext 上下文
     * @return 返回count数
     * @see MybatisSQLProvider#cmdCount(SQLCmdCountQueryContext, ProviderContext)
     */
    @SelectProvider(type = MybatisSQLProvider.class, method = MybatisSQLProvider.COUNT_NAME)
    Integer $count(SQLCmdCountQueryContext queryContext);

    /**
     * count查询 - 从query中
     *
     * @param queryContext 上下文
     * @return 返回count数
     * @see MybatisSQLProvider#countFromQuery(SQLCmdCountFromQueryContext, ProviderContext)
     */
    @SelectProvider(type = MybatisSQLProvider.class, method = MybatisSQLProvider.QUERY_COUNT_NAME)
    Integer $countFromQuery(SQLCmdCountFromQueryContext queryContext);
}
