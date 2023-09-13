package org.mybatis.mp.core.mybatis.provider;

import db.sql.core.DatabaseId;
import db.sql.core.SQLMode;
import db.sql.core.SqlBuilderContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MybatisSqlBuilderContext extends SqlBuilderContext {

    public MybatisSqlBuilderContext(DatabaseId databaseId, SQLMode sqlMode) {
        super(databaseId, sqlMode);
    }

    public MybatisSqlBuilderContext(String databaseId, SQLMode sqlMode) {
        super(databaseId, sqlMode);
    }

    private int paramIndex = 0;

    private Map<Integer, String> paramNameCache = new HashMap<>();

    private final List<Object> paramList=new ArrayList<>();

    @Override
    public String addParam(Object value) {

        paramList.add(value);

        return paramNameCache.computeIfAbsent(++paramIndex, (key) -> {
            return "?";
        });
    }

    public List<Object> getParamList() {
        return paramList;
    }

    public Object[] getParams() {
        return paramList.stream().toArray();
    }
}
