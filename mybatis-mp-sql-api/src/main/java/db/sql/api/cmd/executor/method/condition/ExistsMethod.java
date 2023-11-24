package db.sql.api.cmd.executor.method.condition;


import db.sql.api.cmd.executor.Query;

public interface ExistsMethod<RV> {

    default RV exists(Query query) {
        return this.exists(true, query);
    }

    RV exists(boolean when, Query query);

    default RV notExists(Query query) {
        return this.notExists(true, query);
    }

    RV notExists(boolean when, Query query);
}
