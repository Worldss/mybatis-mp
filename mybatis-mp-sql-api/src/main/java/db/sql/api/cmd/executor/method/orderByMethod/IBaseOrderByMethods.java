package db.sql.api.cmd.executor.method.orderByMethod;

import db.sql.api.Getter;
import db.sql.api.cmd.basic.IOrderByDirection;
import db.sql.api.cmd.executor.method.IOrderByMethod;

public interface IBaseOrderByMethods {

    IOrderByDirection defaultOrderByDirection();

}
