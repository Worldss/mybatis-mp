package cn.mybatis.mp.core.mybatis.mapper;


import cn.mybatis.mp.core.db.reflect.TableInfo;
import cn.mybatis.mp.core.db.reflect.Tables;
import cn.mybatis.mp.core.logicDelete.LogicDeleteUtil;
import cn.mybatis.mp.core.sql.executor.Wheres;
import cn.mybatis.mp.core.sql.executor.chain.DeleteChain;
import cn.mybatis.mp.core.sql.executor.chain.QueryChain;
import db.sql.api.impl.cmd.struct.Where;

import java.io.Serializable;
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
        TableInfo tableInfo = Tables.get(entityType);
        if (tableInfo.getIdFieldInfo() == null) {
            throw new RuntimeException("Not Supported");
        }

        return QueryChain.of(this)
                .select(entityType)
                .from(entityType)
                .connect(self -> {
                    self.eq(self.$().field(entityType, tableInfo.getIdFieldInfo().getField().getName(), 1), id);
                })
                .setReturnType(entityType)
                .get();
    }


    default int $delete(Class<T> entityType, Serializable id) {
        return this.$delete(entityType, Tables.get(entityType), id);
    }

    default int $delete(Class entityType, TableInfo tableInfo, Serializable id) {
        if (LogicDeleteUtil.isNeedLogicDelete(tableInfo)) {
            //逻辑删除处理
            return LogicDeleteUtil.logicDelete(this, entityType, tableInfo, id);
        }
        return DeleteChain.of(this)
                .delete(entityType)
                .from(entityType)
                .connect(self -> {
                    self.eq(self.$().field(entityType, tableInfo.getIdFieldInfo().getField().getName(), 1), id);
                })
                .execute();
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
        TableInfo tableInfo = Tables.get(entity.getClass());
        if (tableInfo.getIdFieldInfo() == null) {
            throw new RuntimeException("Not Supported");
        }
        Serializable id;
        try {
            id = (Serializable) tableInfo.getIdFieldInfo().getReadFieldInvoker().invoke(entity, null);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return this.$delete(entity.getClass(), tableInfo, id);
    }

    /**
     * 多个删除，非批量行为
     *
     * @param list
     * @return 修改条数
     */
    default int delete(List<T> list) {
        Class entityType = getEntityType();
        TableInfo tableInfo = Tables.get(entityType);
        if (tableInfo.getIdFieldInfo() == null) {
            throw new RuntimeException("Not Supported");
        }
        int cnt = 0;
        for (T entity : list) {
            Serializable id;
            try {
                id = (Serializable) tableInfo.getIdFieldInfo().getReadFieldInvoker().invoke(entity, null);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            cnt += this.$delete(entityType, tableInfo, id);
        }
        return cnt;
    }

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    default int deleteById(Serializable id) {
        Class entityType = getEntityType();
        TableInfo tableInfo = Tables.get(entityType);
        if (tableInfo.getIdFieldInfo() == null) {
            throw new RuntimeException("Not Supported");
        }

        return this.$delete(entityType, tableInfo, id);
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
        Class entityType = getEntityType();
        TableInfo tableInfo = Tables.get(entityType);
        if (tableInfo.getIdFieldInfo() == null) {
            throw new RuntimeException("Not Supported");
        }
        return DeleteChain.of(this)
                .connect(self -> {
                    self.in(self.$().field(entityType, tableInfo.getIdFieldInfo().getField().getName(), 1), ids);
                })
                .execute();
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
        Class entityType = getEntityType();
        TableInfo tableInfo = Tables.get(entityType);
        if (tableInfo.getIdFieldInfo() == null) {
            throw new RuntimeException("Not Supported");
        }
        return DeleteChain.of(this)
                .connect(self -> {
                    self.in(self.$().field(entityType, tableInfo.getIdFieldInfo().getField().getName(), 1), ids);
                })
                .execute();
    }

    @Override
    default int delete(Consumer<Where> consumer) {
        Where where = Wheres.create();
        consumer.accept(where);
        if (!where.hasContent()) {
            throw new RuntimeException("delete has on where condition content ");
        }
        Class entityType = getEntityType();
        TableInfo tableInfo = Tables.get(entityType);
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
