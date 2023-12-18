package db.sql.api.impl.exception;

public class ConditionValueNullException extends RuntimeException {

    public ConditionValueNullException() {

    }

    public ConditionValueNullException(String message) {
        super(message);
    }
}
