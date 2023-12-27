package cn.mybatis.mp.core.mybatis.configuration;

import cn.mybatis.mp.core.mybatis.provider.MybatisSQLProvider;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.builder.annotation.ProviderSqlSource;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;

import java.lang.reflect.Method;

public final class MappedStatementUtil {

    public static MappedStatement wrap(MappedStatement ms) {
        TableIdGeneratorWrapper.addEntityKeyGenerator(ms);
        ResultMapWrapper.replaceResultMap(ms);
        if (ms.getSqlSource() instanceof ProviderSqlSource) {
            ProviderSqlSource providerSqlSource = (ProviderSqlSource) ms.getSqlSource();
            MetaObject sqlSourceMetaObject = ms.getConfiguration().newMetaObject(providerSqlSource);
            Class<?> providerType = (Class<?>) sqlSourceMetaObject.getValue("providerType");
            if (MybatisSQLProvider.class.isAssignableFrom(providerType)) {
                Method providerMethod = (Method) sqlSourceMetaObject.getValue("providerMethod");
                ProviderContext providerContext = (ProviderContext) sqlSourceMetaObject.getValue("providerContext");
                SQLCmdSqlSource sqlSource = new SQLCmdSqlSource(ms.getConfiguration(), providerMethod, providerContext);
                MetaObject msMetaObject = ms.getConfiguration().newMetaObject(ms);
                msMetaObject.setValue("sqlSource", sqlSource);
            }
        }
        return ms;
    }
}
