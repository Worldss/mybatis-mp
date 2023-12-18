package db.sql.api.impl.exception;

public class ConditionArrayValueEmptyException extends RuntimeException {

    public ConditionArrayValueEmptyException() {

    }

    public ConditionArrayValueEmptyException(String message) {
        super(message);
    }
}
