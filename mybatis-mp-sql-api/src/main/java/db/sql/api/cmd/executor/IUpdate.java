package db.sql.api.cmd.executor;

import db.sql.api.Cmd;
import db.sql.api.cmd.JoinMode;
import db.sql.api.cmd.executor.method.IJoinMethod;
import db.sql.api.cmd.executor.method.IUpdateMethod;
import db.sql.api.cmd.executor.method.IWhereMethod;
import db.sql.api.cmd.struct.conditionChain.IConditionChain;
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
        CONDITION_CHAIN extends IConditionChain<CONDITION_CHAIN, TABLE_FIELD, COLUMN, V>,
        UPDATE_TABLE extends IUpdateTable<TABLE>,
        JOIN extends IJoin<JOIN, TABLE, ON>,
        ON extends IOn<ON, TABLE, TABLE_FIELD, COLUMN, V, JOIN, CONDITION_CHAIN>,
        WHERE extends IWhere<WHERE, TABLE_FIELD, COLUMN, V, CONDITION_CHAIN>
        >

        extends IUpdateMethod<SELF, TABLE, COLUMN, V>,
        IJoinMethod<SELF, TABLE, ON>,
        IWhereMethod<SELF, TABLE_FIELD, COLUMN, V, CONDITION_CHAIN>,
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
