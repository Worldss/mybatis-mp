package db.sql.api.cmd.executor;

import db.sql.api.Cmd;
import db.sql.api.cmd.JoinMode;
import db.sql.api.cmd.executor.method.JoinMethod;
import db.sql.api.cmd.executor.method.UpdateMethod;
import db.sql.api.cmd.executor.method.WhereMethod;
import db.sql.api.cmd.struct.IConditionChain;
import db.sql.api.cmd.struct.IJoin;
import db.sql.api.cmd.struct.IOn;
import db.sql.api.cmd.struct.IWhere;
import db.sql.api.cmd.struct.update.IUpdateTable;

public interface IUpdate<SELF extends IUpdate,
        TABLE extends DATASET,
        DATASET extends Cmd,
        TABLE_FIELD extends DATASET_FILED,
        DATASET_FILED extends COLUMN,
        COLUMN extends Cmd,
        V,
        CONDITION_CHAIN extends IConditionChain<CONDITION_CHAIN, COLUMN, V>,
        UPDATE_TABLE extends IUpdateTable<TABLE>,
        JOIN extends IJoin<JOIN, TABLE, ON>,
        ON extends IOn<ON, TABLE, COLUMN, V, JOIN, CONDITION_CHAIN>,
        WHERE extends IWhere<WHERE, COLUMN, V, CONDITION_CHAIN>
        >

        extends UpdateMethod<SELF, TABLE, COLUMN, V>,
        JoinMethod<SELF, TABLE, ON>,
        WhereMethod<SELF, COLUMN, V, CONDITION_CHAIN>,
        IExecutor<SELF, TABLE, DATASET, TABLE_FIELD, DATASET_FILED> {


    UPDATE_TABLE $update(TABLE... tables);

    JOIN $join(JoinMode mode, TABLE mainTable, TABLE secondTable);

    WHERE $where();

    @Override
    default SELF update(TABLE... tables) {
        $update(tables);
        return (SELF) this;
    }

    @Override
    default CONDITION_CHAIN conditionChain() {
        return $where().conditionChain();
    }
}
