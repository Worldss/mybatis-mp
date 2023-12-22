package db.sql.api.cmd.executor.method;


import db.sql.api.Cmd;
import db.sql.api.cmd.executor.method.orderByMethod.IOrderByMethods;

public interface IOrderByMethod<SELF extends IOrderByMethod,
        TABLE_FIELD extends DATASET_FILED,
        DATASET_FILED extends Cmd,
        COLUMN extends Cmd>

        extends IOrderByMethods<SELF,
        TABLE_FIELD,
        DATASET_FILED,
        COLUMN> {


}
