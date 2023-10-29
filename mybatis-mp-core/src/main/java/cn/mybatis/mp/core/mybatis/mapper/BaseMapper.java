package cn.mybatis.mp.core.mybatis.mapper;

import cn.mybatis.mp.core.db.reflect.TableFieldInfo;
import cn.mybatis.mp.core.db.reflect.TableInfo;
import cn.mybatis.mp.core.db.reflect.Tables;
import cn.mybatis.mp.core.mybatis.mapper.context.*;
import cn.mybatis.mp.core.mybatis.provider.MybatisSQLProvider;
import cn.mybatis.mp.core.sql.executor.Delete;
import cn.mybatis.mp.core.sql.executor.Insert;
import cn.mybatis.mp.core.sql.executor.Query;
import cn.mybatis.mp.core.sql.executor.Update;
import cn.mybatis.mp.db.Model;
import db.sql.api.Getter;
import db.sql.core.api.tookit.LambdaUtil;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.session.RowBounds;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BaseMapper<T> {

    /**
     * 动态查询 返回单个当前实体
     *
     * @param query
     * @return
     */

    default <R> R get(Query query) {
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
     * model插入 部分字段插入
     *
     * @param model
     * @return
     */
    default int save(Model<T> model) {
        return this.$$save(new ModelInsertContext<>(model));
    }

    /**
     * 动态插入
     *
     * @param insert
     * @return
     */
    default int save(Insert insert) {
        return this.$$$save(new SQLCmdInsertContext<>(insert));
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
        for (Getter<T> getter : forceUpdateFields) {
            forceUpdateFieldsSet.add(LambdaUtil.getName(getter));
        }
        return this.$update(new EntityUpdateContext(entity, forceUpdateFieldsSet));
    }


    /**
     * model插入 部分字段修改
     *
     * @param model
     * @return
     */
    default int update(Model<T> model) {
        return this.$$update(new ModelUpdateContext<>(model));
    }

    /**
     * model插入 部分字段修改
     *
     * @param model
     * @param forceUpdateFields 强制更新指定，解决需要修改为null的需求
     * @return
     */
    default <M extends Model<T>> int update(Model<T> model, Getter<M>... forceUpdateFields) {
        Set<String> forceUpdateFieldsSet = new HashSet<>();
        for (Getter<M> getter : forceUpdateFields) {
            forceUpdateFieldsSet.add(LambdaUtil.getName(getter));
        }
        return this.$$update(new ModelUpdateContext<>(model, forceUpdateFieldsSet));
    }

    /**
     * 动态修改
     *
     * @param update
     * @return
     */
    default int update(Update update) {
        return this.$$update(new SQLCmdUpdateContext(update));
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
    default int delete(Delete delete) {
        return this.$delete(new SQLCmdDeleteContext(delete));
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
    default <R> Pager<R> paging(Query query, Pager<R> pager) {
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
     * @see MybatisSQLProvider#save(SQLCmdInsertContext, ProviderContext)
     */
    @InsertProvider(type = MybatisSQLProvider.class, method = MybatisSQLProvider.SAVE_NAME)
    int $save(EntityInsertContext<T> insertContext);

    @InsertProvider(type = MybatisSQLProvider.class, method = MybatisSQLProvider.SAVE_NAME)
    int $$save(ModelInsertContext<Model<T>> insertContext);

    @InsertProvider(type = MybatisSQLProvider.class, method = MybatisSQLProvider.SAVE_NAME)
    int $$$save(SQLCmdInsertContext insertContext);

    /**
     * @param updateContext
     * @return
     * @see MybatisSQLProvider#update(SQLCmdUpdateContext, ProviderContext)
     */
    @UpdateProvider(type = MybatisSQLProvider.class, method = MybatisSQLProvider.UPDATE_NAME)
    int $update(EntityUpdateContext<T> updateContext);

    @UpdateProvider(type = MybatisSQLProvider.class, method = MybatisSQLProvider.UPDATE_NAME)
    int $$update(SQLCmdUpdateContext updateContext);


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
     * @see MybatisSQLProvider#countCmdQuery(SQLCmdQueryContext, ProviderContext)
     */
    @SelectProvider(type = MybatisSQLProvider.class, method = "countCmdQuery")
    Integer $count(SQLCmdQueryContext queryContext);
}
