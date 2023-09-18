package org.mybatis.mp.core.mybatis.configuration;


import org.apache.ibatis.builder.annotation.ProviderSqlSource;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.UnknownTypeHandler;
import org.mybatis.mp.core.db.reflect.ResultTables;
import org.mybatis.mp.core.mybatis.mapper.MapperTables;
import org.mybatis.mp.core.mybatis.provider.SQLCmdQueryContext;
import org.mybatis.mp.core.mybatis.provider.MybatisSQLProvider;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class MybatisConfiguration extends Configuration {

    /**
     * 列名否是下划线命名
     */
    private boolean columnUnderline = true;

    /**
     * 表名否是下划线命名
     */
    private boolean tableUnderline = true;

    public MybatisConfiguration(Environment environment) {
        super(environment);
    }

    @Override
    public ParameterHandler newParameterHandler(MappedStatement mappedStatement, Object parameterObject, BoundSql boundSql) {
        if (parameterObject instanceof SQLCmdQueryContext) {
            return (ParameterHandler) interceptorChain.pluginAll(new SQLCmdParamHandler((SQLCmdQueryContext) parameterObject));
        }
        return super.newParameterHandler(mappedStatement, parameterObject, boundSql);
    }

    @Override
    public ResultSetHandler newResultSetHandler(Executor executor, MappedStatement mappedStatement, RowBounds rowBounds, ParameterHandler parameterHandler, ResultHandler resultHandler, BoundSql boundSql) {
        if (!mappedStatement.getResultMaps().isEmpty() && mappedStatement.getResultMaps().get(0).getType() == Object.class) {
            ResultSetHandler resultSetHandler = new MpResultSetHandler(executor, mappedStatement, parameterHandler, resultHandler, boundSql, rowBounds);
            return (ResultSetHandler) interceptorChain.pluginAll(resultSetHandler);
        }
        return super.newResultSetHandler(executor, mappedStatement, rowBounds, parameterHandler, resultHandler, boundSql);
    }

    @Override
    public void addMappedStatement(MappedStatement ms) {
        if (ms.getSqlSource() instanceof ProviderSqlSource) {
            MetaObject metaObject = newMetaObject(ms.getSqlSource());
            Class providerType = (Class) metaObject.getValue("providerType");
            if (providerType == MybatisSQLProvider.class) {
                TableIdGeneratorWrapper.addEntityKeyGenerator(ms, getEntityClass(ms));
                if (ms.getParameterMap().getType().isAssignableFrom(SQLCmdQueryContext.class)) {
                    ParameterMapping parameterMapping = new ParameterMapping.Builder(ms.getConfiguration(), "SQLCmdParams", Object.class)
                            .mode(ParameterMode.IN).build();
                    newMetaObject(ms.getParameterMap()).setValue("parameterMappings", Collections.singletonList(parameterMapping));
                }
            }
        }
        ResultMapWrapper.replaceResultMap(ms);
        super.addMappedStatement(ms);
    }


    @Override
    public <T> void addMapper(Class<T> type) {
        if (MapperTables.add(type)) {
            //提前缓存
            ResultTables.load(type, this);
        }
        super.addMapper(type);
    }


    private String getMapperName(MappedStatement ms) {
        return ms.getId().substring(0, ms.getId().lastIndexOf("."));
    }

    private Class getEntityClass(MappedStatement ms) {
        return MapperTables.get(getMapperName(ms));
    }

    public ResultMapping buildResultMapping(Field property, String columnName, JdbcType jdbcType, Class<? extends TypeHandler<?>> typeHandlerClass) {
        return new ResultMapping.Builder(this, property.getName())
                .column(columnName)
                .javaType(property.getType())
                .jdbcType(jdbcType)
                .typeHandler(this.buildTypeHandler(property, typeHandlerClass))
                .build();
    }

    /**
     * 注册 ResultMap
     *
     * @param nestedResultMapId
     * @param property
     * @param resultMappings
     * @return
     */
    public void registerNestedResultMap(String nestedResultMapId, Field property, List<ResultMapping> resultMappings) {
        ResultMap resultMap = new ResultMap.Builder(this, nestedResultMapId, property.getType(), resultMappings, false).build();
        addResultMap(resultMap);
    }

    /**
     * 构建内嵌 ResultMapping
     *
     * @param nestedResultMapId
     * @param property
     * @return
     */
    public ResultMapping buildNestedResultMapping(String nestedResultMapId, Field property) {
        return new ResultMapping.Builder(this, property.getName())
                .nestedResultMapId(nestedResultMapId)
                .build();
    }

    public TypeHandler buildTypeHandler(Field property, Class<? extends TypeHandler<?>> typeHandlerClass) {
        if (typeHandlerClass == UnknownTypeHandler.class) {
            TypeHandler typeHandler = this.getTypeHandlerRegistry().getTypeHandler(property.getType());
            if (Objects.nonNull(typeHandler)) {
                return typeHandler;
            }
        }

        TypeHandler typeHandler = this.getTypeHandlerRegistry().getMappingTypeHandler(typeHandlerClass);
        if (Objects.nonNull(typeHandler)) {
            return typeHandler;
        }

        typeHandler = this.getTypeHandlerRegistry().getInstance(property.getType(), typeHandlerClass);
        this.getTypeHandlerRegistry().register(typeHandler);
        return typeHandler;
    }


    public boolean isColumnUnderline() {
        return columnUnderline;
    }

    public void setColumnUnderline(boolean columnUnderline) {
        this.columnUnderline = columnUnderline;
    }

    public boolean isTableUnderline() {
        return tableUnderline;
    }

    public void setTableUnderline(boolean tableUnderline) {
        this.tableUnderline = tableUnderline;
    }
}



