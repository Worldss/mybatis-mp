package db.sql.api.impl.cmd.struct;

import db.sql.api.impl.cmd.ConditionFaction;
import db.sql.api.impl.cmd.basic.Table;

public class OnTable extends On<OnTable, Table,JoinTable>{
    public OnTable(ConditionFaction conditionFaction, JoinTable join) {
        super(conditionFaction, join);
    }
}
