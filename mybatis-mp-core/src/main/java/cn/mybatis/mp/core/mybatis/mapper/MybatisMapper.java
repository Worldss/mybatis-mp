package cn.mybatis.mp.core.mybatis.mapper;


import cn.mybatis.mp.core.db.reflect.TableInfo;
import cn.mybatis.mp.core.db.reflect.Tables;
import cn.mybatis.mp.core.sql.executor.chain.DeleteChain;
import cn.mybatis.mp.core.sql.executor.chain.QueryChain;

import java.io.Serializable;
import java.util.Objects;

/**
 * 数据库 Mapper
 *
 * @param <T>
 */
public interface MybatisMapper<T> extends BaseMapper<T> {

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
    Class<MybatisMapper<T>> getMapperType();

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


    /**
     * 根据实体类删除
     *
     * @param entity
     * @return
     */
    @Override
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
        Class entityType = entity.getClass();
        return DeleteChain.of(this)
                .delete(entityType)
                .from(entityType)
                .connect(self -> {
                    self.eq(self.$().field(entityType, tableInfo.getIdFieldInfo().getField().getName(), 1), id);
                })
                .execute();
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
        return DeleteChain.of(this)
                .delete(entityType)
                .from(entityType)
                .connect(self -> {
                    self.eq(self.$().field(entityType, tableInfo.getIdFieldInfo().getField().getName(), 1), id);
                })
                .execute();
    }

}
