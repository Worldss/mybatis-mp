package db.sql.api.impl.cmd.executor;

import db.sql.api.Cmd;
import db.sql.api.Getter;
import db.sql.api.impl.cmd.CmdFactory;
import db.sql.api.impl.cmd.basic.*;
import db.sql.api.tookit.CmdUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class BaseExecutor<SELF extends BaseExecutor, CMD_FACTORY extends CmdFactory> implements Executor<SELF, CMD_FACTORY> {
    protected final List<Cmd> cmds = new ArrayList<>();

    private Map<Class<? extends Cmd>, Integer> cmdSorts;

    public <T> TableField $(Table table, Getter<T> column) {
        return $().field(table, column);
    }

    public <T> TableField $(Getter<T> column, int storey) {
        return $().field(column, storey);
    }

    public DatasetField $(Dataset dataset, String columnName) {
        return $().field(dataset, columnName);
    }

    public TableField $(Table table, String columnName) {
        return $().field(table, columnName);
    }

    public <T> SubQueryTableField $(SubQuery subQuery, Getter<T> column) {
        return this.$(subQuery, column, 1);
    }

    public <T> SubQueryTableField $(SubQuery subQuery, Getter<T> column, int storey) {
        return new SubQueryTableField(subQuery, subQuery.$(column, storey));
    }

    public <T> SubQueryTableField $(db.sql.api.cmd.executor.SubQuery subQuery, Getter<T> column) {
        return this.$(subQuery, column, 1);
    }

    public <T> SubQueryTableField $(db.sql.api.cmd.executor.SubQuery subQuery, Getter<T> column, int storey) {
        return this.$((SubQuery) subQuery, column, 1);
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
