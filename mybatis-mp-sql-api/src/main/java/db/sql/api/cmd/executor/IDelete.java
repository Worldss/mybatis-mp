package db.sql.api.cmd.executor;

import db.sql.api.Cmd;
import db.sql.api.cmd.JoinMode;
import db.sql.api.cmd.executor.method.IDeleteMethod;
import db.sql.api.cmd.executor.method.IFromMethod;
import db.sql.api.cmd.executor.method.IJoinMethod;
import db.sql.api.cmd.executor.method.IWhereMethod;
import db.sql.api.cmd.struct.IFrom;
import db.sql.api.cmd.struct.IJoin;
import db.sql.api.cmd.struct.IOn;
import db.sql.api.cmd.struct.IWhere;
import db.sql.api.cmd.struct.conditionChain.IConditionChain;
import db.sql.api.cmd.struct.delete.IDeleteTable;

public interface IDelete<SELF extends IDelete,
        TABLE extends DATASET,
        DATASET extends Cmd,
        TABLE_FIELD extends DATASET_FILED,
        DATASET_FILED extends COLUMN,
        COLUMN extends Cmd,
        V,
        CONDITION_CHAIN extends IConditionChain<CONDITION_CHAIN, TABLE_FIELD, COLUMN, V>,
        DELETE_TABLE extends IDeleteTable<TABLE>,
        FROM extends IFrom<TABLE>,
        JOIN extends IJoin<JOIN, TABLE, ON>,
        ON extends IOn<ON, TABLE, TABLE_FIELD, COLUMN, V, JOIN, CONDITION_CHAIN>,
        WHERE extends IWhere<WHERE, TABLE_FIELD, COLUMN, V, CONDITION_CHAIN>>

        extends IDeleteMethod<SELF, TABLE>,
        IFromMethod<SELF, TABLE>,
        IJoinMethod<SELF, TABLE, ON>,
        IWhereMethod<SELF, TABLE_FIELD, COLUMN, V, CONDITION_CHAIN>,
        IExecutor<SELF, TABLE, DATASET, TABLE_FIELD, DATASET_FILED> {

    DELETE_TABLE $delete(TABLE... tables);

    FROM $from(TABLE... tables);

    JOIN $join(JoinMode mode, TABLE mainTable, TABLE secondTable);

    WHERE $where();

    @Override
    default SELF delete(TABLE... tables) {
        $delete(tables);
        return (SELF) this;
    }

    @Override
    default SELF from(TABLE... tables) {
        $from(tables);
        return (SELF) this;
    }

    @Override
    default CONDITION_CHAIN conditionChain() {
        return $where().conditionChain();
    }
}
