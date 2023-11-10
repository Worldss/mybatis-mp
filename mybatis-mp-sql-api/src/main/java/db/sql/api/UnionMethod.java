package db.sql.api;

import db.sql.api.executor.Query;

public interface UnionMethod<SELF extends UnionMethod> {

    SELF union(Query unionQuery);

    SELF unionAll(Query unionQuery);

}
