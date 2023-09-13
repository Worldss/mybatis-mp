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
import java.util.stream.Collectors;

public class ResultMapWrapper {

    public static void replaceResultMap(MappedStatement ms) {
        if (ms.getSqlCommandType() != SqlCommandType.SELECT) {
            return;
        }
        List<ResultMap> resultMaps = ms.getResultMaps().stream().map(item -> {
            TableInfo tableInfo = TableInfos.get(item.getType(), (MybatisConfiguration) ms.getConfiguration());
            if (tableInfo != null) {
                return new ResultMap.Builder(ms.getConfiguration(), item.getId(), item.getType(), tableInfo.getResultMappings(), true).build();
            }

            ResultTableInfo resultTableInfo = ResultTables.get(item.getType(), (MybatisConfiguration) ms.getConfiguration());
            if (resultTableInfo != null) {
                return new ResultMap.Builder(ms.getConfiguration(), item.getId(), item.getType(), resultTableInfo.getResultMappings(), true).build();
            }

            return item;
        }).collect(Collectors.toList());

        MetaObject msMetaObject = ms.getConfiguration().newMetaObject(ms);
        msMetaObject.setValue("resultMaps", resultMaps);
    }


}
