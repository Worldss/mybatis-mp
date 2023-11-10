package db.sql.core.api.cmd.executor;

import db.sql.core.api.cmd.CmdFactory;

public class Delete extends AbstractDelete<Delete, CmdFactory> {

    public Delete() {
        super(new CmdFactory());
    }


}
