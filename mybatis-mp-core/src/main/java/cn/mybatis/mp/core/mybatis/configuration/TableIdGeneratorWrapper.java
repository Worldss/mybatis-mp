package cn.mybatis.mp.core.mybatis.configuration;

import cn.mybatis.mp.core.db.reflect.TableIds;
import cn.mybatis.mp.core.db.reflect.TableInfo;
import cn.mybatis.mp.core.db.reflect.TableInfos;
import cn.mybatis.mp.core.mybatis.mapper.MapperTables;
import cn.mybatis.mp.core.mybatis.mapper.context.EntityInsertContext;
import cn.mybatis.mp.db.annotations.TableId;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.builder.annotation.ProviderSqlSource;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Collections;
import java.util.Objects;

public class TableIdGeneratorWrapper {

    private static String getMapperName(MappedStatement ms) {
        return ms.getId().substring(0, ms.getId().lastIndexOf("."));
    }

    private static Class getEntityClass(MappedStatement ms) {
        return MapperTables.get(getMapperName(ms));
    }

    public static void addEntityKeyGenerator(MappedStatement ms) {
        if (ms.getSqlCommandType() != SqlCommandType.INSERT || !(ms.getSqlSource() instanceof ProviderSqlSource) || ms.getParameterMap().getType() != EntityInsertContext.class) {
            return;
        }
        String selectKeyId = ms.getId() + SelectKeyGenerator.SELECT_KEY_SUFFIX;
        Class entityClass = getEntityClass(ms);
        if (entityClass == null) {
            //可能是动态的 所以无法获取entityClass
            return;
        }
        TableInfo tableInfo = TableInfos.get(entityClass);
        if (Objects.nonNull(tableInfo.getIdFieldInfo())) {
            KeyGenerator keyGenerator = null;
            TableId tableId = TableIds.get(ms.getConfiguration(), entityClass);
            switch (tableId.value()) {
                //数据库默认自增
                case AUTO: {
                    keyGenerator = Jdbc3KeyGenerator.INSTANCE;
                    break;
                }
                //自己输入
                case NONE: {
                    keyGenerator = NoKeyGenerator.INSTANCE;
                    break;
                }
                //序列
                case SQL: {

                    SqlSource sqlSource = new StaticSqlSource(ms.getConfiguration(), tableId.sql());
                    ResultMap selectKeyResultMap = new ResultMap.Builder(ms.getConfiguration(), selectKeyId, tableInfo.getIdFieldInfo().getField().getType(),
                            Collections.emptyList(), false).build();
                    MappedStatement selectKeyMappedStatement = new MappedStatement.Builder(ms.getConfiguration(), selectKeyId, sqlSource, SqlCommandType.SELECT)
                            .keyProperty("id")
                            .resultMaps(Collections.singletonList(selectKeyResultMap))
                            .keyGenerator(NoKeyGenerator.INSTANCE)
                            .useCache(false)
                            .build();
                    keyGenerator = new SelectKeyGenerator(selectKeyMappedStatement, tableId.executeBefore());
                    break;
                }
                case GENERATOR: {
                    try {
                        keyGenerator = tableId.generator().newInstance();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;
                }
                default: {
                    throw new RuntimeException("Not supported");
                }
            }
            MetaObject msMetaObject = ms.getConfiguration().newMetaObject(ms);
            msMetaObject.setValue("keyGenerator", keyGenerator);
            msMetaObject.setValue("keyProperties", new String[]{"id"});
            msMetaObject.setValue("keyColumns", new String[]{tableInfo.getIdFieldInfo().getColumnName()});

            ms.getConfiguration().addKeyGenerator(selectKeyId, keyGenerator);
        }
    }
}
