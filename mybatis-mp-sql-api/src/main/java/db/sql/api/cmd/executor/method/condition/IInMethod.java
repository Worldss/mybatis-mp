package db.sql.api.cmd.executor.method.condition;

import db.sql.api.cmd.executor.IQuery;
import db.sql.api.cmd.executor.method.condition.compare.IInGetterCompare;

import java.io.Serializable;
import java.util.List;

public interface IInMethod<RV, COLUMN> extends IInGetterCompare<RV> {

    default RV in(COLUMN column, IQuery query) {
        return this.in(true, column, query);
    }

    RV in(boolean when, COLUMN column, IQuery query);

    default RV in(COLUMN column, Serializable... values) {
        return this.in(true, column, values);
    }

    RV in(boolean when, COLUMN column, Serializable... values);

    default RV in(COLUMN column, List<Serializable> values) {
        return this.in(true, column, values);
    }

    RV in(boolean when, COLUMN column, List<Serializable> values);


}
