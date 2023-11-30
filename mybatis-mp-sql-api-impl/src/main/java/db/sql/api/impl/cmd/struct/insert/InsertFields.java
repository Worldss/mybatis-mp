package db.sql.api.impl.cmd.struct.insert;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.cmd.struct.insert.IInsertFields;
import db.sql.api.impl.cmd.basic.TableField;
import db.sql.api.impl.tookit.Lists;
import db.sql.api.impl.tookit.SqlConst;
import db.sql.api.tookit.CmdUtils;

import java.util.ArrayList;
import java.util.List;

public class InsertFields implements IInsertFields<TableField> {

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
    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
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
