package db.sql.core.api.cmd.executor;

import db.sql.core.api.cmd.CmdFactory;
import db.sql.core.api.cmd.ConditionFaction;

public class Delete extends AbstractDelete<Delete, CmdFactory> {

    public Delete() {
        super(new CmdFactory());
    }


}
