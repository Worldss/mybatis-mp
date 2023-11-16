package db.sql.core.api.cmd.condition;


import db.sql.core.api.cmd.basic.Condition;

public abstract class BaseCondition<COLUMN, V> implements Condition<COLUMN, V> {

    private final String operator;

    public BaseCondition(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }
}
