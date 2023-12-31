package cn.mybatis.mp.core.mybatis.configuration;

import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;

import java.util.Collections;

public class DynamicsMappedStatement {

    public static MappedStatement create(Class returnTypeClass, MappedStatement ms) {
        String id = ms.getId() + "." + returnTypeClass.getName();
        if (ms.getConfiguration().hasStatement(id)) {
            return ms.getConfiguration().getMappedStatement(id);
        }
        ResultMap resultMap;
        String resultMapId = returnTypeClass.getName();
        if (ms.getConfiguration().hasResultMap(resultMapId)) {
            resultMap = ms.getConfiguration().getResultMap(resultMapId);
        } else {
            resultMap = new ResultMap.Builder(ms.getConfiguration(), resultMapId, returnTypeClass, Collections.emptyList(), false).build();
        }
        MappedStatement.Builder msBuilder = new MappedStatement.Builder(ms.getConfiguration(), id, ms.getSqlSource(), ms.getSqlCommandType())
                .resource(ms.getResource())
                .resultMaps(ResultMapWrapper.replaceResultMap((MybatisConfiguration) ms.getConfiguration(), Collections.singletonList(resultMap)))
                .parameterMap(ms.getParameterMap())
                .keyGenerator(NoKeyGenerator.INSTANCE)
                .fetchSize(ms.getFetchSize())
                .statementType(ms.getStatementType())
                .lang(ms.getLang())
                .timeout(ms.getTimeout())
                .useCache(ms.isUseCache())
                .cache(ms.getCache());
        MappedStatement newMappedStatement = msBuilder.build();
        try {
            if (ms.getConfiguration().hasStatement(id)) {
                return ms.getConfiguration().getMappedStatement(id);
            }
            ms.getConfiguration().addMappedStatement(newMappedStatement);
        } catch (IllegalArgumentException e) {
            ms.getStatementLog().warn(e.getMessage());
        }

        return newMappedStatement;

    }
}
