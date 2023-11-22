package db.sql.api.impl.cmd.executor;

import db.sql.api.impl.cmd.CmdFactory;

public class Insert extends AbstractInsert<Insert, CmdFactory> {

    public Insert() {
        super(new CmdFactory());
    }
}
