package db.sql.api.impl.cmd.struct;

import db.sql.api.impl.cmd.ConditionFactory;
import db.sql.api.impl.cmd.basic.Dataset;

public class OnDataset extends On<OnDataset, Dataset, JoinDataset> {
    public OnDataset(ConditionFactory conditionFactory, JoinDataset join) {
        super(conditionFactory, join);
    }
}
