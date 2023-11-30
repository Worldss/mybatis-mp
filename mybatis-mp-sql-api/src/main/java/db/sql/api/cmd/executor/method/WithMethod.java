package db.sql.api.cmd.executor.method;

import db.sql.api.cmd.executor.ISubQuery;

public interface WithMethod<SELF> {
    SELF with(ISubQuery subQuery);
}
