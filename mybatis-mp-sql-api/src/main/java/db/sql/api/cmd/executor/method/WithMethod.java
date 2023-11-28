package db.sql.api.cmd.executor.method;

import db.sql.api.cmd.executor.SubQuery;

public interface WithMethod<SELF> {
    SELF with(SubQuery subQuery);
}
