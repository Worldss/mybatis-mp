package db.sql.api.impl.cmd.executor;

import db.sql.api.impl.cmd.CmdFactory;

public class Delete extends AbstractDelete<Delete, CmdFactory> {

    public Delete() {
        super(new CmdFactory());
    }
}
