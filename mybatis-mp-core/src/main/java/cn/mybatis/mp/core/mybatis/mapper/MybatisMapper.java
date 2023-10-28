package cn.mybatis.mp.core.mybatis.mapper;


import cn.mybatis.mp.core.db.reflect.TableInfo;
import cn.mybatis.mp.core.db.reflect.TableInfos;
import cn.mybatis.mp.core.mybatis.provider.MybatisSQLProvider;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.builder.annotation.ProviderContext;

import java.io.Serializable;
import java.util.Objects;

/**
 * 数据库 Mapper
 *
 * @param <T>
 */
public interface MybatisMapper<T> extends BaseMapper<T> {

    /**
     * @param id
     * @return
     * @see MybatisSQLProvider#getById(Serializable, ProviderContext)
     */
    @SelectProvider(type = MybatisSQLProvider.class, method = MybatisSQLProvider.GET_BY_ID_NAME)
    T getById(Serializable id);


    @Override
    default int delete(T entity) {
        if (Objects.isNull(entity)) {
            return 0;
        }
        TableInfo tableInfo = TableInfos.get(entity.getClass());
        try {
            Serializable id = (Serializable) tableInfo.getIdFieldInfo().getReadFieldInvoker().invoke(entity, null);
            return this.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param id
     * @return
     * @see MybatisSQLProvider#deleteById(Serializable, ProviderContext)
     */
    @DeleteProvider(type = MybatisSQLProvider.class, method = MybatisSQLProvider.DELETE_BY_ID_NAME)
    int deleteById(Serializable id);

}
