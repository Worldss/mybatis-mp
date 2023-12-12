package cn.mybatis.mp.core.mybatis.configuration;


import cn.mybatis.mp.core.db.reflect.TableIds;
import cn.mybatis.mp.core.mybatis.mapper.BaseMapper;
import cn.mybatis.mp.core.mybatis.mapper.MybatisMapper;
import cn.mybatis.mp.core.mybatis.mapper.context.SQLCmdContext;
import cn.mybatis.mp.core.util.GenericUtil;
import cn.mybatis.mp.db.annotations.Table;
import org.apache.ibatis.executor.*;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.session.*;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.UnknownTypeHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;


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
        if (MybatisMapper.class.isAssignableFrom(type)) {
            Optional<Class> entityOptional = GenericUtil.getGenericInterfaceClass(type).stream().filter(item -> item.isAnnotationPresent(Table.class)).findFirst();
            if (!entityOptional.isPresent()) {
                throw new RuntimeException(type + " did not add a generic");
            }
            ResultMapUtils.getResultMap(this, entityOptional.get());
            TableIds.get(this, entityOptional.get());
        }
        super.addMapper(type);
    }

    @Override
    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        T t = super.getMapper(type, sqlSession);
        if (BaseMapper.class.isAssignableFrom(type)) {
            return (T) Proxy.newProxyInstance(type.getClassLoader(), new Class[]{type}, new MybatisMapperProxy<>(type, t));
        }
        return t;
    }

    public ResultMapping buildResultMapping(boolean id, Field property, String columnName, JdbcType jdbcType, Class<? extends TypeHandler<?>> typeHandlerClass) {
        ResultMapping.Builder resultMappingBuilder = new ResultMapping.Builder(this, property.getName())
                .column(columnName)
                .javaType(property.getType())
                .jdbcType(jdbcType)
                .typeHandler(this.buildTypeHandler(property.getType(), typeHandlerClass));
        if (id) {
            resultMappingBuilder.flags(Collections.singletonList(ResultFlag.ID));
        }
        return resultMappingBuilder.build();
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



