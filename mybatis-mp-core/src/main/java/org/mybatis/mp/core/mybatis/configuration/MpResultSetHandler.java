package org.mybatis.mp.core.mybatis.configuration;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.DefaultResultSetHandler;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.mp.core.db.reflect.ResultTableInfo;
import org.mybatis.mp.core.db.reflect.ResultTables;
import org.mybatis.mp.core.db.reflect.TableInfo;
import org.mybatis.mp.core.db.reflect.TableInfos;
import org.mybatis.mp.core.mybatis.provider.SQLCmdQueryContext;
import org.mybatis.mp.db.annotations.ResultTable;
import org.mybatis.mp.db.annotations.Table;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;


public class MpResultSetHandler extends DefaultResultSetHandler {

    public static final String GENERIC_CLASS_KEY = "GENERIC_CLASS";

    private static final Map<String, MappedStatement> CACHE = new ConcurrentHashMap<>(100);

    private static Class getReturnTypeClass(Type[] types) {
        //从后往前找
        for (int i = types.length - 1; i >= 0; i--) {
            Type type = types[i];
            if (!(type instanceof ParameterizedType)) {
                continue;
            }

            Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
            for (Type actualTypeArgument : actualTypeArguments) {
                if (!(actualTypeArgument instanceof Class)) {
                    continue;
                }
                Class actualType = (Class) actualTypeArgument;
                if (actualType.isAnnotationPresent(Table.class)) {
                    return actualType;
                } else if (actualType.isAnnotationPresent(ResultTable.class)) {
                    return actualType;
                }
            }
        }
        return null;
    }

    private static Class getReturnTypeClass(Object object) {
        Class returnTypeClass = null;
        Type type = object.getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            returnTypeClass = getReturnTypeClass(new Type[]{type});
        }
        if (Objects.isNull(returnTypeClass)) {
            returnTypeClass = getReturnTypeClass(object.getClass().getGenericInterfaces());
        }
        return returnTypeClass;
    }


    private static MappedStatement create(MappedStatement ms, BoundSql boundSql) {
        if (ms.getResultMaps().isEmpty() || ms.getResultMaps().get(0).getType() != Object.class) {
            return ms;
        }

        Class returnTypeClass = null;
        if (boundSql.getParameterObject() instanceof Map) {
            Map map = (Map) boundSql.getParameterObject();
            if (map.containsKey(GENERIC_CLASS_KEY)) {
                returnTypeClass = (Class) map.get(GENERIC_CLASS_KEY);
            }
        } else if (boundSql.getParameterObject() instanceof SQLCmdQueryContext) {
            SQLCmdQueryContext queryContext = (SQLCmdQueryContext) boundSql.getParameterObject();
            returnTypeClass = getReturnTypeClass(queryContext.getQuery());
        } else {
            returnTypeClass = getReturnTypeClass(boundSql.getParameterObject());
        }

        if (!Objects.isNull(returnTypeClass)) {
            return create(returnTypeClass, ms);
        }
        return ms;
    }

    private static MappedStatement create(Class returnTypeClass, MappedStatement ms) {
        String id = ms.getId() + "." + returnTypeClass.getName();
        MappedStatement mappedStatement = CACHE.get(id);
        if (!Objects.isNull(mappedStatement)) {
            return mappedStatement;
        }
        ResultMap resultMap = null;
        TableInfo tableInfo = TableInfos.get(returnTypeClass, (MybatisConfiguration) ms.getConfiguration());
        if (!Objects.isNull(tableInfo)) {
            resultMap = new ResultMap.Builder(ms.getConfiguration(), returnTypeClass.getName(), returnTypeClass, tableInfo.getResultMappings(), true).build();
        }

        if (Objects.isNull(tableInfo)) {
            ResultTableInfo resultTableInfo = ResultTables.get(returnTypeClass, (MybatisConfiguration) ms.getConfiguration());
            if (resultTableInfo != null) {
                resultMap = new ResultMap.Builder(ms.getConfiguration(), returnTypeClass.getName(), returnTypeClass, resultTableInfo.getResultMappings(), true).build();
            }
        }

        MappedStatement.Builder msBuilder = new MappedStatement.Builder(ms.getConfiguration(), id, ms.getSqlSource(), ms.getSqlCommandType())
                .resultMaps(Collections.singletonList(resultMap))
                .parameterMap(ms.getParameterMap())
                .useCache(false);

        return msBuilder.build();
    }

    public MpResultSetHandler(Executor executor, MappedStatement mappedStatement, ParameterHandler parameterHandler, ResultHandler<?> resultHandler, BoundSql boundSql, RowBounds rowBounds) {
        super(executor, create(mappedStatement, boundSql), parameterHandler, resultHandler, boundSql, rowBounds);
    }
}
