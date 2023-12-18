package db.sql.api.cmd.executor.method;

import db.sql.api.cmd.executor.ISubQuery;

public interface IWithMethod<SELF> {
    SELF with(ISubQuery subQuery);
}
