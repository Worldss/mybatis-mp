package cn.mybatis.mp.core.mybatis.configuration;

import cn.mybatis.mp.core.mybatis.mapper.context.SQLCmdContext;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.defaults.RawLanguageDriver;
import org.apache.ibatis.session.Configuration;

import java.util.Collections;
import java.util.Objects;

public class MybatisLanguageDriver extends RawLanguageDriver {

    @Override
    public SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType) {
        if (Objects.nonNull(parameterType) && SQLCmdContext.class.isAssignableFrom(parameterType)) {
            return new StaticSqlSource(configuration, script, Collections.emptyList());
        }
        return super.createSqlSource(configuration, script, parameterType);
    }
}
