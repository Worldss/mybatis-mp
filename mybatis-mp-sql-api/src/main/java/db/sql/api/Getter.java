package db.sql.api;

import java.io.Serializable;

@FunctionalInterface
public interface Getter<T> extends Serializable {
    Object get(T source);
}
