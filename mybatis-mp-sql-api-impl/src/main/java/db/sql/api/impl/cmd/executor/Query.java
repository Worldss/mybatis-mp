package db.sql.api.impl.cmd.executor;


import db.sql.api.impl.cmd.CmdFactory;


public class Query extends AbstractQuery<Query, CmdFactory> {

    public Query() {
        super(new CmdFactory());
    }
}
