package cn.mybatis.mp.core.mybatis.mapper;


import cn.mybatis.mp.core.db.reflect.TableInfo;
import cn.mybatis.mp.core.db.reflect.Tables;
import cn.mybatis.mp.core.logicDelete.LogicDeleteUtil;
import cn.mybatis.mp.core.sql.executor.Wheres;
import cn.mybatis.mp.core.sql.executor.chain.DeleteChain;
import cn.mybatis.mp.core.sql.executor.chain.QueryChain;
import db.sql.api.impl.cmd.CmdFactory;
import db.sql.api.impl.cmd.struct.Where;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * 数据库 Mapper
 *
 * @param <T>
 */
public interface MybatisMapper<T> extends BaseMapper<T> {

    default T getById(Serializable id) {
        Class entityType = this.getEntityType();
        TableInfo tableInfo = this.getTableInfo();
        this.$checkId(tableInfo);
        return QueryChain.of(this)
                .select(entityType)
                .from(entityType)
                .connect(self -> self.eq(self.$().field(entityType, tableInfo.getIdFieldInfo().getField().getName(), 1), id))
                .setReturnType(entityType)
                .get();
    }

    default void $checkId(TableInfo tableInfo) {
        if (tableInfo.getIdFieldInfo() == null) {
            throw new RuntimeException("Not Supported");
        }
    }


    default Serializable $getIdFromEntity(T entity) {
        if (entity.getClass() != getEntityType()) {
            throw new RuntimeException("Not Supported");
        }
        TableInfo tableInfo = this.getTableInfo();
        this.$checkId(tableInfo);
        Serializable id;
        try {
            id = (Serializable) tableInfo.getIdFieldInfo().getReadFieldInvoker().invoke(entity, null);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return id;
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
        return this.deleteById(this.$getIdFromEntity(entity));
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
        for (int i = 0; i < length; i++) {
            ids.add(this.$getIdFromEntity(list.get(i)));
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
            Class entityType = getEntityType();
            TableInfo tableInfo = this.getTableInfo();
            this.$checkId(tableInfo);
            CmdFactory $ = where.getConditionFactory().getCmdFactory();
            where.eq($.field(entityType, tableInfo.getIdFieldInfo().getField().getName(), 1), id);
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
            Class entityType = getEntityType();
            TableInfo tableInfo = this.getTableInfo();
            this.$checkId(tableInfo);
            CmdFactory $ = where.getConditionFactory().getCmdFactory();
            where.in($.field(entityType, tableInfo.getIdFieldInfo().getField().getName(), 1), ids);
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
            Class entityType = getEntityType();
            TableInfo tableInfo = this.getTableInfo();
            this.$checkId(tableInfo);
            CmdFactory $ = where.getConditionFactory().getCmdFactory();
            where.in($.field(entityType, tableInfo.getIdFieldInfo().getField().getName(), 1), ids);
        });
    }

    @Override
    default int delete(Consumer<Where> consumer) {
        Where where = Wheres.create();
        consumer.accept(where);
        if (!where.hasContent()) {
            throw new RuntimeException("delete has on where condition content ");
        }
        Class entityType = getEntityType();
        TableInfo tableInfo = this.getTableInfo();
        if (LogicDeleteUtil.isNeedLogicDelete(tableInfo)) {
            //逻辑删除处理
            return LogicDeleteUtil.logicDelete(this, entityType, tableInfo, where);
        }
        return DeleteChain.of(this, where)
                .delete(entityType)
                .from(entityType)
                .execute();
    }

}
