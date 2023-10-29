package cn.mybatis.mp.core.mybatis.configuration;

import cn.mybatis.mp.core.db.reflect.ResultMaps;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.reflection.MetaObject;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ResultMapWrapper {

    public static void replaceResultMap(MappedStatement ms) {
        if (ms.getSqlCommandType() != SqlCommandType.SELECT) {
            return;
        }
        MetaObject msMetaObject = ms.getConfiguration().newMetaObject(ms);
        msMetaObject.setValue("resultMaps", getResultMap((MybatisConfiguration) ms.getConfiguration(), ms.getResultMaps()));
    }

    public static List<ResultMap> getResultMap(MybatisConfiguration configuration, List<ResultMap> sourceResultMap) {
        return sourceResultMap.stream().map(item -> {
            String resultMapId = item.getType().getName();
            if (configuration.hasResultMap(resultMapId)) {
                return configuration.getResultMap(resultMapId);
            }
            List<ResultMapping> resultMappings = ResultMaps.getResultMappings(configuration, item.getType());
            if (Objects.nonNull(resultMappings)) {
                ResultMap resultMap = new ResultMap.Builder(configuration, item.getType().getName(), item.getType(), resultMappings, false).build();
                configuration.addResultMap(resultMap);
                return resultMap;
            }
            return item;
        }).collect(Collectors.toList());
    }


}
