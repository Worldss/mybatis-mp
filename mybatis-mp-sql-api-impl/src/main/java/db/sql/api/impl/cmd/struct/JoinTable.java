package db.sql.api.impl.cmd.struct;

import db.sql.api.cmd.JoinMode;
import db.sql.api.impl.cmd.basic.Table;

import java.util.function.Function;

public class JoinTable extends Join<JoinTable, Table, OnTable> {

    public JoinTable(JoinMode mode, Table mainTable, Table secondTable, Function<JoinTable, OnTable> onFunction) {
        super(mode, mainTable, secondTable, onFunction);
    }
}
