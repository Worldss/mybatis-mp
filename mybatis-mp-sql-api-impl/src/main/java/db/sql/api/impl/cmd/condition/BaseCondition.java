package db.sql.api.impl.cmd.condition;


import db.sql.api.impl.cmd.basic.Condition;

public abstract class BaseCondition<COLUMN, V> implements Condition<COLUMN, V> {

    private final String operator;

    public BaseCondition(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }
}
