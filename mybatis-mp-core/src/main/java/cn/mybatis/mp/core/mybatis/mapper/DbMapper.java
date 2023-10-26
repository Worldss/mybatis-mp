package cn.mybatis.mp.core.mybatis.mapper;


import org.mybatis.mp.core.mybatis.mapper.context.*;

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
