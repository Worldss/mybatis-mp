package db.sql.api.cmd.executor;

import db.sql.api.Cmd;
import db.sql.api.cmd.JoinMode;
import db.sql.api.cmd.executor.method.JoinMethod;
import db.sql.api.cmd.executor.method.UpdateMethod;
import db.sql.api.cmd.executor.method.WhereMethod;
import db.sql.api.cmd.struct.ConditionChain;
import db.sql.api.cmd.struct.Join;
import db.sql.api.cmd.struct.On;
import db.sql.api.cmd.struct.Where;
import db.sql.api.cmd.struct.update.UpdateTable;

public interface Update<SELF extends Update,
        TABLE extends DATASET,
        DATASET extends Cmd,
        TABLE_FIELD extends DATASET_FILED,
        DATASET_FILED extends COLUMN,
        COLUMN extends Cmd,
        V,
        CONDITION_CHAIN extends ConditionChain<CONDITION_CHAIN, COLUMN, V>,
        UPDATE_TABLE extends UpdateTable<TABLE>,
        JOIN extends Join<JOIN, TABLE, ON>,
        ON extends On<ON, TABLE, COLUMN, V, JOIN, CONDITION_CHAIN>,
        WHERE extends Where<WHERE, COLUMN, V, CONDITION_CHAIN>
        >

        extends UpdateMethod<SELF, TABLE, COLUMN, V>,
        JoinMethod<SELF, TABLE, ON>,
        WhereMethod<SELF, COLUMN, V, CONDITION_CHAIN>,
        Executor<SELF, TABLE, DATASET, TABLE_FIELD, DATASET_FILED> {


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
