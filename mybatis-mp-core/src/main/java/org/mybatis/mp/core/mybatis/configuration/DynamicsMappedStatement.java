package org.mybatis.mp.core.mybatis.configuration;

import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;

import java.util.Collections;
import java.util.Objects;

public class DynamicsMappedStatement {

    public static MappedStatement create(Class returnTypeClass, MappedStatement ms) {
        String id = ms.getId() + "." + returnTypeClass.getName();
        if (ms.getConfiguration().hasStatement(id)) {
            return ms.getConfiguration().getMappedStatement(id);
        }

        ResultMap resultMap = ms.getConfiguration().getResultMap(returnTypeClass.getName());
        if (Objects.isNull(resultMap)) {
            resultMap = new ResultMap.Builder(ms.getConfiguration(), returnTypeClass.getName(), returnTypeClass, Collections.emptyList(), true).build();
            ms.getConfiguration().addResultMap(resultMap);
        }
        MappedStatement.Builder msBuilder = new MappedStatement.Builder(ms.getConfiguration(), id, ms.getSqlSource(), ms.getSqlCommandType())
                .resultMaps(Collections.singletonList(resultMap))
                .parameterMap(ms.getParameterMap())
                .keyGenerator(NoKeyGenerator.INSTANCE)
                .useCache(ms.isUseCache())
                .cache(ms.getCache());
        MappedStatement newMappedStatement = msBuilder.build();
        ms.getConfiguration().addMappedStatement(newMappedStatement);
        return newMappedStatement;

    }
}
