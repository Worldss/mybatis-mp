package cn.mybatis.mp.core.mybatis.mapper;

import cn.mybatis.mp.core.db.reflect.TableFieldInfo;
import cn.mybatis.mp.core.db.reflect.TableInfo;
import cn.mybatis.mp.core.db.reflect.Tables;
import cn.mybatis.mp.core.mybatis.mapper.context.*;
import cn.mybatis.mp.core.mybatis.provider.MybatisSQLProvider;
import cn.mybatis.mp.core.sql.executor.*;
import cn.mybatis.mp.db.Model;
import db.sql.api.Getter;
import db.sql.api.impl.cmd.struct.Where;
import db.sql.api.impl.tookit.LambdaUtil;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.session.RowBounds;

import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;


public interface BaseMapper<T> {

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

    default int update(Model<T> model, Consumer<Where> consumer) {
        Where where = Wheres.create();
        consumer.accept(where);
        return this.$update(new ModelUpdateWithWhereContext(model, where));
    }

    default int update(Model<T> model, Where where, Getter<T>... forceUpdateFields) {
        Set<String> forceUpdateFieldsSet = new HashSet<>();
        for (Getter getter : forceUpdateFields) {
            forceUpdateFieldsSet.add(LambdaUtil.getName(getter));
        }
        return this.$update(new ModelUpdateWithWhereContext(model, where, forceUpdateFieldsSet));
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
     * 实体类删除
     *
     * @param entity
     * @return
     */
    default int delete(T entity) {
        TableInfo tableInfo = Tables.get(entity.getClass());
        try {
            TableFieldInfo idInfo = tableInfo.getIdFieldInfo();
            Delete delete = new Delete().delete(entity.getClass()).from(entity.getClass());
            Serializable id = (Serializable) idInfo.getReadFieldInvoker().invoke(entity, null);
            delete.eq(delete.$().field(entity.getClass(), idInfo.getField().getName(), 1), id);
            return this.delete(delete);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
