package org.mybatis.mp.core.mybatis.provider;

import db.sql.core.DatabaseId;
import db.sql.core.SQLMode;
import db.sql.core.SqlBuilderContext;

import java.util.*;

public class MybatisSqlBuilderContext extends SqlBuilderContext {

    public MybatisSqlBuilderContext(String databaseId, SQLMode sqlMode) {
        super(databaseId, sqlMode);
    }

    private final List<Object> paramList = new ArrayList<>();

    private volatile Object[] params;

    @Override
    public String addParam(Object value) {
        paramList.add(value);
        return "?";
    }

    public Object[] getParams() {
        if (Objects.isNull(params)) {
            params = paramList.stream().toArray();
        }
        return params;
    }
}
