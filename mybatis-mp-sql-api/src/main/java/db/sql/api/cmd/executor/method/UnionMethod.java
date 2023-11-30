package db.sql.api.cmd.executor.method;

import db.sql.api.cmd.executor.IQuery;

public interface UnionMethod<SELF extends UnionMethod> {

    SELF union(IQuery unionQuery);

    SELF unionAll(IQuery unionQuery);

}
