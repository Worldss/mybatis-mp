package cn.mybatis.mp.core.mybatis.provider;


import db.sql.api.DbType;
import db.sql.api.SQLMode;
import db.sql.api.SqlBuilderContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MybatisSqlBuilderContext extends SqlBuilderContext {

    private final List<Object> paramList = new ArrayList<>();

    private Object[] params;

    public MybatisSqlBuilderContext(DbType dbType, SQLMode sqlMode) {
        super(dbType, sqlMode);
    }

    @Override
    public String addParam(Object value) {
        paramList.add(value);
        return "?";
    }

    public Object[] getParams() {
        if (Objects.isNull(params)) {
            params = paramList.toArray();
        }
        return params;
    }
}
