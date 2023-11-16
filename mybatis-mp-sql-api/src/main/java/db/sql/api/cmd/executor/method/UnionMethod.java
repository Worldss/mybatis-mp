package db.sql.api.cmd.executor.method;

import db.sql.api.cmd.executor.Query;

public interface UnionMethod<SELF extends UnionMethod> {

    SELF union(Query unionQuery);

    SELF unionAll(Query unionQuery);

}
