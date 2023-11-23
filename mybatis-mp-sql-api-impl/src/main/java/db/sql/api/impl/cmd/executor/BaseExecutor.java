package db.sql.api.impl.cmd.executor;

import db.sql.api.Cmd;
import db.sql.api.Getter;
import db.sql.api.impl.cmd.CmdFactory;
import db.sql.api.impl.cmd.basic.Dataset;
import db.sql.api.impl.cmd.basic.DatasetField;
import db.sql.api.impl.cmd.basic.Table;
import db.sql.api.impl.cmd.basic.TableField;
import db.sql.api.tookit.CmdUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class BaseExecutor<SELF extends BaseExecutor, CMD_FACTORY extends CmdFactory> implements Executor<SELF, CMD_FACTORY> {
    protected final List<Cmd> cmds = new ArrayList<>();

    private Map<Class<? extends Cmd>, Integer> cmdSorts;

    public <T> TableField $(Table table, Getter<T> getter) {
        return $().field(table, getter);
    }

    public <T> TableField $(Getter<T> getter, int storey) {
        return $().field(getter, storey);
    }

    public DatasetField $(Dataset dataset, String columnName) {
        return $().field(dataset, columnName);
    }

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

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.cmds);
    }
}
