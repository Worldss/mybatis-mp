package db.sql.api.cmd.executor.method;

import db.sql.api.cmd.executor.IQuery;

public interface IUnionMethod<SELF extends IUnionMethod> {

    SELF union(IQuery unionQuery);

    SELF unionAll(IQuery unionQuery);

}
