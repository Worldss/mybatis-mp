package cn.mybatis.mp.core.sql.executor;

public class Query extends BaseQuery<Query> {

    public static final Query create() {
        return new Query();
    }
}
