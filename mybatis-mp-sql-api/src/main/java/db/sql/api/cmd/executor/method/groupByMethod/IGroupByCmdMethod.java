package db.sql.api.cmd.executor.method.groupByMethod;


import db.sql.api.Cmd;

import java.util.List;

public interface IGroupByCmdMethod<SELF extends IGroupByCmdMethod, COLUMN extends Cmd> {

    SELF groupBy(COLUMN column);

    default SELF groupBy(COLUMN... columns) {
        for (COLUMN column : columns) {
            this.groupBy(column);
        }
        return (SELF) this;
    }


    default SELF groupBy(List<COLUMN> columns) {
        for (COLUMN column : columns) {
            this.groupBy(column);
        }
        return (SELF) this;
    }
}
