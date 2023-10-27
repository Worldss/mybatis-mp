package db.sql.core.api.cmd.executor;

import db.sql.core.api.cmd.CmdFactory;

public class Insert extends AbstractInsert<Insert, CmdFactory> {

    public Insert() {
        super(new CmdFactory());
    }
}
