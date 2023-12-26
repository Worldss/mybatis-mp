package cn.mybatis.mp.core.mybatis.configuration;

import cn.mybatis.mp.core.mybatis.mapper.context.*;
import cn.mybatis.mp.core.mybatis.provider.MybatisSQLProvider;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;

import java.lang.reflect.Method;
import java.util.Collections;

public class SQLCmdSqlSource implements SqlSource {

    private final Configuration configuration;

    private final Method providerMethod;

    private final ProviderContext providerContext;

    public SQLCmdSqlSource(Configuration configuration, Method providerMethod, ProviderContext providerContext) {
        this.configuration = configuration;
        this.providerMethod = providerMethod;
        this.providerContext = providerContext;
    }

    @Override
    public BoundSql getBoundSql(Object parameterObject) {
        StringBuilder sql;
        switch (providerMethod.getName()) {
            case MybatisSQLProvider.QUERY_NAME: {
                sql = MybatisSQLProvider.cmdQuery((SQLCmdQueryContext) parameterObject, providerContext);
                break;
            }
            case MybatisSQLProvider.COUNT_NAME: {
                sql = MybatisSQLProvider.cmdCount((SQLCmdCountQueryContext) parameterObject, providerContext);
                break;
            }
            case MybatisSQLProvider.QUERY_COUNT_NAME: {
                sql = MybatisSQLProvider.countFromQuery((SQLCmdCountFromQueryContext) parameterObject, providerContext);
                break;
            }
            case MybatisSQLProvider.UPDATE_NAME: {
                sql = MybatisSQLProvider.update((SQLCmdUpdateContext) parameterObject, providerContext);
                break;
            }
            case MybatisSQLProvider.DELETE_NAME: {
                sql = MybatisSQLProvider.delete((SQLCmdDeleteContext) parameterObject, providerContext);
                break;
            }
            case MybatisSQLProvider.SAVE_NAME: {
                sql = MybatisSQLProvider.save((SQLCmdInsertContext) parameterObject, providerContext);
                break;
            }
            default: {
                throw new RuntimeException("Unadapted");
            }
        }
        return new BoundSql(this.configuration, sql.toString(), Collections.emptyList(), parameterObject);
    }
}
