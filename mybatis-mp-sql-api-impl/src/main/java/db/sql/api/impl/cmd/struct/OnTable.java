package db.sql.api.impl.cmd.struct;

import db.sql.api.impl.cmd.ConditionFactory;
import db.sql.api.impl.cmd.basic.Table;

public class OnTable extends On<OnTable, Table, JoinTable> {
    public OnTable(ConditionFactory conditionFactory, JoinTable join) {
        super(conditionFactory, join);
    }
}
