package db.sql.api.cmd.struct.query;

import db.sql.api.Cmd;
import db.sql.api.cmd.executor.Query;

public interface Union extends Cmd {

    String getOperator();

    Query getUnionQuery();
}
