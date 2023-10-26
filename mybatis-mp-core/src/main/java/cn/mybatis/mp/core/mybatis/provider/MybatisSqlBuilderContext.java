package cn.mybatis.mp.core.mybatis.provider;



import db.sql.api.SQLMode;
import db.sql.api.SqlBuilderContext;
import db.sql.core.api.DatabaseId;

import java.util.ArrayList;
import java.util.List;

public class MybatisSqlBuilderContext extends SqlBuilderContext {

    public MybatisSqlBuilderContext(DatabaseId databaseId, SQLMode sqlMode) {
        super(databaseId, sqlMode);
    }

    public MybatisSqlBuilderContext(String databaseId, SQLMode sqlMode) {
        this(DatabaseId.getByName(databaseId), sqlMode);
    }

    private final List<Object> paramList = new ArrayList<>();

    @Override
    public String addParam(Object value) {
        paramList.add(value);
        return "?";
    }

    public Object[] getParams() {
        return paramList.stream().toArray();
    }
}
