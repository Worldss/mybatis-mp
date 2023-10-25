package org.mybatis.mp.core.mybatis.mapper;


import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.mp.core.db.reflect.FieldInfo;
import org.mybatis.mp.core.db.reflect.TableInfo;
import org.mybatis.mp.core.db.reflect.TableInfos;
import org.mybatis.mp.core.mybatis.configuration.MybatisConfiguration;
import org.mybatis.mp.core.mybatis.mapper.context.*;
import org.mybatis.mp.core.mybatis.provider.MybatisSQLProvider;
import org.mybatis.mp.core.sql.executor.Delete;
import org.mybatis.mp.core.sql.executor.Query;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 数据库 Mapper
 */
//public interface DbMapper extends BaseMapper {
//
//    default <T> T getById(Class<T> entity, Serializable id) {
//        TableInfo tableInfo = TableInfos.get(entity);
//        if (tableInfo == null) {
//
//            tableInfo = TableInfos.load(entity, MybatisConfiguration.INSTANCE);
//        }
//        try {
//            FieldInfo idInfo = tableInfo.getIdInfo();
//            Query query = new Query().select(entity).from(entity);
//            query.eq(query.$().field(entity, idInfo.getReflectField().getName(), 1), id);
//            query.setReturnType(entity);
//            return (T) this.get(query);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
