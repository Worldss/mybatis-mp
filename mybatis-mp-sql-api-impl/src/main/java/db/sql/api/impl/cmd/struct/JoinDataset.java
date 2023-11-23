package db.sql.api.impl.cmd.struct;

import db.sql.api.cmd.JoinMode;
import db.sql.api.impl.cmd.basic.Dataset;

import java.util.function.Function;

public class JoinDataset extends Join<JoinDataset, Dataset, OnDataset> {

    public JoinDataset(JoinMode mode, Dataset mainTable, Dataset secondTable, Function<JoinDataset, OnDataset> onFunction) {
        super(mode, mainTable, secondTable, onFunction);
    }
}
