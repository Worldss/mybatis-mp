package db.sql.api.cmd.executor.method;

import db.sql.api.Cmd;
import db.sql.api.cmd.executor.method.groupByMethod.IGroupByMethods;

public interface IGroupByMethod<SELF extends IGroupByMethod,
        TABLE_FIELD extends DATASET_FILED,
        DATASET_FILED extends Cmd,
        COLUMN extends Cmd>

        extends IGroupByMethods<SELF,
        TABLE_FIELD,
        DATASET_FILED,
        COLUMN> {

}