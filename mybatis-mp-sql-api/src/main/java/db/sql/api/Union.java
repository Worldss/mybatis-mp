package db.sql.api;

import db.sql.api.executor.Query;

public interface Union extends Cmd {

    String getOperator();

    Query getUnionQuery();
}
