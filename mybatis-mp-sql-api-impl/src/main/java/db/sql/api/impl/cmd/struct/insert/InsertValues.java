package db.sql.api.impl.cmd.struct.insert;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.impl.tookit.SqlConst;
import db.sql.api.tookit.CmdUtils;

import java.util.ArrayList;
import java.util.List;

public class InsertValues implements db.sql.api.cmd.struct.insert.InsertValues<Cmd>, Cmd {

    protected List<List<Cmd>> values;

    @Override
    public List<List<Cmd>> getValues() {
        return values;
    }

    public InsertValues add(List<Cmd> values) {
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
        for (List<Cmd> values : this.values) {
            if (!isFirstLine) {
                sqlBuilder = sqlBuilder.append(SqlConst.DELIMITER);

            }
            sqlBuilder.append(SqlConst.BLANK).append(SqlConst.BRACKET_LEFT);
            boolean isFirst = true;
            for (Cmd value : values) {
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

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.values);
    }
}
