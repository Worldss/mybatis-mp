package db.sql.core.api.cmd;

public class ConditionBlock {

    private final Connector connector;

    private final db.sql.api.Condition condition;

    public ConditionBlock(Connector connector, db.sql.api.Condition condition) {
        this.connector = connector;
        this.condition = condition;
    }

    public Connector getConnector() {
        return connector;
    }

    public db.sql.api.Condition getCondition() {
        return condition;
    }
}
