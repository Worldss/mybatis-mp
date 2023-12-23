package db.sql.api.cmd.executor.method.selectMethod;


import db.sql.api.Cmd;
import db.sql.api.cmd.executor.method.ISelectMethod;

import java.util.List;

public interface ISelectCmdMethod<SELF extends ISelectMethod, COLUMN extends Cmd> {

    SELF select(COLUMN column);

    default SELF select(COLUMN... columns) {
        for (COLUMN column : columns) {
            this.select(column);
        }
        return (SELF) this;
    }


    default SELF select(List<COLUMN> columns) {
        for (COLUMN column : columns) {
            this.select(column);
        }
        return (SELF) this;
    }
}
