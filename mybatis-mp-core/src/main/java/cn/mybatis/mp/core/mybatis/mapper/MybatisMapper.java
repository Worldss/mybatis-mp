package cn.mybatis.mp.core.mybatis.mapper;


import cn.mybatis.mp.core.db.reflect.TableInfo;
import cn.mybatis.mp.core.logicDelete.LogicDeleteUtil;
import cn.mybatis.mp.core.mybatis.configuration.MapKeySQLCmdQueryContext;
import cn.mybatis.mp.core.mybatis.mapper.context.*;
import cn.mybatis.mp.core.mybatis.provider.MybatisSQLProvider;
import cn.mybatis.mp.core.sql.executor.*;
import cn.mybatis.mp.core.sql.executor.chain.DeleteChain;
import cn.mybatis.mp.core.sql.executor.chain.QueryChain;
import cn.mybatis.mp.core.util.TableInfoUtil;
import cn.mybatis.mp.core.util.WhereUtil;
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

import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;

/**
 * 数据库 Mapper
 * $ 开头的方法一般不建议去使用
 *
 * @param <T>
 */
public interface MybatisMapper<T> {

    /**
     * 根据ID查询
     *
     * @param id
     * @return 当个当前实体类
     */
    default T getById(Serializable id) {
        TableInfo tableInfo = this.getTableInfo();
        Class entityType = tableInfo.getType();

        QueryChain queryChain = QueryChain.of(this);
        queryChain.from(entityType);
        queryChain.setReturnType(entityType);

        WhereUtil.appendIdWhere(queryChain.$where(), tableInfo, id);

        if (tableInfo.isHasIgnoreField()) {
            queryChain.select(entityType);
        } else {
            queryChain.select(queryChain.$(entityType, 1).$("*"));
        }
        return queryChain.get();
    }

    /**
     * 根据ID查询，只返回指定列
     *
     * @param id
     * @param selectFields select列
     * @return 当个当前实体类
     */
    default T getById(Serializable id, Getter<T>... selectFields) {
        TableInfo tableInfo = this.getTableInfo();
        Class entityType = tableInfo.getType();

        QueryChain queryChain = QueryChain.of(this);
        queryChain.from(entityType);
        queryChain.setReturnType(entityType);

        WhereUtil.appendIdWhere(queryChain.$where(), tableInfo, id);
        queryChain.select(selectFields);
        return queryChain.get();
    }


    /**
     * 根据实体类删除
     *
     * @param entity
     * @return
     */
    default int delete(T entity) {
        if (Objects.isNull(entity)) {
            return 0;
        }
        return this.deleteById(TableInfoUtil.getEntityIdValue(getTableInfo(), entity));
    }

    /**
     * 多个删除
     *
     * @param list
     * @return 修改条数
     */
    default int delete(List<T> list) {
        int length = list.size();
        List<Serializable> ids = new ArrayList<>();
        TableInfo tableInfo = getTableInfo();
        for (int i = 0; i < length; i++) {
            ids.add(TableInfoUtil.getEntityIdValue(tableInfo, list.get(i)));
        }
        return this.deleteByIds(ids);
    }


    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    default int deleteById(Serializable id) {
        return this.delete(where -> {
            WhereUtil.appendIdWhere(where, getTableInfo(), id);
        });
    }

    /**
     * 批量删除多个
     *
     * @param ids
     * @return
     */
    default int deleteByIds(Serializable... ids) {
        if (ids == null || ids.length < 1) {
            throw new RuntimeException("ids array can't be empty");
        }
        return this.delete(where -> {
            WhereUtil.appendIdsWhere(where, getTableInfo(), ids);
        });
    }

    /**
     * 批量删除多个
     *
     * @param ids
     * @return
     */
    default int deleteByIds(List<Serializable> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new RuntimeException("ids list can't be empty");
        }
        return this.delete(where -> {
            WhereUtil.appendIdsWhere(where, getTableInfo(), ids);
        });
    }


    default int delete(Consumer<Where> consumer) {
        Where where = Wheres.create();
        consumer.accept(where);
        if (!where.hasContent()) {
            throw new RuntimeException("delete has on where condition content ");
        }
        TableInfo tableInfo = this.getTableInfo();
        Class entityType = tableInfo.getType();

        if (LogicDeleteUtil.isNeedLogicDelete(tableInfo)) {
            //逻辑删除处理
            return LogicDeleteUtil.logicDelete(this, tableInfo, where);
        }
        return DeleteChain.of(this, where)
                .delete(entityType)
                .from(entityType)
                .execute();
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
    Class<? extends MybatisMapper<T>> getMapperType();

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
        return QueryChain.of(this, WhereUtil.where(consumer)).get(false);
    }

    /**
     * 是否存在
     *
     * @param consumer where consumer
     * @return 是否存在
     */
    default boolean exists(Consumer<Where> consumer) {
        return QueryChain.of(this, WhereUtil.where(consumer)).exists(false);
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
        return this.$update(new EntityUpdateWithWhereContext(entity, WhereUtil.where(consumer)));
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
        return QueryChain.of(this, WhereUtil.where(consumer)).list(false);
    }

    default List<T> list(Consumer<Where> consumer, Getter<T>... selectFields) {
        QueryChain queryChain = QueryChain.of(this, WhereUtil.where(consumer));
        queryChain.select(getEntityType());
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
        return QueryChain.of(this, WhereUtil.where(consumer)).cursor(false);
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
        return QueryChain.of(this, WhereUtil.where(consumer)).count();
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
        pager.setOptimize(false);
        return QueryChain.of(this, WhereUtil.where(consumer)).paging(pager);
    }


    default Pager<T> paging(Consumer<Where> consumer, Pager<T> pager, Getter<T>... selectFields) {
        pager.setOptimize(false);
        QueryChain queryChain = QueryChain.of(this, WhereUtil.where(consumer));
        queryChain.select(selectFields);
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
     * 将结果转成map
     *
     * @param mapKey 指定的map的key属性
     * @param query  查询对象
     * @param <K>    map的key
     * @param <V>    map的value
     * @return
     */
    default <K, V, G> Map<K, V> mapWithKey(Getter<G> mapKey, BaseQuery query) {
        return this.mapWithKey(mapKey, query, true);
    }

    /**
     * 根据多个id查询结果转map
     *
     * @param mapKey map的key
     * @param ids    ids
     * @param <K>    map的key的类型
     * @return 一个map
     */
    default <K> Map<K, T> mapWithKey(Getter<T> mapKey, Serializable... ids) {
        return this.mapWithKey(mapKey, Arrays.asList(ids));
    }

    /**
     * 根据多个id查询结果转map
     *
     * @param mapKey map的key
     * @param ids    ids
     * @param <K>    map的key的类型
     * @return 一个map
     */
    default <K> Map<K, T> mapWithKey(Getter<T> mapKey, List<Serializable> ids) {
        return this.mapWithKey(mapKey, where -> {
            WhereUtil.appendIdsWhere(where, getTableInfo(), ids);
        });
    }

    /**
     * 根据多个id查询结果转map
     *
     * @param mapKey map的key
     * @param ids    ids
     * @param <K>    map的key的类型
     * @return 一个map
     */
    default <K> Map<K, T> mapWithKey(String mapKey, Serializable... ids) {
        return this.mapWithKey(mapKey, Arrays.asList(ids));
    }

    /**
     * 根据多个id查询结果转map
     *
     * @param mapKey map的key
     * @param ids    ids
     * @param <K>    map的key的类型
     * @return 一个map
     */
    default <K> Map<K, T> mapWithKey(String mapKey, List<Serializable> ids) {
        return this.mapWithKey(mapKey, where -> {
            WhereUtil.appendIdsWhere(where, getTableInfo(), ids);
        });
    }

    /**
     * 根据多个id查询结果转map
     *
     * @param mapKey   map的key
     * @param consumer where consumer
     * @param <K>      map的key的类型
     * @return 一个map
     */
    default <K> Map<K, T> mapWithKey(Getter<T> mapKey, Consumer<Where> consumer) {
        return QueryChain.of(this, WhereUtil.where(consumer)).mapWithKey(mapKey, false);
    }

    /**
     * 根据多个id查询结果转map
     *
     * @param mapKey   map的key
     * @param consumer where consumer
     * @param <K>      map的key的类型
     * @return 一个map
     */
    default <K> Map<K, T> mapWithKey(String mapKey, Consumer<Where> consumer) {
        return this.mapWithKey(mapKey, QueryChain.of(this, WhereUtil.where(consumer)), false);
    }

    /**
     * 将结果转成map
     *
     * @param mapKey   指定的map的key属性
     * @param query    查询对象
     * @param optimize 是否优化sql
     * @param <K>      map的key
     * @param <V>      map的value
     * @return
     */
    default <K, V, G> Map<K, V> mapWithKey(Getter<G> mapKey, BaseQuery query, boolean optimize) {
        return this.mapWithKey(LambdaUtil.getName(mapKey), query, optimize);
    }

    /**
     * 将结果转成map
     *
     * @param mapKey 指定的map的key属性
     * @param query  查询对象
     * @param <K>    map的key
     * @param <V>    map的value
     * @return
     */
    default <K, V, G> Map<K, V> mapWithKey(String mapKey, BaseQuery query) {
        return this.mapWithKey(mapKey, query, true);
    }

    /**
     * 将结果转成map
     *
     * @param mapKey   指定的map的key属性
     * @param query    查询对象
     * @param optimize 是否优化sql
     * @param <K>      map的key
     * @param <V>      map的value
     * @return
     */
    default <K, V, G> Map<K, V> mapWithKey(String mapKey, BaseQuery query, boolean optimize) {
        return this.$mapWithKey(new MapKeySQLCmdQueryContext(mapKey, query, optimize));
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


    /**
     * 将结果转成map
     *
     * @param queryContext
     * @param <K>
     * @param <V>
     * @return
     */
    @SelectProvider(type = MybatisSQLProvider.class, method = MybatisSQLProvider.QUERY_NAME)
    <K, V> Map<K, V> $mapWithKey(MapKeySQLCmdQueryContext queryContext);

}
