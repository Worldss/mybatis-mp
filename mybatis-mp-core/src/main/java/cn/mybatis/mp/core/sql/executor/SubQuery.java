package cn.mybatis.mp.core.sql.executor;


/**
 * 子查询
 */
public class SubQuery extends BaseSubQuery<SubQuery> {

    public SubQuery() {
        this(null);
    }

    public SubQuery(String alias) {
        super(alias);
    }

    public static final SubQuery create() {
        return new SubQuery(null);
    }

    public static final SubQuery create(String alias) {
        return new SubQuery(alias);
    }
}
