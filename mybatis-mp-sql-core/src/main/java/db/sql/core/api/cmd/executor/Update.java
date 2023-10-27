package db.sql.core.api.cmd.executor;

import db.sql.core.api.cmd.CmdFactory;

public class Update extends AbstractUpdate<Update, CmdFactory> {
    public Update() {
        super(new CmdFactory());
    }
}
