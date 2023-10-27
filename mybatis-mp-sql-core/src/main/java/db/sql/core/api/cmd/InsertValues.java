package db.sql.core.api.cmd;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.core.api.tookit.SqlConst;

import java.util.ArrayList;
import java.util.List;

public class InsertValues implements db.sql.api.InsertValues<Value>, Cmd {

    protected List<List<Value>> values;

    @Override
    public List<List<Value>> getValues() {
        return values;
    }

    public InsertValues add(List<Value> values) {
        if (this.values == null) {
            this.values = new ArrayList<>();
        }
        this.values.add(values);
        return this;
    }

    @Override
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = sqlBuilder.append(SqlConst.VALUES);
        boolean isFirstLine = true;
        for (List<Value> values : this.values) {
            if (!isFirstLine) {
                sqlBuilder = sqlBuilder.append(SqlConst.DELIMITER);

            }
            sqlBuilder.append(SqlConst.BLANK).append(SqlConst.BRACKET_LEFT);
            boolean isFirst = true;
            for (Value value : values) {
                if (!isFirst) {
                    sqlBuilder = sqlBuilder.append(SqlConst.DELIMITER);
                }
                sqlBuilder = value.sql(this, context, sqlBuilder);
                isFirst = false;
            }
            sqlBuilder.append(SqlConst.BRACKET_RIGHT).append(SqlConst.BLANK);
            isFirstLine = false;
        }
        return sqlBuilder;
    }
}
