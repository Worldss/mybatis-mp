package db.sql.api.cmd.struct.query;

import db.sql.api.Cmd;

import java.util.List;

public interface IGroupBy<SELF extends IGroupBy, COLUMN> extends Cmd {

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

    List<COLUMN> getGroupByFields();

}
