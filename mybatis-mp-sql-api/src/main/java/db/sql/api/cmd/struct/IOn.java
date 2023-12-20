package db.sql.api.cmd.struct;


import db.sql.api.Cmd;
import db.sql.api.cmd.executor.method.IConditionMethod;

public interface IOn<SELF extends IOn,
        TABLE,
        TABLE_FIELD,
        COLUMN,
        V,
        JOIN extends IJoin<JOIN, TABLE, SELF>,
        CONDITION_CHAIN extends IConditionChain<CONDITION_CHAIN, TABLE_FIELD, COLUMN, V>>

        extends IConditionMethod<SELF, TABLE_FIELD, COLUMN, V, CONDITION_CHAIN>,
        Cmd {
    JOIN getJoin();
}
