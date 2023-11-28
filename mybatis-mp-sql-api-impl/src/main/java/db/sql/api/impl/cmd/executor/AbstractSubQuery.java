package db.sql.api.impl.cmd.executor;

import db.sql.api.Cmd;
import db.sql.api.cmd.executor.SubQuery;
import db.sql.api.cmd.struct.Joins;
import db.sql.api.impl.cmd.CmdFactory;
import db.sql.api.impl.cmd.basic.Dataset;
import db.sql.api.impl.cmd.basic.DatasetField;
import db.sql.api.impl.cmd.basic.Table;
import db.sql.api.impl.cmd.basic.TableField;
import db.sql.api.impl.cmd.struct.*;
import db.sql.api.impl.cmd.struct.query.*;

public abstract class AbstractSubQuery<SELF extends AbstractSubQuery, CMD_FACTORY extends CmdFactory> extends AbstractQuery<SELF, CMD_FACTORY>
        implements SubQuery<SELF,
        Table,
        Dataset,
        TableField,
        DatasetField,
        Cmd,
        Object,
        CMD_FACTORY,
        ConditionChain,
        With,
        Select,
        FromDataset,
        JoinDataset,
        OnDataset,
        Joins<JoinDataset>,
        Where,
        GroupBy,
        Having,
        OrderBy,
        Limit,
        ForUpdate,
        Union
        > {
    public AbstractSubQuery(CMD_FACTORY $) {
        super($);
    }

}

