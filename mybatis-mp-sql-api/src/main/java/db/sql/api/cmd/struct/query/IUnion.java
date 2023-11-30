package db.sql.api.cmd.struct.query;

import db.sql.api.Cmd;
import db.sql.api.cmd.executor.IQuery;

public interface IUnion extends Cmd {

    String getOperator();

    IQuery getUnionQuery();
}
