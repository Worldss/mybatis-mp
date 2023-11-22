package db.sql.api.impl.cmd.executor;

import db.sql.api.impl.cmd.CmdFactory;

public class Update extends AbstractUpdate<Update, CmdFactory> {
    public Update() {
        super(new CmdFactory());
    }
}
