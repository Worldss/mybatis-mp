package cn.mybatis.mp.core.mybatis.configuration;


import cn.mybatis.mp.core.db.reflect.ResultMaps;
import cn.mybatis.mp.core.db.reflect.TableIds;
import cn.mybatis.mp.core.mybatis.mapper.MapperTables;
import cn.mybatis.mp.core.mybatis.mapper.context.SQLCmdContext;
import org.apache.ibatis.executor.*;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.UnknownTypeHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;


public class MybatisConfiguration extends Configuration {

    public MybatisConfiguration() {
        super();
    }

    public MybatisConfiguration(Environment environment) {
        super(environment);
    }

    public void printBanner() {
        try (BufferedReader reader = new BufferedReader(Resources.getResourceAsReader("mybatis-mp.banner"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ParameterHandler newParameterHandler(MappedStatement mappedStatement, Object parameterObject, BoundSql boundSql) {
        if (parameterObject instanceof SQLCmdContext && !mappedStatement.getId().endsWith(SelectKeyGenerator.SELECT_KEY_SUFFIX)) {
            return (ParameterHandler) interceptorChain.pluginAll(new PreparedParameterHandler(this, (SQLCmdContext) parameterObject));
        }
        return super.newParameterHandler(mappedStatement, parameterObject, boundSql);
    }

    @Override
    public ResultSetHandler newResultSetHandler(Executor executor, MappedStatement mappedStatement, RowBounds rowBounds, ParameterHandler parameterHandler, ResultHandler resultHandler, BoundSql boundSql) {
        ResultSetHandler resultSetHandler = new MybatisResultSetHandler(executor, mappedStatement, parameterHandler, resultHandler, boundSql, rowBounds);
        return (ResultSetHandler) interceptorChain.pluginAll(resultSetHandler);
    }

    @Override
    public void addMappedStatement(MappedStatement ms) {
        TableIdGeneratorWrapper.addEntityKeyGenerator(ms);
        ResultMapWrapper.replaceResultMap(ms);
        super.addMappedStatement(ms);
    }

    @Override
    public <T> void addMapper(Class<T> type) {
        if (MapperTables.add(type)) {
            //提前缓存
            Class entity = MapperTables.get(type);
            ResultMaps.getResultMappings(this, entity);
            TableIds.get(this, entity);
        }
        super.addMapper(type);
    }

    public ResultMapping buildResultMapping(Field property, String columnName, JdbcType jdbcType, Class<? extends TypeHandler<?>> typeHandlerClass) {
        return new ResultMapping.Builder(this, property.getName())
                .column(columnName)
                .javaType(property.getType())
                .jdbcType(jdbcType)
                .typeHandler(this.buildTypeHandler(property.getType(), typeHandlerClass))
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

    public TypeHandler buildTypeHandler(Class type, Class<? extends TypeHandler<?>> typeHandlerClass) {
        if (typeHandlerClass == UnknownTypeHandler.class) {
            TypeHandler typeHandler = this.getTypeHandlerRegistry().getTypeHandler(type);
            if (Objects.nonNull(typeHandler)) {
                return typeHandler;
            }
        }

        TypeHandler typeHandler = this.getTypeHandlerRegistry().getMappingTypeHandler(typeHandlerClass);
        if (Objects.nonNull(typeHandler)) {
            return typeHandler;
        }

        typeHandler = this.getTypeHandlerRegistry().getInstance(type, typeHandlerClass);
        return typeHandler;
    }

    @Override
    public Executor newExecutor(Transaction transaction, ExecutorType executorType) {
        executorType = executorType == null ? this.defaultExecutorType : executorType;
        Executor executor;
        if (ExecutorType.BATCH == executorType) {
            executor = new BatchExecutor(this, transaction);
        } else if (ExecutorType.REUSE == executorType) {
            executor = new ReuseExecutor(this, transaction);
        } else {
            executor = new SimpleExecutor(this, transaction);
        }
        executor = new MybatisExecutor(executor);
        if (this.cacheEnabled) {
            executor = new CachingExecutor(executor);
        }
        return (Executor) this.interceptorChain.pluginAll(executor);
    }
}



