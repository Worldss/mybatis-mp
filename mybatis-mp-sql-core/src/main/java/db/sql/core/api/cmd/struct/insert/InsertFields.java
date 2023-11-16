package db.sql.core.api.cmd.struct.insert;

import db.sql.api.cmd.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.tookit.CmdUtils;
import db.sql.core.api.cmd.basic.TableField;
import db.sql.core.api.tookit.Lists;
import db.sql.core.api.tookit.SqlConst;

import java.util.ArrayList;
import java.util.List;

public class InsertFields implements db.sql.api.cmd.struct.insert.InsertFields<TableField>, Cmd {

    protected List<TableField> tableFields;

    public InsertFields filed(TableField field) {
        if (tableFields == null) {
            this.tableFields = new ArrayList<>();
        }
        this.tableFields.add(field);
        return this;
    }

    public InsertFields filed(TableField... fields) {
        if (tableFields == null) {
            this.tableFields = new ArrayList<>();
        }
        Lists.merge(this.tableFields, fields);
        return this;
    }

    public InsertFields filed(List<TableField> fields) {
        if (tableFields == null) {
            this.tableFields = new ArrayList<>();
        }
        this.tableFields.addAll(fields);
        return this;
    }

    @Override
    public List<TableField> getFields() {
        return this.tableFields;
    }

    @Override
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = sqlBuilder.append(SqlConst.BLANK).append(SqlConst.BRACKET_LEFT);
        boolean isFirst = true;
        for (TableField tableField : this.tableFields) {
            if (!isFirst) {
                sqlBuilder = sqlBuilder.append(SqlConst.DELIMITER);
            }
            sqlBuilder = sqlBuilder.append(tableField.getName());
            isFirst = false;
        }
        sqlBuilder = sqlBuilder.append(SqlConst.BRACKET_RIGHT).append(SqlConst.BLANK);
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.tableFields);
    }
}
