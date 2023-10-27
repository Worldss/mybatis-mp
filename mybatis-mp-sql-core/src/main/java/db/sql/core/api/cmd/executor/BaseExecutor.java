package db.sql.core.api.cmd.executor;

import db.sql.api.Cmd;
import db.sql.core.api.cmd.CmdFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseExecutor<SELF extends BaseExecutor, CMD_FACTORY extends CmdFactory> implements Executor<SELF, CMD_FACTORY> {
    protected final List<Cmd> cmds = new ArrayList<>();
    private Map<Class<? extends Cmd>, Integer> cmdSorts;

    @Override
    public SELF append(Cmd cmd) {
        this.cmds.add(cmd);
        return (SELF) this;
    }

    @Override
    public List<Cmd> cmds() {
        return cmds;
    }

    @Override
    public Map<Class<? extends Cmd>, Integer> cmdSorts() {
        if (cmdSorts == null) {
            cmdSorts = new HashMap<>();
            this.initCmdSorts(cmdSorts);
        }
        return cmdSorts;
    }

    abstract void initCmdSorts(Map<Class<? extends Cmd>, Integer> cmdSorts);
}
