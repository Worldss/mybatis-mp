package db.sql.core.api.cmd;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.core.api.tookit.CmdJoins;
import db.sql.core.api.tookit.Lists;
import db.sql.core.api.tookit.SqlConst;

import java.util.ArrayList;
import java.util.List;

public class Select implements db.sql.api.Select<Select, Cmd>, Cmd {

    private final List<Cmd> selectFields = new ArrayList<>();

    @Override
    public List<Cmd> getSelectFiled() {
        return selectFields;
    }

    @Override
    public Select select(Cmd field) {
        selectFields.add(field);
        return this;
    }

    @Override
    public Select select(Cmd... fields) {
        Lists.merge(this.selectFields, fields);
        return this;
    }

    @Override
    public Select select(List<Cmd> fields) {
        this.selectFields.addAll(fields);
        return this;
    }

    @Override
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = sqlBuilder.append(SqlConst.SELECT);
        sqlBuilder = CmdJoins.join(this, context, sqlBuilder, this.getSelectFiled(), SqlConst.DELIMITER);
        return sqlBuilder;
    }
}