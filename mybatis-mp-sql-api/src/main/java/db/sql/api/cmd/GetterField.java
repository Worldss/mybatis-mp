package db.sql.api.cmd;

import db.sql.api.Getter;

public class GetterField<T> {

    private final Getter<T> getter;
    private final int storey;

    public GetterField(Getter<T> getter) {
        this(getter, 1);
    }

    public GetterField(Getter<T> getter, int storey) {
        this.getter = getter;
        this.storey = storey;
    }

    public static <T> GetterField create(Getter<T> getter) {
        return create(getter, 1);
    }

    public static <T> GetterField create(Getter<T> getter, int storey) {
        return new GetterField(getter, storey);
    }

    public Getter<T> getGetter() {
        return getter;
    }

    public int getStorey() {
        return storey;
    }

    public String getSS() {
        return null;
    }
}
