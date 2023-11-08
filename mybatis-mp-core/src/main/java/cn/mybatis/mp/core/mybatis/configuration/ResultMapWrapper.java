package cn.mybatis.mp.core.mybatis.configuration;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
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
        msMetaObject.setValue("resultMaps", replaceResultMap((MybatisConfiguration) ms.getConfiguration(), ms.getResultMaps()));
    }

    public static List<ResultMap> replaceResultMap(MybatisConfiguration configuration, List<ResultMap> sourceResultMap) {
        return sourceResultMap.stream().map(item -> {
            String resultMapId = item.getType().getName();
            if (configuration.hasResultMap(resultMapId)) {
                return configuration.getResultMap(resultMapId);
            }
            ResultMap newResultMap = ResultMapUtils.getResultMap(configuration, item.getType());
            if (Objects.nonNull(newResultMap)) {
                return newResultMap;
            }
            return item;
        }).collect(Collectors.toList());
    }


}
