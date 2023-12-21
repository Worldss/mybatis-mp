package db.sql.api.cmd;

import db.sql.api.Getter;

public class GetterColumnField<T> implements ColumnField {

    private final Getter<T> getter;

    private final int storey;

    public GetterColumnField(Getter<T> getter) {
        this(getter, 1);
    }

    public GetterColumnField(Getter<T> getter, int storey) {
        this.getter = getter;
        this.storey = storey;
    }

    public static <T> GetterColumnField create(Getter<T> getter) {
        return create(getter, 1);
    }

    public static <T> GetterColumnField create(Getter<T> getter, int storey) {
        return new GetterColumnField(getter, storey);
    }

    public Getter<T> getGetter() {
        return getter;
    }

    public int getStorey() {
        return storey;
    }


}
