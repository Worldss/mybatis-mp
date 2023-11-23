package db.sql.api.impl.cmd.struct;

import db.sql.api.impl.cmd.ConditionFaction;
import db.sql.api.impl.cmd.basic.Dataset;

public class OnDataset extends On<OnDataset, Dataset,JoinDataset>{
    public OnDataset(ConditionFaction conditionFaction, JoinDataset join) {
        super(conditionFaction, join);
    }
}
