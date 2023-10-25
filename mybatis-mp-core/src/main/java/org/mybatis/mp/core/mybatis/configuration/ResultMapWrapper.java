package org.mybatis.mp.core.mybatis.configuration;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.mp.core.db.reflect.ResultTableInfo;
import org.mybatis.mp.core.db.reflect.ResultTables;
import org.mybatis.mp.core.db.reflect.TableInfo;
import org.mybatis.mp.core.db.reflect.TableInfos;

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
            TableInfo tableInfo = TableInfos.get(item.getType(), configuration);
            String resultMapId = item.getType().getName();
            if (Objects.nonNull(tableInfo)) {
                ResultMap resultMap;
                if (configuration.hasResultMap(resultMapId)) {
                    resultMap = configuration.getResultMap(resultMapId);
                } else {
                    resultMap = new ResultMap.Builder(configuration, resultMapId, item.getType(), tableInfo.getResultMappings(), false).build();
                    configuration.addResultMap(resultMap);
                }
                return resultMap;
            }
            ResultTableInfo resultTableInfo = ResultTables.get(item.getType(), configuration);
            if (Objects.nonNull(resultTableInfo)) {
                ResultMap resultMap;
                if (configuration.hasResultMap(resultMapId)) {
                    resultMap = configuration.getResultMap(resultMapId);
                } else {
                    resultMap = new ResultMap.Builder(configuration, item.getType().getName(), item.getType(), resultTableInfo.getResultMappings(), false).build();
                    configuration.addResultMap(resultMap);
                }
                return resultMap;
            }
            return item;
        }).collect(Collectors.toList());
    }


}
