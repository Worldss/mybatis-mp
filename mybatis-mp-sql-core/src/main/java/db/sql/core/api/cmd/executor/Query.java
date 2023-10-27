package db.sql.core.api.cmd.executor;


import db.sql.core.api.cmd.CmdFactory;


public class Query extends AbstractQuery<Query, CmdFactory> {

    public Query() {
        super(new CmdFactory());
    }
}
