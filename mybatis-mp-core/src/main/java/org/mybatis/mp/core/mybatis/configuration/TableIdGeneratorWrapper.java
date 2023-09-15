package org.mybatis.mp.core.mybatis.configuration;

import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.mp.core.db.reflect.TableInfo;
import org.mybatis.mp.core.db.reflect.TableInfos;
import org.mybatis.mp.core.mybatis.provider.TableSQLProvider;
import org.mybatis.mp.db.annotations.Id;

import java.util.Collections;
import java.util.Objects;

public class TableIdGeneratorWrapper {
    public static void addEntityKeyGenerator(MappedStatement ms, Class tableClass) {
        if (!ms.getId().endsWith("." + TableSQLProvider.SAVE_NAME)) {
            return;
        }

        TableInfo tableInfo = TableInfos.get(tableClass, (MybatisConfiguration) ms.getConfiguration());
        if (Objects.nonNull(tableInfo.getIdInfo())) {
            KeyGenerator keyGenerator = null;
            Id id = tableInfo.getIdInfo().getIdAnnotation();
            switch (id.value()) {
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
                    String selectKeyId = ms.getId() + SelectKeyGenerator.SELECT_KEY_SUFFIX;
                    SqlSource sqlSource = new StaticSqlSource(ms.getConfiguration(), id.sql());
                    ResultMap selectKeyResultMap = new ResultMap.Builder(ms.getConfiguration(), selectKeyId, tableInfo.getIdInfo().getReflectField().getType(),
                            Collections.emptyList()).build();
                    MappedStatement selectKeyMappedStatement = new MappedStatement.Builder(ms.getConfiguration(), selectKeyId, sqlSource, SqlCommandType.SELECT)
                            .keyProperty(tableInfo.getIdInfo().getReflectField().getName())
                            .resultMaps(Collections.singletonList(selectKeyResultMap))
                            .keyGenerator(NoKeyGenerator.INSTANCE)
                            .useCache(false)
                            .build();
                    keyGenerator = new SelectKeyGenerator(selectKeyMappedStatement, id.executeBefore());
                    break;
                }
                case GENERATOR: {
                    try {
                        keyGenerator = id.generator().newInstance();
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
            msMetaObject.setValue("keyProperties", new String[]{tableInfo.getIdInfo().getReflectField().getName()});
            msMetaObject.setValue("keyColumns", new String[]{tableInfo.getIdInfo().getColumnName()});
        }
    }
}
