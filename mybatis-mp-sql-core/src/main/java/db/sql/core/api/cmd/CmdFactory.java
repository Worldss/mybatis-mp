package db.sql.core.api.cmd;


import db.sql.api.Cmd;
import db.sql.api.Getter;
import db.sql.api.LikeMode;
import db.sql.core.api.cmd.condition.*;
import db.sql.core.api.tookit.LambdaUtil;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;


public class CmdFactory {

    protected Map<String, Table> tableCache = new HashMap<>();

    protected Map<String, TableField> tableFieldCache = new HashMap<>();

    protected int tableNums = 0;

    protected String tableAs(int storey, int tableNums) {
        StringBuilder as = new StringBuilder();
        for (int i = 0; i < storey; i++) {
            as.append('t');
        }
        return as.append(tableNums == 1 ? "" : tableNums).toString();
    }

    public Table table(Class clazz) {
        return this.table(clazz, 1);
    }

    public Table table(Class clazz, int storey) {
        return tableCache.computeIfAbsent(clazz.getName(), key -> {
            Table table = new Table(clazz.getSimpleName());
            table.as(tableAs(storey, ++tableNums));
            return table;
        });
    }

    public <T> TableField field(Getter<T> getter) {
        return this.field(getter, 1);
    }

    public <T> TableField field(Getter<T> getter, int storey) {
        Class clazz = LambdaUtil.getClass(getter);
        String filedName = LambdaUtil.getName(getter);
        return this.field(clazz, filedName, storey);
    }

    protected TableField field(Class clazz, String filedName, int storey) {
        return tableFieldCache.computeIfAbsent(String.format("%s.%s", clazz.getName(), filedName), key -> {
            Table table = table(clazz, storey);
            return new TableField(table, filedName);
        });
    }

    public Consumer<On> on(Class mainTable, Class secondTable, Consumer<On> consumer) {
        return consumer;
    }

    public Table table(String name) {
        return new Table(name);
    }

    public BasicValue value(Object value) {
        return new BasicValue(value);
    }

    public DatasetField field(Dataset table, String name) {
        return new DatasetField(table, name);
    }

    public TableField field(Table table, String name) {
        return new TableField(table, name);
    }

    public AllField all(Dataset table) {
        return new AllField(table);
    }

    public Eq eq(Cmd key, Serializable value) {
        return eq(key, value(value));
    }

    public Eq eq(Cmd key, Cmd value) {
        return new Eq(key, value);
    }

    public Ne ne(Cmd key, Serializable value) {
        return ne(key, value(value));
    }

    public Ne ne(Cmd key, Cmd value) {
        return new Ne(key, value);
    }

    public Gt gt(Cmd key, Serializable value) {
        return gt(key, value(value));
    }

    public Gt gt(Cmd key, Cmd value) {
        return new Gt(key, value);
    }

    public Gte gte(Cmd key, Serializable value) {
        return gte(key, value(value));
    }

    public Gte gte(Cmd key, Cmd value) {
        return new Gte(key, value);
    }

    public Lt lt(Cmd key, Serializable value) {
        return lt(key, value(value));
    }

    public Lt lt(Cmd key, Cmd value) {
        return new Lt(key, value);
    }

    public Lte lte(Cmd key, Serializable value) {
        return lte(key, value(value));
    }

    public Lte lte(Cmd key, Cmd value) {
        return new Lte(key, value);
    }

    public Between between(Cmd key, Serializable value1, Serializable value2) {
        return between(key, value(value1), value(value2));
    }

    public Between between(Cmd key, Cmd value1, Cmd value2) {
        return new Between(key, value1, value2);
    }

    public NotBetween notBetween(Cmd key, Serializable value1, Serializable value2) {
        return notBetween(key, value(value1), value(value2));
    }

    public NotBetween notBetween(Cmd key, Cmd value1, Cmd value2) {
        return new NotBetween(key, value1, value2);
    }

    public Like like(Cmd key, Serializable value) {
        return like(key, value(value), LikeMode.DEFAULT);
    }

    public Like like(Cmd key, Serializable value, LikeMode mode) {
        return like(key, value(value), mode);
    }

    public Like like(Cmd key, Cmd value, LikeMode mode) {
        return new Like(key, value, mode);
    }

    public NotLike notLike(Cmd key, Serializable value) {
        return notLike(key, value(value), LikeMode.DEFAULT);
    }

    public NotLike notLike(Cmd key, Serializable value, LikeMode mode) {
        return notLike(key, value(value), mode);
    }

    public NotLike notLike(Cmd key, Cmd value, LikeMode mode) {
        return new NotLike(key, value, mode);
    }

    public In in(Cmd main) {
        In in = new In(main);
        return in;
    }
}
