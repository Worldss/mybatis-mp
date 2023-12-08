package cn.mybatis.mp.core.mybatis.provider;


import db.sql.api.DbType;
import db.sql.api.SQLMode;
import db.sql.api.SqlBuilderContext;

import java.util.ArrayList;
import java.util.List;

public class MybatisSqlBuilderContext extends SqlBuilderContext {

    private final List<Object> paramList = new ArrayList<>();

    public MybatisSqlBuilderContext(DbType dbType, SQLMode sqlMode) {
        super(dbType, sqlMode);
    }

    @Override
    public String addParam(Object value) {
        paramList.add(value);
        return "?";
    }

    public Object[] getParams() {
        return paramList.toArray();
    }
}
