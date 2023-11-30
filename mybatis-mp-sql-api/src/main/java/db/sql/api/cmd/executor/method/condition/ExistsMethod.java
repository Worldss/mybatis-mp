package db.sql.api.cmd.executor.method.condition;


import db.sql.api.cmd.executor.IQuery;

public interface ExistsMethod<RV> {

    default RV exists(IQuery query) {
        return this.exists(true, query);
    }

    RV exists(boolean when, IQuery query);

    default RV notExists(IQuery query) {
        return this.notExists(true, query);
    }

    RV notExists(boolean when, IQuery query);
}
